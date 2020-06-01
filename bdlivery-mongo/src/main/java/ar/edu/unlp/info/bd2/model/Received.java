package ar.edu.unlp.info.bd2.model;

import java.util.Date;

public class Received extends Status {
	
	public Received(){}
	
	public Received(Date date) { 
		this.setStartDate(date);
		this.setStatus();
	}
	/*public Received(Order myOrden) {
		super(myOrden);
		this.setStatus();
	}
	
	public Received(Date date, Order myOrden) {
		super(date, myOrden);
		this.setStatus();
	}*/
	
	public Boolean isReceived(){
        return true;
    }
	
	public void setStatus() {
		this.status = "Delivered";
	}
}