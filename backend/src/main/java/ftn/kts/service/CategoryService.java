package ftn.kts.service;

import ftn.kts.dto.CategoryDTO;
import ftn.kts.exceptions.UniqueConstraintViolationException;
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
	private SubcategoryService subcategoryService;

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

	public CategoryDTO create(CategoryDTO dto) throws UniqueConstraintViolationException {
		checkUnique(dto);
		Category category = toEntity(dto);
		return toDTO(categoryRepository.save(category));
	}

	public CategoryDTO update(CategoryDTO dto, Long id) throws UniqueConstraintViolationException {
		Category category = getOne(id);
		dto.setId(id);
		checkUnique(dto);
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

	private void checkUnique(CategoryDTO dto) throws UniqueConstraintViolationException {
		Category category = categoryRepository.findByNameIgnoringCase(dto.getName());
		if (category != null) {
			if (dto.getId() == null) {
				throw new UniqueConstraintViolationException("Unique key constraint violated!", "name",
						"Category with this field already exists!");
			} else {
				if (!category.getId().equals(dto.getId()))
					throw new UniqueConstraintViolationException("Unique key constraint violated!", "name",
							"Category with this field already exists!");							
			}
		}
	}

	private Category toEntity(CategoryDTO dto) {
		return new Category(dto.getId(), dto.getName());
	}

	private CategoryDTO toDTO(Category cat) {
		CategoryDTO dto = new CategoryDTO(cat.getId(), cat.getName());
		dto.setSubcategories(subcategoryService.convertToDTO(cat.getSubcategories()));
		return dto;
	}

	private Category updateCategory(Category category, CategoryDTO dto) {
		category.setName(dto.getName());
		return category;
	}

	@Autowired
	public void setCategoryRepository(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	@Autowired
	public void setSubcategoryService(SubcategoryService subcategoryService) {
		this.subcategoryService = subcategoryService;
	}
}
