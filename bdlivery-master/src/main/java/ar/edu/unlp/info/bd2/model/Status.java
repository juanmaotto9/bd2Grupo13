package ar.edu.unlp.info.bd2.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@DiscriminatorColumn(name="type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Status {
	
	@ManyToOne(fetch = FetchType.LAZY)
	protected Order orden;
	
	public Status() {
		this.setStatus();
		
		//para mi se crea con el dia de hoy a la hora que se creo, pero consultar
		this.startDate = new Date();
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
	
	@Column(name="status")
	protected String status;
	
	@Column(name="start_date")
	protected Date startDate;
	
    public abstract void setId(Long id);
    public abstract Long getId();  
    
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
    abstract public void setStatus();
    
    public Date getStartDate() {
    	return this.startDate;
    }
    
    abstract public void setStartDate(Date startDate);    
    
}
