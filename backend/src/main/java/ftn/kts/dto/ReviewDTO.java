package ftn.kts.dto;

import ftn.kts.model.CulturalOffer;
import ftn.kts.model.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ReviewDTO {
    private Long id;

    @NotNull(message = "Rating is required!")
    private Long rating;

    @NotBlank(message = "Content is required!")
    private String content;

    private Long user;
    private Long culturalOffer;

//    private Set<PicturesDTO> pictures;


    public ReviewDTO() {
    }

    public ReviewDTO(Long id, Long rating, String content, User user, CulturalOffer offer) {
        this.id = id;
        this.rating = rating;
        this.content = content;
        this.user = user.getId();
        this.culturalOffer = offer.getId();
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
}
