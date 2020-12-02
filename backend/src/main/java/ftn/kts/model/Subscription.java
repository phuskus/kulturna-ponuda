package ftn.kts.model;

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

	@ManyToOne(fetch = FetchType.EAGER)
	private Subcategory subcategory;
	@ManyToOne(fetch = FetchType.EAGER)
	private CulturalOffer culturalOffer;
	@ManyToOne(fetch = FetchType.LAZY)
	private RegisteredUser user;

	public Subscription(Long id, Date dateOfSubscription, Subcategory subcategory, CulturalOffer culturalOffer, RegisteredUser registeredUser) {
		this.id = id;
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
