package ftn.kts.service;

import static ftn.kts.constants.PostConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import ftn.kts.dto.PictureDTO;
import ftn.kts.dto.PostDTO;
import ftn.kts.model.Post;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PostServiceIntegrationTest {
	
	@Autowired
	private PostService postService;
	
	@Test
	public void getAllDTO_FirstPageThreePosts_ThreeEntitiesReturned() {
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE,PAGEABLE_SIZE);
		Page<PostDTO> found = postService.getAllDTO(pageable);
		assertEquals(found.getNumberOfElements(), FIND_ALL_NUMBER_OF_ITEMS);
	}
	
	@Test
	public void getAllDTO_SecondPageNoPosts_NoEntitiesReturned() {
		Pageable pageable = PageRequest.of(PAGEABLE_FIRST_PAGE,PAGEABLE_SIZE);
		Page<PostDTO> found = postService.getAllDTO(pageable);
		assertEquals(found.getNumberOfElements(), NO_ITEMS);
	}
	
	@Test
	public void getOneDTO_PostExists_OneEntityReturned() {
		PostDTO found = postService.getOneDTO(DB_POST_ID);
		assertNotNull(found);
		assertEquals(found.getContent(), DB_POST_CONTENT);
	}
	
	@Test(expected = NoSuchElementException.class)
	public void getOneDTO_PostNotExists_NoSuchElementExceptionThrown() {
		postService.getOneDTO(DB_POST_NO_SUCH_ID);
	}
	
	@Test
	public void getOne_PostExists_OneEntityReturned() {
		Post found = postService.getOne(DB_POST_ID);
		assertNotNull(found);
		assertEquals(found.getContent(), DB_POST_CONTENT);
	}
	
	@Test(expected = NoSuchElementException.class)
	public void getOne_PostNotExists_NoSuchElementExceptionThrown() {
		postService.getOne(DB_POST_NO_SUCH_ID);
	}
	
	@Test
	public void getAll_PostsExist_ThreeEntitesReturned() {
		List<Post> posts = postService.getAll();
		assertEquals(posts.size(), FIND_ALL_NUMBER_OF_ITEMS);
	}
	
	@Test
	public void convertToDTO_PostsExist_ThreeEntityDtosReturned() {
		List<Post> posts = postService.getAll();
		Set<Post> postsSet = new HashSet<>(posts);
		Set<PostDTO> postsDTOSet = postService.convertToDTO(postsSet);
		assertEquals(postsDTOSet.size(), FIND_ALL_NUMBER_OF_ITEMS);
	}
	
	@Test(expected = NoSuchElementException.class)
	public void createAndDelete_EntitySavedAndDeleted() {
		PictureDTO pictureDTO = new PictureDTO();
		pictureDTO.setId(DB_PICTURE_ID);
		Set<PictureDTO> pictures = new HashSet<>();
		pictures.add(pictureDTO);
		PostDTO newPostDTO = new PostDTO(DB_NEW_POST_CONTENT, DB_CULTURAL_OFFER_ID, pictures);
		Post createdPost = postService.create(newPostDTO);
		assertNotNull(createdPost);
		assertEquals(createdPost.getId(), DB_ID_CREATED_POST);
		
		//clean up
		postService.delete(DB_ID_CREATED_POST);
		postService.getOne(DB_ID_CREATED_POST);
	}
	
	@Test
	public void updatePost_PostExists_EntityUpdatedAndReveretedToPreviousState() {
		Post post = postService.getOne(DB_POST_ID);
		Set<PictureDTO> picturesDTO = new HashSet<>();
		PictureDTO newPicture = new PictureDTO(DB_NEW_PICTURE_ID);
		PictureDTO oldPicture = new PictureDTO(DB_PICTURE_ID);
		picturesDTO.add(newPicture);
		picturesDTO.add(oldPicture);
		PostDTO dto = new PostDTO(post.getId(), DB_NEW_POST_CONTENT, post.getCulturalOffer().getId(), picturesDTO);
		
		PostDTO updatedDTO = postService.update(dto, DB_POST_ID);
		assertEquals(updatedDTO.getPictures().size(), DB_NUMBER_OF_PICTURES);
		assertEquals(updatedDTO.getContent(), DB_NEW_POST_CONTENT);
		
		//clean up
		PostDTO oldDTO = new PostDTO(post.getId(), post.getContent(), post.getCulturalOffer().getId());
		picturesDTO.remove(newPicture);
		oldDTO.setPictures(picturesDTO);
		postService.update(oldDTO, DB_POST_ID);
	}
}
