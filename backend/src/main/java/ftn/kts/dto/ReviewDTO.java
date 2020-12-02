package ftn.kts.dto;

import ftn.kts.model.CulturalOffer;
import ftn.kts.model.User;

import javax.validation.constraints.NotBlank;

public class ReviewDTO {
    private Long id;

    @NotBlank(message = "Rating is required!")
    private String rating;

    @NotBlank(message = "Content is required!")
    private String content;

    private Long user;
    private Long culturalOffer;


    public ReviewDTO() {

    }

    public ReviewDTO(Long id, String ratin, String conent, User user, CulturalOffer offer) {
    }
}
