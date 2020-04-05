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
		this.repository.persistProduct(producto);
		return producto;
	}
	
	public Supplier createSupplier(String name, String cuil, String address, Float coordX, Float coordY) {
		Supplier supplier = new Supplier(name, cuil, address, coordX, coordY);
		this.repository.persistSupplier(supplier);
		return supplier;
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
	/*
	public Order deliverOrder(Long id, User deliveryUser) throws DBliveryException {
		try {
			Order order = this.repository.findOrderById(id);
			if (order == null) throw new Exception("Order not found");
			if (!order.isCancel() ) throw new Exception("Order can not sended");
			//if (!order.getOrderLines().isEmpty()) throw new Exception("Order can not sended");			;
			return this.repository.updateOrder(order.deliverOrder(deliveryUser));

		} catch (Exception e) { return null; }
	}
	
	public Order cancelOrder(Long order) throws DBliveryException {
		try {
			Order anOrder = repository.findOrderById(order);
			if (anOrder == null) throw new Exception("Order not found");
			if (!anOrder.canCancel()) throw new Exception("Order can not canceled");
			anOrder.changeStateCanceled();
			return this.repository.updateOrder(anOrder);
		} catch (Exception e) { return null; }
	}
	
	
	public Order finishOrder(Long id) throws DBliveryException {
		try {
			Order order = this.repository.findOrderById(id);
			if (order == null) throw new Exception("Order not found");
			if (!order.canFinish()) throw new Exception("Order can not finished");
			return this.repository.updateOrder(order.changeStateDelivered());
		} catch (Exception e) {
			return null;
		}
	}
	
	
	public boolean canCancel(Long id) throws DBliveryException {
		try {
			Order order = this.repository.findOrderById(id);
			if (order == null) throw new Exception("Order not found");
			return order.canCancel();
		} catch (Exception e) {
			return false;
		}
	}
	

	public boolean canFinish(Long id) throws DBliveryException {
		try {
			Order order = this.repository.findOrderById(id);
			if (order == null) throw new Exception("Order not found");
			return order.canFinish();
		} catch (Exception e) {
			return false;
		}
	}
	
	
	public boolean canDeliver(Long id) throws DBliveryException {
		try {
			Order order = this.repository.findOrderById(id);
			if (order == null) throw new Exception("Order not found");
			if (!order.getOrderLines().isEmpty()) throw new Exception("Order can not sended");
			return order.canSended();
		} catch (Exception e) {
			return false;
		}
	}
	
	
	public OrderState getActualStatus(Long id) {
		try {
			Order order = this.repository.findOrderById(id);
			return order.getOrderState();
		} catch (Exception e) {
			return null;
		}
	}
	*/
	
	public List <Product> getProductByName(String name) {
		return this.repository.findProductByName(name);
	}
	
	
}
