package ftn.kts.e2e.tests;

import static ftn.kts.e2e.constants.ResultsPageConstants.*;
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
import ftn.kts.e2e.pages.OfferPage;
import ftn.kts.e2e.pages.ResultsPage;


public class ResultsE2ETests {

    private WebDriver driver;

    private MainPage mainPage;
    
    private ResultsPage resultsPage;
    
    private OfferPage offerPage;

    @Before
    public void setUp() {

        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
        
        driver.manage().window().maximize();
        mainPage = PageFactory.initElements(driver, MainPage.class);
        resultsPage = PageFactory.initElements(driver, ResultsPage.class);
        offerPage = PageFactory.initElements(driver, OfferPage.class);
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
        
        offerPage.ensureIsVisibleSubscribeButton();

        assertEquals(SINGLE_OFFER_URL + offerId, driver.getCurrentUrl());
    }
    
    
    @Test
    @Rollback
    public void searchAgain_StaysOnSamePage() throws InterruptedException {
        driver.navigate().to(RESULTS_URL + "?query=");
        String query = "Novi Sad";
        
        resultsPage.ensureIsDisplayedSearchBar();
        resultsPage.ensureIsVisiblePaginationControl();
        resultsPage.ensureIsVisibleOfferList();
        
        WebElement search = resultsPage.getSearchBar();
        
        search.sendKeys(query);
        
        search.submit();
        
        assertEquals(RESULTS_URL + "?query=" + query.replace(" ","%20"), driver.getCurrentUrl());

        resultsPage.ensureIsDisplayedSearchBar();
        resultsPage.ensureIsVisiblePaginationControl();
        resultsPage.ensureIsVisibleOfferList();
    }
    
    @Test
    @Rollback
    public void filterResultsByRegionAndCity_HasResults_ShowsResults() throws InterruptedException {
        driver.navigate().to(RESULTS_URL + "?query=");
        
        resultsPage.ensureIsDisplayedSearchBar();
        resultsPage.ensureIsVisiblePaginationControl();
        resultsPage.ensureIsVisibleOfferList();
        
        WebElement filterButton = resultsPage.getFilterButton();
        filterButton.click();
        
        resultsPage.ensureIsDisplayedFilterApply();
        resultsPage.ensureAreVisibleFilterOptions();
        
        List<WebElement> filterCriteria = resultsPage.getFilterOptions();
        WebElement vojvodinaRegionCriteria = filterCriteria.get(0);
        WebElement noviSadCityCriteria = filterCriteria.get(6);
        vojvodinaRegionCriteria.click();
        noviSadCityCriteria.click();
        
        WebElement filterConfirm = resultsPage.getFilterConfirm();
        filterConfirm.click();
        
        resultsPage.ensureIsVisibleOfferList();
        resultsPage.ensureIsNotDisplayedFilterApply();
        
        List<WebElement> filterResults = resultsPage.getLocationList();
        
        for (WebElement e : filterResults) {
        	String content = e.getText();
        	String city = content.split(", ")[1];
        	assertEquals("Novi Sad", city);
        }
    }
    
    @Test
    @Rollback
    public void filterResultsByCity_HasResults_ShowsResults() throws InterruptedException {
        driver.navigate().to(RESULTS_URL + "?query=");
        
        resultsPage.ensureIsDisplayedSearchBar();
        resultsPage.ensureIsVisiblePaginationControl();
        resultsPage.ensureIsVisibleOfferList();
        
        WebElement filterButton = resultsPage.getFilterButton();
        filterButton.click();
        
        resultsPage.ensureIsDisplayedFilterApply();
        resultsPage.ensureAreVisibleFilterOptions();
        
        List<WebElement> filterCriteria = resultsPage.getFilterOptions();
        WebElement belgradeCityCriteria = filterCriteria.get(5);
        belgradeCityCriteria.click();
        
        WebElement filterConfirm = resultsPage.getFilterConfirm();
        filterConfirm.click();
        
        resultsPage.ensureIsVisibleOfferList();
        resultsPage.ensureIsNotDisplayedFilterApply();
        
        List<WebElement> filterResults = resultsPage.getLocationList();
        
        for (WebElement e : filterResults) {
        	String content = e.getText();
        	String city = content.split(", ")[1];
        	assertEquals("Belgrade", city);
        }
    }
    
