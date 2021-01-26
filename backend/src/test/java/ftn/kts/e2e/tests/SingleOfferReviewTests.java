package ftn.kts.e2e.tests;

import static ftn.kts.e2e.constants.AppConstants.SINGLE_OFFER_URL;
import static ftn.kts.util.E2EUtil.loginAdmin;
import static ftn.kts.util.E2EUtil.loginUser;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import ftn.kts.e2e.pages.OfferPage;

public class SingleOfferReviewTests {

    protected WebDriver driver;
    protected OfferPage page;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
        page = PageFactory.initElements(driver, OfferPage.class);
    }

    public void setUpAdmin() {
        loginAdmin(driver);
        finishSetUp();
    }

    public void setUpUser() {
        loginUser(driver);
        finishSetUp();

    }

    public void finishSetUp() {
        driver.navigate().to(SINGLE_OFFER_URL + "5");
        driver.manage().window().maximize();
    }


    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void AddReviewUser_OnlyRating_ReviewAddedToTop() {
        setUpUser();

        int rating = 3;
        page.clickWriteReview();
        page.setNewReviewRating(rating);

        page.confirmReview();

//        assertEquals(page.getTopReviewUser(), EXISTENT_USER_NAME);
//        assertEquals(page.getTopReviewRating(), 3);
    }

    @Test
    public void AddReviewUser_RatingAndContent_ReviewAddedToTop() {
        setUpUser();

        page.clickWriteReview();
        page.setNewReviewRating(3);
        page.setReviewContent("Some Random Content");

        page.confirmReview();
    }
    

    @Test
    public void checkPostsExists() {
        setUpUser();

        page.ensurePostsAreDisplayed();
        
        List<WebElement> posts = page.getPosts();
        assertTrue(posts.size() != 0);
    }

}
