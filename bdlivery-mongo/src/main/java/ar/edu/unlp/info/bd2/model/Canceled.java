package ar.edu.unlp.info.bd2.model;

import java.util.Date;

public class Canceled extends Status {
	
	public Canceled() {
		this.startDate = new Date();
		this.setStatus();
	}
	
	public Canceled(Date date) { 
		this.setStartDate(date);
		this.setStatus();
	}
	/*public Canceled(Order myOrden) {
		super(myOrden);
		this.setStatus();
	}
	
	public Canceled(Date date, Order myOrden) {
		super(date, myOrden);
		this.setStatus();
	}*/
	
	public Boolean isCanceled(){
        return true;
    }
	
	public void setStatus() {
		this.status = "Cancelled";
	}
}
