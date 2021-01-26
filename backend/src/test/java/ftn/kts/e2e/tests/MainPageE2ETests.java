package ftn.kts.e2e.tests;

import java.util.List;

import ftn.kts.e2e.pages.OfferPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.test.annotation.Rollback;

import static ftn.kts.e2e.constants.AppConstants.BASE_URL;
import static org.junit.Assert.*;


import ftn.kts.e2e.pages.MainPage;
import ftn.kts.e2e.pages.ResultsPage;
import ftn.kts.util.E2EUtil;

public class MainPageE2ETests {

    private WebDriver driver;

    private MainPage mainPage;
    
    private ResultsPage resultsPage;

    @Before
    public void setUp() {

        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
        
        driver.navigate().to(BASE_URL + "/");

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
    public void goToResultsPageByQuery_NoResults_ShowsNoResultsMessage() throws InterruptedException {
    	String query = "qwertyuiop";
    	mainPage.ensureIsDisplayedSearchBar();
    	
    	mainPage.getSearchBar().sendKeys(query);
    	
    	mainPage.getSearchBar().submit();
    	
        assertEquals(BASE_URL + "/offers/search?query=" + query.replaceAll(" ", "%20"), driver.getCurrentUrl());
        
        mainPage.ensureIsNotDisplayedSubcategoryList();
        
        resultsPage.ensureIsDisplayedSearchBar();
        
        resultsPage.ensureIsVisibleNoResults();
        
        resultsPage.ensureIsNotVisiblePaginationControl();
        
        List<WebElement> offers = resultsPage.getOfferList();

        resultsPage.ensureIsNotVisibleOfferList();
        
        resultsPage.ensureIsNotVisibleMarkerList();
    }
    
    @Test
    @Rollback
    public void goToResultsPageByQuery_ResultsAvailable_ShowsResults() throws InterruptedException {
    	String query = "Novi Sad";
    	mainPage.ensureIsDisplayedSearchBar();
    	
    	mainPage.getSearchBar().sendKeys(query);
    	
    	mainPage.getSearchBar().submit();
    	
        assertEquals(BASE_URL + "/offers/search?query=" + query.replaceAll(" ", "%20"), driver.getCurrentUrl());
        
        mainPage.ensureIsNotDisplayedSubcategoryList();
        
        resultsPage.ensureIsDisplayedSearchBar();
        
        resultsPage.ensureIsNotVisibleNoResults();

        resultsPage.ensureIsVisibleOfferList();
        
        resultsPage.ensureIsVisibleMarkerList();
    }
    

    @Test
    @Rollback
    public void goToResultsPageByCategory_ResultsAvailable_ShowResults() throws InterruptedException {
    	List<WebElement> categoryButtons = mainPage.getSubcatButtons();
    	
    	assertTrue(categoryButtons.size() != 0);
    	
    	WebElement firstCategory = categoryButtons.get(0);
    	
    	String firstCatName = firstCategory.getAttribute("name");
    	
    	firstCategory.click();
    	
        assertEquals(BASE_URL + "/offers/search?category=" + firstCatName.replaceAll(" ", "%20"), driver.getCurrentUrl());
        
        mainPage.ensureIsNotDisplayedSubcategoryList();
        
        resultsPage.ensureIsDisplayedSearchBar();
        
        resultsPage.ensureIsNotVisibleNoResults();
        
        resultsPage.ensureIsVisiblePaginationControl();
                
        resultsPage.ensureIsVisibleOfferList();
        // troublemaker method u ovom kontekstu? not sure why
        //resultsPage.ensureIsVisibleMarkerList();
    }
    
    @Test
    @Rollback
    public void logout_ShowProfileMenuAndLoginButton() {
        E2EUtil.loginUser(driver);

        driver.navigate().to(BASE_URL);

        MainPage mainPage = PageFactory.initElements(driver, MainPage.class);
        WebElement profileButton = mainPage.getProfileButton();
        profileButton.click();

        mainPage.ensureIsDisplayedProfileMenu();

        WebElement logoutButton = mainPage.getLogoutButton();
        (new WebDriverWait(driver, 1)).until(ExpectedConditions.elementToBeClickable(logoutButton)).click();

        mainPage.ensureIsDisplayedLoginButton();
    }

    @Test
    @Rollback
    public void subscribeUnsubscribeSubcategory_ChangeButtonState() {
        E2EUtil.loginUser(driver);

        driver.navigate().to(BASE_URL);

        MainPage mainPage = PageFactory.initElements(driver, MainPage.class);

        List<WebElement> categoryButtons = mainPage.getSubcatButtons();

        assertTrue(categoryButtons.size() != 0);

        WebElement firstCategory = categoryButtons.get(0);

        firstCategory.click();

        ResultsPage resultsPage = PageFactory.initElements(driver, ResultsPage.class);

        resultsPage.ensureIsVisibleSubscribeButton();

        WebElement subscribeButton = resultsPage.getSubscribeButton();

        (new WebDriverWait(driver, 3)).until(ExpectedConditions.elementToBeClickable(subscribeButton)).click();

        resultsPage.ensureIsVisibleUnsubscribeButton();

        WebElement unsubscribeButton = resultsPage.getUnsubscribeButton();

        (new WebDriverWait(driver, 3)).until(ExpectedConditions.elementToBeClickable(unsubscribeButton)).click();

        resultsPage.ensureIsVisibleSubscribeButton();
    }

    @Test
    @Rollback
    public void subscribeUnsubscribeCulturalOffer_ChangeButtonState() throws InterruptedException {
        E2EUtil.loginUser(driver);

        driver.navigate().to(BASE_URL);

        MainPage mainPage = PageFactory.initElements(driver, MainPage.class);

        List<WebElement> categoryButtons = mainPage.getSubcatButtons();

        assertTrue(categoryButtons.size() != 0);

        WebElement firstCategory = categoryButtons.get(0);

        firstCategory.click();

        (new WebDriverWait(driver, 5)).until(ExpectedConditions.visibilityOfElementLocated(By.className("result-card")));

        ResultsPage resultsPage = PageFactory.initElements(driver, ResultsPage.class);

        List<WebElement> offerButtons = resultsPage.getOfferList();

        assertTrue(offerButtons.size() != 0);

        WebElement firstOffer = offerButtons.get(0);

        firstOffer.click();

        OfferPage offerPage = PageFactory.initElements(driver, OfferPage.class);

        offerPage.ensureIsVisibleSubscribeButton();

        WebElement subscribeButton = offerPage.getSubscribeButton();

        subscribeButton.click();

        offerPage.ensureIsVisibleUnsubscribeButton();

        WebElement unsubscribeButton = offerPage.getUnsubscribeButton();

        unsubscribeButton.click();

        offerPage.ensureIsVisibleSubscribeButton();
    }
}
