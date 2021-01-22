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
    public void createDelete_ValidNewObject_CreatesAndDeletesSuccessfully(){
        UserDTO user = new UserDTO(EXISTENT_USER_ID, "name", "name", EXISTEND_USERNAME, "pass");
        ReviewDTO review = new ReviewDTO(NEW_RATING, NEW_CONTENT, user, EXISTENT_OFFER_ID, "name");
        ReviewDTO createdReview = reviewService.create(review);
        assertEquals(review, createdReview);

        // delete
        reviewService.delete(createdReview.getId());
        assertThrows(NoSuchElementException.class, () -> reviewService.getOneDTO(createdReview.getId()));
    }

    @Test
    public void delete_Nonexistent_Id_ThrowsNoSuchElementException(){
        assertThrows(EmptyResultDataAccessException.class, () -> reviewService.delete(NONEXISTENT_ID));
    }

    @Test
    public void update_SetNewContent_ContentChanged() {
        ReviewDTO oldReview = reviewService.getOneDTO(EXISTENT_ID);
        assertEquals(CONTENT, oldReview.getContent());

        oldReview.setContent(NEW_CONTENT);
        reviewService.update(oldReview, oldReview.getId());

        ReviewDTO newReview = reviewService.getOneDTO(EXISTENT_ID);
        assertEquals(NEW_CONTENT, newReview.getContent());

        // return to old value
        newReview.setContent(CONTENT);
        reviewService.update(newReview, newReview.getId());
        assertEquals(CONTENT, reviewService.getOneDTO(EXISTENT_ID).getContent());
    }
}
