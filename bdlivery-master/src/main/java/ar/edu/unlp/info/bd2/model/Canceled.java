package ar.edu.unlp.info.bd2.model;

import javax.persistence.*;

@Entity
@DiscriminatorValue(value="canceled")
public class Canceled extends Status {
	
	public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }
	
	public Boolean isCanceled(){
        return true;
    }

}
