package ftn.kts.controller;

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
import java.util.List;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ftn.kts.dto.PictureDTO;
import ftn.kts.dto.PostDTO;
import ftn.kts.exceptions.ErrorMessage;
import ftn.kts.model.Post;
import ftn.kts.pages.PagePosts;
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
		ResponseEntity<PagePosts> responseEntity = restTemplate
				.exchange("/posts?pageNo=0&pageSize=1", HttpMethod.GET, httpEntity, PagePosts.class);
		PagePosts posts = responseEntity.getBody();
		
		List<PostDTO> postsList = posts.getContent();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(PAGEABLE_ONE_ELEMENT, postsList.size());
	}
	
	@Test
	public void getAllPosts_SecondPageOnePost_OneEntityReturned() {
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(getAuthHeadersUser(restTemplate));
		ResponseEntity<PagePosts> responseEntity = restTemplate
				.exchange("/posts?pageNo=1&pageSize=1", HttpMethod.GET, httpEntity, PagePosts.class);
		PagePosts posts = responseEntity.getBody();
		
		List<PostDTO> postsList = posts.getContent();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(PAGEABLE_ONE_ELEMENT, postsList.size());
	}
	
	@Test
	public void getAllPosts_ThirdPageNoPost_NoEntitiesReturned() {
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(getAuthHeadersUser(restTemplate));
		ResponseEntity<PagePosts> responseEntity = restTemplate
				.exchange("/posts?pageNo=2&pageSize=2", HttpMethod.GET, httpEntity, PagePosts.class);
		PagePosts posts = responseEntity.getBody();
		
		List<PostDTO> postsList = posts.getContent();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(NO_ITEMS, postsList.size());
	}
	
	@Test
	public void getAllPosts_SortByIdAscending_SortedListReturned() {
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(getAuthHeadersUser(restTemplate));
		ResponseEntity<PagePosts> responseEntity = restTemplate
				.exchange("/posts?pageNo=0&pageSize=2&descending=false&sortBy=id", HttpMethod.GET, httpEntity, PagePosts.class);
		PagePosts posts = responseEntity.getBody();
		
		List<PostDTO> postsList = posts.getContent();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(PAGEABLE_TWO_ELEMENTS, postsList.size());
		assertThat(postsList.get(0).getId()).isLessThan(postsList.get(1).getId());
	}
	
	@Test
	public void getAllPosts_SortByIdDescending_SortedListReturned() {
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(getAuthHeadersUser(restTemplate));
		ResponseEntity<PagePosts> responseEntity = restTemplate
				.exchange("/posts?pageNo=0&pageSize=2&descending=true", HttpMethod.GET, httpEntity, PagePosts.class);
		PagePosts posts = responseEntity.getBody();
		
		List<PostDTO> postsList = posts.getContent();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(PAGEABLE_TWO_ELEMENTS, postsList.size());
		assertThat(postsList.get(0).getId()).isGreaterThan(postsList.get(1).getId());
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
	public void addPostAndDelete_ValidPostAsAdmin_Success() throws JsonProcessingException {
		PostDTO dto = new PostDTO();
		dto.setTitle("title");
		dto.setContent("test content");
		dto.setCulturalOffer(1L);
		PictureDTO picture = new PictureDTO(1L);
		Set<PictureDTO> pictures = new HashSet<>();
		pictures.add(picture);
		dto.setPictures(pictures);
		
		ObjectMapper mapper  = new ObjectMapper();
		String post = mapper.writeValueAsString(dto);
		MultipartFile[] files = new MultipartFile[]{};
		
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("post", post);
		body.add("files", files);
		
		HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(body, getAuthHeadersAdmin(restTemplate));
		ResponseEntity<PostDTO> responseEntity = restTemplate.exchange("/posts", HttpMethod.POST,
				httpEntity, PostDTO.class);
		PostDTO created = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(created.getId());
		
		//clean up
		postService.delete(created.getId());
		
	}
	
	@Test
	public void addPost_ValidPostAsUser_UnauthorizedReturned() throws JsonProcessingException {
		PostDTO dto = new PostDTO();
		dto.setTitle("title");
		dto.setContent("test content");
		dto.setCulturalOffer(1L);
		PictureDTO picture = new PictureDTO(1L);
		Set<PictureDTO> pictures = new HashSet<>();
		pictures.add(picture);
		dto.setPictures(pictures);
		
		ObjectMapper mapper  = new ObjectMapper();
		String post = mapper.writeValueAsString(dto);
		MultipartFile[] files = new MultipartFile[]{};
		
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("post", post);
		body.add("files", files);
		
		HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(body, getAuthHeadersUser(restTemplate));
		ResponseEntity<PostDTO> responseEntity = restTemplate.exchange("/posts", HttpMethod.POST,
				httpEntity, PostDTO.class);
		PostDTO notCreated = responseEntity.getBody();
		
		assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
		assertNull(notCreated.getId());
		
	}
	
	@Test
	public void addPost_NoAuthToken_UnauthorizedReturned() throws JsonProcessingException {
		PostDTO dto = new PostDTO();
		dto.setContent("test content");
		dto.setCulturalOffer(1L);
		PictureDTO picture = new PictureDTO(1L);
		Set<PictureDTO> pictures = new HashSet<>();
		pictures.add(picture);
		dto.setPictures(pictures);
		
		ObjectMapper mapper  = new ObjectMapper();
		String post = mapper.writeValueAsString(dto);
		MultipartFile[] files = new MultipartFile[]{};
		
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("post", post);
		body.add("files", files);
		
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(headers);
		ResponseEntity<PostDTO> responseEntity = restTemplate.exchange("/posts", HttpMethod.POST,
				httpEntity, PostDTO.class);
		PostDTO notCreated = responseEntity.getBody();
		
		assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
		assertNull(notCreated.getId());
	}
		
	@Test
	public void addPost_NoCulturalOffer_BadRequestReturned() throws JsonProcessingException {
		PostDTO dto = new PostDTO();
		dto.setContent("test content");
		PictureDTO picture = new PictureDTO(1L);
		Set<PictureDTO> pictures = new HashSet<>();
		pictures.add(picture);
		dto.setPictures(pictures);
		
		ObjectMapper mapper  = new ObjectMapper();
		String post = mapper.writeValueAsString(dto);
		MultipartFile[] files = new MultipartFile[]{};
		
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("post", post);
		body.add("files", files);
		
		HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(body, getAuthHeadersAdmin(restTemplate));
		ResponseEntity<PostDTO> responseEntity = restTemplate.exchange("/posts", HttpMethod.POST,
				httpEntity, PostDTO.class);
		PostDTO created = responseEntity.getBody();
		
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		assertNull(created.getId());
	}
	
	@Test
	public void addPost_CulturalOfferNotExists_NotFoundReturned() throws JsonProcessingException {
		PostDTO dto = new PostDTO();
		dto.setContent("test content");
		dto.setCulturalOffer(55L);
		PictureDTO picture = new PictureDTO(1L);
		Set<PictureDTO> pictures = new HashSet<>();
		pictures.add(picture);
		dto.setPictures(pictures);
		
		ObjectMapper mapper  = new ObjectMapper();
		String post = mapper.writeValueAsString(dto);
		MultipartFile[] files = new MultipartFile[]{};
		
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("post", post);
		body.add("files", files);
		
		HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(body, getAuthHeadersAdmin(restTemplate));
		ResponseEntity<PostDTO> responseEntity = restTemplate.exchange("/posts", HttpMethod.POST,
				httpEntity, PostDTO.class);
		PostDTO created = responseEntity.getBody();
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertNull(created.getId());
	}
	
	
	@Test
	public void updatePost_ValidPostAsAdmin_Success() throws JsonProcessingException {
		Post oldPost = postService.getOne(1L);
		PostDTO dto = new PostDTO(oldPost.getId(), oldPost.getTitle(), "new content", oldPost.getDatePosted(), oldPost.getCulturalOffer().getId(), oldPost.getCulturalOffer().getName());
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
		PostDTO dto = new PostDTO(oldPost.getId(), oldPost.getTitle(), "", oldPost.getDatePosted(), oldPost.getCulturalOffer().getId(), oldPost.getCulturalOffer().getName());
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
		PostDTO dto = new PostDTO(oldPost.getId(), oldPost.getTitle(), oldPost.getContent(), oldPost.getDatePosted(), null, null);
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
	public void updatePost_CulturalOfferNotExistsAsAdmin_NullReturned() {
		Post oldPost = postService.getOne(1L);
		PostDTO dto = new PostDTO(oldPost.getId(), oldPost.getTitle(), oldPost.getContent(), oldPost.getDatePosted(), 55L, null);
		PictureDTO picture = new PictureDTO(1L);
		Set<PictureDTO> pictures = new HashSet<>();
		pictures.add(picture);
		dto.setPictures(pictures);

		HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto, getAuthHeadersAdmin(restTemplate));
		ResponseEntity<PostDTO> responseEntity = restTemplate.exchange("/posts/1", HttpMethod.PUT,
				httpEntity, PostDTO.class);
		PostDTO notUpdated = responseEntity.getBody();

		assertNull(notUpdated.getId());
	}

	@Test
	public void updatePost_ValidPostAsUser_NullReturned() {
		Post oldPost = postService.getOne(1L);
		PostDTO dto = new PostDTO(oldPost.getId(), oldPost.getTitle(), oldPost.getContent(), oldPost.getDatePosted(), 2L, "cultural_offer2");
		PictureDTO picture = new PictureDTO(1L);
		Set<PictureDTO> pictures = new HashSet<>();
		pictures.add(picture);
		dto.setPictures(pictures);

		HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto, getAuthHeadersUser(restTemplate));
		ResponseEntity<PostDTO> responseEntity = restTemplate.exchange("/posts/1", HttpMethod.PUT,
				httpEntity, PostDTO.class);
		PostDTO notUpdated = responseEntity.getBody();
		
		assertNull(notUpdated.getId());
	}

	@Test
	public void updatePost_NoAuthToken_UnauthorizedReturned() {
		Post oldPost = postService.getOne(1L);
		PostDTO dto = new PostDTO(oldPost.getId(), "title", oldPost.getContent(), oldPost.getDatePosted(), 2L, "cultural_offer2");
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
