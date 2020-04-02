package ar.edu.unlp.info.bd2.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Price")
public class Price {

	@ManyToOne(fetch = FetchType.LAZY)
	private Product product;
	
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
	
	public Price() {
	}
	
	public Price(Float price, Date startDate) {
		super();
		this.price = price;
		this.startDate = startDate;
	}
	
	
	
	
	
}
