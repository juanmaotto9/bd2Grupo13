package ar.edu.unlp.info.bd2.model;

import javax.persistence.*;

@Entity
@DiscriminatorValue(value="canceled")
public class Canceled extends Status {

}
