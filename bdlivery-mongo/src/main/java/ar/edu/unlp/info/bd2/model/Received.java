package ar.edu.unlp.info.bd2.model;

import java.util.Date;

import org.bson.codecs.pojo.annotations.BsonIgnore;

public class Received extends Status {
	
	public Received(){
		this.startDate = new Date();
		this.setStatus();
	}
	
	public Received(Date date) { 
		this.setStartDate(date);
		this.setStatus();
	}
	
	@BsonIgnore
	public Boolean isReceived(){
        return true;
    }
	
	public void setStatus() {
		this.status = "Delivered";
	}
}