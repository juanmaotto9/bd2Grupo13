package ar.edu.unlp.info.bd2.repositories;

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
		String hql = "from Order where user = :id ";
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("user", id);
		List<Order> orders = query.getResultList();
		return !orders.isEmpty() ? orders : null;
	}
	
	/*public List<User> getUserSpendingMoreThan(Float amount) {
		String hql = " ";
		
		return null;
	}*/
	
}
