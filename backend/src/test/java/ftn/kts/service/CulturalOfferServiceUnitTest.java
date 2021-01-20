package ftn.kts.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static ftn.kts.constants.CulturalOfferConstants.UNIT_TEST_NAME_FIRST;
import static ftn.kts.constants.CulturalOfferConstants.UNIT_TEST_NAME_SECOND;
import static ftn.kts.constants.CulturalOfferConstants.UNIT_TEST_DESCRIPTION;
import static ftn.kts.constants.CulturalOfferConstants.UNIT_TEST_ADDRESS;
import static ftn.kts.constants.CulturalOfferConstants.UNIT_TEST_CITY;
import static ftn.kts.constants.CulturalOfferConstants.UNIT_TEST_REGION;
import static ftn.kts.constants.CulturalOfferConstants.UNIT_TEST_ID_FIRST;
import static ftn.kts.constants.CulturalOfferConstants.UNIT_TEST_ID_SECOND;
import static ftn.kts.constants.CulturalOfferConstants.UNIT_TEST_NAME_CHANGED;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import ftn.kts.dto.CulturalOfferDTO;
import ftn.kts.exceptions.UniqueConstraintViolationException;
import ftn.kts.model.Admin;
import ftn.kts.model.Category;
import ftn.kts.model.CulturalOffer;
import ftn.kts.model.Picture;
import ftn.kts.model.Subcategory;
import ftn.kts.repository.CulturalOfferRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CulturalOfferServiceUnitTest {

	@Autowired
	private CulturalOfferService offerService;

	@MockBean
	private CulturalOfferRepository offerRepository;

	private CulturalOffer offerToBeChanged;

	@Before
	public void setup() {
		Admin admin = new Admin("Admin", "Admin", "admin@test.com", "admin");
		admin.setId(1L);

		Category cat = new Category("New category");
		cat.setId(1L);

		Picture picture = new Picture("path", "placeholder");
		
		Subcategory subcat = new Subcategory("New subcategory", cat, picture);
		subcat.setId(1L);

		CulturalOffer firstOffer = new CulturalOffer(UNIT_TEST_NAME_FIRST, UNIT_TEST_DESCRIPTION, 0F, 0F,
				UNIT_TEST_ADDRESS, UNIT_TEST_CITY, UNIT_TEST_REGION, admin, subcat);
		firstOffer.setId(1L);

		offerToBeChanged = new CulturalOffer(UNIT_TEST_NAME_SECOND, UNIT_TEST_DESCRIPTION, 0F, 0F, UNIT_TEST_ADDRESS,
				UNIT_TEST_CITY, UNIT_TEST_REGION, admin, subcat);
		offerToBeChanged.setId(2L);

		CulturalOffer changedOffer = new CulturalOffer(UNIT_TEST_NAME_SECOND, UNIT_TEST_DESCRIPTION, 0F, 0F,
				UNIT_TEST_ADDRESS, UNIT_TEST_CITY, UNIT_TEST_REGION, admin, subcat);
		changedOffer.setId(2L);
		changedOffer.setName(UNIT_TEST_NAME_CHANGED);

		given(offerRepository.findById(UNIT_TEST_ID_FIRST)).willReturn(java.util.Optional.of(firstOffer));

		given(offerRepository.findById(UNIT_TEST_ID_SECOND)).willReturn(java.util.Optional.of(offerToBeChanged));

		given(offerRepository.findByNameIgnoringCase(UNIT_TEST_NAME_FIRST)).willReturn(firstOffer);
		given(offerRepository.findByNameIgnoringCase(UNIT_TEST_NAME_SECOND)).willReturn(offerToBeChanged);
		given(offerRepository.findByNameIgnoringCase("neki new thing")).willReturn(null);

		given(offerRepository.save(offerToBeChanged)).willReturn(changedOffer);
	}

	@Test
	public void updateOffer_UniqueConstaintNotViolated_TestPasses() throws Exception {
		CulturalOfferDTO dto = new CulturalOfferDTO(UNIT_TEST_NAME_CHANGED, UNIT_TEST_DESCRIPTION, 0F, 0F,
				UNIT_TEST_ADDRESS, UNIT_TEST_CITY, UNIT_TEST_REGION, 1L, 1L);
		CulturalOfferDTO updated = offerService.update(dto, UNIT_TEST_ID_SECOND);

		verify(offerRepository, times(1)).findById(UNIT_TEST_ID_SECOND);
		verify(offerRepository, times(1)).findByNameIgnoringCase(UNIT_TEST_NAME_CHANGED);
		verify(offerRepository, times(1)).save(offerToBeChanged);

		assertEquals(UNIT_TEST_NAME_CHANGED, updated.getName());
	}

	@Test(expected = UniqueConstraintViolationException.class)
	public void updateOffer_UniqueConstaintViolated_ExceptionThrown() throws Exception {
		// tries to set second to first name, so it violates unique constraint
		CulturalOfferDTO dto = new CulturalOfferDTO(UNIT_TEST_NAME_FIRST, UNIT_TEST_DESCRIPTION, 0F, 0F,
				UNIT_TEST_ADDRESS, UNIT_TEST_CITY, UNIT_TEST_REGION, 1L, 1L);
		offerService.update(dto, UNIT_TEST_ID_SECOND);
	}

}
