package ftn.kts.model;

import java.util.Date;
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
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "rating", unique = false, nullable = false)
    private Long rating;
    @Column(name = "content", unique = false, nullable = false)
    private String content;
    @Column(name = "datePosted", unique = false, nullable = false)
    private Date datePosted;

    @ManyToOne(fetch = FetchType.EAGER)
    private RegisteredUser user;
    @ManyToOne(fetch = FetchType.EAGER)
    private CulturalOffer culturalOffer;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Picture> pictures;

    public Review() {
        this.pictures = new HashSet<>();
    }

    public Review(Long id, Long rating, String content, RegisteredUser user, CulturalOffer offer) {
        this.id = id;
        this.rating = rating;
        this.content = content;
        this.user = user;
        this.culturalOffer = offer;
        this.pictures = new HashSet<>();
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

    public RegisteredUser getUser() {
        return user;
    }

    public void setUser(RegisteredUser user) {
        this.user = user;
    }

    public CulturalOffer getCulturalOffer() {
        return culturalOffer;
    }

    public void setCulturalOffer(CulturalOffer culturalOffer) {
        this.culturalOffer = culturalOffer;
    }

    public Set<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(Set<Picture> pictures) {
        this.pictures = pictures;
    }

    public Date getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(Date datePosted) {
        this.datePosted = datePosted;
    }
}