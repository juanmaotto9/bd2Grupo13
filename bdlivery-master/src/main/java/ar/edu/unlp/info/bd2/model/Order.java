package ar.edu.unlp.info.bd2.model;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="order")
public class Order {
	
	@Id
	private Integer id;
	
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
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
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
	
	
}
