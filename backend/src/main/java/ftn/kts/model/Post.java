package ftn.kts.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "posts")
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "content", unique = false, nullable = false)
	private String content;

	@ManyToOne(fetch = FetchType.EAGER)
	private CulturalOffer culturalOffer;

	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Picture> pictures;
	

	public Post() {}

	public Post(Long id, String content, CulturalOffer culturalOffer) {
		this.id = id;
		this.content = content;
		this.culturalOffer = culturalOffer;
		this.pictures = new HashSet<>();
	}
	
	public Post(Long id, String content, CulturalOffer culturalOffer, Set<Picture> pictures) {
		this.id = id;
		this.content = content;
		this.culturalOffer = culturalOffer;
		this.pictures = pictures;
	}
	
	public Post(String content, CulturalOffer culturalOffer, Set<Picture> pictures) {
		this.content = content;
		this.culturalOffer = culturalOffer;
		this.pictures = pictures;
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

	public Set<Picture> getPictures() {
		return pictures;
	}

	public void setPictures(Set<Picture> pictures) {
		this.pictures = pictures;
	}

	public CulturalOffer getCulturalOffer() {
		return culturalOffer;
	}

	public void setCulturalOffer(CulturalOffer culturalOffer) {
		this.culturalOffer = culturalOffer;
	}

}