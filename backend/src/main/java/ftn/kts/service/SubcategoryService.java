package ftn.kts.service;

import ftn.kts.dto.SubcategoryDTO;
import ftn.kts.model.*;
import ftn.kts.model.Subcategory;
import ftn.kts.repository.CategoryRepository;
import ftn.kts.repository.SubcategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubcategoryService {

    private SubcategoryRepository subcategoryRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public SubcategoryService(SubcategoryRepository subcategoryRepository, CategoryRepository categoryRepository) {
        this.subcategoryRepository = subcategoryRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<SubcategoryDTO> getAll() {
        List<Subcategory> subcategories = subcategoryRepository.findAll();
        List<SubcategoryDTO> dtoList = new ArrayList<SubcategoryDTO>();
        for (Subcategory o : subcategories) {
            dtoList.add(toDTO(o));
        }
        return dtoList;
    }

    public SubcategoryDTO getOne(long id) {
        return toDTO(subcategoryRepository.findById(id).get());
    }

    public void create(SubcategoryDTO dto) {
        Subcategory subcategory = toEntity(dto);
        subcategoryRepository.save(subcategory);
    }

    public SubcategoryDTO update(SubcategoryDTO dto, Long id) {
        Subcategory subcategory = subcategoryRepository.findById(id).get();
        updateSubcategory(subcategory, dto);
        return toDTO(subcategory);
    }

    public void delete(Long id) {
        subcategoryRepository.deleteById(id);
    }

    private Subcategory toEntity(SubcategoryDTO dto) {
        Category category = categoryRepository.findById(dto.getCategoryId()).get();
        return new Subcategory(dto.getId(), dto.getName(), category);
    }

    private SubcategoryDTO toDTO(Subcategory entity) {
        return new SubcategoryDTO(entity.getId(), entity.getName(), entity.getCategory());
    }

    private void updateSubcategory(Subcategory subcategory, SubcategoryDTO dto) {
        subcategory.setName(dto.getName());
        subcategory.setCategory(categoryRepository.findById(dto.getCategoryId()).get());
        // TODO: Add sets of subscriptions and cultural offers?
    }
}
