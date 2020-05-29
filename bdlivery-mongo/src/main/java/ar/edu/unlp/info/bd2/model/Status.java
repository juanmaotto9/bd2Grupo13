package ar.edu.unlp.info.bd2.model;

import java.util.Date;

import ar.edu.unlp.info.bd2.mongo.GeneralPersistentObject;


//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//dejo el comentario porq hay que saber como manejamos la herencia para cada estado
public abstract class Status extends GeneralPersistentObject {
	

	protected String status;
	protected Date startDate;
	protected Order orden;
	
	public Status() {}
	
	public Status(Order myOrden) {
		this.startDate = new Date();
		this.setOrden(myOrden);
	}
	
	public Status(Date date, Order myOrden) {
		this.setStartDate(date);
		this.orden = myOrden;
	}
	    
    protected Boolean isSent(){
        return false;
    }

    protected Boolean isPending(){
        return false;
    }

    protected Boolean isCanceled(){
        return false;
    }

    protected Boolean isReceived(){
        return false;
    }
    
    public String getStatus() {
    	return this.status;
    }
    
    public Date getStartDate() {
    	return this.startDate;
    }
    	
    public Order getOrden() {
		return orden;
	}

	public void setOrden(Order orden) {
		this.orden = orden;
	}

	public void setStartDate(Date startDate) {
    	this.startDate = startDate;
    }
    
    abstract public void setStatus();
    
}
