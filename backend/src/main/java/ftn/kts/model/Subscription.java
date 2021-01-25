package ftn.kts.model;

import ftn.kts.model.uniqueClasses.UniqueSubscription;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "subscriptions")
public class Subscription {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "date_of_subscription", unique = false, nullable = false)
	private Date dateOfSubscription;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	private CulturalOffer culturalOffer;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	private Subcategory subcategory;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private RegisteredUser user;

	public Subscription()
	{

	}

	public Subscription(Long id, Date dateOfSubscription, Subcategory subcategory, CulturalOffer culturalOffer, RegisteredUser registeredUser) {
		this.id = id;
		this.dateOfSubscription = dateOfSubscription;
		this.subcategory = subcategory;
		this.culturalOffer = culturalOffer;
		this.user = registeredUser;
	}

	public Subscription(Date dateOfSubscription, Subcategory subcategory, CulturalOffer culturalOffer, RegisteredUser registeredUser) {
		this.dateOfSubscription = dateOfSubscription;
		this.subcategory = subcategory;
		this.culturalOffer = culturalOffer;
		this.user = registeredUser;
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

	public RegisteredUser getUser() {
		return user;
	}

	public void setUser(RegisteredUser registeredUser) {
		this.user = registeredUser;
	}
}
