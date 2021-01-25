package ftn.kts.e2e.tests;

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

public class MainPageE2ETests {

    public static final String BASE_URL = "http://localhost:4200";

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
        
        mainPage.ensureIsNotDisplayedSubcategory();
        
        resultsPage.ensureIsDisplayedSearchBar();
        
        resultsPage.ensureIsVisibleNoResults();
        
        List<WebElement> offers = resultsPage.getOfferList();
        
        assertTrue(offers.size() == 0);
    }
    
    @Test
    @Rollback
    public void goToResultsPageByQuery_ResultsAvailable_ShowsResults() throws InterruptedException {
    	String query = "Novi Sad";
    	mainPage.ensureIsDisplayedSearchBar();
    	
    	mainPage.getSearchBar().sendKeys(query);
    	
    	mainPage.getSearchBar().submit();
    	
        assertEquals(BASE_URL + "/offers/search?query=" + query.replaceAll(" ", "%20"), driver.getCurrentUrl());
        
        mainPage.ensureIsNotDisplayedSubcategory();
        
        resultsPage.ensureIsDisplayedSearchBar();
        
        resultsPage.ensureIsNotVisibleNoResults();
        
        List<WebElement> offers = resultsPage.getOfferList();
        
        assertTrue(offers.size() != 0);
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
        
        mainPage.ensureIsNotDisplayedSubcategory();
        
        resultsPage.ensureIsDisplayedSearchBar();
        
        resultsPage.ensureIsNotVisibleNoResults();
        
        List<WebElement> offers = resultsPage.getOfferList();
        System.out.println(offers.size());
        assertTrue(offers.size() != 0);
    }
    
    
}
