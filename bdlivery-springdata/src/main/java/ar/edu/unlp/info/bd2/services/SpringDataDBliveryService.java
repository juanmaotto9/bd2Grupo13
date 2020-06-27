package ar.edu.unlp.info.bd2.services;


import ar.edu.unlp.info.bd2.model.*;
import ar.edu.unlp.info.bd2.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

public class SpringDataDBliveryService implements DBliveryService {
	

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    SupplierRepository supplierRepository;

    @Autowired
    OrderRepository orderRepository;
    
    @Autowired
    StatusRepository statusRepository;
    
    
    @Transactional	
    @Override
    public Product createProduct(String name, Float price, Float weight, Supplier supplier) {
        Product prod = new Product(name, price, weight, supplier);
        return productRepository.save(prod);
    }

    @Transactional	
    @Override
    public Product createProduct(String name, Float price, Float weight, Supplier supplier, Date date) {
        Product prod = new Product(name, price, weight, supplier, date);
        return productRepository.save(prod);
    }
    
    @Transactional	
    @Override
    public Supplier createSupplier(String name, String cuil, String address, Float coordX, Float coordY) {
        Supplier supp = new Supplier(name, cuil, address, coordX, coordY);
        return supplierRepository.save(supp);
    }
    
    @Transactional	
    @Override
    public User createUser(String email, String password, String username, String name, Date dateOfBirth) {
        User user = new User(username, name, email, password, dateOfBirth);
        return userRepository.save(user);
    }

    
    @Transactional	
    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    
    @Transactional	
    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    @Transactional	
    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    @Transactional	
    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByNameContaining(name);
    }
    
    @Transactional
	@Override
	public Product getMaxWeigth() {
    	return this.productRepository.findMaxWeight();
	}

	@Transactional	
	@Override
	public List<Order> getAllOrdersMadeByUser(String username) {
		Optional<User> user = this.getUserByUsername(username);
		return orderRepository.findByUser(user.get());
	}
	
	@Transactional
	@Override
	public List<Order> getPendingOrders() {
		return orderRepository.findPendingOrders();
	}
	
	@Transactional
	@Override
	public List<Order> getSentOrders() {
		return orderRepository.findSentOrders();
	}
	
	@Transactional
	@Override
	public List<Order> getDeliveredOrdersInPeriod(Date startDate, Date endDate) {
		return orderRepository.findDeliveredOrdersInPeriod(startDate, endDate);
	}

	@Transactional
	@Override
	public List<Order> getDeliveredOrdersForUser(String username) {
		return this.orderRepository.findDeliveredOrdersForUser(username);
	}

	
	@Transactional
	@Override
	public List<Product> getProductsOnePrice() {
		return this.productRepository.findProductWithOnePrice();
	}

	@Transactional
	@Override
	public List<Product> getSoldProductsOn(Date day) {
		return this.productRepository.findSoldProductsOn(day);
	}
	
	@Transactional	
	@Override
	public Product updateProductPrice(Long id, Float price, Date startDate) throws DBliveryException {
		Optional<Product> optProd = this.productRepository.findById(id);
        if (optProd.isPresent()){
        	Product prod = optProd.get();
        	prod.updateProductPrice(price, startDate);
        	this.productRepository.save(prod);
        	return prod;
        } else {
        	throw new DBliveryException("The product don't exist");
        }
	}
	
	@Transactional	
	@Override
	public Optional<Order> getOrderById(Long id) {
		return orderRepository.findById(id);
	}

	@Transactional	
	@Override
	public Order createOrder(Date dateOfOrder, String address, Float coordX, Float coordY, User client) {
		Order orden = new Order(dateOfOrder, address, coordX, coordY, client);
		return this.orderRepository.save(orden);
	}

	@Transactional	
	@Override
	public Order addProduct(Long order, Long quantity, Product product) throws DBliveryException {
		Optional<Order> orden = this.getOrderById(order);
    	if (orden.isPresent()) {
    		Order ord= orden.get();
    		ord.addProductOrder(quantity, product); 
    		Float priceAct = product.findPriceAtPeriod(ord.getDateOfOrder());
    		ord.addAmountProduct(priceAct, quantity);
    		return this.orderRepository.save(ord);
    	}else throw new DBliveryException("Orden no encontrada");

	}
	
	@Transactional	
	@Override
	public Order deliverOrder(Long order, User deliveryUser) throws DBliveryException {
		if(this.canDeliver(order)) {
			Optional<Order> o = this.getOrderById(order);
			if (o.isPresent()) {
				Order orden= o.get();
				this.orderRepository.save(orden.deliverOrder(deliveryUser));
				return orden;
			}else throw new DBliveryException("The order can't be delivered");
		}else throw new DBliveryException("The order can't be delivered");
	}

	@Transactional	
	@Override
	public Order deliverOrder(Long order, User deliveryUser, Date date) throws DBliveryException {
		if(this.canDeliver(order)) {
			Optional<Order> o = this.getOrderById(order);
			if (o.isPresent()) {
				Order orden= o.get();
				this.orderRepository.save(orden.deliverOrder(deliveryUser, date));
				return orden;
			}else throw new DBliveryException("The order can't be delivered");
		}else throw new DBliveryException("The order can't be delivered");
	}
	
	@Transactional	
	@Override
	public Order cancelOrder(Long order) throws DBliveryException {
		if(this.canCancel(order)) {
			Optional<Order> o = this.getOrderById(order);
			if (o.isPresent()) {
				Order orden = o.get();
				orden.changeStateToCanceled();
				this.orderRepository.save(orden);
				return orden;
			}else throw new DBliveryException("The order can't be cancelled");
		}else throw new DBliveryException("The order can't be cancelled");
	}

	@Transactional
	@Override
	public Order cancelOrder(Long order, Date date) throws DBliveryException {
		if(this.canCancel(order)) {
			Optional<Order> o = this.getOrderById(order);
			if (o.isPresent()) {
				Order orden = o.get();
				orden.changeStateToCanceled(date);
				this.orderRepository.save(orden);
				return orden;
			}else throw new DBliveryException("The order can't be cancelled");
		}else throw new DBliveryException("The order can't be cancelled");
	}

	@Transactional	
	@Override
	public Order finishOrder(Long order) throws DBliveryException {
		if(this.canFinish(order)) {
			Optional<Order> o = this.getOrderById(order);
			if (o.isPresent()) {
	    		Order orden= o.get();
	    		this.orderRepository.save(orden.changeStateToReceived());
			return orden;
			}else throw new DBliveryException("The order can't be finished");
		}else throw new DBliveryException("The order can't be finished");
	}

	@Transactional	
	@Override
	public Order finishOrder(Long order, Date date) throws DBliveryException {
		if(this.canFinish(order)) {
			Optional<Order> o = this.getOrderById(order);
			if (o.isPresent()) {
	    		Order orden= o.get();
	    		this.orderRepository.save(orden.changeStateToReceived(date));
	    		return orden;
			}else throw new DBliveryException("The order can't be finished");
		}else throw new DBliveryException("The order can't be finished");
	}

	@Transactional	
	@Override
	public boolean canCancel(Long order) throws DBliveryException {
		try {
			Optional<Order> o = this.getOrderById(order); 
			if (o.isPresent()) {
	    		Order orden= o.get();
	    		return orden.isPending();
			}else  throw new DBliveryException("Order not found");
		} catch (Exception e) {
			return false;
		}
	}

	@Transactional	
	@Override
	public boolean canFinish(Long id) throws DBliveryException {
		try {
			Optional<Order> order = this.getOrderById(id);
			if (order.isPresent()) {
	    		Order ord= order.get();
				if (ord.getDeliveryUser() == null) throw new DBliveryException("The order can't be finished--DeliveryUser");
				return !ord.isCancel();
			}else  throw new DBliveryException("Order not found");
		} catch (Exception e) {
			return false;
		}
	}

	@Transactional	
	@Override
	public boolean canDeliver(Long order) throws DBliveryException {
		try {
			Optional<Order> o = this.getOrderById(order); 
			if (o.isPresent()) {
	    		Order orden= o.get();
				if (orden.getProducts().isEmpty()) throw new Exception("Order without products");
				return orden.isPending();
			}else  throw new DBliveryException("Order not found"); 
		} catch (Exception e) {
			return false;
		}
	}

	@Transactional
	@Override
	public Status getActualStatus(Long order) {
		try {
			Optional <Order> o = this.getOrderById(order);
			if(o.isPresent()) {
				Order orden = o.get();
				return orden.getMyState();
			}else throw new DBliveryException("The order can't be delivered");
		}catch (Exception e) {
			return null;
		}
	}

}
