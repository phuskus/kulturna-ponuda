package ftn.kts.dto;

import ftn.kts.model.Category;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class SubcategoryDTO {
    private Long id;
    @NotBlank(message = "Subcategory name is required!")
    private String name;
    @NotNull(message = "Subcategory needs to belong to a category!")
    private Long category;

    // TODO: Sets of cultural offers and subscriptions?
    
    public SubcategoryDTO() {
    	
    }

    public SubcategoryDTO(Long id, String name, Category category) {
        this.id = id;
        this.name = name;
        this.category = category.getId();
    }
    
    public SubcategoryDTO(String name, Long category) {
        this.name = name;
        this.category = category;
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

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }
}
