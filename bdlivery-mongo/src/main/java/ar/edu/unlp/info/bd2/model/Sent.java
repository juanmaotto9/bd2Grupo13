package ar.edu.unlp.info.bd2.model;

import java.util.Date;

public class Sent extends Status {
	
	public Sent(Date date) { 
		this.setStartDate(date);
		this.setStatus();
	}
	public Sent() {
		this.startDate = new Date();
		this.setStatus();
	}

		
	public Boolean isSent(){
        return true;
    }

	public void setStatus() {
		this.status = "Sent";
	}
	
}
