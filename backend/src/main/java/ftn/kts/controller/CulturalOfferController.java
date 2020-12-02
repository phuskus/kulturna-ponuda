package ftn.kts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/cultural_offers")
public class CulturalOfferController {
	
    private CulturalOfferService service;

    @Autowired
    public CulturalOfferController(CulturalOfferService service) {
        this.service = service;
    }

	@GetMapping
	public ResponseEntity<List<CulturalOfferDTO>> getAllOffers() {
		List<CulturalOfferDTO> offers = service.getAll();
		return new ResponseEntity<>(offers, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CulturalOfferDTO> getOffer(@PathVariable("id") long id) {
		try {
			return new ResponseEntity<>(service.getOne(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping
	public ResponseEntity<Object> addOffer(@RequestBody CulturalOfferDTO dto) {
		try {
			service.create(dto);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CulturalOfferDTO> updateOffer(@RequestBody CulturalOfferDTO dto, @PathVariable long id) {
		try {
			CulturalOfferDTO updated = service.update(dto, id);
			return new ResponseEntity<>(updated, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<CulturalOfferDTO> deleteOffer(@PathVariable("id") long id) {
		try {
			service.delete(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}


}
