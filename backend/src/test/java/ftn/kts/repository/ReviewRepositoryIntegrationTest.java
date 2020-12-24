package ftn.kts.repository;

import ftn.kts.model.Review;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import static ftn.kts.constants.ReviewConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReviewRepositoryIntegrationTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void findAllPageable_ValidPageableObject_ReturnsReviews() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        Page<Review> found = reviewRepository.findAll(pageable);
        assertEquals(FIND_ALL_NUMBER_OF_ITEMS, found.getTotalElements());
    }

    @Test
    public void findAllPageable_NullPageableObject_ThrowsNullPtrException() {
        Pageable pageable = null;
        assertThrows(NullPointerException.class, () -> reviewRepository.findAll(pageable));
    }

    @Test
    public void findAllPageableSorted_SortByRating_ReturnsSortedReviews() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE, Sort.by("rating"));
        Page<Review> found = reviewRepository.findAll(pageable);

        long[] sizes = new long[(int) found.getTotalElements()];
        int i = 0;
        for (Review review : found) {
            sizes[i] = review.getRating();
            if (i > 0) {
                assertTrue(sizes[i] >= sizes[i - 1]);
            }
            i++;
        }
    }

}
