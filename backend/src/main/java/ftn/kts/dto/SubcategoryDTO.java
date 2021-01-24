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
    @NotNull(message = "Icon must be selected!")
    private PictureDTO icon;
    private boolean containsOffers;
    private String categoryName;
    
    
    // TODO: Sets of cultural offers and subscriptions?
    
    public SubcategoryDTO() {
    	
    }

    public SubcategoryDTO(Long id, String name, Category category, PictureDTO icon, boolean containsOffers) {
        this.id = id;
        this.name = name;
        this.categoryId = category.getId();
        this.icon = icon;
        this.setContainsOffers(containsOffers);
        this.setCategoryName(category.getName());
    }
    
    public SubcategoryDTO(String name, Long categoryId, PictureDTO icon) {
        this.name = name;
        this.categoryId = categoryId;
        this.icon = icon;
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

    public PictureDTO getIcon() {
		return icon;
	}

	public void setIcon(PictureDTO icon) {
		this.icon = icon;
	}

	public boolean isContainsOffers() {
		return containsOffers;
	}

	public void setContainsOffers(boolean containsOffers) {
		this.containsOffers = containsOffers;
	}

	/**
	 * @return the categoryName
	 */
	public String getCategoryName() {
		return categoryName;
	}

	/**
	 * @param categoryName the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	@Override
    public boolean equals(Object obj) {
        if (obj == this) return true;

        if (!(obj instanceof SubcategoryDTO)) return false;

        SubcategoryDTO subcategory = (SubcategoryDTO) obj;
        return this.name.equals(subcategory.getName());
    }
}
