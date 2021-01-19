package ftn.kts.dto;

import ftn.kts.model.CulturalOffer;
import ftn.kts.model.Review;
import ftn.kts.model.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

public class ReviewDTO {
    private Long id;

    @NotNull(message = "Rating is required!")
    private Long rating;

    @NotBlank(message = "Content is required!")
    private String content;

    private UserDTO user;
    private Long culturalOfferId;
    private String culturalOfferName;

    private Set<PictureDTO> pictures;


    public ReviewDTO() {
        this.pictures = new HashSet<>();
    }

    public ReviewDTO(Long id, Long rating, String content, User user, CulturalOffer offer) {
        this.id = id;
        this.rating = rating;
        this.content = content;
        this.user = new UserDTO(user.getId(), user.getName(), user.getUsername(), user.getPassword());
        this.culturalOfferId = offer.getId();
        this.culturalOfferName = offer.getName();
        this.pictures = new HashSet<>();
    }

    public ReviewDTO(Long rating, String content, User user, Long offerId, String culturalOfferName) {
        this.rating = rating;
        this.content = content;
        this.user = new UserDTO(user.getId(), user.getName(), user.getUsername(), user.getPassword());
        this.culturalOfferId = offerId;
        this.culturalOfferName = culturalOfferName;
        this.pictures = new HashSet<>();
    }

    public ReviewDTO(Long rating, String content, UserDTO user, Long offerId, String culturalOfferName) {
        this.rating = rating;
        this.content = content;
        this.user = user;
        this.culturalOfferId = offerId;
        this.culturalOfferName = culturalOfferName;
        this.pictures = new HashSet<>();
    }

    public ReviewDTO(Long id, Long rating, String content, User user, Long offerId, String culturalOfferName) {
        this.id = id;
        this.rating = rating;
        this.content = content;
        this.user = new UserDTO(user.getId(), user.getName(), user.getUsername(), user.getPassword());
        this.culturalOfferId = offerId;
        this.culturalOfferName = culturalOfferName;
        this.pictures = new HashSet<>();
    }

    public ReviewDTO(Long id, Long rating, String content, UserDTO user, Long offerId, String culturalOfferName) {
        this.id = id;
        this.rating = rating;
        this.content = content;
        this.user = user;
        this.culturalOfferId = offerId;
        this.culturalOfferName = culturalOfferName;
        this.pictures = new HashSet<>();
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;

        if (!(obj instanceof ReviewDTO)) return false;

        ReviewDTO review = (ReviewDTO) obj;
        return this.getCulturalOfferId().equals(review.getCulturalOfferId()) && this.getContent().equals(review.getContent())
                && this.getUser().equals(review.getUser());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRating() {
        return rating;
    }

    public void setRating(Long rating) {
        this.rating = rating;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public Long getCulturalOfferId() {
        return culturalOfferId;
    }

    public void setCulturalOfferId(Long culturalOffer) {
        this.culturalOfferId = culturalOffer;
    }

    public String getCulturalOfferName() {
        return culturalOfferName;
    }

    public void setCulturalOfferName(String culturalOfferName) {
        this.culturalOfferName = culturalOfferName;
    }

    public Set<PictureDTO> getPictures() {
        return pictures;
    }

    public void setPictures(Set<PictureDTO> pictures) {
        this.pictures = pictures;
    }
}
