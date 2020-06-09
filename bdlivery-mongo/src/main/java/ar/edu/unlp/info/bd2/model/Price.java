package ar.edu.unlp.info.bd2.model;

import java.util.Calendar;

import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.types.ObjectId;
import java.util.Date;

import ar.edu.unlp.info.bd2.mongo.GeneralPersistentObject;

public class Price {
	
	@BsonIgnore
	private ObjectId product;	//Deberia ser de tipo Product-
	private Float price;
	private Date startDate;
	private Date endDate;
	
	public Price() {}
	
	public Float getPrice() {
		return price;
	}
	
	public void setPrice(Float price) {
		this.price = price;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}
	
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public ObjectId getProduct() {
		return product;
	}
	
	public void setProduct(ObjectId prod) {
		this.product = prod;
	}
	
	
	//para el primer producto el cual no recibe una startDate
	public Price(Float price, ObjectId myProduct) {
		this.setPrice(price);
		Calendar date = Calendar.getInstance();
		this.setStartDate(date.getTime());
		this.product = myProduct;
	}
	
	public Price(Float price, Date startDate, ObjectId myProduct) {
		super();
		this.price = price;
		this.startDate = startDate;
		this.product = myProduct;
	}
	
}
