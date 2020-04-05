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
	public Product createProduct(String name, Float price, Float weight, Supplier supplier) {
		Product producto = new Product(name, price, weight, supplier);
		return this.repository.persistProduct(producto);
	}
	
	public Supplier createSupplier(String name, String cuil, String address, Float coordX, Float coordY) {
		Supplier supplier = new Supplier(name, cuil, address, coordX, coordY);
		return this.repository.persistSupplier(supplier);
	}
	
	public User createUser(String email, String password, String username, String name, Date dateOfBirth) {
		User user = new User(username, name, email, password, dateOfBirth);
		this.repository.persistUser(user);
		return user;
	}

	
	public Product updateProductPrice(Long id, Float price, Date startDate) throws DBliveryException{
		try {
			Product producto = this.repository.findProductById(id);
			if(producto == null) { 
				throw new DBliveryException("producto no encontrado");
			}
			producto.updateProductPrice(price, startDate);
			return this.repository.updateProductPrice(producto);
		}catch(Exception e) {
			return null;
		}
	}
	
	public Optional<User> getUserById(Long id){
		return Optional.ofNullable(this.repository.findUserById(id));
	}
	
	public Optional<User> getUserByEmail(String email){
		return Optional.ofNullable(this.repository.findUserByEmail(email));
	}
	
	public Optional<User> getUserByUsername(String username){
		return Optional.ofNullable(this.repository.findUserByUsername(username));
	}
	
	public List <Product> getProductByName(String name) {
		return this.repository.findProductByName(name);
	}
	
	public Optional<Product> getProductById(Long id){
		return Optional.ofNullable(this.repository.findProductById(id));
	}
	
	public Optional<Order> getOrderById(Long id){
		return Optional.ofNullable(this.repository.findOrderById(id));
	}
	
	public Order createOrder(Date dateOfOrder, String address, Float coordX, Float coordY,User client) {
		Order orden = new Order(dateOfOrder, address, coordX, coordY, client);
		this.repository.persistOrder(orden);
		return orden;
		
	}
	
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

	public boolean canDeliver(Long id) throws DBliveryException {
		try {
			Order order = this.repository.findOrderById(id);
			if (order == null) throw new Exception("Orden no encontrada");
			if (!order.getProducts().isEmpty()) throw new Exception("La orden no puede ser enviada por falta de productos");
			return order.isPending();
		} catch (Exception e) {
			return false;
		}
	}
	
	//acá cambie en el segundo if por isPending(). 
	public Order deliverOrder(Long id, User deliveryUser) throws DBliveryException {
		try {
			Order order = this.repository.findOrderById(id);
			if (order == null) throw new Exception("Orden no encontrada");
			if (!order.isPending() ) throw new Exception("La orden no se puede enviar");
			if (!order.getProducts().isEmpty()) throw new Exception("La orden no puede ser enviada por falta de productos");			;
			return this.repository.updateOrder(order.deliverOrder(deliveryUser));

		} catch (Exception e) { return null; }
	}

	//acá cambie en el return por isPending(). 
	public boolean canCancel(Long id) throws DBliveryException {
		try {
			Order order = this.repository.findOrderById(id);
			if (order == null) throw new Exception("Orden no encontrada");
			return order.isPending();
		} catch (Exception e) {
			return false;
		}
	}
	
	
	//acá cambie en el segundo if por isPending().
	public Order cancelOrder(Long order) throws DBliveryException {
		try {
			Order anOrder = repository.findOrderById(order);
			if (anOrder == null) throw new Exception("Orden no encontrada");
			if (!anOrder.isPending()) throw new Exception("La orden no puede ser cancelada");
			anOrder.changeStateToCanceled();
			return this.repository.updateOrder(anOrder);
		} catch (Exception e) { return null; }
	}
	
	public Status getActualStatus(Long id) {
		try {
			Order order = this.repository.findOrderById(id);
			return order.getMyState();
		} catch (Exception e) {
			return null;
		}
	}
	
	public Order finishOrder(Long id) throws DBliveryException {
		try {
			Order order = this.repository.findOrderById(id);
			if (order == null) throw new Exception("Orden no encontrada");
			if (!order.isCancel()) throw new Exception("La orden no puede finalizarse");
			return this.repository.updateOrder(order.changeStateToReceived());
		} catch (Exception e) {
			return null;
		}
	}
	
	//Por lo que entendí, si el pedido no está cancelado entonces puede pasar por cualquier state
	public boolean canFinish(Long id) throws DBliveryException {
		try {
			Order order = this.repository.findOrderById(id);
			if (order == null) throw new Exception("Order not found");
			return !order.isCancel();
		} catch (Exception e) {
			return false;
		}
	}
	//FIN :)
}
