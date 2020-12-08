package ftn.kts.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ftn.kts.dto.CulturalOfferDTO;
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
	public ResponseEntity<List<CulturalOfferDTO>> getAllOffers() {
		List<CulturalOfferDTO> offers = service.getAllDTO();
		return new ResponseEntity<>(offers, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CulturalOfferDTO> getOffer(@PathVariable("id") long id) {
		return new ResponseEntity<>(service.getOneDTO(id), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<String> addOffer(@Valid @RequestBody CulturalOfferDTO dto) {
		service.create(dto);
		return new ResponseEntity<>("Successfully added cultural offer!", HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Object> updateOffer(@Valid @RequestBody CulturalOfferDTO dto, @PathVariable long id) {
		CulturalOfferDTO updated = service.update(dto, id);
		return new ResponseEntity<>(updated, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteOffer(@PathVariable("id") long id) {
		service.delete(id);
		return new ResponseEntity<>("Successfully deleted cultural offer!", HttpStatus.OK);
	}

}
