package ar.edu.unlp.info.bd2.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Date;
import java.util.Calendar;
import org.bson.types.ObjectId;

import ar.edu.unlp.info.bd2.mongo.GeneralPersistentObject;

public class Product extends GeneralPersistentObject {

	private ObjectId supplier;
	private List<ProductOrder> productOrder =new ArrayList<ProductOrder>();
	private Price priceNow;
	private List<Price> prices =new ArrayList<Price>();
	private String name;
	private Float actualPrice;
	private Float weight;
	private Date creationDate;
	
	public Product() { super(); }
	
	
	public Product(String name, Float priceNew, Float weight, ObjectId supplier) {
		super();
		this.setName(name);
		this.setPrice(priceNew);
		this.setWeight(weight);
		this.setSupplier(supplier);
		this.addPrices(priceNew);
	}
	
	

	
	
	// -------------------------- set y get --------------
	  
	public ObjectId getSupplier() {
		return supplier;
	}

	public void setSupplier(ObjectId supplier) {
		this.supplier = supplier;
	}
	
	public List<ProductOrder> getProductOrder() {
		return productOrder;
	}

	public void setProductOrder(List<ProductOrder> productOrder) {
		this.productOrder = productOrder;
	}
	
	public void setPrices(List<Price> prices) {
		this.prices = prices;
	}
	
	public List<Price> getPrices(){
		return this.prices;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Float getWeight() {
		return weight;
	}
	public void setWeight(Float weight) {
		this.weight = weight;
	}
	
	public void addPrices(Float price) {
		Price newPrice = new Price(price, this.getObjectId());
		this.priceNow = newPrice;
		this.prices.add(this.priceNow);
	}
	
	public Date getCreationDate() {
		return this.creationDate;
	}
	
    public void setCreationDate(Date creationDate) {
    	this.creationDate = creationDate;
    }
    
	public Float getPrice() {
		return actualPrice;
	}
	
	public void setPrice(Float priceNew) {
		this.actualPrice = priceNew;
	}
    
    
  // ---------- hacen lo mismo pero fue para ver si pasaba el codec-----
	public Price getPriceNow() {
		return priceNow;
	}

	public void setPriceNow(Price priceNow) {
		this.priceNow = priceNow;
	}
	//-------------------------------------------------------------------
	
	
	
	
	/*
	public Product(String name, Float price, Float weight, ObjectId supplier, Date date) {
		this(name,price,weight,supplier);
		this.setcreationDate(date);
		this.updateDayPrice(date);
	}

	

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	
	
	
	//-------------------------- end set y get --------------
	
	public void updateDayPrice(Date day) {
		this.getPriceNow().setStartDate(day);
	}
	
	   //encaradisimo 
	public Price updateProductPrice(Float newPrice, Date startDate) {
		Date endDate = this.DateUpdateDay(startDate, -1);
		this.priceNow.setEndDate(endDate);
		Price price= new Price(newPrice, startDate, this.getObjectId());
		this.priceNow = price;
		this.prices.add(this.priceNow);
		this.addPrice(newPrice);
		return priceNow;
	}
	
	public Date DateUpdateDay(Date fecha, int dias){

	      Calendar calendar = Calendar.getInstance();		
	      calendar.setTime(fecha); // fecha que se recibe
	      calendar.add(Calendar.DAY_OF_YEAR, dias);  // numero de días a añadir, o restar en caso de días<0
	      return calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos
	 }
	
    
    public Float findPriceAtPeriod(Date dateOfOrder) {
    	Iterator setprices = this.prices.iterator();
    	boolean found=false;
    	Float precio=0F;
    	while (!found && setprices.hasNext() ) {
    		Price p = (Price) setprices.next();
    		if ((p.getEndDate()==(null) || dateOfOrder.after(p.getEndDate()) ) && p.getStartDate().before(dateOfOrder) ) {
    			precio =(Float) p.getPrice();
    			found = true;
    		}
    	}
    	return precio;
    }*/
}
