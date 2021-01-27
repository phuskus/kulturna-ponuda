package ftn.kts.service;

import ftn.kts.dto.PictureDTO;
import ftn.kts.dto.ReviewDTO;
import ftn.kts.model.CulturalOffer;
import ftn.kts.model.Picture;
import ftn.kts.model.RegisteredUser;
import ftn.kts.model.Review;
import ftn.kts.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.Date;
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

    public Page<ReviewDTO> getForCulturalOffer(long offerId, Pageable pageable) {
        CulturalOffer offer = offerService.getOne(offerId);
        return reviewRepository.findByCulturalOffer(offerId, pageable).map(this::toDTO);
    }

    public Page<ReviewDTO> search(String query, Pageable pageable) {
        System.out.println(query.toLowerCase());
        return reviewRepository.search(query.toLowerCase(), pageable).map(this::toDTO);
    }

    public ReviewDTO create(ReviewDTO dto, MultipartFile[] files) {
        if (files == null)
            files = new MultipartFile[]{};

        for (MultipartFile file : files) {
            try {
                dto.getPictures().add(pictureService.add(file));
            } catch (IOException ex) {
                System.out.println("File upload failed: " + file.getName());
            }
        }
        Review review = toEntity(dto);
        review.setDatePosted(new Date());
        return toDTO(reviewRepository.save(review));
    }


    public void delete(Long id) {
        reviewRepository.deleteById(id);
    }

    public Review getOne(long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Review with id " + id + " doesn't exist!"));
    }

    public List<Review> getAll() {
        return reviewRepository.findAll();
    }

    public Set<ReviewDTO> convertToDTO(Set<Review> reviews) {
        return reviews.stream().map(this::toDTO).collect(Collectors.toSet());
    }

    private Review toEntity(ReviewDTO dto) {
        RegisteredUser user = userService.getOne(dto.getUser().getId());
        CulturalOffer offer = offerService.getOne(dto.getCulturalOfferId());
        Review review = new Review(dto.getId(), dto.getRating(), dto.getContent(), user, offer);
        if (dto.getDatePosted() != null)
            review.setDatePosted(dto.getDatePosted());
        if (dto.getPictures().size() > 0)
            review.setPictures(pictureService.convertToEntity(dto.getPictures()));
        return review;
    }

    private ReviewDTO toDTO(Review review) {
        ReviewDTO dto = new ReviewDTO(review.getId(), review.getRating(), review.getContent(), review.getUser(),
                review.getCulturalOffer());
        dto.setPictures(pictureService.convertToDTO(review.getPictures()));
        dto.setDatePosted(review.getDatePosted());
        return dto;
    }

    public ReviewDTO createMock(ReviewDTO dto, MultipartFile[] files){
        if (files == null)
            files = new MultipartFile[]{};

        for (MultipartFile file : files) {
            try {
                dto.getPictures().add(pictureService.add(file));
            } catch (IOException ex) {
                System.out.println("File upload failed: " + file.getName());
            }
        }
        Review review = toEntity(dto);
        return toDTO(reviewRepository.save(review));
    }

    @Autowired
    public void setUserService(RegisteredUserService userService) {
        this.userService = userService;
    }

}
