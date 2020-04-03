package ar.edu.unlp.info.bd2.model;

import javax.persistence.*;

@Entity
@DiscriminatorColumn(name="type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Status {
	
	@Id
	private Integer id;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String myClass() {
		return this.getClass().toString();
	}

}
