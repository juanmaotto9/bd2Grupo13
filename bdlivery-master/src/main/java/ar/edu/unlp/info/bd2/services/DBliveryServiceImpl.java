package ar.edu.unlp.info.bd2.services;

import java.util.Date;
import java.util.Optional;
import java.util.List;

import ar.edu.unlp.info.bd2.model.*;
import ar.edu.unlp.info.bd2.repositories.*;

import org.springframework.transaction.annotation.Transactional;


public class DBliveryServiceImpl implements DBliveryService {
	
	private DBliveryRepository repository;
	
	public DBliveryServiceImpl(DBliveryRepository repository) {
		this.repository = repository;
	}
	@Transactional
	@Override
	public Product createProduct(String name, Float price, Float weight, Supplier supplier) {
		Product producto = new Product(name, price, weight, supplier);
		return this.repository.persistProduct(producto);
	}
	@Transactional
	@Override
	public Product createProduct(String name, Float price, Float weight, Supplier supplier, Date date) {
		Product producto = new Product(name, price, weight, supplier, date);
		return this.repository.persistProduct(producto);
	}
	@Transactional
	@Override
	public Supplier createSupplier(String name, String cuil, String address, Float coordX, Float coordY) {
		Supplier supplier = new Supplier(name, cuil, address, coordX, coordY);
		return this.repository.persistSupplier(supplier);
	}
	@Transactional
	@Override
	public User createUser(String email, String password, String username, String name, Date dateOfBirth) {
		User user = new User(username, name, email, password, dateOfBirth);
		this.repository.persistUser(user);
		return user;
	}
	@Transactional
	@Override
	public Product updateProductPrice(Long id, Float price, Date startDate) throws DBliveryException{
		Optional<Product> optProduct = this.getProductById(id);
    	if (optProduct.isPresent()) {
    		Product product = optProduct.get();
    		product.updateProductPrice(price, startDate); // consultar
    		return this.repository.updateProductPrice(product);
		}else throw new DBliveryException("producto no encontrado");		
	}
	@Transactional
	@Override
	public Optional<User> getUserById(Long id){
		return Optional.ofNullable(this.repository.findUserById(id));
	}
	@Transactional
	@Override
	public Optional<User> getUserByEmail(String email){
		return Optional.ofNullable(this.repository.findUserByEmail(email));
	}
	@Transactional
	@Override
	public Optional<User> getUserByUsername(String username){
		return Optional.ofNullable(this.repository.findUserByUsername(username));
	}
	@Transactional
	@Override
	public List <Product> getProductByName(String name) {
		return this.repository.findProductByName(name);
	}
	@Transactional
	@Override
	public Optional<Product> getProductById(Long id){
		return Optional.ofNullable(this.repository.findProductById(id));
	}
	@Transactional
	@Override
	public Optional<Order> getOrderById(Long id){
		return Optional.ofNullable(this.repository.findOrderById(id));
	}
	@Transactional
	@Override
	public Order createOrder(Date dateOfOrder, String address, Float coordX, Float coordY,User client) {
		Order orden = new Order(dateOfOrder, address, coordX, coordY, client);
		this.repository.persistOrder(orden);
		return orden;
	}
	@Transactional
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
	@Transactional
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
	@Transactional
	@Override
	public Order deliverOrder(Long id, User deliveryUser) throws DBliveryException {
		if(this.canDeliver(id)) {
			Order order = this.repository.findOrderById(id);
			return this.repository.updateOrder(order.deliverOrder(deliveryUser));
		}else throw new DBliveryException("The order can't be delivered");
	}
	@Transactional
	@Override
	public Order deliverOrder(Long id, User deliveryUser, Date date) throws DBliveryException{
		if(this.canDeliver(id)) {
			Order order = this.repository.findOrderById(id);
			return this.repository.updateOrder(order.deliverOrder(deliveryUser, date));
		}else throw new DBliveryException("The order can't be delivered -deliver order");
	}
	@Transactional
	@Override
	public boolean canCancel(Long id) throws DBliveryException {
		try {
			Order order = this.repository.findOrderById(id);
			if (order == null) throw new Exception("Orden no encontrada");
			//
			return order.isPending();
		} catch (Exception e) {
			return false;
		}
	}
	@Transactional
	@Override
	public Order cancelOrder(Long order) throws DBliveryException {
		if(this.canCancel(order)) {
			Order anOrder = repository.findOrderById(order);
			anOrder.changeStateToCanceled();
			return this.repository.updateOrder(anOrder);
		}else throw new DBliveryException("The order can't be cancelled");
	}
	@Transactional
	@Override
	public Order cancelOrder(Long order, Date date) throws DBliveryException{
		if(this.canCancel(order)) {
			Order anOrder = repository.findOrderById(order);
			anOrder.changeStateToCanceled(date);
			return this.repository.updateOrder(anOrder);
		}else throw new DBliveryException("The order can't be cancelled");
	}
	@Transactional
	@Override
	public Status getActualStatus(Long id) {
		try {
			Order order = this.repository.findOrderById(id);
			return order.getMyState();
		} catch (Exception e) {
			return null;
		}
	}
	@Transactional
	@Override
	public Order finishOrder(Long id) throws DBliveryException {
		if(this.canFinish(id)) {
			Order order = this.repository.findOrderById(id);
			return this.repository.updateOrder(order.changeStateToReceived());
		}else throw new DBliveryException("The order can't be finished");
	}
	@Transactional
	@Override
	public Order finishOrder(Long id, Date date) throws DBliveryException{
		if(this.canFinish(id)) {
			Order order = this.repository.findOrderById(id);
			order.changeStateToReceived(date);
			return this.repository.updateOrder(order);
		}else throw new DBliveryException("The order can't be finished");
	}
	@Transactional
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
	// comienzo de 2da parte:
	@Transactional
	@Override
	public List<Order> getAllOrdersMadeByUser(String username){
		Optional<User> user = this.getUserByUsername(username);
		if ( user.isPresent() ) {
			User usuario = user.get();
			List<Order> ordenes = this.repository.findAllOrdersMadeByUser(usuario.getId());
			return ordenes;
		}else {
			List<Order> ordenes= null;
			return ordenes;// lista vacia
		}
	}
	
