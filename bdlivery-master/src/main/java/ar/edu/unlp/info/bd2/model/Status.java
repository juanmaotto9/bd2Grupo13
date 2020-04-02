package ar.edu.unlp.info.bd2.model;

import javax.persistence.*;

@Entity
@DiscriminatorColumn(name="type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Status {
	
	@ManyToOne(fetch = FetchType.LAZY)
	protected Order myOrder;
	
	@Id
	public Integer id;
	
    public abstract void setId(Integer id);
    public abstract Integer getId();
	
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

}
