package ftn.kts.dto;

import ftn.kts.model.Admin;
import ftn.kts.model.Category;
import ftn.kts.model.CulturalOffer;
import ftn.kts.model.Subcategory;

import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

public class AdminDTO {

    private Long id;    
    @NotBlank(message = "Name is required!")
    private String name;
    @NotBlank(message = "Username is required!")
    private String username;
    @NotBlank(message = "Password is required!")
    private String password;

    private Set<Category> categories;
    private Set<CulturalOffer> culturalOffers;

    public AdminDTO() {
        this.categories = new HashSet<>();
        this.culturalOffers = new HashSet<>();
    }

    public AdminDTO(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.categories = new HashSet<>();
        this.culturalOffers = new HashSet<>();
    }

    public AdminDTO(Long id, String name, String username, String password, Set<Category> categories, Set<CulturalOffer> culturalOffers) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.categories = categories;
        this.culturalOffers = culturalOffers;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;

        if (!(obj instanceof AdminDTO)) return false;

        AdminDTO admin = (AdminDTO) obj;
        return this.name.equals(admin.getName()) && this.username.equals(admin.getUsername());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
