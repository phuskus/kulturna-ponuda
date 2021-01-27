package ftn.kts.service;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.multipart.MultipartFile;

import ftn.kts.dto.PictureDTO;
import ftn.kts.dto.SubcategoryDTO;
import ftn.kts.exceptions.UniqueConstraintViolationException;
import ftn.kts.model.Category;
import ftn.kts.model.Picture;
import ftn.kts.model.Subcategory;
import ftn.kts.repository.SubcategoryRepository;

@Service
public class SubcategoryService {

	private SubcategoryRepository subcategoryRepository;
	private CategoryService categoryService;
	private PictureService pictureService;

	public Page<SubcategoryDTO> getAllDTO(Pageable pageable) {
		return subcategoryRepository.findAll(pageable).map(this::toDTO);
	}
	
	public Set<SubcategoryDTO> getAllDTO() {
		return subcategoryRepository.findAll().stream().map(this::toDTO).collect(Collectors.toSet());
	}

	public Set<SubcategoryDTO> convertToDTO(Set<Subcategory> subcategories) {
		return subcategories.stream().map(this::toDTO).collect(Collectors.toSet());
	}

	public SubcategoryDTO getOneDTO(long id) {
		return toDTO(getOne(id));
	}
	
	public Subcategory findByName(String categoryName) {
		return subcategoryRepository.findByNameIgnoringCase(categoryName);
	}

	public SubcategoryDTO create(SubcategoryDTO dto, MultipartFile[] files) throws UniqueConstraintViolationException, MethodArgumentNotValidException {
		checkUnique(dto);
		if (files.length != 1) {
			FieldError error = new FieldError("subcategory", "icon", "Subcategory must have exactly one picture as an icon!");
			DirectFieldBindingResult errors = new DirectFieldBindingResult(null, "subcategory");
			errors.addError(error);
			throw new MethodArgumentNotValidException(null, errors);
		}
		PictureDTO icon = null;
		try {
			icon = pictureService.add(files[0]);
		} catch (IOException e) {
			System.out.println("File upload failed");
		}
		dto.setIcon(icon);
		return save(dto);
	}
	
	public SubcategoryDTO save(SubcategoryDTO subcat) {
		Subcategory subcategory = toEntity(subcat);
		subcategoryRepository.save(subcategory);
		return toDTO(subcategory);
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

	public Subcategory findByNameIgnoringCase(String name) {
		return subcategoryRepository.findByNameIgnoringCase(name);
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
		Category category = categoryService.getOne(dto.getCategoryId());
		Picture icon = pictureService.getOne(dto.getIcon().getId());
		return new Subcategory(dto.getName(), category, icon);
	}

	private SubcategoryDTO toDTO(Subcategory entity) {
		PictureDTO icon = null;
		icon = this.pictureService.convertToDTO(entity.getIcon());
		boolean containsOffers = false;
		if (entity.getCulturalOffers() != null && entity.getCulturalOffers().size() != 0) {
			containsOffers = true;
		}
		return new SubcategoryDTO(entity.getId(), entity.getName(), entity.getCategory(), icon, containsOffers);
	}

	private void updateSubcategory(Subcategory subcategory, SubcategoryDTO dto) {
		subcategory.setName(dto.getName());
		subcategory.setCategory(categoryService.getOne(dto.getCategoryId()));
	}

	@Autowired
	public void setSubcategoryRepository(SubcategoryRepository subcategoryRepository) {
		this.subcategoryRepository = subcategoryRepository;
	}

	@Autowired
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@Autowired
	public void setPictureService(PictureService pictureService) {
		this.pictureService = pictureService;
	}

}
