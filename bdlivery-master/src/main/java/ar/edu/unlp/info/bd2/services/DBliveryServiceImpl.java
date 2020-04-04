package ar.edu.unlp.info.bd2.services;

import java.util.Date;

import ar.edu.unlp.info.bd2.model.Product;
import ar.edu.unlp.info.bd2.model.Supplier;
import ar.edu.unlp.info.bd2.model.User;
import ar.edu.unlp.info.bd2.repositories.*;

public class DBliveryServiceImpl implements DBliveryService {
	
	private DBliveryRepository repository;
	
	public DBliveryServiceImpl(DBliveryRepository repository) {
		this.repository = repository;
	}
	public Product createProduct(String name, Float price, Float weight, Supplier supplier) {
		Product producto = new Product(name, price, weight, supplier);
		return producto;
	}
	
	public Supplier createSupplier(String name, String cuil, String address, Float coordX, Float coordY) {
		Supplier supplier = new Supplier(name, cuil, address, coordX, coordY);
		return supplier;
	}
	
	public User createUser(String email, String password, String username, String name, Date dateOfBirth) {
		User user = new User(username, name, email, password, dateOfBirth);
		return user;
	}
	
	/**

	
	Product updateProductPrice(Long id, Float price, Date startDate) throws DBliveryException{
		try {
			
		}catch{
			
		}
		
	}
	
	@Override 
	public Optional<User> getUserById(Long id) {
	    return Optional.of(repository.getUserBy("id", id).get(0));
	}
 */
	
	
}
