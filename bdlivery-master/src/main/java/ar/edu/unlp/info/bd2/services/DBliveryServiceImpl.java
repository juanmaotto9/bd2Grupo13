package ar.edu.unlp.info.bd2.services;

import java.util.Date;
import java.util.Optional;
import java.util.List;

import ar.edu.unlp.info.bd2.model.*;
import ar.edu.unlp.info.bd2.repositories.*;

public class DBliveryServiceImpl implements DBliveryService {
	
	private DBliveryRepository repository;
	
	public DBliveryServiceImpl(DBliveryRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public Product createProduct(String name, Float price, Float weight, Supplier supplier) {
		Product producto = new Product(name, price, weight, supplier);
		return this.repository.persistProduct(producto);
	}
	
	@Override
	public Product createProduct(String name, Float price, Float weight, Supplier supplier, Date date) {
		Product producto = new Product(name, price, weight, supplier, date);
		return this.repository.persistProduct(producto);
	}
	
	@Override
	public Supplier createSupplier(String name, String cuil, String address, Float coordX, Float coordY) {
		Supplier supplier = new Supplier(name, cuil, address, coordX, coordY);
		return this.repository.persistSupplier(supplier);
	}
	
	@Override
	public User createUser(String email, String password, String username, String name, Date dateOfBirth) {
		User user = new User(username, name, email, password, dateOfBirth);
		this.repository.persistUser(user);
		return user;
	}

	@Override
	public Product updateProductPrice(Long id, Float price, Date startDate) throws DBliveryException{
		Optional<Product> optProduct = this.getProductById(id);
    	if (optProduct.isPresent()) {
    		Product product = optProduct.get();
    		product.updateProductPrice(price, startDate); // consultar
    		return this.repository.updateProductPrice(product);
		}else throw new DBliveryException("producto no encontrado");		
	}
	
	@Override
	public Optional<User> getUserById(Long id){
		return Optional.ofNullable(this.repository.findUserById(id));
	}
	
	@Override
	public Optional<User> getUserByEmail(String email){
		return Optional.ofNullable(this.repository.findUserByEmail(email));
	}
	
	@Override
	public Optional<User> getUserByUsername(String username){
		return Optional.ofNullable(this.repository.findUserByUsername(username));
	}
	
	@Override
	public List <Product> getProductByName(String name) {
		return this.repository.findProductByName(name);
	}
	
	@Override
	public Optional<Product> getProductById(Long id){
		return Optional.ofNullable(this.repository.findProductById(id));
	}
	
	@Override
	public Optional<Order> getOrderById(Long id){
		return Optional.ofNullable(this.repository.findOrderById(id));
	}
	
	@Override
	public Order createOrder(Date dateOfOrder, String address, Float coordX, Float coordY,User client) {
		Order orden = new Order(dateOfOrder, address, coordX, coordY, client);
		this.repository.persistOrder(orden);
		return orden;
	}
	
	@Override
	public Order addProduct(Long order,Long quantity, Product product )throws DBliveryException{
		try {
			Order orden = this.repository.findOrderById(order);
			if(orden == null) {
				throw new DBliveryException("Orden no encontrada");
			}
			orden.addProductOrder(quantity, product); 
			return this.repository.updateOrder(orden);
		}catch(Exception e) {
			return null;
		}	
	}

	@Override
	public boolean canDeliver(Long id) throws DBliveryException {
		try {
			Order order = this.repository.findOrderById(id);
			if (order == null) throw new Exception("Orden no encontrada");
			if (order.getProducts().isEmpty()) throw new Exception("La orden no puede ser enviada por falta de productos");
			return order.isPending();
		} catch (Exception e) {
			return false;
		}
	}
	
	@Override
	public Order deliverOrder(Long id, User deliveryUser) throws DBliveryException {
		if(this.canDeliver(id)) {
			Order order = this.repository.findOrderById(id);
			return this.repository.updateOrder(order.deliverOrder(deliveryUser));
		}else throw new DBliveryException("The order can't be delivered");
	}
	
	public Order deliverOrder(Long id, User deliveryUser, Date date) throws DBliveryException{
		if(this.canDeliver(id)) {
			Order order = this.repository.findOrderById(id);
			order.deliverOrder(deliveryUser, date);
			return this.repository.updateOrder(order);
		}else throw new DBliveryException("The order can't be delivered");
	}

	@Override
	public boolean canCancel(Long id) throws DBliveryException {
		try {
			Order order = this.repository.findOrderById(id);
			if (order == null) throw new Exception("Orden no encontrada");
			return order.isPending();
		} catch (Exception e) {
			return false;
		}
	}
	
	@Override
	public Order cancelOrder(Long order) throws DBliveryException {
		if(this.canCancel(order)) {
			Order anOrder = repository.findOrderById(order);
			anOrder.changeStateToCanceled();
			return this.repository.updateOrder(anOrder);
		}else throw new DBliveryException("The order can't be cancelled");
	}
	
	@Override
	public Order cancelOrder(Long order, Date date) throws DBliveryException{
		if(this.canCancel(order)) {
			Order anOrder = repository.findOrderById(order);
			anOrder.changeStateToCanceled(date);
			return this.repository.updateOrder(anOrder);
		}else throw new DBliveryException("The order can't be cancelled");
	}
	
	@Override
	public Status getActualStatus(Long id) {
		try {
			Order order = this.repository.findOrderById(id);
			return order.getMyState();
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public Order finishOrder(Long id) throws DBliveryException {
		if(this.canFinish(id)) {
			Order order = this.repository.findOrderById(id);
			return this.repository.updateOrder(order.changeStateToReceived());
		}else throw new DBliveryException("The order can't be finished");
	}
	
	@Override
	public Order finishOrder(Long id, Date date) throws DBliveryException{
		if(this.canFinish(id)) {
			Order order = this.repository.findOrderById(id);
			order.changeStateToReceived(date);
			return this.repository.updateOrder(order);
		}else throw new DBliveryException("The order can't be finished");
	}
	
	@Override
	public boolean canFinish(Long id) throws DBliveryException {
		try {
			Order order = this.repository.findOrderById(id);
			if (order == null) throw new DBliveryException("Order not found");
			if (order.getDeliveryUser() == null) throw new DBliveryException("The order can't be finished");
			return !order.isCancel();
		} catch (Exception e) {
			return false;
		}
	}
	//FIN :)
}
