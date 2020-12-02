package ftn.kts.service;

import ftn.kts.dto.ReviewDTO;
import ftn.kts.model.Review;
import ftn.kts.repository.CulturalOfferRepository;
import ftn.kts.repository.RegisteredUserRepository;
import ftn.kts.repository.ReviewRepository;
import ftn.kts.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {
    private ReviewRepository reviewRepository;
    private RegisteredUserRepository userRepository;
    private CulturalOfferRepository offerRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, RegisteredUserRepository userRepository, CulturalOfferRepository offerRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.offerRepository = offerRepository;
    }

    public List<ReviewDTO> getAll(){
        List<Review> reviews = reviewRepository.findAll();
        List<ReviewDTO> dtoList = new ArrayList<>();
        for (Review c : reviews)
            dtoList.add(toDTO(c));
        return dtoList;
    }

    public ReviewDTO getOne(long id) {
        Review review = reviewRepository.findById(id).get();
        ReviewDTO dto = toDTO(review);
        return dto;
    }

    public void create(ReviewDTO dto) {
        Review review = toEntity(dto);
        reviewRepository.save(review);
    }

    public ReviewDTO update(ReviewDTO dto, Long id) {
        Review review = reviewRepository.findById(id).get();
        updateCategory(review, dto);
        return toDTO(review);
    }

    public void delete(Long id) {
        reviewRepository.deleteById(id);
    }


    private Review toEntity(ReviewDTO dto) {
        return new Review(dto.getId(), dto.getRating(), dto.getContent());
    }

    private ReviewDTO toDTO(Review review) {
        return new ReviewDTO(review.getId(), review.getRating(), review.getContent(), review.getUser(), review.getCulturalOffer());
    }

    private Review updateCategory(Review review, ReviewDTO dto){
        review.setRating(dto.getRating());
        review.setContent(dto.getContent());
        review.setCulturalOffer(offerRepository.findById(dto.getCulturalOffer()).get());
        review.setUser(userRepository.findById(dto.getUser()).get());

        return review;
    }
}
