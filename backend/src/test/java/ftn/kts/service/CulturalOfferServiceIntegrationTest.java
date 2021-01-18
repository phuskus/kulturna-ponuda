package ftn.kts.service;

import static ftn.kts.constants.CulturalOfferConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import ftn.kts.dto.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import ftn.kts.exceptions.UniqueConstraintViolationException;
import ftn.kts.model.CulturalOffer;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CulturalOfferServiceIntegrationTest {
	
	@Autowired
	private CulturalOfferService cultOfferService;
	
	@Test
	public void getAllDTO_FirstPageTwoOffers_TwoEntityDTOsReturned() {
		Pageable paging = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
		Page<CulturalOfferDTO> found = cultOfferService.getAllDTO(paging);
		assertEquals(found.getNumberOfElements(), FIND_ALL_NUMBER_OF_ITEMS);
	}
	
	@Test
	public void getAllDTO_SecondPageNoOffers_NoDTOsReturned() {
		Pageable paging = PageRequest.of(PAGEABLE_SECOND_PAGE, PAGEABLE_SIZE);
		Page<CulturalOfferDTO> found = cultOfferService.getAllDTO(paging);
		assertEquals(found.getNumberOfElements(), PAGEABLE_NO_ELEMENTS);
	}
	
	@Test
	public void getOneDTO_OfferExists_OneEntityReturned() {
		CulturalOfferDTO found = cultOfferService.getOneDTO(DB_CULTURAL_OFFER_ID);
		assertNotNull(found);
	}
	
	@Test(expected = NoSuchElementException.class)
	public void getOneDTO_OfferNotExists_NoSuchElementExceptionThrown() {
		cultOfferService.getOneDTO(DB_NO_CULTURAL_OFFER_ID);
	}
	
	@Test(expected = NoSuchElementException.class)
	public void createAndDelete_EntitySavedAndDeleted() throws UniqueConstraintViolationException {
		CulturalOfferDTO newOfferDTO = new CulturalOfferDTO(CREATE_NAME, CREATE_DESCRIPTION,
				CREATE_LATITUDE, CREATE_LONGITUDE, CREATE_ADRESS, CREATE_CITY, CREATE_REGION,
				ADMIN_ID, CATEGORY_ID);
		CulturalOfferDTO createdOffer = cultOfferService.create(newOfferDTO);
		assertNotNull(createdOffer.getId());
		
		//clean up
		cultOfferService.delete(createdOffer.getId());
		cultOfferService.getOne(createdOffer.getId());
	}
	
	@Test(expected = UniqueConstraintViolationException.class)
	public void createAndDelete_NameExists_UniqueConstraintViolationExceptionThrown() throws UniqueConstraintViolationException {
		CulturalOfferDTO newOfferDTO = new CulturalOfferDTO(DB_CULTURAL_OFFER_NAME, CREATE_DESCRIPTION,
				CREATE_LATITUDE, CREATE_LONGITUDE, CREATE_ADRESS, CREATE_CITY, CREATE_REGION,
				ADMIN_ID, CATEGORY_ID);
		cultOfferService.create(newOfferDTO);
	}
	
	@Test(expected = NoSuchElementException.class)
	public void delete_OfferNotExists_NoSuchElementExceptionThrown() {
		cultOfferService.delete(DB_NO_CULTURAL_OFFER_ID);
	}
	
	@Test
	public void updateOffer_EntityUpdatedAndRevertedToPreviousState() throws UniqueConstraintViolationException {
		CulturalOffer cultOffer = cultOfferService.getOne(DB_CULTURAL_OFFER_ID);
		Set<ReviewDTO> reviewsDTO = new HashSet<>();
		UserDTO user = new UserDTO(1L, "name", "user", "pass");
		ReviewDTO review1 = new ReviewDTO(1L, 5L, "Otkazano zbog korone", user, 2L, "name");
		ReviewDTO review2 = new ReviewDTO(2L, 0L, "Ne valja nista", user, 2L, "name");
		reviewsDTO.add(review1);
		reviewsDTO.add(review2);
		Set<PostDTO> postsDTO = new HashSet<>();
		PostDTO post1 = new PostDTO(FIRST_POST_ID, DB_CULTURAL_OFFER_ID);
		PostDTO post2 = new PostDTO(FIRST_POST_ID, DB_CULTURAL_OFFER_ID);
		postsDTO.add(post1);
		postsDTO.add(post2);
		// update name, description and pictures
		Set<PictureDTO> picturesDTO = new HashSet<>();
		PictureDTO newPicture = new PictureDTO(DB_NEW_PICTURE_ID);
		PictureDTO oldPicture = new PictureDTO(DB_PICTURE_ID);
		picturesDTO.add(newPicture);
		picturesDTO.add(oldPicture);
		CulturalOfferDTO dto = new CulturalOfferDTO(cultOffer.getId(), CREATE_NAME, CREATE_DESCRIPTION,
				  cultOffer.getLatitude(), cultOffer.getLongitude(), cultOffer.getAddress(), cultOffer.getCity(),
				  cultOffer.getRegion(), cultOffer.getAdmin().getId(), cultOffer.getCategory().getId(), reviewsDTO,
				  postsDTO, picturesDTO);
		CulturalOfferDTO updatedDTO = cultOfferService.update(dto, DB_CULTURAL_OFFER_ID);
		assertEquals(updatedDTO.getName(), CREATE_NAME);
		assertEquals(updatedDTO.getDescription(), CREATE_DESCRIPTION);
		assertEquals(updatedDTO.getPictures().size(), NUMBER_OF_NEW_PICTURES);
		
		//clean up
		dto.setName(cultOffer.getName());
		dto.setDescription(cultOffer.getDescription());
		picturesDTO.remove(newPicture);
		dto.setPictures(picturesDTO);
		CulturalOfferDTO checkCleanUp = cultOfferService.update(dto, DB_CULTURAL_OFFER_ID);
		assertEquals(checkCleanUp.getPictures().size(), NUMBER_OF_OLD_PICTURES);
	}
	
	@Test(expected = UniqueConstraintViolationException.class)
	public void updateOffer_NameExists_UniqueConstraintViolationExceptionThrown() throws UniqueConstraintViolationException {
		CulturalOffer cultOffer = cultOfferService.getOne(DB_CULTURAL_OFFER_ID);
		Set<ReviewDTO> reviewsDTO = new HashSet<>();
		Set<PostDTO> postsDTO = new HashSet<>();
		PostDTO post1 = new PostDTO(FIRST_POST_ID, DB_CULTURAL_OFFER_ID);
		PostDTO post2 = new PostDTO(FIRST_POST_ID, DB_CULTURAL_OFFER_ID);
		postsDTO.add(post1);
		postsDTO.add(post2);
		// update name
		Set<PictureDTO> picturesDTO = new HashSet<>();
		PictureDTO oldPicture = new PictureDTO(DB_PICTURE_ID);
		picturesDTO.add(oldPicture);
		CulturalOfferDTO dto = new CulturalOfferDTO(cultOffer.getId(), UPDATE_DUPLICATE_NAME, cultOffer.getDescription(),
				  cultOffer.getLatitude(), cultOffer.getLongitude(), cultOffer.getAddress(), cultOffer.getCity(),
				  cultOffer.getRegion(), cultOffer.getAdmin().getId(), cultOffer.getCategory().getId(), reviewsDTO,
				  postsDTO, picturesDTO);
		cultOfferService.update(dto, DB_CULTURAL_OFFER_ID);
	}
	
	@Test
	public void getOne_OfferExists_OneEntityReturned() {
		CulturalOffer found = cultOfferService.getOne(DB_CULTURAL_OFFER_ID);
		assertNotNull(found);
	}
	
	@Test(expected = NoSuchElementException.class)
	public void getOne_OfferNotExists_NoSuchElementExceptionThrown() {
		cultOfferService.getOne(DB_NO_CULTURAL_OFFER_ID);
	}
	
	@Test
	public void getAll_TwoEntitesReturned() {
		List<CulturalOffer> found = cultOfferService.getAll();
		assertEquals(found.size(), FIND_ALL_NUMBER_OF_ITEMS);
	}
	
	@Test
	public void filterCategory_FirstPageTwoOffers_TwoEntitiesReturned() {
		Pageable paging = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
		Page<CulturalOfferDTO> found = cultOfferService.filterCategory(CATEGORY_ONE_ID, paging);
		assertEquals(found.getNumberOfElements(), CATEGORY_ONE_RESULTS);
	}
	
	@Test
	public void filterCategory_SecondPageNoOffers_EmptyPageReturned() {
		Pageable paging = PageRequest.of(PAGEABLE_SECOND_PAGE, PAGEABLE_SIZE);
		Page<CulturalOfferDTO> found = cultOfferService.filterCategory(CATEGORY_ONE_ID, paging);
		assertEquals(found.getNumberOfElements(), PAGEABLE_NO_ELEMENTS);
	}
	
	@Test
	public void filterCategory_FirstPageTwoOffersAscending_TwoEntitiesAscendingIdReturned() {
		Pageable paging = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE, Sort.by("id").ascending());
		List<CulturalOfferDTO> found =  cultOfferService.filterCategory(CATEGORY_ONE_ID, paging).getContent();
		assertEquals(found.size(), CATEGORY_ONE_RESULTS);
		assertThat(found.get(0).getId()).isLessThan(found.get(1).getId());
	}
	
	@Test
	public void filterCategory_FirstPageTwoOffersDescending_TwoEntitiesDescendingIdReturned() {
		Pageable paging = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE, Sort.by("id").descending());
		List<CulturalOfferDTO> found =  cultOfferService.filterCategory(CATEGORY_ONE_ID, paging).getContent();
		assertEquals(found.size(), CATEGORY_ONE_RESULTS);
		assertThat(found.get(0).getId()).isGreaterThan(found.get(1).getId());
	}
	
	@Test
	public void filterCategory_NoMatch_EmptyPageReturned() {
		Pageable paging = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
		Page<CulturalOfferDTO> found = cultOfferService.filterCategory(CATEGORY_TWO_ID, paging);
		assertEquals(found.getNumberOfElements(), CATEGORY_TWO_RESULTS);
	}
	
	@Test
	public void filterCity_FirstPageTwoOffers_OneEntityReturned() {
		Pageable paging = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
		Page<CulturalOfferDTO> found =  cultOfferService.filterCity(DB_CITY_NAME, paging);
		assertEquals(found.getNumberOfElements(), FIND_ALL_NUMBER_OF_ITEMS_BY_CITY);
	}
	
	@Test
	public void filterCity_MixedCityNameAsending_TwoEntitiesAscendingCityReturned() {
		Pageable paging = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE, Sort.by("city").ascending());
		List<CulturalOfferDTO> found =  cultOfferService.filterCity(DB_CITY_MIXED_NAME, paging).getContent();
		assertEquals(found.size(), PAGEABLE_TWO_ELEMENTS);
		assertThat(found.get(0).getCity()).isLessThan(found.get(1).getCity());
	}
	
	@Test
	public void filterCity_MixedCityNameDescending_TwoEntitiesDescendingCityReturned() {
		Pageable paging = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE, Sort.by("city").descending());
		List<CulturalOfferDTO> found =  cultOfferService.filterCity(DB_CITY_MIXED_NAME, paging).getContent();
		assertEquals(found.size(), PAGEABLE_TWO_ELEMENTS);
		assertThat(found.get(0).getCity()).isGreaterThan(found.get(1).getCity());
	}
	
	@Test
	public void filterCity_NoMatch_EmptyPageReturned() {
		Pageable paging = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
		Page<CulturalOfferDTO> found =  cultOfferService.filterCity(DB_NO_SUCH_CITY, paging);
		assertEquals(found.getNumberOfElements(), PAGEABLE_NO_ELEMENTS);
	}
	
	@Test
	public void filterName_OfferNameSubstring_TwoEntitesAscendingNameReturned() {
		Pageable paging = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE, Sort.by("name").ascending());
		List<CulturalOfferDTO> found =  cultOfferService.filterName(DB_CULTURAL_OFFER_PART_NAME, paging).getContent();
		assertEquals(found.size(), PAGEABLE_TWO_ELEMENTS);
		assertThat(found.get(0).getName()).isLessThan(found.get(1).getName());
	}
	
	@Test
	public void filterName_OfferNameSubstring_TwoEntitesDescendingNameReturned() {
		Pageable paging = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE, Sort.by("name").descending());
		List<CulturalOfferDTO> found =  cultOfferService.filterName(DB_CULTURAL_OFFER_PART_NAME, paging).getContent();
		assertEquals(found.size(), PAGEABLE_TWO_ELEMENTS);
		assertThat(found.get(0).getName()).isGreaterThan(found.get(1).getName());
	}
	
	@Test
	public void filterName_NoMatch_EmptyPageReturned() {
		Pageable paging = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
		Page<CulturalOfferDTO> found =  cultOfferService.filterName(DB_CULTURAL_OFFER_NO_SUCH_NAME, paging);
		assertEquals(found.getNumberOfElements(), PAGEABLE_NO_ELEMENTS);
	}
	
	@Test
	public void filterDescription_DescriptionSubstring_TwoEntitesReturned() {
		Pageable paging = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
		Page<CulturalOfferDTO> found = cultOfferService.filterDescription(DB_PART_DESCRIPTION, paging);
		assertEquals(found.getNumberOfElements(), PAGEABLE_TWO_ELEMENTS);
	}
	
	@Test
	public void filterDescription_NoMatch_EmptyPageReturned() {
		Pageable paging = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
		Page<CulturalOfferDTO> found =  cultOfferService.filterName(DB_NO_SUCH_DESCRIPTION, paging);
		assertEquals(found.getNumberOfElements(), PAGEABLE_NO_ELEMENTS);
	}
	
}
