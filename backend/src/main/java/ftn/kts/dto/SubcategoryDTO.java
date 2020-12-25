package ftn.kts.dto;

import ftn.kts.model.Category;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class SubcategoryDTO {
    private Long id;
    @NotBlank(message = "Subcategory name is required!")
    private String name;
    @NotNull(message = "Subcategory needs to belong to a category!")
    private Long categoryId;

    // TODO: Sets of cultural offers and subscriptions?
    
    public SubcategoryDTO() {
    	
    }

    public SubcategoryDTO(Long id, String name, Category category) {
        this.id = id;
        this.name = name;
        this.categoryId = category.getId();
    }
    
    public SubcategoryDTO(String name, Long categoryId) {
        this.name = name;
        this.categoryId = categoryId;
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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;

        if (!(obj instanceof SubcategoryDTO)) return false;

        SubcategoryDTO subcategory = (SubcategoryDTO) obj;
        return this.name.equals(subcategory.getName());
    }
}
