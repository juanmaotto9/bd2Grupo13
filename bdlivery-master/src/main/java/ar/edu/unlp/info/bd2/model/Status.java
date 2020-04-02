package ar.edu.unlp.info.bd2.model;

import javax.persistence.*;

@Entity
@DiscriminatorColumn(name="type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Status {
	
	@ManyToOne(fetch = FetchType.LAZY)
	protected Order myOrder;
	
	public Status() {
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
	
	@Column(name="status")
	protected String status;
	
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
    
}
