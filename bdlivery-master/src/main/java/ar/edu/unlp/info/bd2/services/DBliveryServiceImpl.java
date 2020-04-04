package ar.edu.unlp.info.bd2.services;

import java.util.Date;
import java.util.Optional;

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
		
	}
	

	
	
}
