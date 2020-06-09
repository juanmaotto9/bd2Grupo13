package ar.edu.unlp.info.bd2.model;

import java.util.Date;

import org.bson.codecs.pojo.annotations.BsonIgnore;

public class Canceled extends Status {
	
	public Canceled() {
		this.startDate = new Date();
		this.setStatus();
	}
	
	public Canceled(Date date) { 
		this.setStartDate(date);
		this.setStatus();
	}
	
	@BsonIgnore
	public Boolean isCanceled(){
        return true;
    }
	
	public void setStatus() {
		this.status = "Cancelled";
	}
}
