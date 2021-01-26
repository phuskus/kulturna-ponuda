package ftn.kts.e2e.tests;

import ftn.kts.e2e.pages.DashboardPage;
import org.junit.After;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import static ftn.kts.e2e.constants.AppConstants.DASHBOARD_URL;
import static ftn.kts.util.E2EUtil.loginAdmin;
import static org.junit.Assert.fail;

public abstract class AbstractDashboard {

    protected WebDriver driver;
    protected DashboardPage page;

    public void setUp(String tableName) {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();

        loginAdmin(driver);

        driver.navigate().to(DASHBOARD_URL);
        driver.manage().window().maximize();

        page = PageFactory.initElements(driver, DashboardPage.class);
        page.setTable(tableName);

        page.ensureTableIsDisplayed();
        if (page.getTable().getTotalElements() == 0) {
            fail("Table has no elements");
        }
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
