package ar.edu.unlp.info.bd2.model;

import java.util.Date;

import org.bson.codecs.pojo.annotations.BsonIgnore;

public class Pending extends Status {
	
	public Pending(){
		this.startDate = new Date();
		this.setStatus();
	}
	
	public Pending(Date date) { 
		this.setStartDate(date);
		this.setStatus();
	}
	
	@BsonIgnore
	public Boolean isPending(){
        return true;
    }

	public void setStatus() {
		this.status = "Pending";
	}    
}
