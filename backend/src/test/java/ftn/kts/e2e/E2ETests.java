package ftn.kts.e2e;

import ftn.kts.e2e.tests.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.test.context.TestPropertySource;

@RunWith(Suite.class)
@SuiteClasses({
        DashboardAdminTests.class,
        DashboardCategoryTests.class,
        DashboardPostTests.class,
        DashboardReviewTests.class,
        DashboardSubcategoryTests.class,
        ForgotPasswordE2ETests.class,
        LoginE2ETests.class,
        MainPageE2ETests.class,
        RegistrationE2ETests.class,
        ResetPasswordE2ETests.class,
        ResultsE2ETests.class,
        SingleOfferReviewTests.class
})
public class E2ETests {
}
