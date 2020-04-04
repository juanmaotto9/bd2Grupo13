package ar.edu.unlp.info.bd2.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "Supplier")
public class Supplier {
	
	@OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
	private Set<Product> product =new HashSet<Product>();
	
	@Id
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
	
	
	
}