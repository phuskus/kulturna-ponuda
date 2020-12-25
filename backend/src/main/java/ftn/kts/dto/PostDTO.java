package ftn.kts.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

public class PostDTO {

	private Long id;
	@NotBlank(message = "Content is required!")
	private String content;
	@NotNull(message = "Cultural offer is required")
	private Long culturalOffer;
	private Set<PictureDTO> pictures;

	public PostDTO() {
	}

	public PostDTO(Long id, String content, Long culturalOffer) {
		this.id = id;
		this.content = content;
		this.culturalOffer = culturalOffer;
		this.pictures = new HashSet<>();
	}

	public PostDTO(Long id, String content, Long culturalOffer, Set<PictureDTO> pictures) {
		this.id = id;
		this.content = content;
		this.culturalOffer = culturalOffer;
		this.pictures = pictures;
	}
	
	public PostDTO(String content, Long culturalOffer, Set<PictureDTO> pictures) {
		this.content = content;
		this.culturalOffer = culturalOffer;
		this.pictures = pictures;
	}
	
	public PostDTO(Long id, Long culturalOffer) {
		this.id = id;
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

	public Set<PictureDTO> getPictures() {
		return pictures;
	}

	public void setPictures(Set<PictureDTO> pictures) {
		this.pictures = pictures;
	}
}
