package ftn.kts.e2e.tests;

import static ftn.kts.e2e.constants.AppConstants.SINGLE_OFFER_URL;
import static ftn.kts.util.E2EUtil.loginAdmin;
import static ftn.kts.util.E2EUtil.loginUser;
import static org.junit.Assert.assertTrue;

import java.util.List;

import ftn.kts.e2e.pages.OfferPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import java.io.File;

import static ftn.kts.e2e.constants.AppConstants.*;
import static org.junit.Assert.*;

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
        page.ensureOfferContentIsDisplayed();
    }


    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void ScrollToLastReview_ReviewNotNull() {
        setUpUser();

        int totalElements = page.getNumberOfReviews();
        if (totalElements == 0)
            fail("Offer has no reviews");

        WebElement lastReview = page.getReview(totalElements - 1);

        assertNotNull(page.getReviewUser(lastReview));
        assertNotNull(page.getReviewContent(lastReview));
    }

    @Test
    public void AddReviewUser_OnlyRating_ReviewAddedToTop() {
        setUpUser();

        int rating = 3;

        page.clickWriteReview();
        page.setNewReviewRating(rating);
        page.confirmReview();

        WebElement review = page.getTopReview();
        assertEquals(EXISTENT_USER_NAME, page.getReviewUser(review));
        assertEquals(rating, page.getReviewRating(review));
    }

    @Test
    public void AddReviewUser_RatingAndContent_ReviewAddedToTop() {
        setUpUser();

        int rating = 2;
        String content = "Some Random Content";

        page.clickWriteReview();
        page.setNewReviewRating(rating);
        page.setReviewContent(content);
        page.confirmReview();

        WebElement review = page.getTopReview();
        assertEquals(EXISTENT_USER_NAME, page.getReviewUser(review));
        assertEquals(rating, page.getReviewRating(review));
        assertEquals(content, page.getReviewContent(review));
    }


    @Test
    public void AddReviewUser_RatingContentAndImage_ReviewAddedToTop() {
        setUpUser();

        int rating = 2;
        String content = "Some Random Content";

        page.clickWriteReview();
        page.setNewReviewRating(rating);
        page.setReviewContent(content);

        File file = new File(EXISTENT_IMAGE_PATH);
        page.uploadImage(file.getAbsolutePath());

        page.confirmReview();

        WebElement review = page.getTopReview();
        assertEquals(EXISTENT_USER_NAME, page.getReviewUser(review));
        assertEquals(rating, page.getReviewRating(review));
        assertEquals(content, page.getReviewContent(review));
        assertEquals(1, page.getNumberOfPicturesForReview(review));
    }

    @Test
    public void AddReviewAdmin_ButtonDisabled() {
        setUpAdmin();

        assertFalse(page.getReviewButton().isEnabled());
    }
    

    @Test
    public void checkPostsExists() {
        setUpUser();

        page.ensurePostsAreDisplayed();
        
        List<WebElement> posts = page.getPosts();
        assertTrue(posts.size() != 0);
    }

}
