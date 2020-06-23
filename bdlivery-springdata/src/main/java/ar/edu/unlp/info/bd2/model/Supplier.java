package ar.edu.unlp.info.bd2.model;

import java.util.HashSet;
import java.util.*;
import javax.persistence.*;

@Entity
@Table(name = "supplier")
public class Supplier {
	
	@OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
	private Set<Product> products =new HashSet<Product>();
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "cuil")
	private String cuil;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "coord_x")
	private Float coordX;
	
	@Column(name = "coord_y")
	private Float coordY;
	
	public Supplier() { super(); }
	
	public Supplier(String name, String cuil, String address, Float coordX, Float coordY) {
		super();
		this.name = name;
		this.cuil = cuil;
		this.address = address;
		this.coordX = coordX;
		this.coordY = coordY;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCuil() {
		return cuil;
	}

	public void setCuil(String cuil) {
		this.cuil = cuil;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Float getCoordX() {
		return coordX;
	}

	public void setCoordX(Float coordX) {
		this.coordX = coordX;
	}

	public Float getCoordY() {
		return coordY;
	}

	public void setCoordY(Float coordY) {
		this.coordY = coordY;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Product product) {
		this.products.add(product);
	}
	
	public Product getProductById(Long id) {
		Product productById = new Product();
		Iterator productIterator = this.products.iterator();
		while (productIterator.hasNext()){
			Product aProduct = (Product) productIterator.next();
			if (aProduct.getId() == id){
				productById = aProduct;
			}
		}
		return productById;
	}
	
	public Product createProduct(String name, Float weight, Float price) {
		Product productNew = new Product(name, price, weight, this);
		this.setProducts(productNew);
		return productNew;
	}

	public Product updateProductPrice(Long id, Float price, Date startDate){
		Product aProduct = this.getProductById(id);
		if (aProduct.getId() == id ) {
			aProduct.updateProductPrice(price, startDate);
		}
		return aProduct;
	}
	
	
	
}