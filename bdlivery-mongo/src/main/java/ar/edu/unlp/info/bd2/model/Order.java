package ar.edu.unlp.info.bd2.model;

import java.util.*;

public class Order extends GeneralPersistentObject {
	
	private User user;	
	private User deliveryUser;	
	private Status myState;	
	private Set<Status> status =new HashSet<Status>();
	private Set<ProductOrder> products =new HashSet<ProductOrder>();
	private Date dateOfOrder;
	private String address;
	private Float coordX;
	private Float coordY;
	private Float amount;
	
	
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
