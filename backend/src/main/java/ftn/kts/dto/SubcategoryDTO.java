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

    public SubcategoryDTO(Long id, String name, Category category) {
        this.id = id;
        this.name = name;
        this.categoryId = category.getId();
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
}
