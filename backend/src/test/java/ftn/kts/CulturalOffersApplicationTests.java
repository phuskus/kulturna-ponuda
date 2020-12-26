package ftn.kts;

import ftn.kts.controller.*;
import ftn.kts.service.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.test.context.TestPropertySource;

import ftn.kts.repository.AdminRepositoryIntegrationTest;
import ftn.kts.repository.AuthorityRepositoryIntegrationTest;
import ftn.kts.repository.CategoryRepositoryIntegrationTest;
import ftn.kts.repository.CulturalOfferRepositoryIntegrationTest;
import ftn.kts.repository.ReviewRepositoryIntegrationTest;
import ftn.kts.repository.UserRepositoryIntegrationTest;

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
        AdminServiceIntegrationTest.class,
        AdminControllerIntegrationTest.class,
        CategoryRepositoryIntegrationTest.class,
        ReviewRepositoryIntegrationTest.class,
        CategoryServiceIntegrationTest.class,
        CategoryControllerIntegrationTest.class,
        CategoryServiceUnitTest.class,
        ReviewServiceIntegrationTest.class,
        ReviewControllerIntegrationTest.class
})
@TestPropertySource("classpath:application.properties")
class CulturalOffersApplicationTests {

}