	@Transactional
	@Override
	public List <Order> getCancelledOrdersInPeriod(Date startDate, Date endDate) {
		return this.repository.findOrdersInPeriod("Cancelled", startDate, endDate);
	}
	
	@Transactional
	@Override
	public List <Order> getDeliveredOrdersInPeriod(Date startDate, Date endDate){
		return this.repository.findOrdersInPeriod("Delivered", startDate, endDate);
	}
	
	@Transactional
	@Override
	public List<Product> getTop10MoreExpensiveProducts() {
		return this.repository.findTop9MoreExpensiveProducts();
	}
	
	@Transactional
	@Override
	public List<User> getTop6UsersMoreOrders(){
		return this.repository.findTop6UsersMoreOrders();
	}

	@Transactional
	@Override
	public List <Order> getPendingOrders(){
		return this.repository.findPendingOrders();
	}
	
	@Transactional
	@Override
	public List <Order>  getSentOrders(){
		return this.repository.findSentOrders();
	}
	
	@Transactional
	@Override
	public List<Supplier> getTopNSuppliersInSentOrders(int n) {
		return this.repository.findTopNSuppliersInSentOrders(n);
	}

	@Transactional
	@Override
	public List <Order> getOrderWithMoreQuantityOfProducts(Date day){
		return this.repository.findOrderWithMoreQuantityOfProducts(day);
	}

	
/**
 * Obtiene todos los usuarios que han gastando más de <code>amount</code> en alguna orden en la plataforma
 * @param amount
 * @return una lista de usuarios que satisfagan la condici{on
 
	@Override
	public List<User> getUsersSpendingMoreThan(Float amount){
		List<User> users = this.repository.getUserSpendingMoreThan(amount);
		return null;
	}*/
	@Transactional
	@Override
	public List<Product> getProductsOnePrice() {
		List<Product> pruductos = this.repository.findProductsOnePrice();
		return pruductos;
	}
	
	public Supplier getSupplierLessExpensiveProduct() {
		Supplier sup = this.repository.findSupplierLessExpensiveProduct();
		return sup;
	}
	
	public List <Product> getProductIncreaseMoreThan100(){
		List<Product> productos = this.repository.findProductIncreaseMoreThan100();
		return productos;
	}
	
}
