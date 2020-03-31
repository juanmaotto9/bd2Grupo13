package ar.edu.unlp.info.bd2.model;

import javax.persistence.*;

public class Product {
	
	@Id
	private Integer id;
	private String name;
	private Float price;
	private Float weight;
	private Supplier supplier;
	
/*  testCreateProduct() X --> getPrices() 
 * del producto se debe guardar el historial de precios ¿?  
 * updateProductPrice(producto.getId(),Float.valueOf(3000.0F),startDate); 
 */
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
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
	
	
}
