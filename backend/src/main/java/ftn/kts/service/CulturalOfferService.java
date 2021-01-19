package ftn.kts.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ftn.kts.dto.CulturalOfferDTO;
import ftn.kts.dto.PictureDTO;
import ftn.kts.exceptions.UniqueConstraintViolationException;
import ftn.kts.model.Admin;
import ftn.kts.model.CulturalOffer;
import ftn.kts.model.Picture;
import ftn.kts.model.Subcategory;
import ftn.kts.repository.CulturalOfferRepository;
import ftn.kts.repository.specifications.CulturalOfferSpecification;

@Service
@Transactional
public class CulturalOfferService {

	private CulturalOfferRepository offerRepository;
	private AdminService adminService;
	private SubcategoryService subcategoryService;
	private ReviewService reviewService;
	private PictureService pictureService;
	private PostService postService;

	@Autowired
	public CulturalOfferService(CulturalOfferRepository offerRepository, AdminService adminService,
			SubcategoryService subcategoryService) {
		this.offerRepository = offerRepository;
		this.adminService = adminService;
		this.subcategoryService = subcategoryService;
	}

	public Page<CulturalOfferDTO> getAllDTO(Pageable paging) {
		return offerRepository.findAll(paging).map(this::toDTO);
	}
	
	public CulturalOfferDTO getOneDTO(long id) {
		CulturalOffer offer = getOne(id);
		CulturalOfferDTO dto = toDTO(offer);
		return dto;
	}

	public CulturalOfferDTO create(CulturalOfferDTO dto) throws UniqueConstraintViolationException {
		checkUnique(dto);
		CulturalOffer offer = toEntity(dto);
		CulturalOffer saved = offerRepository.save(offer);
		return toDTO(saved);
	}

	public CulturalOfferDTO update(CulturalOfferDTO dto, Long id) throws UniqueConstraintViolationException {
		CulturalOffer offer = getOne(id);
		dto.setId(id);
		checkUnique(dto);
		updateOffer(offer, dto);
		CulturalOffer saved = offerRepository.save(offer);
		return toDTO(saved);
	}

	public void delete(Long id) {
		CulturalOffer offer = getOne(id);
		offerRepository.delete(offer);
	}

	public CulturalOffer getOne(long id) {
		return offerRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Cultural offer with id " + id + " doesn't exist!"));
	}
	
	public List<CulturalOffer> getAll() {
		return offerRepository.findAll();			
	}
	

	public Page<CulturalOfferDTO> filterAll(String categoryName, String query, String regionNames, String cityNames,
			Pageable paging) {
		List<String> regionList = new ArrayList<>();
		List<String> cityList = new ArrayList<>();
		if (!regionNames.equals("")) {
			String[] regions = regionNames.split(",");
			for (String s : regions) {
				regionList.add(s.toLowerCase());
			}
		}
		if (!cityNames.equals("")) {
			String[] cities = cityNames.split(",");
			for (String s : cities) {
				cityList.add(s.toLowerCase());
			}
		}
		CulturalOfferSpecification spec = new CulturalOfferSpecification(query, categoryName, regionList, cityList);
		return offerRepository.findAll(spec, paging).map(this::toDTO);
		
	}

	public Page<CulturalOfferDTO> filterCategory(long id, Pageable paging) {
		Subcategory category = subcategoryService.getOne(id);
		return offerRepository.findByCategory(category, paging).map(this::toDTO);
	}

	public Page<CulturalOfferDTO> filterCity(String city, Pageable paging) {
		return offerRepository.findByCityContainingIgnoreCase(city, paging).map(this::toDTO);
	}

	public Page<CulturalOfferDTO> filterName(String name, Pageable paging) {
		return offerRepository.findByNameContainingIgnoreCase(name, paging).map(this::toDTO);
	}

	public Page<CulturalOfferDTO> filterDescription(String desc, Pageable paging) {
		return offerRepository.findByDescriptionContainingIgnoreCase(desc, paging).map(this::toDTO);
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
		Admin admin = adminService.getOne(dto.getAdmin());
		Subcategory category = subcategoryService.getOne(dto.getCategory());
		CulturalOffer offer = new CulturalOffer(dto.getName(), dto.getDescription(), dto.getLatitude(),
				dto.getLongitude(), dto.getAddress(), dto.getCity(), dto.getRegion(), admin, category);
		HashSet<Picture> pictures = new HashSet<>();
		for (PictureDTO picture : dto.getPictures()) {
			pictures.add(pictureService.getOne(picture.getId()));
		}
		offer.setPictures(pictures);
		return offer;
	}
	
	private CulturalOfferDTO toDTO(CulturalOffer entity) {
		CulturalOfferDTO dto = new CulturalOfferDTO(entity.getId(), entity.getName(), entity.getDescription(),
				entity.getLatitude(), entity.getLongitude(), entity.getAddress(), entity.getCity(), entity.getRegion(),
				entity.getAdmin().getId(), entity.getCategory().getId());
		dto.setPictures(pictureService.convertToDTO(entity.getPictures()));
		dto.setPosts(postService.convertToDTO(entity.getPosts()));
		dto.setReviews(reviewService.convertToDTO(entity.getReviews()));
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
		HashSet<Picture> pictures = new HashSet<>();
		for (PictureDTO picture : dto.getPictures()) {
			pictures.add(pictureService.getOne(picture.getId()));
		}
		offer.setPictures(pictures);
	}

	@Autowired
	public void setReviewService(ReviewService reviewService) {
		this.reviewService = reviewService;
	}

	@Autowired
	public void setPictureService(PictureService pictureService) {
		this.pictureService = pictureService;
	}

	@Autowired
	public void setPostService(PostService postService) {
		this.postService = postService;
	}


}
