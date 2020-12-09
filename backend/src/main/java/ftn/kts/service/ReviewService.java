package ftn.kts.service;

import ftn.kts.dto.ReviewDTO;
import ftn.kts.model.CulturalOffer;
import ftn.kts.model.RegisteredUser;
import ftn.kts.model.Review;
import ftn.kts.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ReviewService {
	private ReviewRepository reviewRepository;
	private RegisteredUserService userService;
	private CulturalOfferService offerService;

	@Autowired
	public ReviewService(ReviewRepository reviewRepository, RegisteredUserService userService,
			CulturalOfferService offerService) {
		this.reviewRepository = reviewRepository;
		this.userService = userService;
		this.offerService = offerService;
	}

	public List<ReviewDTO> getAllDTO() {
		List<Review> reviews = reviewRepository.findAll();
		List<ReviewDTO> dtoList = new ArrayList<>();
		for (Review c : reviews)
			dtoList.add(toDTO(c));
		return dtoList;
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

	private Review toEntity(ReviewDTO dto) {
		RegisteredUser user = userService.getOne(dto.getUser());
		CulturalOffer offer = offerService.getOne(dto.getCulturalOffer());
		return new Review(dto.getId(), dto.getRating(), dto.getContent(), user, offer);
	}

	private ReviewDTO toDTO(Review review) {
		return new ReviewDTO(review.getId(), review.getRating(), review.getContent(), review.getUser(),
				review.getCulturalOffer());
	}

	private Review updateCategory(Review review, ReviewDTO dto) {
		review.setRating(dto.getRating());
		review.setContent(dto.getContent());
		review.setCulturalOffer(offerService.getOne(dto.getCulturalOffer()));
		review.setUser(userService.getOne(dto.getUser()));

		return review;
	}
}
