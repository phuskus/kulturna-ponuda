package ftn.kts.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "subscriptions")
public class Subscription {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "date_of_subscription", unique = false, nullable = false)
	private Date dateOfSubscription;

	@ManyToOne(fetch = FetchType.EAGER)
	private Subcategory subcategory;
	@ManyToOne(fetch = FetchType.EAGER)
	private CulturalOffer culturalOffer;

	public Subscription() {
		
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDateOfSubscription() {
		return dateOfSubscription;
	}

	public void setDateOfSubscription(Date dateOfSubscription) {
		this.dateOfSubscription = dateOfSubscription;
	}

	public Subcategory getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(Subcategory subcategory) {
		this.subcategory = subcategory;
	}

	public CulturalOffer getCulturalOffer() {
		return culturalOffer;
	}

	public void setCulturalOffer(CulturalOffer culturalOffer) {
		this.culturalOffer = culturalOffer;
	}

}