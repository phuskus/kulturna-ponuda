package ftn.kts.e2e.tests;

import static ftn.kts.e2e.constants.AppConstants.BASE_URL;
import static ftn.kts.e2e.constants.AppConstants.RESULTS_URL;
import static ftn.kts.e2e.constants.AppConstants.SINGLE_OFFER_URL;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.test.annotation.Rollback;

import ftn.kts.e2e.pages.MainPage;
import ftn.kts.e2e.pages.ResultsPage;


public class ResultsE2ETests {

    private WebDriver driver;

    private MainPage mainPage;
    
    private ResultsPage resultsPage;

    @Before
    public void setUp() {

        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
        
        driver.manage().window().maximize();
        mainPage = PageFactory.initElements(driver, MainPage.class);
        resultsPage = PageFactory.initElements(driver, ResultsPage.class);
    }

    @After
    public void tearDown() {
        driver.quit();
    }
    
    @Test
    @Rollback
    public void goBack() throws InterruptedException {
        driver.navigate().to(RESULTS_URL + "?query=");
        
        resultsPage.ensureIsDisplayedSearchBar();
        resultsPage.ensureIsVisiblePaginationControl();
        resultsPage.ensureIsVisibleOfferList();
        
        WebElement backButton = resultsPage.getBackButton();
        
        backButton.click();
        
        assertEquals(BASE_URL + "/", driver.getCurrentUrl());
        
        mainPage.ensureIsDisplayedSubcategoryList();
        mainPage.ensureIsDisplayedLoginButton();
        mainPage.ensureIsDisplayedSearchBar();
    }
    
    @Test
    @Rollback
    public void clickOffer_RedirectsToOfferPage() throws InterruptedException {
        driver.navigate().to(RESULTS_URL + "?query=");
        
        resultsPage.ensureIsDisplayedSearchBar();
        resultsPage.ensureIsVisiblePaginationControl();
        resultsPage.ensureIsVisibleOfferList();
        
        List<WebElement> offers = resultsPage.getOfferList();
        assertTrue(offers.size() != 0);
        WebElement firstOffer = offers.get(0);
        String offerId = firstOffer.getAttribute("name");
        
        firstOffer.click();
        
        resultsPage.ensureIsNotVisibleOfferList();
        resultsPage.ensureIsNotVisibleSearchBar();

        assertEquals(SINGLE_OFFER_URL + offerId, driver.getCurrentUrl());
    }

}
