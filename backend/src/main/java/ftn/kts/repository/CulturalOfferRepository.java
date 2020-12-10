package ftn.kts.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import ftn.kts.model.CulturalOffer;

public interface CulturalOfferRepository extends JpaRepository<CulturalOffer, Long> {

	CulturalOffer findByNameIgnoringCase(String name);
	Page<CulturalOffer> findAll(Pageable pageable);
}
