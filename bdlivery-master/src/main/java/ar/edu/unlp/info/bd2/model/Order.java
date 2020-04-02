package ar.edu.unlp.info.bd2.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name="orden")
public class Order {
	
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;
	
	@OneToOne(fetch = FetchType.LAZY)
	private User deliveryUser;
	
	@OneToOne(fetch = FetchType.LAZY)
	private Status myState;
	
	@OneToMany(mappedBy = "orden", cascade = CascadeType.ALL)
	private Set<Status> status =new HashSet<Status>();
	
	@OneToMany(mappedBy = "orden", cascade = CascadeType.ALL)
	private Set<Product> products =new HashSet<Product>();
	
	public Order() {
	}
	
	@Id
	private Long id;
	
	@Column(name="dateOfOrder")
	private Date dateOfOrder;
	
	@Column(name="address")
	private String address;
	
	@Column(name="coordX")
	private Float coordX;
	
	@Column(name="coordY")
	private Float coordY;
	
	@Column(name="client")
	private User client;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getDateOfOrder() {
		return dateOfOrder;
	}
	public void setDateOfOrder(Date dateOfOrder) {
		this.dateOfOrder = dateOfOrder;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Float getCoordX() {
		return coordX;
	}
	public void setCoordX(Float coordX) {
		this.coordX = coordX;
	}
	public Float getCoordY() {
		return coordY;
	}
	public void setCoordY(Float coordY) {
		this.coordY = coordY;
	}
	public User getClient() {
		return client;
	}
	public void setClient(User client) {
		this.client = client;
	}
	
	public void setMyState(Status status) {
		this.myState = status;
	}
	
	public Status getMyState() {
		return myState;
	}
	
	public User getDeliveryUser() {
		return deliveryUser;
	}

	public void setDeliveryUser(User deliveryUser) {
		this.deliveryUser = deliveryUser;
	}
	
	
	public Order(Date dateOfOrder, String address, Float coordX,  Float coordY, User client) {
		this.client = client;
		this.address = address;
		this.dateOfOrder = dateOfOrder;
		this.coordX = coordX;
		this.coordY = coordY;
		this.status.add(new Pending());
	}
	
	public Order createOrder(Date dateOfOrder, String address, Float coordX,  Float coordY, User client) {
		this.client = client;
		this.address = address;
		this.dateOfOrder = dateOfOrder;
		this.coordX = coordX;
		this.coordY = coordY;
		this.status.add(new Pending());
		return this;
	}
	

	/* Nota: status es la coleccion de estados que tuve, y myState es el estado actual*/
	
	public Set<Status> getStatus() {
		return status;
	}

	public void addStatus(Status myState) {
		this.status.add(myState);
	}
	
	
    public Set<Product> getProducts() {
		return products;
    }
	
	
}
