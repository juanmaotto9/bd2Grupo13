package ar.edu.unlp.info.bd2.model;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.Date;
import java.util.Calendar;

@Entity
@Table(name = "product")
public class Product {

	@ManyToOne(fetch = FetchType.LAZY)
	private Supplier supplier;
	
	@OneToOne(fetch = FetchType.LAZY)
	private ProductOrder productOrder;
		
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
		
		// puede variar, por ahora creo que no es necesario pasarle el dia.
		//siempre va a ser la fecha de creacion, asi que hasta que sea necesario...
		//... si es que lo es, queda asi.
		//this.creationDate = new Date();
	}
	
	public Product(String name, Float price, Float weight, Supplier supplier, Date date) {
		this(name,price,weight,supplier);
		this.setcreationDate(date);
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
	// getPrices()
	
	public Set<Price> getPrices(){
		return this.prices;
	}
	
	public void setPrices(Float price) {
		Price newPrice = new Price(price, this);
		this.priceNow = newPrice;
		this.prices.add(this.priceNow);
	}
	
	/*
	public void setPrices(Float price, Date startDate) {
		Price newPrice= new Price(price, startDate);
		this.priceNow = newPrice;
		this.prices.add(newPrice);
	} */
	
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

	
	
}
