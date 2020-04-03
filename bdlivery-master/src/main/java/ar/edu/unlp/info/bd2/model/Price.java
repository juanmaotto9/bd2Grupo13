package ar.edu.unlp.info.bd2.model;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "Price")
public class Price {
	
	@Id
	private Long id;
	
	@Column(name = "price")
	private Float price;
	
	@Column(name = "start_date")
	private Date startDate;
	
	@Column(name = "end_date")
	private Date endDate;
	
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public Price(Float price) {
		super();
		this.price = price;
    	Calendar cal = Calendar.getInstance();
    	Date dob = cal.getTime();
		this.startDate = dob;
	}
	
	
	
}
