package ar.edu.unlp.info.bd2.repositories;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import javax.persistence.Query;
import org.hibernate.SessionFactory;

import ar.edu.unlp.info.bd2.model.User;
import ar.edu.unlp.info.bd2.model.Order;
import ar.edu.unlp.info.bd2.model.Product;
import ar.edu.unlp.info.bd2.model.Supplier;


public class DBliveryRepository {

	@Autowired
	private SessionFactory sessionFactory;
	
	//UserFunctions
	
	public User findUserById(Long id) {
		String hql = "from User where id = :id ";
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("id", id);
		User user = (User) query.getSingleResult(); // igual decia query.uniqueResult();
		//List<User> users = query.getResultList();
		//return !users.isEmpty() ? users.get(query.getFirstResult()) : null;
		return user;
	}
	

	public User findUserByUsername(String username) {
		String hql = "from User where username = :username ";
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("username", username);
		List<User> users = query.getResultList();
		return !users.isEmpty() ? users.get(query.getFirstResult()) : null;
	}
	
	
	public User findUserByEmail(String email) {
		String hql = "from User where email = :email ";
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("email", email);
		List<User> users = query.getResultList();
		return !users.isEmpty() ? users.get(query.getFirstResult()) : null;
	}


	public User persistUser(User user){
		this.sessionFactory.getCurrentSession().save(user);
		return user;
	}
	
	// OrderFuctions

	public Order findOrderById(Long id){
		String hql = "from Order where id = :id ";
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("id", id);
		List<Order> orders = query.getResultList();
		return !orders.isEmpty() ? orders.get(query.getFirstResult()) : null;
	}

	public Order persistOrder(Order order){
		this.sessionFactory.getCurrentSession().save(order);
		return order;

	}

	public Order updateOrder(Order order) {
		this.sessionFactory.getCurrentSession().update(order);
		return this.findOrderById(order.getId());
	}
	
	
	// ProductFunctions

	public Product findProductById(Long id){
		String hql = "from Product where id = :id ";
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("id", id);
		List<Product> products = query.getResultList();
		return !products.isEmpty() ? products.get(query.getFirstResult()) : null;
	}

	public Product persistProduct(Product product){
		this.sessionFactory.getCurrentSession().save(product);
		return product;

	}

	public List <Product> findProductByName(String name) {
		String hql = "from Product where name LIKE CONCAT('%',:name, '%')";
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("name", name);
		List<Product> products = query.getResultList();
		return !products.isEmpty() ? products : null;
	}

	public Product updateProductPrice(Product product) {
		this.sessionFactory.getCurrentSession().save(product);
		return this.findProductById(product.getId());
	}
	
	// SupplierFunctions

	public Supplier persistSupplier(Supplier supplier){
        this.sessionFactory.getCurrentSession().save(supplier);
        return supplier;
    }
	
	//Comienzo 2da parte - consultas -:
	
	public List<Order> findAllOrdersMadeByUser( Long id ) {
		String hql = "from Order where user.id = :id ";
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("id", id);
		List<Order> orders = query.getResultList();
		return !orders.isEmpty() ? orders : null;
	}
	
	public List <Order> findOrdersInPeriod(String state, Date startDate, Date endDate){
		String hql = "select o from Order o join o.myState as s "
				+ "where s.status = :state and (s.startDate BETWEEN :startDate AND :endDate)";
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("state", state);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		List<Order> orders = query.getResultList();
		return !orders.isEmpty() ? orders : (orders = null); 
	}
	
	public List<Product> findTop9MoreExpensiveProducts(){
		String hql = "select p from Product p join p.priceNow as pr "
				+ "ORDER BY pr.price DESC";
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql).setMaxResults(9);
		List<Product> products = query.getResultList();
		return !products.isEmpty() ? products : (products = null);
	}
	
	/* Obtiene los 6 usuarios que más cantidad de ordenes han realizado */
	public List<User> findTop6UsersMoreOrders(){
		String hql = "select o.user from Order o join o.user u "
				+ "group by u.id order by count(o) desc";
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql).setMaxResults(6);
		List<User> users = query.getResultList();
		return !users.isEmpty() ? users : (users = null);
	}
	
	/* Obtiene el listado de las ordenes pendientes */
	public List <Order> findPendingOrders(){
		String hql = "select o from Order o join o.myState s "
				+ "where s.status = 'Pending'";
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
		List<Order> orders = query.getResultList();
		return !orders.isEmpty() ? orders : (orders = null); 
	}
	
/* Obtiene el listado de las ordenes enviadas y no entregadas */
	public List <Order>  findSentOrders(){
		String hql = "select s.orden from Status s join s.orden as o "
				+ "where s.status = 'Sent' and "
				+ "s.orden not in (select o1 from Status s1 join s1.orden as o1 "
				+ "where s1.status = 'Delivered')";
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
		List<Order> orders = query.getResultList();
		return !orders.isEmpty() ? orders : (orders = null); 
	}
	
	
	/* Obtiene los N proveedores que más productos tienen en ordenes que están siendo enviadas */
	public List<Supplier> findTopNSuppliersInSentOrders(int n){
		String sent = "";
		String hql ="select s from Product p join p.supplier s "
					+ "join p.productOrder po "
					+"join po.orden ord where ord in (select o from Order o join o.myState st"
														+ " where st.status = 'Sent')"
				+ " group by s order by count(p) desc";
		
		
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql).setMaxResults(n);
	
		List<Supplier> suppliers = query.getResultList();
		return !suppliers.isEmpty() ? suppliers : (suppliers = null);
	}

	
	
	
	
	
	
	
	
	
	/*
	public List<Order> findCancelledOrdersInPeriod(Date startDate, Date endDate){
		String hql = "from Orden o where ";
	}
	
	public List<User> getUserSpendingMoreThan(Float amount) {
		String hql = " ";
		
		return null;
	}*/
	
}
