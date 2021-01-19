package ftn.kts.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ftn.kts.dto.CulturalOfferDTO;
import ftn.kts.exceptions.UniqueConstraintViolationException;
import ftn.kts.service.CulturalOfferService;

@RestController
@Validated
@RequestMapping("/cultural_offers")
public class CulturalOfferController {

	private CulturalOfferService service;

	@Autowired
	public CulturalOfferController(CulturalOfferService service) {
		this.service = service;
	}

	@GetMapping
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	public ResponseEntity<Page<CulturalOfferDTO>> getAllOffers(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(defaultValue = "false") String descending) {
		Pageable paging;
		if (descending.equals("true"))
			paging = PageRequest.of(pageNo, pageSize, Sort.by(Direction.DESC, sortBy));
		else
			paging = PageRequest.of(pageNo, pageSize, Sort.by(Direction.ASC, sortBy));
		Page<CulturalOfferDTO> offers = service.getAllDTO(paging);
		return new ResponseEntity<>(offers, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	public ResponseEntity<CulturalOfferDTO> getOffer(@PathVariable("id") long id) {
		return new ResponseEntity<>(service.getOneDTO(id), HttpStatus.OK);
	}


	@GetMapping("/search")
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	public ResponseEntity<Page<CulturalOfferDTO>> filterOffers(@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize,
			@RequestParam(defaultValue = "id") String sortBy, @RequestParam(defaultValue = "false") String descending,
			@RequestParam(defaultValue = "") String categoryName, @RequestParam(defaultValue = "") String query,
			@RequestParam(defaultValue = "") String regionNames, @RequestParam(defaultValue = "") String cityNames) {
		Pageable paging;
		if (descending.equals("true"))
			paging = PageRequest.of(pageNo, pageSize, Sort.by(Direction.DESC, sortBy));
		else
			paging = PageRequest.of(pageNo, pageSize, Sort.by(Direction.ASC, sortBy));
		Page<CulturalOfferDTO> page = service.filterAll(categoryName, query, regionNames, cityNames, paging);
		return new ResponseEntity<>(page, HttpStatus.OK);
	}
	
	@GetMapping("/category/{id}")
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	public ResponseEntity<Page<CulturalOfferDTO>> filterOffersCategory(@PathVariable("id") long id,
			@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize,
			@RequestParam(defaultValue = "id") String sortBy, @RequestParam(defaultValue = "false") String descending) {
		Pageable paging;
		if (descending.equals("true"))
			paging = PageRequest.of(pageNo, pageSize, Sort.by(Direction.DESC, sortBy));
		else
			paging = PageRequest.of(pageNo, pageSize, Sort.by(Direction.ASC, sortBy));
		Page<CulturalOfferDTO> page = service.filterCategory(id, paging);
		return new ResponseEntity<>(page, HttpStatus.OK);
	}
	
	
	@GetMapping("/city/{city}")
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	public ResponseEntity<Page<CulturalOfferDTO>> filterOffersCity(@PathVariable("city") String city,
			@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize,
			@RequestParam(defaultValue = "id") String sortBy, @RequestParam(defaultValue = "false") String descending) {
		Pageable paging;
		if (descending.equals("true"))
			paging = PageRequest.of(pageNo, pageSize, Sort.by(Direction.DESC, sortBy));
		else
			paging = PageRequest.of(pageNo, pageSize, Sort.by(Direction.ASC, sortBy));
		Page<CulturalOfferDTO> page = service.filterCity(city, paging);
		return new ResponseEntity<>(page, HttpStatus.OK);
	}
	
	@GetMapping("/name/{name}")
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	public ResponseEntity<Page<CulturalOfferDTO>> filterOffersName(@PathVariable("name") String name,
			@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize,
			@RequestParam(defaultValue = "id") String sortBy, @RequestParam(defaultValue = "false") String descending) {
		Pageable paging;
		if (descending.equals("true"))
			paging = PageRequest.of(pageNo, pageSize, Sort.by(Direction.DESC, sortBy));
		else
			paging = PageRequest.of(pageNo, pageSize, Sort.by(Direction.ASC, sortBy));
		Page<CulturalOfferDTO> page = service.filterName(name, paging);
		return new ResponseEntity<>(page, HttpStatus.OK);
	}

	@GetMapping("/description/{desc}")
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	public ResponseEntity<Page<CulturalOfferDTO>> filterOffersDescription(@PathVariable("desc") String desc,
			@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize,
			@RequestParam(defaultValue = "id") String sortBy, @RequestParam(defaultValue = "false") String descending) {
		Pageable paging;
		if (descending.equals("true"))
			paging = PageRequest.of(pageNo, pageSize, Sort.by(Direction.DESC, sortBy));
		else
			paging = PageRequest.of(pageNo, pageSize, Sort.by(Direction.ASC, sortBy));
		Page<CulturalOfferDTO> page = service.filterDescription(desc, paging);
		return new ResponseEntity<>(page, HttpStatus.OK);
	}

	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CulturalOfferDTO> addOffer(@Valid @RequestBody CulturalOfferDTO dto)
			throws UniqueConstraintViolationException {
		CulturalOfferDTO created = service.create(dto);
		return new ResponseEntity<>(created, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> updateOffer(@Valid @RequestBody CulturalOfferDTO dto, @PathVariable long id)
			throws UniqueConstraintViolationException {
		CulturalOfferDTO updated = service.update(dto, id);
		return new ResponseEntity<>(updated, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deleteOffer(@PathVariable("id") long id) {
		service.delete(id);
		return new ResponseEntity<>("Successfully deleted cultural offer!", HttpStatus.OK);
	}

}
