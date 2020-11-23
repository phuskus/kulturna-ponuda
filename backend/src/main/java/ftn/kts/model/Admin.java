package ftn.kts.model;

import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends User {
	@OneToMany(fetch = FetchType.LAZY)
	private Set<Category> categories;
	@OneToMany(fetch = FetchType.LAZY)
	private Set<CulturalOffer> culturalOffers;

	public Admin() {
		
	}
	
	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

	public Set<CulturalOffer> getCulturalOffers() {
		return culturalOffers;
	}

	public void setCulturalOffers(Set<CulturalOffer> culturalOffers) {
		this.culturalOffers = culturalOffers;
	}

}