package ar.edu.unlp.info.bd2.model;

public class ProductOrder extends GeneralPersistentObject {

	private Order orden; //por el momento queda
	private Long quantity;
	private Product product;

	public ProductOrder() {}
	
	public ProductOrder(Long quantity, Product myProduct, Order myOrden) {
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
