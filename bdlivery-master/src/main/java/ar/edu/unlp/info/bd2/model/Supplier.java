package ar.edu.unlp.info.bd2.model;

import javax.persistence.*;

public class Supplier {
	
	@Id
	private Integer id;
	private String name;
	private String cuil;
	private String address;
	private Float coordX;
	private Float coordY;
	
/*   constructor de la clase   ¿? lo generé automaticamente  */
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	
	
}
