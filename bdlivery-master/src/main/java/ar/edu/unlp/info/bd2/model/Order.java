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
	private Set<ProductOrder> products =new HashSet<ProductOrder>();
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="dateOfOrder")
	private Date dateOfOrder;
	
	@Column(name="address")
	private String address;
	
	@Column(name="coordX")
	private Float coordX;
	
	@Column(name="coordY")
	private Float coordY;
	
	
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
	
	public void setMyState(Status status) {
		this.myState = status;
	}
	
	public Status getMyState() {
		return myState;
	}
	
	public User getClient() {
		return user;
	}

	public void setClient(User client) {
		this.user = client;
	}
	
	public User getDeliveryUser() {
		return deliveryUser;
	}

	public void setDeliveryUser(User deliveryUser) {
		this.deliveryUser = deliveryUser;
	}
	
	public Order() {
	}
	
	public Order(Date dateOfOrder, String address, Float coordX,  Float coordY, User client) {
		this.user = client;
		this.address = address;
		this.dateOfOrder = dateOfOrder;
		this.coordX = coordX;
		this.coordY = coordY;
		this.myState = new Pending();
		this.status.add(this.myState);
	}
	
	public Order createOrder(Date dateOfOrder, String address, Float coordX,  Float coordY, User client) {
		this.user = client;
		this.address = address;
		this.dateOfOrder = dateOfOrder;
		this.coordX = coordX;
		this.coordY = coordY;
		this.myState = new Pending();
		this.status.add(this.myState);
		return this;
	}	
	

	/* Nota: status es la coleccion de estados que tuve, y myState es el estado actual*/
	
	public Set<Status> getStatus() {
		return status;
	}

	public void addStatus(Status myState) {
		this.status.add(myState);
	}
	
	
	public void addProductOrder(Long quantity, Product product) {
		ProductOrder productOrder = new ProductOrder(quantity, product);
		this.products.add(productOrder);
	}
	
    public Set<ProductOrder> getProducts() {
		return products;
    }
    
	public Boolean isSended() {
		return myState.isSent();
	}

	public Boolean isFinish() {
		return myState.isReceived();
	}

	public Boolean isCancel() {
		return myState.isCanceled();
	}

	public Boolean isPending() {
		return myState.isPending();
	}
	
	public Order changeStateToSent() {
		this.myState = new Sent();
		this.addStatus(this.getMyState());
		return this;
	}

	public Order changeStateToReceived() {
		this.myState = new Received();
		this.addStatus(this.getMyState());
		return this;
	}

	public Order changeStateToCanceled() {
		this.myState = new Canceled();
		this.addStatus(this.getMyState());
		return this;
	}

	public Order deliverOrder(User deliveryUser){
		setDeliveryUser(deliveryUser);
		return changeStateToSent();
	}
	
	
}
