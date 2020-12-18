package ftn.kts;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.test.context.TestPropertySource;

import ftn.kts.repository.AuthorityRepositoryIntegrationTest;
import ftn.kts.repository.PictureRepositoryIntegrationTest;

@RunWith(Suite.class)
@SuiteClasses({AuthorityRepositoryIntegrationTest.class, PictureRepositoryIntegrationTest.class})
@TestPropertySource("classpath:test.properties")
class CulturalOffersApplicationTests {

}
