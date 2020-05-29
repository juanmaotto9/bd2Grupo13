package ar.edu.unlp.info.bd2.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import ar.edu.unlp.info.bd2.mongo.GeneralPersistentObject;

public class User extends GeneralPersistentObject {

	private Set<Order> ordens =new HashSet<Order>();
	private String email;
	private String password;
	private String username;
	private String name;
	private Date dateOfBirth;
	private Set<Order> orders =new HashSet<Order>(); //Por ahora lo dejo, lo eliminamos mas adelante
	
    public User() {
    };

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	public User(String username, String name, String email, String password, Date dateOfBirth) {
        this.setUsername(username);
        this.setName(name);
        this.setEmail(email);
        this.setPassword(password);
        this.setDateOfBirth(dateOfBirth);
    };
	
	public User createUser(String username, String name, String email, String password, Date dateOfBirth) {
        this.setUsername(username);
        this.setName(name);
        this.setEmail(email);
        this.setPassword(password);
        this.setDateOfBirth(dateOfBirth);
        return this;

    }
	
    public Set<Order> getOrders() {
        return ordens;
    }

    public void setOrders(Order order) {
        this.ordens.add(order);
    }

    public void addOrder(Order order) {
        setOrders(order);
    }

    public Order createOrder(Date dateOfOrder, String address, Float coordX,  Float coordY) {
        Order newOrder = new Order(dateOfOrder, address, coordX,  coordY, this);
        addOrder(newOrder);
        return newOrder;
    }
	
}
