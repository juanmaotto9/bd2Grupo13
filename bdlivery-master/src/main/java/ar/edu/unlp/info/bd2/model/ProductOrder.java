package ar.edu.unlp.info.bd2.model;

import javax.persistence.*;


@Entity
@Table(name="productOrder")
public class ProductOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Order order;
	
	@Column(name= "quantity")
	private Long quantity;
	

	@OneToOne(mappedBy = "productOrder")
	private Product product;

	public ProductOrder() {}
	
	public ProductOrder(Long quantity, Product product) {
		super();
		this.quantity = quantity;
		this.product = product;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	

}
