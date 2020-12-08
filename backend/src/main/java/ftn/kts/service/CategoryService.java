package ftn.kts.service;

import ftn.kts.dto.CategoryDTO;
import ftn.kts.model.Category;
import ftn.kts.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDTO> getAllDTO() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDTO> dtoList = new ArrayList<CategoryDTO>();
        for (Category c : categories)
            dtoList.add(toDTO(c));
        return dtoList;
    }

    public CategoryDTO getOneDTO(long id) {
        Category category = getOne(id);
        return toDTO(category);
    }


    public void create(CategoryDTO dto) {
        Category category = toEntity(dto);
        categoryRepository.save(category);
    }

    public CategoryDTO update(CategoryDTO dto, Long id) {
        Category category = getOne(id);
        updateCategory(category, dto);
        categoryRepository.save(category);
        return toDTO(updateCategory(category, dto));
    }

    public void delete(Long id) {
        Category category = getOne(id);
        categoryRepository.delete(category);
    }

	public Category getOne(long id) {
		return categoryRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Category with id " + id + " doesn't exist!"));
	}

	public List<Category> getAll(long id) {
		return categoryRepository.findAll();
	}
    
    private Category toEntity(CategoryDTO dto) {
        return new Category(dto.getId(), dto.getName());
    }

    private CategoryDTO toDTO(Category cat) {
        return new CategoryDTO(cat.getId(), cat.getName(), cat.getSubcategories());
    }

    private Category updateCategory(Category category, CategoryDTO dto){
        category.setName(dto.getName());
        return category;
    }
}
