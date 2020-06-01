package ar.edu.unlp.info.bd2.model;

import java.util.Date;

public class Received extends Status {
	
	public Received(){
		this.startDate = new Date();
		this.setStatus();
	}
	
	public Received(Date date) { 
		this.setStartDate(date);
		this.setStatus();
	}
	
	public Boolean isReceived(){
        return true;
    }
	
	public void setStatus() {
		this.status = "Delivered";
	}
}