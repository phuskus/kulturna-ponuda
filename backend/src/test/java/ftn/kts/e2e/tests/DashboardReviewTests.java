package ftn.kts.e2e.tests;

import ftn.kts.e2e.pages.DashboardPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import static ftn.kts.util.E2EUtil.loginAdmin;
import static ftn.kts.e2e.constants.AppConstants.DASHBOARD_URL;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class DashboardReviewTests {

    private WebDriver driver;
    private DashboardPage page;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();

        loginAdmin(driver);
        driver.navigate().to(DASHBOARD_URL);
        driver.manage().window().maximize();
        page = PageFactory.initElements(driver, DashboardPage.class);
        page.setTable("review");
    }


    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void DeleteReview_ClickConfirm_Success() {
        page.ensureTableIsDisplayed();

        int totalElements = page.getTable().getTotalElements();

        if (totalElements == 0) {
            fail("Table has no elements");
        }

        WebElement deleteButton = page.getTable().getActions(0)
                .findElement(By.tagName("button"));
        deleteButton.click();
        page.ensureDialogIsDisplayed();

        WebElement confirmButton = page.getTable().getConfirmDialog();
        confirmButton.click();
        page.ensureDialogIsNotDisplayed();

        assertEquals(totalElements - 1, page.getTable().getTotalElements());
    }

    @Test
    public void DeleteReview_ClickCancel_Abort() {
        page.ensureTableIsDisplayed();

        int totalElements = page.getTable().getTotalElements();

        if (totalElements == 0) {
            fail("Table has no elements");
        }

        WebElement deleteButton = page.getTable().getActions(0)
                .findElement(By.tagName("button"));
        deleteButton.click();
        page.ensureDialogIsDisplayed();

        WebElement cancelButton = page.getTable().getCancelDialog();
        cancelButton.click();
        page.ensureDialogIsNotDisplayed();

        assertEquals(totalElements, page.getTable().getTotalElements());
    }

    @Test
    public void Search_TypeRandomString_NumElementsShrinks() {
        page.ensureTableIsDisplayed();

        int totalElements = page.getTable().getTotalElements();

        if (totalElements == 0) {
            fail("Table has no elements");
        }


    }
}
