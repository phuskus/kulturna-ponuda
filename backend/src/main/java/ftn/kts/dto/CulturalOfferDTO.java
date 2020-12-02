package ftn.kts.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import ftn.kts.model.Admin;
import ftn.kts.model.Subcategory;

public class CulturalOfferDTO {
	private Long id;
	@NotBlank(message = "Name is required!")
	private String name;
	@NotBlank(message = "Description is required!")
	private String description;
	@NotNull(message = "Latitude is required!")
	private Float latitude;
	@NotNull(message = "Longitude is required!")
	private Float longitude;
	private String address;
	private String city;
	private String region;

	@NotNull(message = "Admin ID is required!")
	private Long admin;
	@NotNull(message = "Category ID is required!")
	private Long category;

//	private Set<ReviewDTO> reviews;
//	private Set<PostDTO> posts;
//	private Set<SubscriptionDTO> subscriptions;
//	private Set<PictureDTO> pictures;

	public CulturalOfferDTO() {
	}

	public CulturalOfferDTO(Long id, String name, String description, float latitude, float longitude, String address,
			String city, String region, Admin admin, Subcategory category) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.latitude = latitude;
		this.longitude = longitude;
		this.address = address;
		this.city = city;
		this.region = region;
		
		this.admin = admin.getId();
		this.category = category.getId();
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

	public Long getAdmin() {
		return admin;
	}

	public void setAdmin(Long admin) {
		this.admin = admin;
	}

	public Long getCategory() {
		return category;
	}

	public void setCategory(Long category) {
		this.category = category;
	}
}