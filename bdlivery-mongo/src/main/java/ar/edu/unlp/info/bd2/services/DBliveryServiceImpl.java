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
	public Product updateProductPrice(ObjectId id, Float price, Date startDate) throws DBliveryException{
        Optional<Product> optProd = repository.getProductById(id);
        if (optProd.isPresent()){
        	Product prod = optProd.get();
        	repository.updateProduct(prod.updateProductPriceProd(price, startDate));
        	return prod;
        } else {
        	throw new DBliveryException("The product don't exist");
        }
	}

	
	@Override
	public User createUser(String email, String password, String username, String name, Date dateOfBirth) {
		User usuario = new User(username, name, email, password, dateOfBirth);
		this.repository.createUser(usuario);
		return usuario;
	}

	@Override
	public Optional<User> getUserById(ObjectId id){
		return this.repository.getUserById(id);
	}

	@Override
	public Optional<User> getUserByEmail(String email){
		return this.repository.getUserByEmail(email);
	}

	@Override
	public Optional<User> getUserByUsername(String username){
		return this.repository.getUserByUsername(username);
	}
	
	/* --------------------- */
	

	@Override
	public List<Product> getProductsByName(String name){
		return this.repository.getProductsByName(name);
	}
	
	@Override
	public Order createOrder(Date dateOfOrder, String address, Float coordX, Float coordY,User client) {
    	Order orden = new Order(dateOfOrder, address, coordX, coordY, client);
        this.repository.persistOrder(orden);
        return orden;
	}
	
/*


	@Override
	public Optional<Order> getOrderById(ObjectId id){
		return this.repository.getOrderById(id);
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
	*/
}
