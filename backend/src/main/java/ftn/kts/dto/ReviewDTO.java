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

    private Long user;
    private Long culturalOffer;

    private Set<PictureDTO> pictures;


    public ReviewDTO() {
        this.pictures = new HashSet<>();
    }

    public ReviewDTO(Long id, Long rating, String content, User user, CulturalOffer offer) {
        this.id = id;
        this.rating = rating;
        this.content = content;
        this.user = user.getId();
        this.culturalOffer = offer.getId();
        this.pictures = new HashSet<>();
    }

    public ReviewDTO(Long rating, String content, Long userId, Long offerId) {
        this.rating = rating;
        this.content = content;
        this.user = userId;
        this.culturalOffer = offerId;
        this.pictures = new HashSet<>();
    }
    
    public ReviewDTO(Long id, Long rating, String content, Long userId, Long offerId) {
        this.id = id;
    	this.rating = rating;
        this.content = content;
        this.user = userId;
        this.culturalOffer = offerId;
        this.pictures = new HashSet<>();
    }
    
    

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;

        if (!(obj instanceof ReviewDTO)) return false;

        ReviewDTO review = (ReviewDTO) obj;
        return this.getCulturalOffer().equals(review.getCulturalOffer()) && this.getContent().equals(review.getContent())
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

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public Long getCulturalOffer() {
        return culturalOffer;
    }

    public void setCulturalOffer(Long culturalOffer) {
        this.culturalOffer = culturalOffer;
    }

    public Set<PictureDTO> getPictures() {
        return pictures;
    }

    public void setPictures(Set<PictureDTO> pictures) {
        this.pictures = pictures;
    }
}
