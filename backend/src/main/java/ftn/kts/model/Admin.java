package ftn.kts.model;

import java.util.HashSet;
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
        this.categories = new HashSet<>();
        this.categories = new HashSet<>();
    }

    public Admin(String name, String username, String password) {
        super(name, username, password);
        this.categories = new HashSet<>();
        this.categories = new HashSet<>();
    }

    public Admin(String name, String username, String password, Set<Category> categories, Set<CulturalOffer> culturalOffers) {
        super(name, username, password);
        this.categories = categories;
        this.culturalOffers = culturalOffers;
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