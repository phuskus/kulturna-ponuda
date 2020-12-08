package ftn.kts.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.kts.dto.CulturalOfferDTO;
import ftn.kts.exceptions.UniqueConstraintViolationException;
import ftn.kts.model.Admin;
import ftn.kts.model.CulturalOffer;
import ftn.kts.model.Subcategory;
import ftn.kts.repository.AdminRepository;
import ftn.kts.repository.CulturalOfferRepository;
import ftn.kts.repository.SubcategoryRepository;

@Service
public class CulturalOfferService {

	private CulturalOfferRepository offerRepository;
	private AdminRepository adminRepository;
	private SubcategoryRepository subcategoryRepository;

	@Autowired
	public CulturalOfferService(CulturalOfferRepository offerRepository, AdminRepository adminRepository,
			SubcategoryRepository subcategoryRepository) {
		this.offerRepository = offerRepository;
		this.adminRepository = adminRepository;
		this.subcategoryRepository = subcategoryRepository;
	}

	public List<CulturalOfferDTO> getAllDTO() {
		List<CulturalOffer> offers = offerRepository.findAll();
		List<CulturalOfferDTO> dtoList = new ArrayList<>();
		for (CulturalOffer o : offers) {
			dtoList.add(toDTO(o));
		}
		return dtoList;
	}

	public CulturalOfferDTO getOneDTO(long id) {
		CulturalOffer offer = getOne(id);
		CulturalOfferDTO dto = toDTO(offer);
		return dto;
	}

	public void create(CulturalOfferDTO dto) throws UniqueConstraintViolationException {
		checkUnique(dto);
		CulturalOffer offer = toEntity(dto);
		offerRepository.save(offer);
	}

	public CulturalOfferDTO update(CulturalOfferDTO dto, Long id) throws UniqueConstraintViolationException {
		CulturalOffer offer = getOne(id);
		dto.setId(id);
		checkUnique(dto);
		updateOffer(offer, dto);
		offerRepository.save(offer);
		return toDTO(offer);
	}

	public void delete(Long id) {
		CulturalOffer offer = getOne(id);
		offerRepository.delete(offer);
	}

	public CulturalOffer getOne(long id) {
		return offerRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Cultural offer with id " + id + " doesn't exist!"));
	}

	public List<CulturalOffer> getAll(long id) {
		return offerRepository.findAll();
	}
	
	private void checkUnique(CulturalOfferDTO dto) throws UniqueConstraintViolationException {
		CulturalOffer offer = offerRepository.findByNameIgnoringCase(dto.getName());
		if (offer != null) {
			if (dto.getId() == null) {
				throw new UniqueConstraintViolationException("Unique key constraint violated!", "name",
						"Cultural Offer with this field already exists!");
			} else {
				if (!offer.getId().equals(dto.getId()))
					throw new UniqueConstraintViolationException("Unique key constraint violated!", "name",
							"Cultural Offer with this field already exists!");							
			}
		}
	}
	
	private CulturalOffer toEntity(CulturalOfferDTO dto) {
		Admin admin = adminRepository.findById(dto.getAdmin()).get();
		Subcategory category = subcategoryRepository.findById(dto.getCategory()).get();
		CulturalOffer offer = new CulturalOffer(dto.getName(), dto.getDescription(), dto.getLatitude(),
				dto.getLongitude(), dto.getAddress(), dto.getCity(), dto.getRegion(), admin, category);
		return offer;
	}

	private CulturalOfferDTO toDTO(CulturalOffer entity) {
		CulturalOfferDTO dto = new CulturalOfferDTO(entity.getId(), entity.getName(), entity.getDescription(),
				entity.getLatitude(), entity.getLongitude(), entity.getAddress(), entity.getCity(), entity.getRegion(),
				entity.getAdmin(), entity.getCategory());
		return dto;
	}

	private void updateOffer(CulturalOffer offer, CulturalOfferDTO dto) {
		offer.setAddress(dto.getAddress());
		offer.setCity(dto.getCity());
		offer.setDescription(dto.getDescription());
		offer.setLatitude(dto.getLatitude());
		offer.setLongitude(dto.getLongitude());
		offer.setName(dto.getName());
		offer.setRegion(dto.getRegion());
	}
}
