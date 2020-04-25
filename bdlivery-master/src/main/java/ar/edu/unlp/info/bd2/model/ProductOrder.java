package ar.edu.unlp.info.bd2.model;

import javax.persistence.*;


@Entity
@Table(name="productOrder")
public class ProductOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Order orden;
	
	@Column(name= "quantity")
	private Long quantity;
	
	//@OneToOne(fetch = FetchType.LAZY)
	@ManyToOne(fetch = FetchType.LAZY)
	private Product product;

	public ProductOrder() {}
	
	public ProductOrder(Long quantity, Product myProduct, Order myOrden) {
		super();
		this.quantity = quantity;
		this.product = myProduct;
		this.orden = myOrden;
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
