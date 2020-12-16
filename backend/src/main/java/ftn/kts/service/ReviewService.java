package ftn.kts.service;

import ftn.kts.dto.ReviewDTO;
import ftn.kts.model.CulturalOffer;
import ftn.kts.model.RegisteredUser;
import ftn.kts.model.Review;
import ftn.kts.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ReviewService {

	private ReviewRepository reviewRepository;
	private RegisteredUserService userService;
	private CulturalOfferService offerService;
	private PictureService pictureService;

	@Autowired
	public ReviewService(ReviewRepository reviewRepository,
			CulturalOfferService offerService, PictureService pictureService) {
		this.reviewRepository = reviewRepository;
		this.offerService = offerService;
		this.pictureService = pictureService;
	}

	public Page<ReviewDTO> getAllDTO(Pageable pageable) {
		return reviewRepository.findAll(pageable).map(this::toDTO);
	}

	public ReviewDTO getOneDTO(long id) {
		Review review = getOne(id);
		ReviewDTO dto = toDTO(review);
		return dto;
	}

	public void create(ReviewDTO dto) {
		Review review = toEntity(dto);
		reviewRepository.save(review);
	}

	public ReviewDTO update(ReviewDTO dto, Long id) {
		Review review = getOne(id);
		reviewRepository.save(updateCategory(review, dto));
		return toDTO(review);
	}

	public void delete(Long id) {
		Review review = getOne(id);
		reviewRepository.delete(review);
	}

	public Review getOne(long id) {
		return reviewRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Review with id " + id + " doesn't exist!"));
	}

	public List<Review> getAll(long id) {
		return reviewRepository.findAll();
	}

	public Set<ReviewDTO> convertToDTO(Set<Review> reviews) {
		return reviews.stream().map(this::toDTO).collect(Collectors.toSet());
	}

	private Review toEntity(ReviewDTO dto) {
		RegisteredUser user = userService.getOne(dto.getUser());
		CulturalOffer offer = offerService.getOne(dto.getCulturalOffer());
		return new Review(dto.getId(), dto.getRating(), dto.getContent(), user, offer);
	}

	private ReviewDTO toDTO(Review review) {
		ReviewDTO dto = new ReviewDTO(review.getId(), review.getRating(), review.getContent(), review.getUser(),
				review.getCulturalOffer());
		dto.setPictures(pictureService.convertToDTO(review.getPictures()));
		return dto;
	}

	private Review updateCategory(Review review, ReviewDTO dto) {
		review.setRating(dto.getRating());
		review.setContent(dto.getContent());
		review.setCulturalOffer(offerService.getOne(dto.getCulturalOffer()));
		review.setUser(userService.getOne(dto.getUser()));

		return review;
	}

	@Autowired
	public void setUserService(RegisteredUserService userService) {
		this.userService = userService;
	}
}
