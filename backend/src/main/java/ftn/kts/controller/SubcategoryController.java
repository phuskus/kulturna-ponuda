package ftn.kts.controller;

import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ftn.kts.dto.ReviewDTO;
import ftn.kts.dto.SubcategoryDTO;
import ftn.kts.exceptions.UniqueConstraintViolationException;
import ftn.kts.service.SubcategoryService;

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
	public ResponseEntity<Set<SubcategoryDTO>> getAllSubcategories() {
		Set<SubcategoryDTO> subcategories = service.getAllDTO();
		return new ResponseEntity<>(subcategories, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	public ResponseEntity<SubcategoryDTO> getSubcategory(@PathVariable("id") long id) {
		return new ResponseEntity<>(service.getOneDTO(id), HttpStatus.OK);
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<SubcategoryDTO> addSubcategory(@Valid @RequestParam String subcat,
			@RequestParam(value = "files", required = false) MultipartFile[] files)
			throws JsonProcessingException, UniqueConstraintViolationException, MethodArgumentNotValidException {
		ObjectMapper mapper = new ObjectMapper();
		SubcategoryDTO dto = mapper.readValue(subcat, SubcategoryDTO.class);
		SubcategoryDTO created = service.create(dto, files);
		return new ResponseEntity<>(created, HttpStatus.CREATED);
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
		return new ResponseEntity<>("{\"message\": \"Successfully deleted subcategory!\"}", HttpStatus.OK);
	}
}
