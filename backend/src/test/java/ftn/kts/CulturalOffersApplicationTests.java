package ftn.kts;

import ftn.kts.controller.*;
import ftn.kts.service.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.test.context.TestPropertySource;

import ftn.kts.controller.CulturalOfferControllerIntegrationTest;
import ftn.kts.controller.LoginControllerIntegrationTest;
import ftn.kts.controller.PictureControllerIntegrationTest;
import ftn.kts.controller.PostControllerIntegrationTest;
import ftn.kts.repository.AdminRepositoryIntegrationTest;
import ftn.kts.repository.AuthorityRepositoryIntegrationTest;
import ftn.kts.repository.CategoryRepositoryIntegrationTest;
import ftn.kts.repository.CulturalOfferRepositoryIntegrationTest;
import ftn.kts.repository.ReviewRepositoryIntegrationTest;
import ftn.kts.repository.SubcategoryRepositoryIntegrationTest;
import ftn.kts.repository.UserRepositoryIntegrationTest;
import ftn.kts.service.AdminServiceIntegrationTest;
import ftn.kts.service.AuthorityServiceIntegrationTest;
import ftn.kts.service.CategoryServiceIntegrationTest;
import ftn.kts.service.CulturalOfferServiceIntegrationTest;
import ftn.kts.service.CulturalOfferServiceUnitTest;
import ftn.kts.service.PictureServiceIntegrationTest;
import ftn.kts.service.PostServiceIntegrationTest;
import ftn.kts.service.ReviewServiceIntegrationTest;
import ftn.kts.service.SubcategoryServiceIntegrationTest;
import ftn.kts.service.UserServiceIntegrationTest;

@RunWith(Suite.class)
@SuiteClasses({
        AuthorityRepositoryIntegrationTest.class,
        AuthorityServiceIntegrationTest.class,
        UserRepositoryIntegrationTest.class,
        UserServiceIntegrationTest.class,
        PictureServiceIntegrationTest.class,
        PictureControllerIntegrationTest.class,
        CulturalOfferRepositoryIntegrationTest.class,
        CulturalOfferServiceIntegrationTest.class,
        CulturalOfferControllerIntegrationTest.class,
        CulturalOfferServiceUnitTest.class,
        AdminRepositoryIntegrationTest.class,
        AdminServiceIntegrationTest.class,
        AdminControllerIntegrationTest.class,
        CategoryRepositoryIntegrationTest.class,
        CategoryServiceIntegrationTest.class,
        CategoryControllerIntegrationTest.class,
        CategoryServiceUnitTest.class,
        ReviewRepositoryIntegrationTest.class,
        ReviewServiceIntegrationTest.class,
        ReviewControllerIntegrationTest.class,
        LoginControllerIntegrationTest.class,
        SubcategoryRepositoryIntegrationTest.class,
        SubcategoryServiceIntegrationTest.class,
        PostServiceIntegrationTest.class,
        PostControllerIntegrationTest.class
})
@TestPropertySource("classpath:application.properties")
class CulturalOffersApplicationTests {

}
