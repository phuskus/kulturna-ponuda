package ftn.kts;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.test.context.TestPropertySource;

import ftn.kts.controller.CulturalOfferControllerIntegrationTest;
import ftn.kts.controller.PictureControllerIntegrationTest;
import ftn.kts.repository.AdminRepositoryIntegrationTest;
import ftn.kts.repository.AuthorityRepositoryIntegrationTest;
import ftn.kts.repository.CategoryRepositoryIntegrationTest;
import ftn.kts.repository.CulturalOfferRepositoryIntegrationTest;
import ftn.kts.repository.ReviewRepositoryIntegrationTest;
import ftn.kts.repository.UserRepositoryIntegrationTest;
import ftn.kts.service.AdminServiceIntegrationTest;
import ftn.kts.service.AuthorityServiceIntegrationTest;
import ftn.kts.service.CategoryServiceIntegrationTest;
import ftn.kts.service.CulturalOfferServiceIntegrationTest;
import ftn.kts.service.CulturalOfferServiceUnitTest;
import ftn.kts.service.PictureServiceIntegrationTest;
import ftn.kts.service.PostServiceIntegrationTest;
import ftn.kts.service.ReviewServiceIntegrationTest;
import ftn.kts.service.UserServiceIntegrationTest;

@RunWith(Suite.class)
@SuiteClasses({
	AuthorityRepositoryIntegrationTest.class, 
	CulturalOfferRepositoryIntegrationTest.class,
	UserRepositoryIntegrationTest.class,
	PictureServiceIntegrationTest.class,
	AuthorityServiceIntegrationTest.class,
	UserServiceIntegrationTest.class,
	PostServiceIntegrationTest.class,
	PictureControllerIntegrationTest.class,
	CulturalOfferControllerIntegrationTest.class,
	CulturalOfferServiceUnitTest.class,
	CulturalOfferServiceIntegrationTest.class,
	AdminRepositoryIntegrationTest.class,
	CategoryRepositoryIntegrationTest.class,
	ReviewRepositoryIntegrationTest.class,
	AdminServiceIntegrationTest.class,
	CategoryServiceIntegrationTest.class,
	ReviewServiceIntegrationTest.class
})
@TestPropertySource("classpath:application.properties")
class CulturalOffersApplicationTests {

}
