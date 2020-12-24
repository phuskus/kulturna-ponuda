package ftn.kts.repository;

import static ftn.kts.constants.CulturalOfferConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import ftn.kts.model.CulturalOffer;
import ftn.kts.model.Subcategory;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CulturalOfferRepositoryIntegrationTest {

	@Autowired
	private CulturalOfferRepository cultOfferRepository;
	
	@Test
	public void findByNameIgnoringCase_ExistsName_ReturnsOneEntity() {
		CulturalOffer found = cultOfferRepository.findByNameIgnoringCase(DB_CULTURAL_OFFER_NAME);
		assertEquals(found.getName(), DB_CULTURAL_OFFER_NAME);
	}
	
	@Test
	public void findByNameIgnoringCase_ExistsNameUppercase_ReturnsOneEntity() {
		CulturalOffer found = cultOfferRepository.findByNameIgnoringCase(DB_CULTURAL_OFFER_NAME_UPPERCASE);
		assertEquals(found.getName(), DB_CULTURAL_OFFER_NAME);
	}
	
	@Test
	public void findByNameIgnoringCase_NotExistsName_ReturnsNull() {
		CulturalOffer notFound = cultOfferRepository.findByNameIgnoringCase(DB_CULTURAL_OFFER_NO_SUCH_NAME);
		assertNull(notFound);
	}
	
	@Test
	public void findByNameContainingIgnoreCase_FirstPageTwoOffers_ReturnsTwoEntities() {
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE,PAGEABLE_SIZE);
		Page<CulturalOffer> found = cultOfferRepository.findByNameContainingIgnoreCase(DB_CULTURAL_OFFER_PART_NAME, pageable);
		assertEquals(FIND_ALL_NUMBER_OF_ITEMS, found.getNumberOfElements());
	}
	
	@Test
	public void findAll_FirstPageTwoOffers_ReturnsTwoEntites() {
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE,PAGEABLE_SIZE);
        Page<CulturalOffer> found = cultOfferRepository.findAll(pageable);
        assertEquals(FIND_ALL_NUMBER_OF_ITEMS, found.getNumberOfElements());
	}
	
	@Test
	public void findAll_SecondPageNoOffers_ReturnsEmptyPage() {
		Pageable pageable = PageRequest.of(PAGEABLE_FIRST_PAGE, PAGEABLE_SIZE);
        Page<CulturalOffer> found = cultOfferRepository.findAll(pageable);
        assertEquals(PAGEABLE_NO_ELEMENTS, found.getNumberOfElements());
	}
	
	@Test
	public void findByCategory_CulturalOfferExists_ReturnsTwoEntites() {
		CulturalOffer offer = cultOfferRepository.findById(DB_CULTURAL_OFFER_ID).get();
		Subcategory subcategory = offer.getCategory();
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE,PAGEABLE_SIZE);
		Page<CulturalOffer> found = cultOfferRepository.findByCategory(subcategory, pageable);
		assertEquals(FIND_ALL_NUMBER_OF_ITEMS_BY_SUBCATEGORY, found.getNumberOfElements());
	}
	
	@Test
	public void findByCategory_CulturalOfferNotExists_ReturnsEmptyPage() {
		CulturalOffer offer = cultOfferRepository.findById(DB_CULTURAL_OFFER_ID).get();
		Subcategory subcategory = offer.getCategory();
		subcategory.setId(CATEGORY_NULL_ID);
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE,PAGEABLE_SIZE);
		Page<CulturalOffer> found = cultOfferRepository.findByCategory(subcategory, pageable);
		assertEquals(PAGEABLE_NO_ELEMENTS, found.getNumberOfElements());
	}
	
	@Test
	public void findByCityContainingIgnoreCase_CityExists_ReturnsOneEntity() {
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE,PAGEABLE_SIZE);
		Page<CulturalOffer> found = cultOfferRepository.findByCityContainingIgnoreCase(DB_CITY_NAME, pageable);
		assertEquals(FIND_ALL_NUMBER_OF_ITEMS_BY_CITY, found.getNumberOfElements());
	}
	
	@Test
	public void findByCityContainingIgnoreCase_CitySubstringExists_ReturnsOneEntity() {
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE,PAGEABLE_SIZE);
		Page<CulturalOffer> found = cultOfferRepository.findByCityContainingIgnoreCase(DB_CITY_PART_NAME, pageable);
		assertEquals(FIND_ALL_NUMBER_OF_ITEMS_BY_CITY, found.getNumberOfElements());
	}
	
	@Test
	public void findByCityContainingIgnoreCase_CityLowercaseExists_ReturnsOneEntity() {
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE,PAGEABLE_SIZE);
		Page<CulturalOffer> found = cultOfferRepository.findByCityContainingIgnoreCase(DB_CITY_NAME_LOWERCASE, pageable);
		assertEquals(FIND_ALL_NUMBER_OF_ITEMS_BY_CITY, found.getNumberOfElements());
	}
	
	@Test
	public void findByCityContainingIgnoreCase_CityNotExists_ReturnsEmptyPage() {
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE,PAGEABLE_SIZE);
		Page<CulturalOffer> found = cultOfferRepository.findByCityContainingIgnoreCase(DB_NO_SUCH_CITY, pageable);
		assertEquals(0, found.getNumberOfElements());
	}
	
	@Test
	public void findByDescriptionContainingIgnoreCase_DescriptionSubstringExists_ReturnsTwoEntites() {
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE,PAGEABLE_SIZE);
		Page<CulturalOffer> found = cultOfferRepository.findByDescriptionContainingIgnoreCase(DB_PART_DESCRIPTION, pageable);
		assertEquals(FIND_ALL_NUMBER_OF_ITEMS_BY_DESCRIPTION, found.getNumberOfElements());
	}
	
	@Test
	public void findByDescriptionContainingIgnoreCase_DescriptionSubstringUppercaseExists_ReturnsTwoEntites() {
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE,PAGEABLE_SIZE);
		Page<CulturalOffer> found = cultOfferRepository.findByDescriptionContainingIgnoreCase(DB_PART_DESCRIPTION_UPPERCASE, pageable);
		assertEquals(FIND_ALL_NUMBER_OF_ITEMS_BY_DESCRIPTION, found.getNumberOfElements());
	}
	
	@Test
	public void findByDescriptionContainingIgnoreCase_DescriptionNotExists_ReturnsEmptyPage() {
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE,PAGEABLE_SIZE);
		Page<CulturalOffer> found = cultOfferRepository.findByDescriptionContainingIgnoreCase(DB_NO_SUCH_DESCRIPTION, pageable);
		assertEquals(0, found.getNumberOfElements());
	}
	 
}
