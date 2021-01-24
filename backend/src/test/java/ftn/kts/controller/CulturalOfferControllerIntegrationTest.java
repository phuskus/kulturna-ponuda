package ftn.kts.controller;

import static ftn.kts.constants.CulturalOfferConstants.DB_CITY_NAME;
import static ftn.kts.constants.CulturalOfferConstants.DB_CITY_NAME_LOWERCASE;
import static ftn.kts.constants.CulturalOfferConstants.DB_CITY_PART_NAME;
import static ftn.kts.constants.CulturalOfferConstants.DB_CULTURAL_OFFER_NAME;
import static ftn.kts.constants.CulturalOfferConstants.DB_CULTURAL_OFFER_NAME_UPPERCASE;
import static ftn.kts.constants.CulturalOfferConstants.DB_CULTURAL_OFFER_NO_SUCH_NAME;
import static ftn.kts.constants.CulturalOfferConstants.DB_CULTURAL_OFFER_PART_NAME;
import static ftn.kts.constants.CulturalOfferConstants.CATEGORY_ONE_ID;
import static ftn.kts.constants.CulturalOfferConstants.CATEGORY_TWO_ID;
import static ftn.kts.constants.CulturalOfferConstants.CATEGORY_NULL_ID;
import static ftn.kts.constants.CulturalOfferConstants.CATEGORY_ONE_RESULTS;
import static ftn.kts.constants.CulturalOfferConstants.CATEGORY_TWO_RESULTS;
import static ftn.kts.constants.CulturalOfferConstants.DB_NO_SUCH_CITY;
import static ftn.kts.constants.CulturalOfferConstants.DB_NO_SUCH_DESCRIPTION;
import static ftn.kts.constants.CulturalOfferConstants.DB_PART_DESCRIPTION;
import static ftn.kts.constants.CulturalOfferConstants.DB_PART_DESCRIPTION_UPPERCASE;
import static ftn.kts.constants.CulturalOfferConstants.FIND_NOT_EXIST_ID;
import static ftn.kts.constants.CulturalOfferConstants.PAGEABLE_ONE_ELEMENTS;
import static ftn.kts.constants.CulturalOfferConstants.PAGEABLE_SORTBYID_ASC_FIRST;
import static ftn.kts.constants.CulturalOfferConstants.PAGEABLE_SORTBYID_ASC_SECOND;
import static ftn.kts.constants.CulturalOfferConstants.PAGEABLE_SORTBYID_DESC_FIRST;
import static ftn.kts.constants.CulturalOfferConstants.PAGEABLE_SORTBYID_DESC_SECOND;
import static ftn.kts.constants.CulturalOfferConstants.PAGEABLE_TWO_ELEMENTS;
import static ftn.kts.constants.CulturalOfferConstants.CREATE_NAME;
import static ftn.kts.constants.CulturalOfferConstants.CREATE_DESCRIPTION;
import static ftn.kts.constants.CulturalOfferConstants.CREATE_CITY;
import static ftn.kts.constants.CulturalOfferConstants.UPDATE_NEW_DESCRIPTION;
import static ftn.kts.constants.CulturalOfferConstants.UPDATE_NEW_CITY;
import static ftn.kts.util.ControllerUtil.getAuthHeadersAdmin;
import static ftn.kts.util.ControllerUtil.getAuthHeadersUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import ftn.kts.dto.CulturalOfferDTO;
import ftn.kts.dto.PictureDTO;
import ftn.kts.exceptions.ErrorMessage;
import ftn.kts.model.CulturalOffer;
import ftn.kts.model.Subcategory;
import ftn.kts.pages.PageCulturalOffers;
import ftn.kts.service.CulturalOfferService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CulturalOfferControllerIntegrationTest {
	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private CulturalOfferService offerService;

	@Test
	public void getOffersPage_FirstPageOneOffer_ReturnsOneEntity() {
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(getAuthHeadersUser(restTemplate));
		ResponseEntity<PageCulturalOffers> responseEntity = restTemplate
				.exchange("/cultural_offers?pageNo=0&pageSize=1", HttpMethod.GET, httpEntity, PageCulturalOffers.class);
		PageCulturalOffers offers = responseEntity.getBody();

		List<CulturalOfferDTO> offersList = offers.getContent();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(PAGEABLE_ONE_ELEMENTS, offersList.size());
	}

	@Test
	public void getOffersPage_SecondPageOneOffer_ReturnsOneEntity() {
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(getAuthHeadersUser(restTemplate));
		ResponseEntity<PageCulturalOffers> responseEntity = restTemplate
				.exchange("/cultural_offers?pageNo=1&pageSize=1", HttpMethod.GET, httpEntity, PageCulturalOffers.class);
		PageCulturalOffers offers = responseEntity.getBody();

		List<CulturalOfferDTO> offersList = offers.getContent();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(PAGEABLE_ONE_ELEMENTS, offersList.size());
	}

	@Test
	public void getOffersPage_ThirdPageOneOffer_ReturnsNoEntities() {
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(getAuthHeadersUser(restTemplate));
		ResponseEntity<PageCulturalOffers> responseEntity = restTemplate
				.exchange("/cultural_offers?pageNo=2&pageSize=1", HttpMethod.GET, httpEntity, PageCulturalOffers.class);
		PageCulturalOffers offers = responseEntity.getBody();

		List<CulturalOfferDTO> offersList = offers.getContent();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(0, offersList.size());
	}

	@Test
	public void getOffersPage_FirstPageTwoOffers_ReturnsTwoEntities() {
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(getAuthHeadersUser(restTemplate));
		ResponseEntity<PageCulturalOffers> responseEntity = restTemplate
				.exchange("/cultural_offers?pageNo=0&pageSize=2", HttpMethod.GET, httpEntity, PageCulturalOffers.class);
		PageCulturalOffers offers = responseEntity.getBody();

		List<CulturalOfferDTO> offersList = offers.getContent();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(PAGEABLE_TWO_ELEMENTS, offersList.size());
	}

	@Test
	public void getOffersPage_SecondPageTwoOffers_ReturnsNoEntities() {
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(getAuthHeadersUser(restTemplate));
		ResponseEntity<PageCulturalOffers> responseEntity = restTemplate
				.exchange("/cultural_offers?pageNo=1&pageSize=2", HttpMethod.GET, httpEntity, PageCulturalOffers.class);
		PageCulturalOffers offers = responseEntity.getBody();

		List<CulturalOfferDTO> offersList = offers.getContent();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(0, offersList.size());
	}

	@Test
	public void getOffersPage_SortByIdAscending_ReturnsSortedList() {
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(getAuthHeadersUser(restTemplate));
		ResponseEntity<PageCulturalOffers> responseEntity = restTemplate.exchange(
				"/cultural_offers?pageNo=0&pageSize=2&descending=false", HttpMethod.GET, httpEntity,
				PageCulturalOffers.class);
		PageCulturalOffers offers = responseEntity.getBody();

		List<CulturalOfferDTO> offersList = offers.getContent();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(2, offersList.size());
		assertEquals(PAGEABLE_SORTBYID_ASC_FIRST, offersList.get(0).getId());
		assertEquals(PAGEABLE_SORTBYID_ASC_SECOND, offersList.get(1).getId());
	}

	@Test
	public void getOffersPage_SortByIdDescending_ReturnsSortedList() {
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(getAuthHeadersUser(restTemplate));
		ResponseEntity<PageCulturalOffers> responseEntity = restTemplate.exchange(
				"/cultural_offers?pageNo=0&pageSize=2&descending=true", HttpMethod.GET, httpEntity,
				PageCulturalOffers.class);
		PageCulturalOffers offers = responseEntity.getBody();

		List<CulturalOfferDTO> offersList = offers.getContent();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(2, offersList.size());
		assertEquals(PAGEABLE_SORTBYID_DESC_FIRST, offersList.get(0).getId());
		assertEquals(PAGEABLE_SORTBYID_DESC_SECOND, offersList.get(1).getId());
	}

	@Test
	public void getOffersPage_SortByNameAscending_ReturnsSortedList() {
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(getAuthHeadersUser(restTemplate));
		ResponseEntity<PageCulturalOffers> responseEntity = restTemplate.exchange(
				"/cultural_offers?pageNo=0&pageSize=2&sortBy=name&descending=false", HttpMethod.GET, httpEntity,
				PageCulturalOffers.class);
		PageCulturalOffers offers = responseEntity.getBody();

		List<CulturalOfferDTO> offersList = offers.getContent();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(2, offersList.size());
		assertThat(offersList.get(0).getName()).isLessThan(offersList.get(1).getName());
	}

	@Test
	public void getOffersPage_SortByNameDescending_ReturnsSortedList() {
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(getAuthHeadersUser(restTemplate));
		ResponseEntity<PageCulturalOffers> responseEntity = restTemplate.exchange(
				"/cultural_offers?pageNo=0&pageSize=2&sortBy=name&descending=true", HttpMethod.GET, httpEntity,
				PageCulturalOffers.class);
		PageCulturalOffers offers = responseEntity.getBody();

		List<CulturalOfferDTO> offersList = offers.getContent();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(2, offersList.size());
		assertThat(offersList.get(0).getName()).isGreaterThan(offersList.get(1).getName());
	}

	@Test
	public void getOffer_ExistsID_ReturnsOneEntity() {
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(getAuthHeadersUser(restTemplate));
		ResponseEntity<CulturalOfferDTO> responseEntity = restTemplate.exchange("/cultural_offers/1", HttpMethod.GET,
				httpEntity, CulturalOfferDTO.class);

		CulturalOfferDTO offer = responseEntity.getBody();
		assertNotNull(offer.getId());
		assertEquals(offer.getName(), DB_CULTURAL_OFFER_NAME);

	}

	@Test
	public void getOffer_NotExistsID_ReturnsNotFound() {
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(getAuthHeadersUser(restTemplate));
		ResponseEntity<ErrorMessage> responseEntity = restTemplate.exchange("/cultural_offers/" + FIND_NOT_EXIST_ID,
				HttpMethod.GET, httpEntity, ErrorMessage.class);

		ErrorMessage error = responseEntity.getBody();
		assertNotNull(error.getMessage());
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals("Cultural offer with id " + FIND_NOT_EXIST_ID + " doesn't exist!", error.getMessage());
	}

	@Test
	public void filterOffersName_ExactName_ReturnsOneEntity() {
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(getAuthHeadersUser(restTemplate));
		ResponseEntity<PageCulturalOffers> responseEntity = restTemplate.exchange(
				"/cultural_offers/name/" + DB_CULTURAL_OFFER_NAME, HttpMethod.GET, httpEntity,
				PageCulturalOffers.class);

		PageCulturalOffers offers = responseEntity.getBody();

		List<CulturalOfferDTO> offersList = offers.getContent();
		
		assertEquals(1, offersList.size());
		CulturalOfferDTO offer = offersList.get(0);
		assertNotNull(offer.getId());
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(DB_CULTURAL_OFFER_NAME, offer.getName());
	}

	@Test
	public void filterOffersName_MixedCaseName_ReturnsOneEntity() {
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(getAuthHeadersUser(restTemplate));
		ResponseEntity<PageCulturalOffers> responseEntity = restTemplate.exchange(
				"/cultural_offers/name/" + DB_CULTURAL_OFFER_NAME_UPPERCASE, HttpMethod.GET, httpEntity,
				PageCulturalOffers.class);

		PageCulturalOffers offers = responseEntity.getBody();

		List<CulturalOfferDTO> offersList = offers.getContent();
		
		assertEquals(1, offersList.size());
		CulturalOfferDTO offer = offersList.get(0);
		assertNotNull(offer.getId());
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(DB_CULTURAL_OFFER_NAME, offer.getName());
	}

	@Test
	public void filterOffersName_NoMatch_ReturnsNoEntities() {
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(getAuthHeadersUser(restTemplate));
		ResponseEntity<PageCulturalOffers> responseEntity = restTemplate.exchange(
				"/cultural_offers/name/" + DB_CULTURAL_OFFER_NO_SUCH_NAME, HttpMethod.GET, httpEntity,
				PageCulturalOffers.class);
		PageCulturalOffers offers = responseEntity.getBody();

		List<CulturalOfferDTO> offersList = offers.getContent();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(0, offersList.size());
	}

	@Test
	public void filterOffersName_PartName_ReturnsTwoEntities() {
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(getAuthHeadersUser(restTemplate));
		ResponseEntity<PageCulturalOffers> responseEntity = restTemplate.exchange(
				"/cultural_offers/name/" + DB_CULTURAL_OFFER_PART_NAME + "?sortBy=id&descending=false", HttpMethod.GET,
				httpEntity, PageCulturalOffers.class);

		PageCulturalOffers offers = responseEntity.getBody();

		List<CulturalOfferDTO> offersList = offers.getContent();
		
		assertEquals(2, offersList.size());
		CulturalOfferDTO offer =offersList.get(0);
		assertNotNull(offer.getId());
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(DB_CULTURAL_OFFER_NAME, offer.getName());
		assertNotNull(offersList.get(1).getId());
		assertEquals(new Long(2), offersList.get(1).getId());
	}

	@Test
	public void filterOffersCity_ExactCity_ReturnsOneEntity() {
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(getAuthHeadersUser(restTemplate));
		ResponseEntity<PageCulturalOffers> responseEntity = restTemplate.exchange(
				"/cultural_offers/city/" + DB_CITY_NAME, HttpMethod.GET, httpEntity, PageCulturalOffers.class);

		PageCulturalOffers offers = responseEntity.getBody();

		List<CulturalOfferDTO> offersList = offers.getContent();
		
		assertEquals(1, offersList.size());
		CulturalOfferDTO offer = offersList.get(0);
		assertNotNull(offer.getId());
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(DB_CULTURAL_OFFER_NAME, offer.getName());
	}

	@Test
	public void filterOffersCity_MixedCaseCity_ReturnsOneEntity() {
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(getAuthHeadersUser(restTemplate));
		ResponseEntity<PageCulturalOffers> responseEntity = restTemplate.exchange(
				"/cultural_offers/city/" + DB_CITY_NAME_LOWERCASE, HttpMethod.GET, httpEntity,
				PageCulturalOffers.class);

		PageCulturalOffers offers = responseEntity.getBody();

		List<CulturalOfferDTO> offersList = offers.getContent();
		
		assertEquals(1, offersList.size());
		CulturalOfferDTO offer = offersList.get(0);
		assertNotNull(offer.getId());
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(DB_CULTURAL_OFFER_NAME, offer.getName());
	}

	@Test
	public void filterOffersCity_NoMatch_ReturnsNoEntities() {
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(getAuthHeadersUser(restTemplate));
		ResponseEntity<PageCulturalOffers> responseEntity = restTemplate.exchange(
				"/cultural_offers/city/" + DB_NO_SUCH_CITY, HttpMethod.GET, httpEntity, PageCulturalOffers.class);
		PageCulturalOffers offers = responseEntity.getBody();

		List<CulturalOfferDTO> offersList = offers.getContent();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(0, offersList.size());
	}

	@Test
	public void filterOffersCity_PartName_ReturnsOneEntity() {
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(getAuthHeadersUser(restTemplate));
		ResponseEntity<PageCulturalOffers> responseEntity = restTemplate.exchange(
				"/cultural_offers/city/" + DB_CITY_PART_NAME + "?sortBy=id&descending=false", HttpMethod.GET,
				httpEntity, PageCulturalOffers.class);

		PageCulturalOffers offers = responseEntity.getBody();

		List<CulturalOfferDTO> offersList = offers.getContent();
		
		assertEquals(1, offersList.size());
		CulturalOfferDTO offer = offersList.get(0);
		assertNotNull(offer.getId());
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(DB_CULTURAL_OFFER_NAME, offer.getName());
		assertEquals(DB_CITY_NAME, offersList.get(0).getCity());
	}

	@Test
	public void filterOffersDescription_PartDescription_ReturnsTwoEntities() {
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(getAuthHeadersUser(restTemplate));
		ResponseEntity<PageCulturalOffers> responseEntity = restTemplate.exchange(
				"/cultural_offers/description/" + DB_PART_DESCRIPTION + "?sortBy=id&descending=false", HttpMethod.GET,
				httpEntity, PageCulturalOffers.class);

		PageCulturalOffers offers = responseEntity.getBody();

		List<CulturalOfferDTO> offersList = offers.getContent();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(2, offersList.size());
		CulturalOfferDTO offer1 = offersList.get(0);
		CulturalOfferDTO offer2 = offersList.get(1);
		assertNotNull(offer1.getId());
		assertNotNull(offer2.getId());
		assertEquals(PAGEABLE_SORTBYID_ASC_FIRST, offer1.getId());
		assertEquals(PAGEABLE_SORTBYID_ASC_SECOND, offer2.getId());
	}

	@Test
	public void filterOffersDescription_PartDescriptionMixedCase_ReturnsTwoEntities() {
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(getAuthHeadersUser(restTemplate));
		ResponseEntity<PageCulturalOffers> responseEntity = restTemplate.exchange(
				"/cultural_offers/description/" + DB_PART_DESCRIPTION_UPPERCASE, HttpMethod.GET, httpEntity,
				PageCulturalOffers.class);

		PageCulturalOffers offers = responseEntity.getBody();

		List<CulturalOfferDTO> offersList = offers.getContent();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(2, offersList.size());
		CulturalOfferDTO offer1 = offersList.get(0);
		CulturalOfferDTO offer2 = offersList.get(1);
		assertNotNull(offer1.getId());
		assertNotNull(offer2.getId());
		assertEquals(PAGEABLE_SORTBYID_ASC_FIRST, offer1.getId());
		assertEquals(PAGEABLE_SORTBYID_ASC_SECOND, offer2.getId());
	}

	@Test
	public void filterOffersDescription_NoMatch_ReturnsNoEntities() {
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(getAuthHeadersUser(restTemplate));
		ResponseEntity<PageCulturalOffers> responseEntity = restTemplate.exchange(
				"/cultural_offers/description/" + DB_NO_SUCH_DESCRIPTION, HttpMethod.GET, httpEntity,
				PageCulturalOffers.class);
		PageCulturalOffers offers = responseEntity.getBody();

		List<CulturalOfferDTO> offersList = offers.getContent();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(0, offersList.size());
	}

	// filter category

	@Test
	public void filterOffersCategory_ExistsCategoryOne_ReturnsTwoEntities() {
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(getAuthHeadersUser(restTemplate));
		ResponseEntity<PageCulturalOffers> responseEntity = restTemplate.exchange(
				"/cultural_offers/category/" + CATEGORY_ONE_ID, HttpMethod.GET, httpEntity, PageCulturalOffers.class);

		PageCulturalOffers offers = responseEntity.getBody();

		List<CulturalOfferDTO> offersList = offers.getContent();
		
		assertEquals(CATEGORY_ONE_RESULTS, offersList.size());
		CulturalOfferDTO offer = offersList.get(0);
		assertNotNull(offer.getId());
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		Subcategory category = offerService.getOne(offer.getId()).getCategory();

		assertEquals(CATEGORY_ONE_ID, category.getId());
	}

	@Test
	public void filterOffersCategory_ExistsCategoryTwo_ReturnsNoEntities() {
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(getAuthHeadersUser(restTemplate));
		ResponseEntity<PageCulturalOffers> responseEntity = restTemplate.exchange(
				"/cultural_offers/category/" + CATEGORY_TWO_ID, HttpMethod.GET, httpEntity, PageCulturalOffers.class);

		PageCulturalOffers offers = responseEntity.getBody();

		List<CulturalOfferDTO> offersList = offers.getContent();
		
		assertEquals(CATEGORY_TWO_RESULTS, offersList.size());
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	public void filterOffersCategory_ExistsCategoryTwo_ReturnsNotFound() {
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(getAuthHeadersUser(restTemplate));
		ResponseEntity<ErrorMessage> responseEntity = restTemplate.exchange(
				"/cultural_offers/category/" + CATEGORY_NULL_ID, HttpMethod.GET, httpEntity, ErrorMessage.class);

		ErrorMessage message = responseEntity.getBody();
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertNotNull(message);
		assertEquals("Subcategory with id " + CATEGORY_NULL_ID + " doesn't exist!", message.getMessage());
	}
//
//	@Test
//	public void addOffer_ValidWithPicturesAsAdmin_ReturnsObject() throws Exception {
//		CulturalOfferDTO dto = new CulturalOfferDTO("testname", "testDesc", 0F, 0F, "adr", "city", "reg", 3L, 1L);
//		HashSet<PictureDTO> pictures = new HashSet<>();
//
//		pictures.add(new PictureDTO(1L));
//		dto.setPictures(pictures);
//
//		HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto, getAuthHeadersAdmin(restTemplate));
//
//		int size = offerService.getAll().size();
//
//		ResponseEntity<CulturalOfferDTO> responseEntity = restTemplate.exchange("/cultural_offers", HttpMethod.POST,
//				httpEntity, CulturalOfferDTO.class);
//
//		CulturalOfferDTO added = responseEntity.getBody();
//
//		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
//		assertNotNull(added.getId());
//		assertEquals(1, added.getPictures().size());
//
//		List<CulturalOffer> offers = offerService.getAll();
//		assertEquals(size + 1, offers.size());
//		assertEquals(added.getId(), offers.get(offers.size() - 1).getId());
//
//		offerService.delete(added.getId());
//	}
//
//	@Test
//	public void addOffer_ValidNoPicturesAsAdmin_ReturnsObject() throws Exception {
//		CulturalOfferDTO dto = new CulturalOfferDTO("testname", "testDesc", 0F, 0F, "adr", "city", "reg", 3L, 1L);
//		HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto, getAuthHeadersAdmin(restTemplate));
//
//		int size = offerService.getAll().size();
//
//		ResponseEntity<CulturalOfferDTO> responseEntity = restTemplate.exchange("/cultural_offers", HttpMethod.POST,
//				httpEntity, CulturalOfferDTO.class);
//
//		CulturalOfferDTO added = responseEntity.getBody();
//
//		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
//		assertNotNull(added.getId());
//		assertEquals(0, added.getPictures().size());
//
//		List<CulturalOffer> offers = offerService.getAll();
//		assertEquals(size + 1, offers.size());
//		assertEquals(added.getId(), offers.get(offers.size() - 1).getId());
//
//		offerService.delete(added.getId());
//	}
//
//	@Test
//	public void addOffer_NullObjectAsAdmin_ReturnsObject() throws Exception {
//		CulturalOfferDTO dto = null;
//		HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto, getAuthHeadersAdmin(restTemplate));
//
//		ResponseEntity<ErrorMessage> responseEntity = restTemplate.exchange("/cultural_offers", HttpMethod.POST,
//				httpEntity, ErrorMessage.class);
//
//		ErrorMessage message = responseEntity.getBody();
//
//		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
//		assertNotNull(message.getMessage());
//	}
//
//	@Test
//	public void addOffer_MissingNameDescriptionAsAdmin_ReturnsObject() throws Exception {
//		CulturalOfferDTO dto = new CulturalOfferDTO("testname", "testDesc", 0F, 0F, "adr", "city", "reg", 3L, 1L);
//		dto.setName(null);
//		dto.setDescription(null);
//		HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto, getAuthHeadersAdmin(restTemplate));
//
//		ResponseEntity<ErrorMessage> responseEntity = restTemplate.exchange("/cultural_offers", HttpMethod.POST,
//				httpEntity, ErrorMessage.class);
//
//		ErrorMessage message = responseEntity.getBody();
//
//		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
//		assertNotNull(message.getMessage());
//		assertEquals("Validation errors occurred!", message.getMessage());
//
//		for (Map.Entry<String, String> kp : message.getErrors().entrySet()) {
//			if (kp.getKey().equals("name")) {
//				assertEquals("Name is required!", kp.getValue());
//			} else if (kp.getKey().equals("description")) {
//				assertEquals("Description is required!", kp.getValue());
//			}
//		}
//	}
//
//	@Test
//	public void addOffer_ValidAsUser_ReturnsUnauthorized() throws Exception {
//		CulturalOfferDTO dto = new CulturalOfferDTO("testname", "testDesc", 0F, 0F, "adr", "city", "reg", 3L, 1L);
//		HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto, getAuthHeadersUser(restTemplate));
//		ResponseEntity<ErrorMessage> responseEntity = restTemplate.exchange("/cultural_offers", HttpMethod.POST,
//				httpEntity, ErrorMessage.class);
//
//		assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
//	}
//
//	@Test
//	public void updateOffer_ChangeDescriptionAsAdmin_ReturnsObject() throws Exception {
//		CulturalOfferDTO dto = new CulturalOfferDTO(CREATE_NAME, CREATE_DESCRIPTION, 0F, 0F, "adr", CREATE_CITY, "reg",
//				3L, 1L);
//
//		CulturalOfferDTO added = offerService.create(dto);
//
//		dto.setDescription(UPDATE_NEW_DESCRIPTION);
//
//		HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto, getAuthHeadersAdmin(restTemplate));
//
//		ResponseEntity<CulturalOfferDTO> responseEntity = restTemplate.exchange("/cultural_offers/" + added.getId(),
//				HttpMethod.PUT, httpEntity, CulturalOfferDTO.class);
//
//		CulturalOfferDTO changed = responseEntity.getBody();
//
//		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//		assertNotNull(changed.getId());
//		assertEquals(added.getId(), changed.getId());
//
//		assertEquals(UPDATE_NEW_DESCRIPTION, changed.getDescription());
//
//		offerService.delete(added.getId());
//	}
//
//	@Test
//	public void updateOffer_ChangeDescriptionAndCityAsAdmin_ReturnsObject() throws Exception {
//		CulturalOfferDTO dto = new CulturalOfferDTO(CREATE_NAME, CREATE_DESCRIPTION, 0F, 0F, "adr", CREATE_CITY, "reg",
//				3L, 1L);
//
//		CulturalOfferDTO added = offerService.create(dto);
//
//		dto.setDescription(UPDATE_NEW_DESCRIPTION);
//		dto.setCity(UPDATE_NEW_CITY);
//
//		HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto, getAuthHeadersAdmin(restTemplate));
//
//		ResponseEntity<CulturalOfferDTO> responseEntity = restTemplate.exchange("/cultural_offers/" + added.getId(),
//				HttpMethod.PUT, httpEntity, CulturalOfferDTO.class);
//
//		CulturalOfferDTO changed = responseEntity.getBody();
//
//		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//		assertNotNull(changed.getId());
//		assertEquals(added.getId(), changed.getId());
//
//		assertEquals(UPDATE_NEW_DESCRIPTION, changed.getDescription());
//		assertEquals(UPDATE_NEW_CITY, changed.getCity());
//
//		offerService.delete(added.getId());
//	}
//
//	@Test
//	public void updateOffer_AddPicturesAsAdmin_ReturnsObject() throws Exception {
//		CulturalOfferDTO dto = new CulturalOfferDTO(CREATE_NAME, CREATE_DESCRIPTION, 0F, 0F, "adr", CREATE_CITY, "reg",
//				3L, 1L);
//
//		CulturalOfferDTO added = offerService.create(dto);
//
//		HashSet<PictureDTO> pictures = new HashSet<>();
//		pictures.add(new PictureDTO(1L));
//		dto.setPictures(pictures);
//
//		HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto, getAuthHeadersAdmin(restTemplate));
//
//		ResponseEntity<CulturalOfferDTO> responseEntity = restTemplate.exchange("/cultural_offers/" + added.getId(),
//				HttpMethod.PUT, httpEntity, CulturalOfferDTO.class);
//
//		CulturalOfferDTO changed = responseEntity.getBody();
//
//		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//		assertNotNull(changed.getId());
//		assertEquals(added.getId(), changed.getId());
//
//		assertEquals(1, changed.getPictures().size());
//		assertEquals(new Long(1L), ((PictureDTO) changed.getPictures().toArray()[0]).getId());
//
//		offerService.delete(added.getId());
//	}
//
//	@Test
//	public void updateOffer_RemovePicturesAsAdmin_ReturnsObject() throws Exception {
//		CulturalOfferDTO dto = new CulturalOfferDTO(CREATE_NAME, CREATE_DESCRIPTION, 0F, 0F, "adr", CREATE_CITY, "reg",
//				3L, 1L);
//		HashSet<PictureDTO> pictures = new HashSet<>();
//		pictures.add(new PictureDTO(1L));
//		dto.setPictures(pictures);
//
//		CulturalOfferDTO added = offerService.create(dto);
//
//		dto.setPictures(new HashSet<>());
//
//		HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto, getAuthHeadersAdmin(restTemplate));
//
//		ResponseEntity<CulturalOfferDTO> responseEntity = restTemplate.exchange("/cultural_offers/" + added.getId(),
//				HttpMethod.PUT, httpEntity, CulturalOfferDTO.class);
//
//		CulturalOfferDTO changed = responseEntity.getBody();
//
//		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//		assertNotNull(changed.getId());
//		assertEquals(added.getId(), changed.getId());
//
//		assertEquals(0, changed.getPictures().size());
//
//		offerService.delete(added.getId());
//	}
//
//	@Test
//	public void updateOffer_UpdateAsUser_ReturnsUnauthorized() throws Exception {
//		CulturalOfferDTO dto = new CulturalOfferDTO(CREATE_NAME, CREATE_DESCRIPTION, 0F, 0F, "adr", CREATE_CITY, "reg",
//				3L, 1L);
//
//		CulturalOfferDTO added = offerService.create(dto);
//
//		dto.setDescription(UPDATE_NEW_DESCRIPTION);
//
//		HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto, getAuthHeadersUser(restTemplate));
//
//		ResponseEntity<ErrorMessage> responseEntity = restTemplate.exchange("/cultural_offers/" + added.getId(),
//				HttpMethod.PUT, httpEntity, ErrorMessage.class);
//
//		assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
//		assertEquals(CREATE_DESCRIPTION, added.getDescription());
//
//		offerService.delete(added.getId());
//	}
//
//	@Test
//	public void deleteOffer_ExistsIDAsAdmin_ReturnsOK() throws Exception {
//		HttpEntity<Object> httpEntity = new HttpEntity<Object>(getAuthHeadersAdmin(restTemplate));
//		CulturalOfferDTO dto = new CulturalOfferDTO(CREATE_NAME, CREATE_DESCRIPTION, 0F, 0F, "adr", CREATE_CITY, "reg",
//				3L, 1L);
//
//		CulturalOfferDTO added = offerService.create(dto);
//		int size = offerService.getAll().size();
//
//		ResponseEntity<Void> responseEntity = restTemplate.exchange("/cultural_offers/" + added.getId(),
//				HttpMethod.DELETE, httpEntity, Void.class);
//
//		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//
//		assertEquals(size - 1, offerService.getAll().size());
//	}
//
//	@Test
//	public void deleteOffer_NotExistsIDAsAdmin_ReturnsNotFound() throws Exception {
//		HttpEntity<Object> httpEntity = new HttpEntity<Object>(getAuthHeadersAdmin(restTemplate));
//
//		ResponseEntity<ErrorMessage> responseEntity = restTemplate.exchange("/cultural_offers/" + FIND_NOT_EXIST_ID,
//				HttpMethod.DELETE, httpEntity, ErrorMessage.class);
//
//		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
//	}
//
//	@Test
//	public void deleteOffer_ExistsIDAsUser_ReturnsUnauthorized() throws Exception {
//		HttpEntity<Object> httpEntity = new HttpEntity<Object>(getAuthHeadersUser(restTemplate));
//
//		CulturalOfferDTO dto = new CulturalOfferDTO("testname", "testDesc", 0F, 0F, "adr", "city", "reg", 3L, 1L);
//
//		CulturalOfferDTO added = offerService.create(dto);
//
//		ResponseEntity<ErrorMessage> responseEntity = restTemplate.exchange("/cultural_offers/" + added.getId(),
//				HttpMethod.DELETE, httpEntity, ErrorMessage.class);
//
//		assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
//
//		offerService.delete(added.getId());
//	}
}
