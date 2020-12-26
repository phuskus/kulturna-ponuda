package ftn.kts.controller;

import static ftn.kts.constants.PostConstants.DB_ID_CREATED_POST;
import static ftn.kts.constants.PostConstants.DB_POST_ID;
import static ftn.kts.constants.PostConstants.DB_POST_NO_SUCH_ID;
import static ftn.kts.constants.PostConstants.NO_ITEMS;
import static ftn.kts.constants.PostConstants.PAGEABLE_ONE_ELEMENT;
import static ftn.kts.constants.PostConstants.PAGEABLE_TWO_ELEMENTS;
import static ftn.kts.util.ControllerUtil.getAuthHeadersAdmin;
import static ftn.kts.util.ControllerUtil.getAuthHeadersUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import ftn.kts.dto.PictureDTO;
import ftn.kts.dto.PostDTO;
import ftn.kts.exceptions.ErrorMessage;
import ftn.kts.model.Post;
import ftn.kts.service.PostService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostControllerIntegrationTest {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private PostService postService;
	
	@Test
	public void getAllPosts_FirstPageOnePost_OneEntityReturned() {
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(getAuthHeadersUser(restTemplate));
		ResponseEntity<PostDTO[]> responseEntity = restTemplate
				.exchange("/posts?pageNo=0&pageSize=1", HttpMethod.GET, httpEntity, PostDTO[].class);
		PostDTO[] posts = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(PAGEABLE_ONE_ELEMENT, posts.length);
	}
	
	@Test
	public void getAllPosts_SecondPageOnePost_OneEntityReturned() {
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(getAuthHeadersUser(restTemplate));
		ResponseEntity<PostDTO[]> responseEntity = restTemplate
				.exchange("/posts?pageNo=1&pageSize=1", HttpMethod.GET, httpEntity, PostDTO[].class);
		PostDTO[] posts = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(PAGEABLE_ONE_ELEMENT, posts.length);
	}
	
	@Test
	public void getAllPosts_ThirdPageNoPost_NoEntitiesReturned() {
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(getAuthHeadersUser(restTemplate));
		ResponseEntity<PostDTO[]> responseEntity = restTemplate
				.exchange("/posts?pageNo=2&pageSize=2", HttpMethod.GET, httpEntity, PostDTO[].class);
		PostDTO[] posts = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(NO_ITEMS, posts.length);
	}
	
	@Test
	public void getAllPosts_SortByIdAscending_SortedListReturned() {
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(getAuthHeadersUser(restTemplate));
		ResponseEntity<PostDTO[]> responseEntity = restTemplate
				.exchange("/posts?pageNo=0&pageSize=2&descending=false", HttpMethod.GET, httpEntity, PostDTO[].class);
		PostDTO[] posts = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(PAGEABLE_TWO_ELEMENTS, posts.length);
		assertThat(posts[0].getId()).isLessThan(posts[1].getId());
	}
	
	@Test
	public void getAllPosts_SortByIdDescending_SortedListReturned() {
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(getAuthHeadersUser(restTemplate));
		ResponseEntity<PostDTO[]> responseEntity = restTemplate
				.exchange("/posts?pageNo=0&pageSize=2&descending=true", HttpMethod.GET, httpEntity, PostDTO[].class);
		PostDTO[] posts = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(PAGEABLE_TWO_ELEMENTS, posts.length);
		assertThat(posts[0].getId()).isGreaterThan(posts[1].getId());
	}
	
	@Test
	public void getPost_PostExists_OneEntityReturned() {
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(getAuthHeadersUser(restTemplate));
		ResponseEntity<PostDTO> responseEntity = restTemplate
				.exchange("/posts/" + DB_POST_ID, HttpMethod.GET, httpEntity, PostDTO.class);
		PostDTO post = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(post);
		assertEquals(DB_POST_ID, post.getId());
	}
	
	@Test
	public void getPost_PostNotExists_NotFoundReturned() {
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(getAuthHeadersUser(restTemplate));
		ResponseEntity<PostDTO> responseEntity = restTemplate
				.exchange("/posts/" + DB_POST_NO_SUCH_ID, HttpMethod.GET, httpEntity, PostDTO.class);
		PostDTO post = responseEntity.getBody();
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertNull(post.getId());
	}
	
	@Test
	public void getPost_NoAuthToken_UnauthorizedReturned() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
		ResponseEntity<PostDTO> responseEntity = restTemplate
				.exchange("/posts/" + DB_POST_ID, HttpMethod.GET, httpEntity, PostDTO.class);
		PostDTO post = responseEntity.getBody();
		
		assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
		assertNull(post.getId());
	}
	
	@Test
	public void addPostAndDelete_ValidPostAsAdmin_Success() {
		PostDTO dto = new PostDTO();
		dto.setContent("test content");
		dto.setCulturalOffer(1L);
		PictureDTO picture = new PictureDTO(1L);
		Set<PictureDTO> pictures = new HashSet<>();
		pictures.add(picture);
		dto.setPictures(pictures);
		
		HttpEntity<PostDTO> httpEntity = new HttpEntity<PostDTO>(dto, getAuthHeadersAdmin(restTemplate));
		ResponseEntity<PostDTO> responseEntity = restTemplate.exchange("/posts", HttpMethod.POST,
				httpEntity, PostDTO.class);
		PostDTO created = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(DB_ID_CREATED_POST, created.getId());
		
		//clean up
		postService.delete(DB_ID_CREATED_POST);
	}
	
	@Test
	public void addPost_ValidPostAsUser_UnauthorizedReturned() {
		PostDTO dto = new PostDTO();
		dto.setContent("test content");
		dto.setCulturalOffer(1L);
		PictureDTO picture = new PictureDTO(1L);
		Set<PictureDTO> pictures = new HashSet<>();
		pictures.add(picture);
		dto.setPictures(pictures);
		
		HttpEntity<PostDTO> httpEntity = new HttpEntity<PostDTO>(dto, getAuthHeadersUser(restTemplate));
		ResponseEntity<PostDTO> responseEntity = restTemplate.exchange("/posts", HttpMethod.POST,
				httpEntity, PostDTO.class);
		PostDTO notCreated = responseEntity.getBody();
		
		assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
		assertNull(notCreated.getId());
		
	}
	
	@Test
	public void addPost_NoAuthToken_UnauthorizedReturned() {
		PostDTO dto = new PostDTO();
		dto.setContent("test content");
		dto.setCulturalOffer(1L);
		PictureDTO picture = new PictureDTO(1L);
		Set<PictureDTO> pictures = new HashSet<>();
		pictures.add(picture);
		dto.setPictures(pictures);
		
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
		ResponseEntity<PostDTO> responseEntity = restTemplate.exchange("/posts", HttpMethod.POST,
				httpEntity, PostDTO.class);
		PostDTO notCreated = responseEntity.getBody();
		
		assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
		assertNull(notCreated.getId());
	}
	
	@Test
	public void addPost_NoContent_BadRequestReturned() {
		PostDTO dto = new PostDTO();
		dto.setCulturalOffer(1L);
		PictureDTO picture = new PictureDTO(1L);
		Set<PictureDTO> pictures = new HashSet<>();
		pictures.add(picture);
		dto.setPictures(pictures);
		
		HttpEntity<PostDTO> httpEntity = new HttpEntity<PostDTO>(dto, getAuthHeadersAdmin(restTemplate));
		ResponseEntity<PostDTO> responseEntity = restTemplate.exchange("/posts", HttpMethod.POST,
				httpEntity, PostDTO.class);
		PostDTO created = responseEntity.getBody();
		
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		assertNull(created.getId());
	}
	
	@Test
	public void addPost_NoCulturalOffer_BadRequestReturned() {
		PostDTO dto = new PostDTO();
		dto.setContent("test content");
		PictureDTO picture = new PictureDTO(1L);
		Set<PictureDTO> pictures = new HashSet<>();
		pictures.add(picture);
		dto.setPictures(pictures);
		
		HttpEntity<PostDTO> httpEntity = new HttpEntity<PostDTO>(dto, getAuthHeadersAdmin(restTemplate));
		ResponseEntity<PostDTO> responseEntity = restTemplate.exchange("/posts", HttpMethod.POST,
				httpEntity, PostDTO.class);
		PostDTO created = responseEntity.getBody();
		
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		assertNull(created.getId());
	}
	
	@Test
	public void addPost_CulturalOfferNotExists_NotFoundReturned() {
		PostDTO dto = new PostDTO();
		dto.setContent("test content");
		dto.setCulturalOffer(55L);
		PictureDTO picture = new PictureDTO(1L);
		Set<PictureDTO> pictures = new HashSet<>();
		pictures.add(picture);
		dto.setPictures(pictures);
		
		HttpEntity<PostDTO> httpEntity = new HttpEntity<PostDTO>(dto, getAuthHeadersAdmin(restTemplate));
		ResponseEntity<PostDTO> responseEntity = restTemplate.exchange("/posts", HttpMethod.POST,
				httpEntity, PostDTO.class);
		PostDTO created = responseEntity.getBody();
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertNull(created.getId());
	}
	
	//no picture?
	
	@Test
	public void updatePost_ValidPostAsAdmin_Success() {
		Post oldPost = postService.getOne(1L);
		PostDTO dto = new PostDTO(oldPost.getId(), "new content", oldPost.getCulturalOffer().getId());
		PictureDTO picture = new PictureDTO(1L);
		PictureDTO newPicture = new PictureDTO(2L);
		Set<PictureDTO> pictures = new HashSet<>();
		pictures.add(picture);
		pictures.add(newPicture);
		dto.setPictures(pictures);
		
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto, getAuthHeadersAdmin(restTemplate));
		ResponseEntity<PostDTO> responseEntity = restTemplate.exchange("/posts/1", HttpMethod.PUT,
				httpEntity, PostDTO.class);
		PostDTO updated = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(dto.getContent(), updated.getContent());
		assertEquals(dto.getPictures().size(), updated.getPictures().size());
		
		//clean up
		PostDTO cleaned = postService.save(oldPost);
		assertEquals(cleaned.getPictures().size(), oldPost.getPictures().size());
		assertEquals(cleaned.getContent(), oldPost.getContent());
	}
	
	@Test
	public void updatePost_NoContentAsAdmin_BadRequestReturned() {
		Post oldPost = postService.getOne(1L);
		PostDTO dto = new PostDTO(oldPost.getId(), "", oldPost.getCulturalOffer().getId());
		PictureDTO picture = new PictureDTO(1L);
		Set<PictureDTO> pictures = new HashSet<>();
		pictures.add(picture);
		dto.setPictures(pictures);
		
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto, getAuthHeadersAdmin(restTemplate));
		ResponseEntity<PostDTO> responseEntity = restTemplate.exchange("/posts/1", HttpMethod.PUT,
				httpEntity, PostDTO.class);
		PostDTO notUpdated = responseEntity.getBody();
		
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		assertNull(notUpdated.getId());
	}
	
	@Test
	public void updatePost_NoCulturalOfferAsAdmin_BadRequestReturned() {
		Post oldPost = postService.getOne(1L);
		PostDTO dto = new PostDTO(oldPost.getId(), oldPost.getContent(), null);
		PictureDTO picture = new PictureDTO(1L);
		Set<PictureDTO> pictures = new HashSet<>();
		pictures.add(picture);
		dto.setPictures(pictures);
		
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto, getAuthHeadersAdmin(restTemplate));
		ResponseEntity<PostDTO> responseEntity = restTemplate.exchange("/posts/1", HttpMethod.PUT,
				httpEntity, PostDTO.class);
		PostDTO notUpdated = responseEntity.getBody();
		
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		assertNull(notUpdated.getId());
	}
	
	@Test
	public void updatePost_CulturalOfferNotExistsAsAdmin_NotFoundReturned() {
		Post oldPost = postService.getOne(1L);
		PostDTO dto = new PostDTO(oldPost.getId(), oldPost.getContent(), 55L);
		PictureDTO picture = new PictureDTO(1L);
		Set<PictureDTO> pictures = new HashSet<>();
		pictures.add(picture);
		dto.setPictures(pictures);
		
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto, getAuthHeadersAdmin(restTemplate));
		ResponseEntity<PostDTO> responseEntity = restTemplate.exchange("/posts/1", HttpMethod.PUT,
				httpEntity, PostDTO.class);
		PostDTO notUpdated = responseEntity.getBody();
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertNull(notUpdated.getId());
	}
	
	@Test
	public void updatePost_ValidPostAsUser_UnauthorizedReturned() {
		Post oldPost = postService.getOne(1L);
		PostDTO dto = new PostDTO(oldPost.getId(), oldPost.getContent(), 2L);
		PictureDTO picture = new PictureDTO(1L);
		Set<PictureDTO> pictures = new HashSet<>();
		pictures.add(picture);
		dto.setPictures(pictures);
		
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto, getAuthHeadersUser(restTemplate));
		ResponseEntity<PostDTO> responseEntity = restTemplate.exchange("/posts/1", HttpMethod.PUT,
				httpEntity, PostDTO.class);
		PostDTO notUpdated = responseEntity.getBody();
		
		assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
		assertNull(notUpdated.getId());
	}
	
	@Test
	public void updatePost_NoAuthToken_UnauthorizedReturned() {
		Post oldPost = postService.getOne(1L);
		PostDTO dto = new PostDTO(oldPost.getId(), oldPost.getContent(), 2L);
		PictureDTO picture = new PictureDTO(1L);
		Set<PictureDTO> pictures = new HashSet<>();
		pictures.add(picture);
		dto.setPictures(pictures);
		
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto, headers);
		ResponseEntity<PostDTO> responseEntity = restTemplate.exchange("/posts/1", HttpMethod.PUT,
				httpEntity, PostDTO.class);
		PostDTO notUpdated = responseEntity.getBody();
		
		assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
		assertNull(notUpdated.getId());
	}
	
	@Test
	public void deletePost_PostNotExistsAsAdmin_NotFoundReturned() {
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(getAuthHeadersAdmin(restTemplate));
		ResponseEntity<ErrorMessage> responseEntity = restTemplate.exchange("/posts/55", HttpMethod.DELETE,
				httpEntity, ErrorMessage.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	@Test
	public void deletePost_PostExistsAsAdmin_UnauthorizedReturned() {
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(getAuthHeadersUser(restTemplate));
		ResponseEntity<ErrorMessage> responseEntity = restTemplate.exchange("/posts/1", HttpMethod.DELETE,
				httpEntity, ErrorMessage.class);
		
		assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
	}
	
	@Test
	public void deletePost_NoAuthToken_UnauthorizedReturned() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
		ResponseEntity<ErrorMessage> responseEntity = restTemplate.exchange("/posts/55", HttpMethod.DELETE,
				httpEntity, ErrorMessage.class);
		
		assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
	}
	
	
	

}
