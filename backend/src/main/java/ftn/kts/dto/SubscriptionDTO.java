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

    public SubscriptionDTO() { }

    public SubscriptionDTO(Long id, Date dateOfSubscription, RegisteredUser registeredUser, Subcategory subcategory, CulturalOffer culturalOffer) {
        this.id = id;
        this.dateOfSubscription = dateOfSubscription;
        this.registeredUserId = registeredUser.getId();
        this.subcategoryId = subcategory.getId();
        this.culturalOfferId = culturalOffer.getId();
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

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;

        if (!(obj instanceof SubscriptionDTO)) return false;

        SubscriptionDTO subscription = (SubscriptionDTO) obj;
        return this.dateOfSubscription.equals(subscription.getDateOfSubscription())
                && this.subcategoryId.equals(subscription.getSubcategoryId())
                && this.culturalOfferId.equals(subscription.getCulturalOfferId())
                && this.registeredUserId.equals(subscription.getRegisteredUserId());
    }
}
