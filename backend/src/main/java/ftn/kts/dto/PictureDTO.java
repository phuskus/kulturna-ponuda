package ftn.kts.dto;

public class PictureDTO {

	private Long id;
	private String placeholder;
	private String image;

	public PictureDTO() {
	}

	public PictureDTO(Long id, String placeholder, String image) {
		this.id = id;
		this.placeholder = placeholder;
		this.image = image;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPlaceholder() {
		return placeholder;
	}

	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
