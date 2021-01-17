package ftn.kts.controller;

import ftn.kts.dto.ReviewDTO;
import ftn.kts.dto.UserDTO;
import ftn.kts.service.ReviewService;
import org.junit.Test;
import org.junit.runner.RunWith;

import static ftn.kts.util.ControllerUtil.getAuthHeadersAdmin;
import static ftn.kts.util.ControllerUtil.getAuthHeadersUser;
import static org.junit.Assert.assertEquals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static ftn.kts.constants.ReviewConstants.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class ReviewControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ReviewService reviewService;

    @Test
    public void getAll_ReturnsAllReviews() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(getAuthHeadersUser(restTemplate));
        ResponseEntity<ReviewDTO[]> responseEntity = restTemplate
                .exchange("/reviews?pageNo=0&pageSize=" + NUM_ITEMS, HttpMethod.GET, httpEntity, ReviewDTO[].class);
        ReviewDTO[] reviews = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(NUM_ITEMS, reviews.length);
    }

    @Test
    public void getAll_FirstReview_ReturnsAllReviews() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(getAuthHeadersUser(restTemplate));
        ResponseEntity<ReviewDTO[]> responseEntity = restTemplate
                .exchange("/reviews?pageNo=0&pageSize=1", HttpMethod.GET, httpEntity, ReviewDTO[].class);
        ReviewDTO[] reviews = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, reviews.length);
    }

    @Test
    public void getOne_ExistentId_ReturnsOkAndCategory() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(getAuthHeadersAdmin(restTemplate));
        ResponseEntity<ReviewDTO> responseEntity = restTemplate
                .exchange("/reviews/" + EXISTENT_ID, HttpMethod.GET, httpEntity, ReviewDTO.class);
        ReviewDTO review = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(CONTENT, review.getContent());
    }

    @Test
    public void getOne_NonexistentId_ReturnsNotFound() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(getAuthHeadersAdmin(restTemplate));
        ResponseEntity<ReviewDTO> responseEntity = restTemplate
                .exchange("/reviews/" + NONEXISTENT_ID, HttpMethod.GET, httpEntity, ReviewDTO.class);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void add_ValidReview_ReturnsOkAndReview() {
        int size = reviewService.getAll().size();
        UserDTO user = new UserDTO(1L, "name", "user", "pass");
        ReviewDTO dto = new ReviewDTO(3L, "Content", user, 2L);
        HttpEntity<Object> httpEntity = new HttpEntity<>(dto, getAuthHeadersAdmin(restTemplate));
        ResponseEntity<ReviewDTO> responseEntity = restTemplate
                .exchange("/reviews", HttpMethod.POST, httpEntity, ReviewDTO.class);

        ReviewDTO review = responseEntity.getBody();
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(size + 1, reviewService.getAll().size());

        // delete admin
        reviewService.delete(review.getId());
    }


    @Test
    public void add_NullCategory_ReturnsBadRequest() {
        int size = reviewService.getAll().size();
        HttpEntity<Object> httpEntity = new HttpEntity<>(null, getAuthHeadersAdmin(restTemplate));
        ResponseEntity<ReviewDTO> responseEntity = restTemplate
                .exchange("/reviews", HttpMethod.POST, httpEntity, ReviewDTO.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(size, reviewService.getAll().size());
    }

    @Test
    public void update_ValidNewObject_ReturnsOk() {
        ReviewDTO oldReview = reviewService.getOneDTO(EXISTENT_ID);
        assertEquals(CONTENT, oldReview.getContent());
        oldReview.setContent(NEW_CONTENT);

        HttpEntity<Object> httpEntity = new HttpEntity<>(oldReview, getAuthHeadersAdmin(restTemplate));
        ResponseEntity<ReviewDTO> responseEntity = restTemplate
                .exchange("/reviews/" + EXISTENT_ID, HttpMethod.PUT, httpEntity, ReviewDTO.class);

        ReviewDTO newReview = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(NEW_CONTENT, newReview.getContent());

        // update to old name
        newReview.setContent(CONTENT);

        httpEntity = new HttpEntity<>(newReview, getAuthHeadersAdmin(restTemplate));
        restTemplate
                .exchange("/reviews/" + EXISTENT_ID, HttpMethod.PUT, httpEntity, ReviewDTO.class);
    }


    @Test
    public void delete_ExistentId_ReturnsOk() {
        ReviewDTO review = reviewService.getOneDTO(EXISTENT_ID);
        HttpEntity<Object> httpEntity = new HttpEntity<>(getAuthHeadersAdmin(restTemplate));
        ResponseEntity<Void> responseEntity = restTemplate
                .exchange("/reviews/" + EXISTENT_ID, HttpMethod.DELETE, httpEntity, Void.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // add back to db
        httpEntity = new HttpEntity<>(review, getAuthHeadersAdmin(restTemplate));
        restTemplate.exchange("/reviews", HttpMethod.POST, httpEntity, ReviewDTO.class);
    }


    @Test
    public void delete_NonexistentId_ReturnsNotFound() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(getAuthHeadersAdmin(restTemplate));
        ResponseEntity<Void> responseEntity = restTemplate
                .exchange("/reviews/" + NONEXISTENT_ID, HttpMethod.DELETE, httpEntity, Void.class);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}
