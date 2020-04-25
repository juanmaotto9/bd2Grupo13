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
		return users.get(query.getFirstResult()); //!users.isEmpty() ? users.get(query.getFirstResult()) : null;
	}
	
	
	public User findUserByEmail(String email) {
		String hql = "from User where email = :email ";
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("email", email);
		List<User> users = query.getResultList();
		return  users.get(query.getFirstResult());  //!users.isEmpty() ? users.get(query.getFirstResult()) : null;
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
		return orders.get(query.getFirstResult());  //!orders.isEmpty() ? orders.get(query.getFirstResult()) : null;
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
		return products.get(query.getFirstResult());//!products.isEmpty() ? products.get(query.getFirstResult()) : null;
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
		return products; //!products.isEmpty() ? products : null;
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
		return orders;
	}
	
	public List <Order> findOrdersInPeriod(String state, Date startDate, Date endDate){
		String hql = "select o from Order o join o.myState as s "
				+ "where s.status = :state and (s.startDate BETWEEN :startDate AND :endDate)";
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("state", state);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		List<Order> orders = query.getResultList();
		return orders; 
	}
	
	public List<Product> findTop9MoreExpensiveProducts(){
		String hql = "select p from Product p join p.priceNow as pr "
				+ "ORDER BY pr.price DESC";
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql).setMaxResults(9);
		List<Product> products = query.getResultList();
		return products;
	}
	
	/* Obtiene los 6 usuarios que más cantidad de ordenes han realizado */
	public List<User> findTop6UsersMoreOrders(){
		String hql = "select o.user from Order o join o.user u "
				+ "group by u.id order by count(o) desc";
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql).setMaxResults(6);
		List<User> users = query.getResultList();
		return users;
	}
	
	/* Obtiene el listado de las ordenes pendientes */
	public List <Order> findPendingOrders(){
		String hql = "select o from Order o join o.myState s "
				+ "where s.status = 'Pending'";
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
		List<Order> orders = query.getResultList();
		return orders; 
	}
	
/* Obtiene el listado de las ordenes enviadas y no entregadas */
	public List <Order>  findSentOrders(){
		String hql = "select s.orden from Status s join s.orden as o "
				+ "where s.status = 'Sent' and "
				+ "s.orden not in (select o1 from Status s1 join s1.orden as o1 "
				+ "where s1.status = 'Delivered')";
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
		List<Order> orders = query.getResultList();
		return orders; 
	}
	
	
	/*
	 * 
	 *  Obtiene los N proveedores que más productos tienen en ordenes que están siendo enviadas */
	public List<Supplier> findTopNSuppliersInSentOrders(int n){
		//String sent = "(select o from Order o where o.myState.status ='Sent')";
		
		String hql ="select po.product.supplier from ProductOrder po "
					+ "where po.orden in (select o from Order o where o.myState.status ='Sent') "
					+ " group by po.product.supplier order by (po) desc ";				
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql).setMaxResults(n);
		List<Supplier> suppliers = query.getResultList();
		return suppliers;  // no me sale :(
	}
	/*
	 * 
	*
	*/

	/*  obtiene la/s orden/es con mayor cantidad de productos ordenados de la fecha dada  */
	public List <Order> findOrderWithMoreQuantityOfProducts(Date day){
		String hql1 = "select po.orden from ProductOrder po join po.orden o"
				+ " where o.dateOfOrder= :day group by po.orden order by count(po) desc";
		Query queryy = this.sessionFactory.getCurrentSession().createQuery(hql1);
		queryy.setParameter("day", day);
		List<Order> orders = queryy.getResultList();  //hasta acá solo consigo las ordenes del dia :day
		Order aOrder = orders.get(0);
		
		String hql = "select po.orden from ProductOrder po "
			+ "where po.orden.dateOfOrder=:day "
			+ "GROUP BY po.orden "
			+ "having count(po) = (select count(po1) from ProductOrder po1 "
									+ "where po1.orden = :orden)";
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("day", day);
		query.setParameter("orden", aOrder);
		List<Order> orders1 = query.getResultList();
		return orders1;
	}
	
	
