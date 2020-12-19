package ftn.kts;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.test.context.TestPropertySource;

import ftn.kts.repository.AuthorityRepositoryIntegrationTest;
import ftn.kts.repository.UserRepositoryIntegrationTest;
import ftn.kts.service.AuthorityServiceIntegrationTest;
import ftn.kts.service.PictureServiceIntegrationTest;

@RunWith(Suite.class)
@SuiteClasses({ AuthorityRepositoryIntegrationTest.class, UserRepositoryIntegrationTest.class,
		PictureServiceIntegrationTest.class, AuthorityServiceIntegrationTest.class })
@TestPropertySource("classpath:application.properties")
class CulturalOffersApplicationTests {

}
