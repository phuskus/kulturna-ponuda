package ftn.kts.service;

import ftn.kts.dto.CategoryDTO;
import ftn.kts.model.Category;
import ftn.kts.repository.CategoryRepository;
import ftn.kts.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDTO> getAll() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDTO> dtoList = new ArrayList<CategoryDTO>();
        for (Category c : categories)
            dtoList.add(toDTO(c));
        return dtoList;
    }

    public CategoryDTO getOne(long id) {
        Category category = categoryRepository.findById(id).get();
        return toDTO(category);
    }


    public void create(CategoryDTO dto) {
        Category cat = toEntity(dto);
        categoryRepository.save(cat);
    }

    public CategoryDTO update(CategoryDTO dto, Long id) {
        Category cat = categoryRepository.findById(id).get();
        updateCategory(cat, dto);
        categoryRepository.save(cat);
        return toDTO(updateCategory(cat, dto));
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
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
