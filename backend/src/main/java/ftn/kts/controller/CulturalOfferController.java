package ftn.kts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
	

}
