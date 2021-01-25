package ftn.kts.e2e.tests;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import static ftn.kts.e2e.constants.AppConstants.BASE_URL;
import ftn.kts.e2e.pages.ResultsPage;


public class ResultsE2ETests {

    private WebDriver driver;

    private ResultsPage resultsPage;

    @Before
    public void setUp() {

        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
        
        driver.navigate().to(BASE_URL + "/results?query=");

        driver.manage().window().maximize();
        resultsPage = PageFactory.initElements(driver, ResultsPage.class);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

}
