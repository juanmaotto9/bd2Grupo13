package ar.edu.unlp.info.bd2.model;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Date;
import java.util.Calendar;

@Entity
@Table(name = "product")
public class Product {

	@ManyToOne(fetch = FetchType.LAZY)
	private Supplier supplier;
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private Set<ProductOrder> productOrder =new HashSet<ProductOrder>();
	
	@OneToOne(fetch = FetchType.LAZY)
	private Price priceNow;
	
	@OneToMany(mappedBy = "price", cascade = CascadeType.ALL)
	private Set<Price> prices =new HashSet<Price>();
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "price")
	private Float actualPrice;
	
	@Column(name = "weight")
	private Float weight;
	
	@Column(name= "creation_date")
	private Date creationDate;
	
	public Product() { super(); }
	
	public Product(String name, Float price, Float weight, Supplier supplier) {
		super();
		this.setName(name);
		this.setPrice(price);
		this.setWeight(weight);
		this.setSupplier(supplier);
		this.setPrices(price);
	}
	
	public Product(String name, Float price, Float weight, Supplier supplier, Date date) {
		this(name,price,weight,supplier);
		this.setcreationDate(date);
		this.updateDayPrice(date);
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Float getPrice() {
		return actualPrice;
	}
	public void setPrice(Float price) {
		this.actualPrice = price;
	}
	public Float getWeight() {
		return weight;
	}
	public void setWeight(Float weight) {
		this.weight = weight;
	}
	public Supplier getSupplier() {
		return supplier;
	}
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	public Set<ProductOrder> getProductOrder() {
		return productOrder;
	}

	public void setProductOrder(Set<ProductOrder> productOrder) {
		this.productOrder = productOrder;
	}

	public Price getPriceNow() {
		return priceNow;
	}

	public void setPriceNow(Price priceNow) {
		this.priceNow = priceNow;
	}

	public Float getActualPrice() {
		return actualPrice;
	}

	public void setActualPrice(Float actualPrice) {
		this.actualPrice = actualPrice;
	}

	public void setPrices(Set<Price> prices) {
		this.prices = prices;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Set<Price> getPrices(){
		return this.prices;
	}
	
	public void setPrices(Float price) {
		Price newPrice = new Price(price, this);
		this.priceNow = newPrice;
		this.prices.add(this.priceNow);
	}
	
	public void updateDayPrice(Date day) {
		this.getPriceNow().setStartDate(day);
	}
	
	/*   encaradisimo */
	public Price updateProductPrice(Float newPrice, Date startDate) {
		Date endDate = this.DateUpdateDay(startDate, -1);
		this.priceNow.setEndDate(endDate);
		Price price= new Price(newPrice, startDate, this);
		this.priceNow = price;
		this.prices.add(this.priceNow);
		this.setPrice(newPrice);
		return priceNow;
	}
	
	public Date DateUpdateDay(Date fecha, int dias){

	      Calendar calendar = Calendar.getInstance();		
	      calendar.setTime(fecha); // fecha que se recibe
	      calendar.add(Calendar.DAY_OF_YEAR, dias);  // numero de días a añadir, o restar en caso de días<0
	      return calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos
	 }
	
	public Date getCreationDate() {
		return this.creationDate;
	}
	
    public void setcreationDate(Date creationDate) {
    	this.creationDate = creationDate;
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
    }
	
	
}
