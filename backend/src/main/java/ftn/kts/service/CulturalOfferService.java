package ftn.kts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.kts.repository.CulturalOfferRepository;

@Service
public class CulturalOfferService {

    private CulturalOfferRepository offerRepository;

    @Autowired
    public CulturalOfferService(CulturalOfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }
}
