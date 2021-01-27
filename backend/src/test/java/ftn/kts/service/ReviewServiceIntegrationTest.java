package ftn.kts.service;

import ftn.kts.dto.ReviewDTO;
import ftn.kts.dto.UserDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.NoSuchElementException;

import static ftn.kts.constants.ReviewConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class ReviewServiceIntegrationTest {

    @Autowired
    private ReviewService reviewService;


    @Test
    public void getAll_ValidPageObject_ReturnsReviews() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        Page<ReviewDTO> found = reviewService.getAllDTO(pageable);
        assertEquals(NUM_ITEMS, found.getTotalElements());
    }

    @Test
    public void getAll_NullPageableObject_ThrowsNullPtrException() {
        Pageable pageable = null;
        assertThrows(NullPointerException.class, () -> reviewService.getAllDTO(pageable));
    }

    @Test
    public void getOne_ExistentId_ReturnsReview() {
        ReviewDTO review = reviewService.getOneDTO(EXISTENT_ID);
        assertNotNull(review);
        assertEquals(CONTENT, review.getContent());
    }

    @Test
    public void getOne_NonexistentId_ThrowsNoSuchElementException() {
        assertThrows(NoSuchElementException.class, () -> reviewService.getOneDTO(NONEXISTENT_ID));
    }

    @Test
    public void getForOffer_NonexistentOffer_ThrowsNoSuchElementException() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        assertThrows(NoSuchElementException.class, () -> reviewService.getForCulturalOffer(NONEXISTENT_OFFER_ID, pageable));
    }

    @Test
    public void getForOffer_ExistentOffer_ReturnsReviews() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);

        Page<ReviewDTO> found = reviewService.getForCulturalOffer(EXISTENT_OFFER_ID, pageable);
        assertEquals(NUM_REVIEWS_FOR_OFFER, found.getTotalElements());
    }

    @Test
    public void searchForReviews_EmptyQuery_ReturnsAllReviews() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);

        Page<ReviewDTO> found = reviewService.search("", pageable);
        assertEquals(NUM_ITEMS, found.getTotalElements());
    }

    @Test
    public void searchForReviews_ExistingContent_ReturnsReview() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);

        Page<ReviewDTO> found = reviewService.search(CONTENT, pageable);
        assertEquals(1, found.getTotalElements());
    }

    @Test
    public void searchForReviews_BigUglyQueryString_ReturnsNoReviews() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);

        Page<ReviewDTO> found = reviewService.search(RANDOM_STRING, pageable);
        assertEquals(0, found.getTotalElements());
    }

    @Test
    public void createDelete_ValidNewObject_CreatesAndDeletesSuccessfully(){
        UserDTO user = new UserDTO(EXISTENT_USER_ID, "name", "name", EXISTEND_USERNAME, "pass");
        ReviewDTO review = new ReviewDTO(NEW_RATING, NEW_CONTENT, user, EXISTENT_OFFER_ID, "name");
        MultipartFile[] files = new MultipartFile[]{};
        ReviewDTO createdReview = reviewService.create(review, files);
        assertEquals(review, createdReview);

        // delete
        reviewService.delete(createdReview.getId());
        assertThrows(NoSuchElementException.class, () -> reviewService.getOneDTO(createdReview.getId()));
    }

    @Test
    public void delete_Nonexistent_Id_ThrowsNoSuchElementException(){
        assertThrows(EmptyResultDataAccessException.class, () -> reviewService.delete(NONEXISTENT_ID));
    }

}
