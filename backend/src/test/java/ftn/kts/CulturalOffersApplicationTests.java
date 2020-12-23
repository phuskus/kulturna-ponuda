package ftn.kts;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.test.context.TestPropertySource;

import ftn.kts.repository.AuthorityRepositoryIntegrationTest;
import ftn.kts.repository.CulturalOfferRepositoryIntegrationTest;
import ftn.kts.repository.UserRepositoryIntegrationTest;
import ftn.kts.service.AuthorityServiceIntegrationTest;
import ftn.kts.service.PictureServiceIntegrationTest;
import ftn.kts.service.PostServiceIntegrationTest;
import ftn.kts.service.UserServiceIntegrationTest;

@RunWith(Suite.class)
@SuiteClasses({
	AuthorityRepositoryIntegrationTest.class, 
	UserRepositoryIntegrationTest.class,
	CulturalOfferRepositoryIntegrationTest.class,
	PictureServiceIntegrationTest.class,
	AuthorityServiceIntegrationTest.class,
	UserServiceIntegrationTest.class,
	PostServiceIntegrationTest.class
})
@TestPropertySource("classpath:application.properties")
class CulturalOffersApplicationTests {

}
