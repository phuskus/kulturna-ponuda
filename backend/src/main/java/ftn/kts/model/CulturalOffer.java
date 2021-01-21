package ftn.kts.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "cultural_offers")
public class CulturalOffer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "name", unique = true, nullable = false)
	private String name;
	@Column(name = "description", unique = false, nullable = false)
	private String description;
	@Column(name = "latitude", unique = false, nullable = false)
	private float latitude;
	@Column(name = "longitude", unique = false, nullable = false)
	private float longitude;
	@Column(name = "address", unique = false, nullable = true)
	private String address;
	@Column(name = "city", unique = false, nullable = true)
	private String city;


	@Column(name = "region", unique = false, nullable = true)
	private String region;
	@Column(name = "averageRating", unique = false, nullable = true)
	private double averageRating = -1;

	@ManyToOne(fetch = FetchType.EAGER)
	private Admin admin;
	@ManyToOne(fetch = FetchType.EAGER)
	private Subcategory category;

	@OneToMany(mappedBy = "culturalOffer", fetch = FetchType.LAZY)
	private Set<Review> reviews;
	@OneToMany(mappedBy = "culturalOffer", fetch = FetchType.LAZY)
	private Set<Post> posts;
	@OneToMany(mappedBy = "culturalOffer", fetch = FetchType.LAZY)
	private Set<Subscription> subscriptions;
	@ManyToMany(fetch = FetchType.LAZY)
	private Set<Picture> pictures;
	
	public CulturalOffer() {
		this.reviews = new HashSet<>();
		this.posts = new HashSet<>();
		this.subscriptions = new HashSet<>();
		this.pictures = new HashSet<>();
	}
	
	public CulturalOffer(String name, String description, float latitude, float longitude, 
			String address, String city, String region, Admin admin, Subcategory category) {
		this();
		this.name = name;
		this.description = description;
		this.latitude = latitude;
		this.longitude = longitude;
		this.address = address;
		this.city = city;
		this.region = region;
		
		this.admin = admin;
		this.category = category;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public Set<Picture> getPictures() {
		return pictures;
	}

	public void setPictures(Set<Picture> pictures) {
		this.pictures = pictures;
	}

	public Set<Review> getReviews() {
		return reviews;
	}

	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	}

	public Set<Post> getPosts() {
		return posts;
	}

	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}

	public Set<Subscription> getSubscriptions() {
		return subscriptions;
	}

	public void setSubscriptions(Set<Subscription> subscriptions) {
		this.subscriptions = subscriptions;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public Subcategory getCategory() {
		return category;
	}

	public void setCategory(Subcategory category) {
		this.category = category;
	}

	public double getAverageRating() {
		if (this.averageRating != -1)
			return this.averageRating;

		double average = 0.;
		for (Review review: this.reviews)
			average += review.getRating();

		average = average / Math.max(1, this.reviews.size());
		return average;
	}

	public void setAverageRating(Double averageRating) {
		this.averageRating = averageRating;
	}

}