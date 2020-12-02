package ftn.kts.dto;

import ftn.kts.model.CulturalOffer;
import ftn.kts.model.Picture;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

public class PostDTO {

    private Long id;
    @NotBlank(message = "Content is required!")
    private String content;
    //TODO: consider changing this to CulturalOfferDTO
    @NotNull(message = "Cultural offer is required")
    private Long culturalOffer;
    //private Set<Picture> pictures;

    public PostDTO() {}

    public PostDTO(Long id, String content, Long culturalOffer) {
        this.id = id;
        this.content = content;
        this.culturalOffer = culturalOffer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCulturalOffer() {
        return culturalOffer;
    }

    public void setCulturalOffer(Long culturalOffer) {
        this.culturalOffer = culturalOffer;
    }
}
