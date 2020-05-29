package ar.edu.unlp.info.bd2.model;

import java.util.Date;

public class Sent extends Status {
	
	public Sent() {}
	
	public Sent(Order myOrden) {
		super(myOrden);
		this.setStatus();
	}
	
	public Sent(Date date, Order myOrden) {
		super(date, myOrden);
		this.setStatus();
	}
		
	public Boolean isSent(){
        return true;
    }

	public void setStatus() {
		this.status = "Sent";
	}
	
}
