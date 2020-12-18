package ftn.kts.service;

import ftn.kts.dto.SubcategoryDTO;
import ftn.kts.exceptions.UniqueConstraintViolationException;
import ftn.kts.model.*;
import ftn.kts.repository.SubcategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SubcategoryService {

	private SubcategoryRepository subcategoryRepository;
	private CategoryService categoryService;

	public Page<SubcategoryDTO> getAllDTO(Pageable pageable) {
		return subcategoryRepository.findAll(pageable).map(this::toDTO);
	}

	public Set<SubcategoryDTO> convertToDTO(Set<Subcategory> subcategories) {
		return subcategories.stream().map(this::toDTO).collect(Collectors.toSet());
	}

	public SubcategoryDTO getOneDTO(long id) {
		return toDTO(getOne(id));
	}

	public void create(SubcategoryDTO dto) throws UniqueConstraintViolationException {
		checkUnique(dto);
		Subcategory subcategory = toEntity(dto);
		subcategoryRepository.save(subcategory);
	}

	public SubcategoryDTO update(SubcategoryDTO dto, Long id) throws UniqueConstraintViolationException {
		Subcategory subcategory = getOne(id);
		dto.setId(id);
		checkUnique(dto);
		updateSubcategory(subcategory, dto);
		return toDTO(subcategory);
	}

	public void delete(Long id) {
		Subcategory subcategory = getOne(id);
		subcategoryRepository.delete(subcategory);
	}

	public Subcategory getOne(long id) {
		return subcategoryRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Subcategory with id " + id + " doesn't exist!"));
	}

	public List<Subcategory> getAll(long id) {
		return subcategoryRepository.findAll();
	}
	
	private void checkUnique(SubcategoryDTO dto) throws UniqueConstraintViolationException {
		Subcategory subcategory = subcategoryRepository.findByNameIgnoringCase(dto.getName());
		if (subcategory != null) {
			if (dto.getId() == null) {
				throw new UniqueConstraintViolationException("Unique key constraint violated!", "name",
						"Category with this field already exists!");
			} else {
				if (!subcategory.getId().equals(dto.getId()))
					throw new UniqueConstraintViolationException("Unique key constraint violated!", "name",
							"Category with this field already exists!");							
			}
		}
	}

	private Subcategory toEntity(SubcategoryDTO dto) {
		Category category = categoryService.getOne(dto.getCategory());
		return new Subcategory(dto.getId(), dto.getName(), category);
	}

	private SubcategoryDTO toDTO(Subcategory entity) {
		return new SubcategoryDTO(entity.getId(), entity.getName(), entity.getCategory());
	}

	private void updateSubcategory(Subcategory subcategory, SubcategoryDTO dto) {
		subcategory.setName(dto.getName());
		subcategory.setCategory(categoryService.getOne(dto.getCategory()));
		// TODO: Add sets of subscriptions and cultural offers?
	}

	@Autowired
	public void setSubcategoryRepository(SubcategoryRepository subcategoryRepository) {
		this.subcategoryRepository = subcategoryRepository;
	}

	@Autowired
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
}
