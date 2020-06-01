package ar.edu.unlp.info.bd2.model;

import java.util.Date;

public class Pending extends Status {
	
	public Pending(){
		this.startDate = new Date();
		this.setStatus();
	}
	
	public Pending(Date date) { 
		this.setStartDate(date);
		this.setStatus();
	}
	
	public Boolean isPending(){
        return true;
    }

	public void setStatus() {
		this.status = "Pending";
	}    
}
