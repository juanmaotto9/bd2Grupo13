package ar.edu.unlp.info.bd2.model;

import javax.persistence.*;

@Entity
@DiscriminatorValue(value="pending")
public class Pending extends Status {
	
	public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }
	
	public Boolean isPending(){
        return true;
    }

}
