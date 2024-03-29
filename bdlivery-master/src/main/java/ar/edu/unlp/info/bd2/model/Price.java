package ar.edu.unlp.info.bd2.model;

import javax.persistence.*;

import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "price")
public class Price {

	@ManyToOne(fetch = FetchType.LAZY)
	private Product product;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	//para el primer producto el cual no recibe una startDate
	public Price(Float price, Product myProduct) {
		this.setPrice(price);
		Calendar date = Calendar.getInstance();
		this.setStartDate(date.getTime());
		this.product = myProduct;
	}
	
	public Price(Float price, Date startDate, Product myProduct) {
		super();
		this.price = price;
		this.startDate = startDate;
		this.product = myProduct;
	}
	
	
	
	
	
}
