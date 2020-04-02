package ar.edu.unlp.info.bd2.model;

import javax.persistence.*;

@Entity
@DiscriminatorValue(value="received")
public class Received extends Status {
	
	public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }
	
	public Boolean isReceived(){
        return true;
    }

}
