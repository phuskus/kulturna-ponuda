package ftn.kts.e2e.tests;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.test.annotation.Rollback;

import ftn.kts.e2e.pages.MainPage;

public class MainPageE2ETests {

    public static final String BASE_URL = "http://localhost:4200";

    private WebDriver driver;

    private MainPage mainPage;

    @Before
    public void setUp() {

        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();

        driver.navigate().to(BASE_URL + "/");

        driver.manage().window().maximize();
        mainPage = PageFactory.initElements(driver, MainPage.class);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void GoToLoginPage() throws InterruptedException {

        mainPage.ensureIsDisplayedLoginButton();
        
        mainPage.getLoginButton().click();
        
        assertEquals(BASE_URL + "/login", driver.getCurrentUrl());
    }

    @Test
    @Rollback
    public void GoToResultsPageSearch() throws InterruptedException {
    	String query = "Petrovaradinska";
    	mainPage.ensureIsDisplayedSearchBar();
    	
    	mainPage.getSearchBar().sendKeys(query);
    	
    	mainPage.getSearchBar().submit();
    	
//        categoryPage.ensureIsDisplayedName();
//
//        categoryPage.getName().sendKeys("Institucija");
//
//        categoryPage.getSubmitButton().click();
//
//        categoryPage.ensureIsNotVisibleSubmitButton();
    	
        assertEquals(BASE_URL + "/offers/search?query=" + query, driver.getCurrentUrl());
    }
    
}
