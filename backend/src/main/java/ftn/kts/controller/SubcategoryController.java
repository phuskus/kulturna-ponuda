package ftn.kts.controller;

import ftn.kts.dto.SubcategoryDTO;
import ftn.kts.exceptions.UniqueConstraintViolationException;
import ftn.kts.service.SubcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/subcategories")
public class SubcategoryController {
	private SubcategoryService service;

	@Autowired
	public SubcategoryController(SubcategoryService service) {
		this.service = service;
	}

	@GetMapping
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	public ResponseEntity<Page<SubcategoryDTO>> getAllSubcategories(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(defaultValue = "true") String descending) {
		Pageable paging;
		if (descending.equals("true"))
			paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, sortBy));
		else
			paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.ASC, sortBy));
		Page<SubcategoryDTO> subcategories = service.getAllDTO(paging);
		return new ResponseEntity<>(subcategories, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	public ResponseEntity<SubcategoryDTO> getSubcategory(@PathVariable("id") long id) {
		return new ResponseEntity<>(service.getOneDTO(id), HttpStatus.OK);
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> addSubcategory(@Valid @RequestBody SubcategoryDTO dto)
			throws UniqueConstraintViolationException {
		service.create(dto);
		return new ResponseEntity<>("Successfully added subcategory!", HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<SubcategoryDTO> updateSubcategory(@Valid @RequestBody SubcategoryDTO dto,
			@PathVariable long id) throws UniqueConstraintViolationException {
		SubcategoryDTO updated = service.update(dto, id);
		return new ResponseEntity<>(updated, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deleteSubcategory(@PathVariable("id") long id) {
		service.delete(id);
		return new ResponseEntity<>("Successfully deleted subcategory!", HttpStatus.OK);
	}
}
