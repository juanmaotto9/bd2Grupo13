package ar.edu.unlp.info.bd2.model;

import java.util.Date;

import javax.persistence.*;

@Entity
@DiscriminatorValue(value="pending")
public class Pending extends Status {
	
	public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }
	
	public Boolean isPending(){
        return true;
    }

	public void setStatus() {
		this.status = "Pending";
	}
	
	public Date getStartDate() {
		return this.startDate;
	}
	
    public void setStartDate(Date startDate) {
    	this.startDate = startDate;
    }
}
