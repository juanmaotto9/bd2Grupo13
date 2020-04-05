package ar.edu.unlp.info.bd2.model;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.Date;

@Entity
@Table(name = "Product")
public class Product {

	@ManyToOne(fetch = FetchType.LAZY)
	private Supplier supplier;
	
	@OneToOne(fetch = FetchType.LAZY)
	private ProductOrder productOrder;
	
	@OneToMany(mappedBy = "price", cascade = CascadeType.ALL)
	private Set<Price> prices =new HashSet<Price>();
	
	@Id
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "price")
	private Float actualPrice;
	
	@Column(name = "weight")
	private Float weight;
	
		//preguntar sobre el id, se debe generar automaticamente. "@GenerateValue"
	public Product() { super(); }
	
	public Product(String name, Float price, Float weight, Supplier supplier) {
		super();
		this.setName(name);
		this.setPrice(price);
		this.setWeight(weight);
		this.setSupplier(supplier);
		this.setPrices(price);
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
		Price newPrice = new Price(price);
		this.prices.add(newPrice);
	}
	/*  nono, no se como encararlo */
	public Price updateProductPrice(Float newPrice, Date startDate) {
		Price price= new Price(newPrice, startDate);
		this.prices.add(price);
		this.setPrice(newPrice);
		return price;
	}

	
	
}
