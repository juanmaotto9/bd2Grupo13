package ar.edu.unlp.info.bd2.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name="orden")
public class Order {
	
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
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
	
	@Column(name="amount")
	private Float amount;
	
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
		
	public Float getAmount() {
    	/*Iterator setprod = this.products.iterator();
    	boolean found=false;
    	Float precio=0F;
    	while (!found && setprod.hasNext() ) {
    		ProductOrder p = (ProductOrder) setprod.next();
    		precio= p.getProduct().findPriceAtPeriod(this.dateOfOrder);
    		this.addAmountProduct(precio, p.getQuantity());
    	}*/   //esto lo hice para ver si se solucionaba los errores de la orden con respecto al precio del producto
		return amount;
	}
	public void setAmount(Float amount) {
		this.amount = amount;
	}
	public Order() {}
	
	public Order(Date dateOfOrder, String address, Float coordX,  Float coordY, User client) {
		this.user = client;
		this.address = address;
		this.dateOfOrder = dateOfOrder;
		this.amount = 0F;
		this.coordX = coordX;
		this.coordY = coordY;
		this.myState = new Pending(dateOfOrder, this);
		this.status.add(this.myState);
	}
	
	/* Nota: status es la coleccion de estados que tuve, y myState es el estado actual*/
	
	public Set<Status> getStatus() {
		return status;
	}

	public void addStatus(Status myState) {
		this.status.add(myState);
	}
	
	public void addAmountProduct(Float price, Long quantity) {
		 this.setAmount((price * quantity) + this.getAmount());	
	}
	
	public void addProductOrder(Long quantity, Product myProduct) {
		ProductOrder productOrder = new ProductOrder(quantity, myProduct, this);
		//this.addAmountProduct(myProduct.findPriceAtPeriod(this.dateOfOrder), quantity);
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
		this.myState = new Sent(this);
		this.addStatus(this.getMyState());
		return this;
	}
	
	/*	para la parte2	*/
	public Order changeStateToSent(Date date) {
		this.myState = new Sent(date, this);
		this.addStatus(this.getMyState());
		return this;
	}

	public Order changeStateToReceived() {
		this.myState = new Received(this);
		this.addStatus(this.getMyState());
		return this;
	}
	
	/*	para la parte 2*/
	public Order changeStateToReceived(Date date) {
		this.myState = new Received(date, this);
		this.addStatus(this.getMyState());
		return this;
	}

	public Order changeStateToCanceled() {
		this.myState = new Canceled(this);
		this.addStatus(this.getMyState());
		return this;
	}
	
	/*	para la parte 2	*/
	public Order changeStateToCanceled(Date date) {
		this.myState = new Canceled(date, this);
		this.addStatus(this.getMyState());
		return this;
	}

	public Order deliverOrder(User deliveryUser){
		this.setDeliveryUser(deliveryUser);
		return this.changeStateToSent();
	}
	
	public Order deliverOrder(User deliveryUser, Date date) {
		this.setDeliveryUser(deliveryUser);
		this.changeStateToSent(date);
		return this;
	}
	
	
}
