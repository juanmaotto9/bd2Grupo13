package ar.edu.unlp.info.bd2.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Product")
public class Product {
	
	@Id
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "price")
	private Float price;
	
	@Column(name = "weight")
	private Float weight;
	
	@Column(name = "supplier")
	private Supplier supplier;
	
	@Column(name = "prices")
	private List <Price> prices;
	
	
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
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
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
	
	public List<Price> getPrices(){
		return prices;
	}
	/*
	public void addPrice(Price newPrice) {
		prices.add(newPrice);
	}*/

	
	
}
