package ar.edu.unlp.info.bd2.model;

import javax.persistence.*;

@Entity
@DiscriminatorValue(value="sent")
public class Sent extends Status {

}
