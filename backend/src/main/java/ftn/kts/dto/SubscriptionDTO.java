package ftn.kts.dto;

import ftn.kts.model.CulturalOffer;
import ftn.kts.model.RegisteredUser;
import ftn.kts.model.Subcategory;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class SubscriptionDTO {
    private Long id;
    @NotNull(message = "Date of subscription is required!")
    private Date dateOfSubscription;
    @NotNull(message = "Subscription must belong to a registered user!")
    private Long registeredUserId;
    private Long subcategoryId;
    private Long culturalOfferId;
    private String culturalOfferName;
    private String subcategoryName;

    public SubscriptionDTO() { }

    public SubscriptionDTO(Long id, Date dateOfSubscription, RegisteredUser registeredUser, Subcategory subcategory, CulturalOffer culturalOffer) {
        this(dateOfSubscription, registeredUser, subcategory, culturalOffer);
        this.id = id;
    }

    public SubscriptionDTO(Date dateOfSubscription, RegisteredUser registeredUser, Subcategory subcategory, CulturalOffer culturalOffer) {
        this.dateOfSubscription = dateOfSubscription;
        this.registeredUserId = registeredUser.getId();
        if (subcategory == null) {
            this.subcategoryId = null;
            this.subcategoryName = null;
        } else {
            this.subcategoryId = subcategory.getId();
            this.subcategoryName = subcategory.getName();
        }

        if (culturalOffer == null) {
            this.culturalOfferId = null;
            this.culturalOfferName = null;
        } else {
            this.culturalOfferId = culturalOffer.getId();
            this.culturalOfferName = culturalOffer.getName();
        }
    }

    public SubscriptionDTO(@NotNull(message = "Date of subscription is required!") Date dateOfSubscription, @NotNull(message = "Subscription must belong to a registered user!") Long registeredUserId, Long subcategoryId, Long culturalOfferId) {
        this.dateOfSubscription = dateOfSubscription;
        this.registeredUserId = registeredUserId;
        this.subcategoryId = subcategoryId;
        this.culturalOfferId = culturalOfferId;
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

    public Long getRegisteredUserId() {
        return registeredUserId;
    }

    public void setRegisteredUserId(Long registeredUserId) {
        this.registeredUserId = registeredUserId;
    }

    public Long getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(Long subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    public Long getCulturalOfferId() {
        return culturalOfferId;
    }

    public void setCulturalOfferId(Long culturalOfferId) {
        this.culturalOfferId = culturalOfferId;
    }

    public String getCulturalOfferName() {
        return culturalOfferName;
    }

    public void setCulturalOfferName(String culturalOfferName) {
        this.culturalOfferName = culturalOfferName;
    }

    public String getSubcategoryName() {
        return subcategoryName;
    }

    public void setSubcategoryName(String subcategoryName) {
        this.subcategoryName = subcategoryName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;

        if (!(obj instanceof SubscriptionDTO)) return false;

        SubscriptionDTO subscription = (SubscriptionDTO) obj;

        boolean subcategorySame;
        if (this.subcategoryId == null && subscription.subcategoryId == null)
        {
            subcategorySame = true;
        } else if (this.subcategoryId != null && subscription.subcategoryId != null) {
            subcategorySame = this.subcategoryId.equals(subscription.getSubcategoryId());
        } else {
            subcategorySame = false;
        }

        boolean culturalOfferSame;
        if (this.culturalOfferId == null && subscription.culturalOfferId == null) {
            culturalOfferSame = true;
        } else if (this.culturalOfferId != null && subscription.culturalOfferId != null) {
            culturalOfferSame = this.culturalOfferId.equals(subscription.getCulturalOfferId());
        } else {
            culturalOfferSame = false;
        }

        return this.dateOfSubscription.equals(subscription.getDateOfSubscription())
                && subcategorySame
                && culturalOfferSame
                && this.registeredUserId.equals(subscription.getRegisteredUserId());
    }
}
