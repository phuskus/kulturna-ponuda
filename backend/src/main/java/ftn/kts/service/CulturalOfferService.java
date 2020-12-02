package ftn.kts.service;

import java.util.ArrayList;
import java.util.List;

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
	private SubcategoryService subcategoryService;

	@Autowired
	public CulturalOfferService(CulturalOfferRepository offerRepository, AdminService adminService, SubcategoryService subcategoryService) {
		this.offerRepository = offerRepository;
		this.adminService = adminService;
		this.subcategoryService = subcategoryService;
	}

	private CulturalOffer toEntity(CulturalOfferDTO dto) {
		Admin admin = adminService.getOne(dto.getAdmin());
		Subcategory category = subcategoryService.getOne(dto.getCategory());
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

	public List<CulturalOfferDTO> getAll() {
		List<CulturalOffer> offers = offerRepository.findAll();
		List<CulturalOfferDTO> dtoList = new ArrayList<CulturalOfferDTO>();
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
}
