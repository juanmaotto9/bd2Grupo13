package ar.edu.unlp.info.bd2.model;

import javax.persistence.*;

@Entity
@DiscriminatorValue(value="canceled")
public class Canceled extends Status {
	
	public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }
	
	public Boolean isCanceled(){
        return true;
    }
	
	public void setStatus() {
		this.status = "Cancelled";
	}
}
