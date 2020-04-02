package ar.edu.unlp.info.bd2.model;

import javax.persistence.*;

@Entity
@DiscriminatorValue(value="pending")
public class Pending extends Status {

}
