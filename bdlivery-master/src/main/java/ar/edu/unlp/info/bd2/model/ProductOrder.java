package ar.edu.unlp.info.bd2.model;

import javax.persistence.*;

public class ProductOrder {

	@ManyToOne(fetch = FetchType.LAZY)
	private Order order;
	
	@Column(name= "quantity")
	private Long quantity;
	
	@Id
	private Long id;
	
	
	private Product product;

	public ProductOrder() {}
	
	public ProductOrder(Long quantity, Product product) {
		super();
		this.quantity = quantity;
		this.product = product;
	}
	

}
