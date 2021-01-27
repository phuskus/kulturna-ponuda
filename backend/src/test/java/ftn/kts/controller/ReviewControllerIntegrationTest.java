package ftn.kts.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ftn.kts.dto.RegisteredUserDTO;
import ftn.kts.dto.ReviewDTO;
import ftn.kts.dto.UserDTO;
import ftn.kts.model.RegisteredUser;
import ftn.kts.pages.ReviewPage;
import ftn.kts.service.ReviewService;
import org.junit.Test;
import org.junit.runner.RunWith;

import static ftn.kts.util.ControllerUtil.getAuthHeadersAdmin;
import static ftn.kts.util.ControllerUtil.getAuthHeadersUser;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static ftn.kts.constants.ReviewConstants.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class ReviewControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private WebApplicationContext webApplicationContext;


    @Test
    public void getAll_ReturnsAllReviews() {
        int NUM_ITEMS = reviewService.getAll().size();
        HttpEntity<Object> httpEntity = new HttpEntity<>(getAuthHeadersUser(restTemplate));
        ResponseEntity<ReviewPage> responseEntity = restTemplate
                .exchange("/reviews?pageNo=0&pageSize=" + NUM_ITEMS, HttpMethod.GET, httpEntity, ReviewPage.class);

        List<ReviewDTO> reviews = responseEntity.getBody().getContent();
        assertEquals(NUM_ITEMS, reviews.size());
    }

    @Test
    public void getAll_FirstReview_ReturnsOneReview() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(getAuthHeadersUser(restTemplate));
        ResponseEntity<ReviewPage> responseEntity = restTemplate
                .exchange("/reviews?pageNo=0&pageSize=1", HttpMethod.GET, httpEntity, ReviewPage.class);
        List<ReviewDTO> reviews = responseEntity.getBody().getContent();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, reviews.size());
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
    public void getByOfferId_NonexistentId_ReturnsNotFound() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(getAuthHeadersAdmin(restTemplate));
        ResponseEntity<ReviewPage> responseEntity = restTemplate
                .exchange("/reviews/offer/" + NONEXISTENT_OFFER_ID, HttpMethod.GET, httpEntity, ReviewPage.class);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void getByOfferId_ExistentId_ReturnsReviewsForOffer() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(getAuthHeadersAdmin(restTemplate));
        ResponseEntity<ReviewPage> responseEntity = restTemplate
                .exchange("/reviews/offer/" + EXISTENT_OFFER_ID, HttpMethod.GET, httpEntity, ReviewPage.class);

        List<ReviewDTO> reviews = responseEntity.getBody().getContent();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(NUM_REVIEWS_FOR_OFFER, reviews.size());
    }

    @Test
    public void search_EmptyQuery_ReturnsOkAndAllReviews() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(getAuthHeadersUser(restTemplate));
        ResponseEntity<ReviewPage> responseEntity = restTemplate
                .exchange("/reviews/search?query=", HttpMethod.GET, httpEntity, ReviewPage.class);
        List<ReviewDTO> reviews = responseEntity.getBody().getContent();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(NUM_ITEMS, reviews.size());
    }

    @Test
    public void search_BigAndUglyQuery_ReturnsNoReviews() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(getAuthHeadersUser(restTemplate));
        ResponseEntity<ReviewPage> responseEntity = restTemplate
                .exchange("/reviews/search?query=" + RANDOM_STRING, HttpMethod.GET, httpEntity, ReviewPage.class);
        List<ReviewDTO> reviews = responseEntity.getBody().getContent();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(0, reviews.size());
    }

    @Test
    public void search_ExistentContent_ReturnsOkAndReview() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(getAuthHeadersUser(restTemplate));
        ResponseEntity<ReviewPage> responseEntity = restTemplate
                .exchange("/reviews/search?query="+ REVIEW_CONTENT, HttpMethod.GET, httpEntity, ReviewPage.class);
        List<ReviewDTO> reviews = responseEntity.getBody().getContent();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, reviews.size());
    }

    @Test
    public void add_ValidReview_ReturnsOkAndReview() throws Exception {
        int size = reviewService.getAll().size();
        UserDTO user = new UserDTO(1L, "name", "surname", "user", "pass");
        String content = "content";
        ReviewDTO dto = new ReviewDTO(3L, content, user, 2L, "name");

        ObjectMapper mapper = new ObjectMapper();
        String jsonDTO = mapper.writeValueAsString(dto);

        HttpHeaders headers = getAuthHeadersUser(restTemplate);
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
        MvcResult result = mockMvc.perform(post("/reviews")
                .param("review", jsonDTO).headers(headers)
        ).andExpect(status().isCreated()).andReturn();


        MockHttpServletResponse resp = result.getResponse();
        ReviewDTO newReview = mapper.readValue(resp.getContentAsString(), ReviewDTO.class);
        assertEquals(newReview.getContent(), content);
        assertEquals(size + 1, reviewService.getAll().size());

        // delete review
        reviewService.delete(newReview.getId());
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
    public void delete_ExistentId_ReturnsOk() {
        ReviewDTO review = reviewService.getOneDTO(EXISTENT_ID);
        HttpHeaders headers = getAuthHeadersAdmin(restTemplate);
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<Void> responseEntity = restTemplate
                .exchange("/reviews/" + EXISTENT_ID, HttpMethod.DELETE, httpEntity, Void.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // add back to db
        reviewService.create(review, new MultipartFile[]{});
    }


    @Test
    public void delete_NonexistentId_ReturnsNotFound() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(getAuthHeadersAdmin(restTemplate));
        ResponseEntity<Void> responseEntity = restTemplate
                .exchange("/reviews/" + NONEXISTENT_ID, HttpMethod.DELETE, httpEntity, Void.class);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}
