package ftn.kts.model.uniqueClasses;

import ftn.kts.model.CulturalOffer;
import ftn.kts.model.RegisteredUser;
import ftn.kts.model.Subcategory;

import java.io.Serializable;


public class UniqueSubscription implements Serializable {
    private Long id;

    private CulturalOffer culturalOffer;
    private Subcategory subcategory;
    private RegisteredUser registeredUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CulturalOffer getCulturalOffer() {
        return culturalOffer;
    }

    public void setCulturalOffer(CulturalOffer culturalOffer) {
        this.culturalOffer = culturalOffer;
    }

    public Subcategory getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(Subcategory subcategory) {
        this.subcategory = subcategory;
    }

    public RegisteredUser getRegisteredUser() {
        return registeredUser;
    }

    public void setRegisteredUser(RegisteredUser registeredUser) {
        this.registeredUser = registeredUser;
    }
}
