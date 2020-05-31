package ar.edu.unlp.info.bd2.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;

import ar.edu.unlp.info.bd2.model.*;
import ar.edu.unlp.info.bd2.repositories.*;


public class DBliveryServiceImpl implements DBliveryService {

	private DBliveryMongoRepository repository;
	
	public DBliveryServiceImpl(DBliveryMongoRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public Product createProduct(String name, Float price, Float weight, Supplier supplier) {
		Product producto = new Product(name, price, weight, supplier.getObjectId());
		this.repository.persistProduct(producto);
		return producto;		
	}
	
	@Override
	public Supplier createSupplier(String name, String cuil, String address, Float coordX, Float coordY) {
		Supplier supplier = new Supplier(name, cuil, address, coordX, coordY);
        repository.persistSupplier(supplier);
        return supplier;
	}
	
	@Override
	public Product updateProductPrice(ObjectId id, Float price, Date startDate, Product p1) throws DBliveryException{
        Price newPrice= new Price(price, startDate, id);
    	repository.UpdateProductPrice(id, newPrice);
    	return p1;  //esto no anda porq no se devolver el p2 pero la consulta esta bien creo
	}

	
	@Override
	public User createUser(String email, String password, String username, String name, Date dateOfBirth) {
		User usuario = new User(username, name, email, password, dateOfBirth);
		this.repository.createUser(usuario);
		return usuario;
	}

	/*
	@Override
	Product createProduct(String name, Float price, Float weight, Supplier supplier, Date date) {
		return null;
	}
	



	@Override
	public Optional<User> getUserById(ObjectId id){
		return null;
	}

	@Override
	public Optional<User> getUserByEmail(String email){
		return null;
	}

	@Override
	public Optional<User> getUserByUsername(String username){
		return null;
	}

	@Override
	public Optional<Order> getOrderById(ObjectId id){
		return null;
	}

	@Override
	public Order createOrder(Date dateOfOrder, String address, Float coordX, Float coordY,User client) {
		return null;
	}

	
	@Override
	public Order addProduct (ObjectId order,Long quantity, Product product )throws DBliveryException{
		return null;
	}

	@Override
	public Order deliverOrder(ObjectId order, User deliveryUser) throws DBliveryException{
		return null;
	}
	
	@Override
	public Order deliverOrder(ObjectId order, User deliveryUser, Date date) throws DBliveryException{
		return null;
	}

	@Override
	public Order cancelOrder(ObjectId order) throws DBliveryException{
		return null;
	}
	
	@Override
	public Order cancelOrder(ObjectId order, Date date) throws DBliveryException{
		return null;
	}

	
	@Override
	public Order finishOrder(ObjectId order) throws DBliveryException{
		return null;
	}
	@Override
	public Order finishOrder(ObjectId order, Date date) throws DBliveryException{
		return null;
	}


	@Override
	public boolean canCancel(ObjectId order) throws DBliveryException{
		return false;
	}


	@Override
	public boolean canFinish(ObjectId id) throws DBliveryException{
		return false;
	}


	@Override
	public boolean canDeliver(ObjectId order) throws DBliveryException{
		return false;
	}


	@Override
	public Status getActualStatus(ObjectId order) {
		return null;
	}


	@Override
	public List<Product> getProductsByName(String name){
		return null;
	}
	*/
}
