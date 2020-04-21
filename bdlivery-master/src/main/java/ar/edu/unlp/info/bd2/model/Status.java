package ar.edu.unlp.info.bd2.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@DiscriminatorColumn(name="type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Status {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
	
	@Column(name="status")
	protected String status;
	
	@Column(name="start_date")
	protected Date startDate;
		
	@ManyToOne(fetch = FetchType.LAZY)
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
    
	public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
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
