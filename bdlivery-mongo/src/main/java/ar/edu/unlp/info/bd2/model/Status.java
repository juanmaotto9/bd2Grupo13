package ar.edu.unlp.info.bd2.model;

import java.util.Date;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonIgnore;

import ar.edu.unlp.info.bd2.mongo.GeneralPersistentObject;


@BsonDiscriminator
public abstract class Status {
	
/*-- agrego @BsonIgnore en los metodos isStatus() para que no se mapee --*/
	protected String status;
	protected Date startDate;
	
	
	public Status() {}
	
	public Status(Date date) {
		this.setStartDate(date);
	}
	
	@BsonIgnore    
    protected Boolean isSent(){
        return false;
    }

	@BsonIgnore
    protected Boolean isPending(){
        return false;
    }

	@BsonIgnore
    protected Boolean isCanceled(){
        return false;
    }

	@BsonIgnore
    protected Boolean isReceived(){
        return false;
    }
    
    public String getStatus() {
    	return this.status;
    }
    
    public void setStatus(String aString) {
    	this.status = aString;
    }
    

    public Date getStartDate() {
    	return this.startDate;
    }
    
	public void setStartDate(Date startDate) {
    	this.startDate = startDate;
    }
    

    
    abstract public void setStatus();
    
}