/*Obtiene todos los usuarios que han gastando más de amount en alguna orden en la plataforma*/
	public List<User> findUserSpendingMoreThan(Float amount) {
		String hql = "select o.user from Order o where o.amount > :amount ";
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("amount", amount);
		List<User> users = query.getResultList();
		return users;
	}
	
	/* obtiene el listado de productos con su precio a una fecha dada */
	public List <Object[]> findProductsWithPriceAt(Date day){
		String hql = "select pr, p.price from Price p join p.product pr "
				+ "where ( p.startDate <= :day) and ((:day <= p.endDate) OR (p.endDate = null))";
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("day", day);
		List<Object[]> listObjects = query.getResultList();
		return listObjects;
	}
	
	/*  busca el precio del producto vigente a la fecha de creación de la orden  */
	public Float findPriceAt(Product product, Date day) {
		String precio = "select p.price from Price p "
				+ "where (p.product = :product) and "
					+ "((p.startDate <= :day) and (:day <= p.endDate OR p.endDate = null))";
		Query query = this.sessionFactory.getCurrentSession().createQuery(precio);
		query.setParameter("product", product);
		query.setParameter("day", day);
		List<Float> price = query.getResultList();
		return price.get(query.getFirstResult());	 		
	}
	/* Obtiene los 5 repartidores que menos ordenes tuvieron asignadas (tanto sent como delivered)  */
	public List <User> find5LessDeliveryUsers(){
		String hql = "select o.deliveryUser from Order o "
						+ "where o in (select distinct st.orden from Status st "
											+ "where (st.status='Sent' OR st.status='Delivered') )"
						+ "group by o.deliveryUser order by count(o) asc ";
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql).setMaxResults(5);
		List<User> users= query.getResultList();
		return users;
	}
	/* Obtiene el producto con más demanda  */
	public Product findBestSellingProduct() {
		String hql = "select p.product from ProductOrder p "
				+ "group by p.product order by count(p) desc ";
		Product product =(Product) this.sessionFactory.getCurrentSession().createQuery(hql).setMaxResults(1).getSingleResult();
		 return product;	
	}
	
/* obtiene las ordenes que fueron entregadas en m{as de un día desde que fueron iniciadas(status pending) */
	public List<Order> findOrdersCompleteMoreThanOneDay(){
		String hql = "select s.orden from Status s "
				+ "where s.status='Delivered' and s.startDate > s.orden.dateOfOrder";
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
		List<Order> orders = query.getResultList();
		return orders;
	}
	
	/*
	 * select o.* from orden o inner join Status s ON s.orden_id=o.id 
	 *  where s.status='Delivered' and s.start_date > o.dateOfOrder;
	 * 
	 */
	
	
	public List <Product> findProductsOnePrice(){
		String hql = "select s.product from Price s group by s.product having count(s) = 1 ";
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
		List<Product> productos = query.getResultList();
		return !productos.isEmpty() ? productos : null; 
	}
	
	public Supplier findSupplierLessExpensiveProduct() {
		String hql = "select p.supplier from Product p where p.priceNow.price = "
				+ "(select min(s.price) from Price s )";
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
		Supplier supp = (Supplier) query.getSingleResult();
		return supp; 
	}
	
	public List <Product> findProductIncreaseMoreThan100() {
		String hql = "select p from Product p join p.prices s" //62 
				+ " where p.actualPrice > (s.price*2)";
				//+ "(select v.price * 2 from Price v group by v.product having min(v.start_date))"
				//+ " <= (select m.price from Price m group by m.product having max(m.start_date))"; 
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
		List<Product> productos = query.getResultList();
		return !productos.isEmpty() ? productos : (productos = null); 
	}
	
	public List <Order> findDeliveredOrdersForUser(String username){
		String hql = "select o from Order o where o.myState.status = 'Delivered' "
				+ "and o.user.username = :username";
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("username", username);
		List<Order> orders = query.getResultList();
		return !orders.isEmpty() ? orders : null;
	}
	
	public List <Order> findDeliveredOrdersSameDay(){
		String hql = "select o from Order o join o.myState as s "
				+ "where s.status = 'Delivered' "
				+ "and ( s.startDate BETWEEN o.dateOfOrder AND o.dateOfOrder + 1)";
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
		List<Order> orders = query.getResultList();
		return !orders.isEmpty() ? orders : null;
	}
	
	public List <Order> findSentMoreOneHour(){
		String hql = "select o from Order o join o.myState as s ";
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
		List<Order> orders = query.getResultList();
		return !orders.isEmpty() ? orders : null;
	}
}
