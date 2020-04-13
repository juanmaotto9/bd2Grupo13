package ar.edu.unlp.info.bd2.model;

import java.util.Date;

import javax.persistence.*;

@Entity
@DiscriminatorValue(value="canceled")
public class Canceled extends Status {
	
	public Canceled(Order myOrden) {
		super(myOrden);
		this.setStatus();
		
		//para mi se crea con el dia de hoy a la hora que se creo, pero consultar
		this.startDate = new Date();
		this.orden = myOrden;
	}
	
	public Canceled(Date date, Order myOrden) {
		super(date, myOrden);
		this.setStatus();
		this.setStartDate(date);
		this.orden = myOrden;
	}
	
	public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }
	
	public Boolean isCanceled(){
        return true;
    }
	
	public void setStatus() {
		this.status = "Cancelled";
	}
	
	@Override
	public String getStatus() {
		return this.status;
	}
	
	public Date getStartDate() {
		return this.startDate;
	}
	
    public void setStartDate(Date startDate) {
    	this.startDate = startDate;
    }
}
