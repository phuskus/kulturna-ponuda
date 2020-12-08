package ftn.kts.service;

import ftn.kts.dto.SubcategoryDTO;
import ftn.kts.exceptions.UniqueConstraintViolationException;
import ftn.kts.model.*;
import ftn.kts.repository.CategoryRepository;
import ftn.kts.repository.SubcategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SubcategoryService {

	private SubcategoryRepository subcategoryRepository;
	private CategoryRepository categoryRepository;

	@Autowired
	public SubcategoryService(SubcategoryRepository subcategoryRepository, CategoryRepository categoryRepository) {
		this.subcategoryRepository = subcategoryRepository;
		this.categoryRepository = categoryRepository;
	}

	public List<SubcategoryDTO> getAllDTO() {
		List<Subcategory> subcategories = subcategoryRepository.findAll();
		List<SubcategoryDTO> dtoList = new ArrayList<SubcategoryDTO>();
		for (Subcategory o : subcategories) {
			dtoList.add(toDTO(o));
		}
		return dtoList;
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
		Category category = categoryRepository.findById(dto.getCategory()).get();
		return new Subcategory(dto.getId(), dto.getName(), category);
	}

	private SubcategoryDTO toDTO(Subcategory entity) {
		return new SubcategoryDTO(entity.getId(), entity.getName(), entity.getCategory());
	}

	private void updateSubcategory(Subcategory subcategory, SubcategoryDTO dto) {
		subcategory.setName(dto.getName());
		subcategory.setCategory(categoryRepository.findById(dto.getCategory()).get());
		// TODO: Add sets of subscriptions and cultural offers?
	}
}
