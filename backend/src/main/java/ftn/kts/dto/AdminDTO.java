package ftn.kts.dto;

import ftn.kts.model.Category;
import ftn.kts.model.CulturalOffer;
import ftn.kts.model.Subcategory;

import javax.validation.constraints.NotBlank;
import java.util.Set;

public class AdminDTO {

    private Long id;    //should be removed once admin inherits userDTO
    @NotBlank(message = "Username is required!")
    private String username;
    @NotBlank(message = "Password is required!")
    private String password;

    private Set<Category> categories;
    private Set<CulturalOffer> culturalOffers;

    public AdminDTO(){
    }

    public AdminDTO(Long id, String username, String password, Set<Category> categories, Set<CulturalOffer> culturalOffers){
        this.id  = id;
        this.username = username;
        this.password = password;
        this.categories = categories;
        this.culturalOffers = culturalOffers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Set<CulturalOffer> getCulturalOffers() {
        return culturalOffers;
    }

    public void setCulturalOffers(Set<CulturalOffer> culturalOffers) {
        this.culturalOffers = culturalOffers;
    }
}
