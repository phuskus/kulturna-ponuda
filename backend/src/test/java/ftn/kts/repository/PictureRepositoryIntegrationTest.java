package ftn.kts.repository;

import static ftn.kts.constants.PictureConstants.DB_PICTURE_VALID_ID;
import static ftn.kts.constants.PictureConstants.DB_PICTURE_INVALID_ID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PictureRepositoryIntegrationTest {

	@Autowired
	private PictureRepository pictureRepository;

	@Test
	public void testNextValidId() {
		Long found = pictureRepository.getNextSeriesId();
		assertEquals(found, DB_PICTURE_VALID_ID);
	}

	@Test
	public void testNextInvalidId() {
		Long found = pictureRepository.getNextSeriesId();
		assertNotEquals(found, DB_PICTURE_INVALID_ID);
	}
}