    @Test
    @Rollback
    public void filterResultsByRegionAndCity_HasNoResults() throws InterruptedException {
        driver.navigate().to(RESULTS_URL + "?query=");
        
        resultsPage.ensureIsDisplayedSearchBar();
        resultsPage.ensureIsVisiblePaginationControl();
        resultsPage.ensureIsVisibleOfferList();
        
        WebElement filterButton = resultsPage.getFilterButton();
        filterButton.click();
        
        resultsPage.ensureIsDisplayedFilterApply();
        resultsPage.ensureAreVisibleFilterOptions();
        
        List<WebElement> filterCriteria = resultsPage.getFilterOptions();
        WebElement vojvodinaRegionCriteria = filterCriteria.get(0);
        WebElement belgradeCityCriteria = filterCriteria.get(5);
        vojvodinaRegionCriteria.click();
        belgradeCityCriteria.click();
        
        WebElement filterConfirm = resultsPage.getFilterConfirm();
        filterConfirm.click();
        
        resultsPage.ensureIsNotVisibleOfferList();
        resultsPage.ensureIsNotDisplayedFilterApply();
        resultsPage.ensureIsVisibleNoResults();
        
        List<WebElement> filterResults = resultsPage.getLocationList();
        assertEquals(0, filterResults.size());
    }
    
    @Test
    @Rollback
    public void sortResultsByCity_Ascending_ShowsResults() throws InterruptedException {
        driver.navigate().to(RESULTS_URL + "?query=");
        
        resultsPage.ensureIsDisplayedSearchBar();
        resultsPage.ensureIsVisiblePaginationControl();
        resultsPage.ensureIsVisibleOfferList();
        
        WebElement sortButton = resultsPage.getSortButton();
        sortButton.click();
        
        resultsPage.ensureIsDisplayedSortApply();
        resultsPage.ensureAreVisibleSortOptions();
                
        List<WebElement> sortCriteria = resultsPage.getSortOptions();
        WebElement citySortCriteria = sortCriteria.get(4);
        citySortCriteria.click();
        
        WebElement sortConfirm = resultsPage.getSortConfirm();
        sortConfirm.click();
        
        resultsPage.ensureIsVisibleOfferList();
        resultsPage.ensureIsNotDisplayedSortApply();
        
        List<WebElement> filterResults = resultsPage.getLocationList();
        String firstCity = filterResults.get(0).getText().split(", ")[1];
        assertEquals(FIRST_CITY, firstCity);
    }
    
    @Test
    @Rollback
    public void sortResultsByCity_Descending_ShowsResults() throws InterruptedException {
        driver.navigate().to(RESULTS_URL + "?query=");
        
        resultsPage.ensureIsDisplayedSearchBar();
        resultsPage.ensureIsVisiblePaginationControl();
        resultsPage.ensureIsVisibleOfferList();
        
        WebElement sortButton = resultsPage.getSortButton();
        sortButton.click();
        
        resultsPage.ensureIsDisplayedSortApply();
        
        // by default diretion is ascending
        WebElement sortDirectionButton = resultsPage.getSortDirectionButton();
        // so we click it to change to descending
        sortDirectionButton.click();
        
        resultsPage.ensureAreVisibleSortOptions();
        
        List<WebElement> sortCriteria = resultsPage.getSortOptions();
        WebElement citySortCriteria = sortCriteria.get(4);
        citySortCriteria.click();
        
        WebElement sortConfirm = resultsPage.getSortConfirm();
        sortConfirm.click();
        
        resultsPage.ensureIsVisibleOfferList();
        resultsPage.ensureIsNotDisplayedSortApply();
        
        List<WebElement> filterResults = resultsPage.getLocationList();
        String firstCity = filterResults.get(0).getText().split(", ")[1];
        assertEquals(LAST_CITY, firstCity);
    }

}
