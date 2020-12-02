package ftn.kts.service;

import java.util.ArrayList;
import java.util.List;

import ftn.kts.repository.SubcategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.kts.dto.CulturalOfferDTO;
import ftn.kts.model.Admin;
import ftn.kts.model.CulturalOffer;
import ftn.kts.model.Subcategory;
import ftn.kts.repository.CulturalOfferRepository;

@Service
public class CulturalOfferService {

	private CulturalOfferRepository offerRepository;
	private AdminService adminService;
	private SubcategoryRepository subcategoryRepository;

	@Autowired
	public CulturalOfferService(CulturalOfferRepository offerRepository, AdminService adminService, SubcategoryRepository subcategoryRepository) {
		this.offerRepository = offerRepository;
		this.adminService = adminService;
		this.subcategoryRepository = subcategoryRepository;
	}

	public List<CulturalOfferDTO> getAll() {
		List<CulturalOffer> offers = offerRepository.findAll();
		List<CulturalOfferDTO> dtoList = new ArrayList<>();
		for (CulturalOffer o : offers) {
			dtoList.add(toDTO(o));
		}
		return dtoList;
	}

	public CulturalOfferDTO getOne(long id) {
		CulturalOffer offer = offerRepository.findById(id).get();
		CulturalOfferDTO dto = toDTO(offer);
		return dto;
	}

	public void create(CulturalOfferDTO dto) {
		CulturalOffer offer = toEntity(dto);
		offerRepository.save(offer);
	}
	
	public CulturalOfferDTO update(CulturalOfferDTO dto, Long id) {
		CulturalOffer offer = offerRepository.findById(id).get();
		updateOffer(offer, dto);
		return toDTO(offer);
	}
	
	public void delete(Long id) {
		offerRepository.deleteById(id);
	}
	
	private CulturalOffer toEntity(CulturalOfferDTO dto) {
		Admin admin = adminService.getOne(dto.getAdmin());
		Subcategory category = subcategoryRepository.findById(dto.getCategory()).get();
		CulturalOffer offer = new CulturalOffer(dto.getName(), dto.getDescription(), dto.getLatitude(),
				dto.getLongitude(), dto.getAddress(), dto.getCity(), dto.getRegion(), admin,
				category);
		return offer;
	}

	private CulturalOfferDTO toDTO(CulturalOffer entity) {
		CulturalOfferDTO dto = new CulturalOfferDTO(entity.getId(), entity.getName(), entity.getDescription(), entity.getLatitude(),
				entity.getLongitude(), entity.getAddress(), entity.getCity(), entity.getRegion(), entity.getAdmin(),
				entity.getCategory());
		return dto;
	}
	
	private CulturalOffer updateOffer(CulturalOffer offer, CulturalOfferDTO dto) {
		offer.setAddress(dto.getAddress());
		offer.setCity(dto.getCity());
		offer.setDescription(dto.getDescription());
		offer.setLatitude(dto.getLatitude());
		offer.setLongitude(dto.getLongitude());
		offer.setName(dto.getName());
		offer.setRegion(dto.getRegion());
		return offer;
	}
}
