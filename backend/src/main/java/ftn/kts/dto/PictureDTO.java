package ftn.kts.dto;

public class PictureDTO {

	private Long id;
	private String placeholder;
	private String image;
	private String path;

	public PictureDTO() {
	}
	
	public PictureDTO(Long id) {
		this.id = id;
	}

	public PictureDTO(Long id, String placeholder, String image, String path) {
		this.id = id;
		this.placeholder = placeholder;
		this.image = image;
		this.path = path;
	}
	
	public PictureDTO(String placeholder, String path) {
		this.placeholder = placeholder;
		this.path = path;
	}
	

	public PictureDTO(Long id) {
		this.id = id;
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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
