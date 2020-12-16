package ftn.kts.dto;

import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

public class CategoryDTO {
    private Long id;

    @NotBlank(message = "Name is required!")
    private String name;

    private Set<SubcategoryDTO> subcategories;

    public CategoryDTO(Long id, String name){
        this.id  = id;
        this.name = name;
        this.subcategories = new HashSet<>();
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

    public Set<SubcategoryDTO> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(Set<SubcategoryDTO> subcategories) {
        this.subcategories = subcategories;
    }
}
