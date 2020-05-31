package ar.edu.unlp.info.bd2.model;

import java.util.*;
import org.bson.types.ObjectId;
import org.bson.codecs.pojo.annotations.BsonId;

import ar.edu.unlp.info.bd2.mongo.GeneralPersistentObject;

public class Supplier extends GeneralPersistentObject {
	
	
	//private List<Product> products =new ArrayList<Product>();
	private String name;
	private String cuil;
	private String address;
	private Float coordX;
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
	
	//set para que ande mongo, no deberiamos usarlo...
	/*public void setProducts(List<Product> productsNew){
		this.products = productsNew;
	}
	
	public List<Product> getProducts() {
		return products;
	}
	

	public void addProducts(Product product) {
		this.products.add(product);
	}
	
	public Product getProductById(ObjectId id) {
		Product productById = new Product();
		Iterator productIterator = this.products.iterator();
		while (productIterator.hasNext()){
			Product aProduct = (Product) productIterator.next();
			if (aProduct.getObjectId() == id){
				productById = aProduct;
			}
		}
		return productById;
	}
	
	public Product createProduct(String name, Float weight, Float price) {
		Product productNew = new Product(name, price, weight, this.getObjectId());
		this.addProducts(productNew);
		return productNew;
	}

	public Product updateProductPrice(ObjectId id, Float price, Date startDate){
		Product aProduct = this.getProductById(id);
		if (aProduct.getObjectId() == id ) {
			aProduct.updateProductPrice(price, startDate);
		} aProduct.updateProductPrice(price, startDate);
		return aProduct;
	}*/
	
	
	
}