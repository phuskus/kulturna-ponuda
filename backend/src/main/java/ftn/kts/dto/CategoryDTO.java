package ftn.kts.dto;

import ftn.kts.model.Subcategory;

import javax.validation.constraints.NotBlank;
import java.util.Set;

public class CategoryDTO {
    private Long id;

    @NotBlank(message = "Name is required!")
    private String name;

//    private Set<SubCategoryDTO>

    public CategoryDTO(Long id, String name, Set<Subcategory> subcategorySet){
        this.id  = id;
        this.name = name;
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
}
