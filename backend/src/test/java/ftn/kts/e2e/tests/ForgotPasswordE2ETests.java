package ftn.kts.e2e.tests;

import static ftn.kts.e2e.constants.AppConstants.FORGOT_PASSWORD_URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import ftn.kts.e2e.pages.ForgotPasswordPage;

public class ForgotPasswordE2ETests {
	
	protected WebDriver driver;
	protected ForgotPasswordPage page;
	
	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.navigate().to(FORGOT_PASSWORD_URL);
        page = PageFactory.initElements(driver, ForgotPasswordPage.class);
	}
	
	@After
	public void tearDown() {
		driver.quit();
	}
	
	@Test
	public void forgotPassword_ValidUser_MessageDisplayed() {
		page.setUsername("yahoo@yahoo.com");
		page.clickSendLoginLinkButton();
		page.ensureLoginFormIsDisplayed();
		page.ensureMessageIsDisplayed();
	}
	
	@Test
	public void forgotPassword_InvalidUser_ErrorMessageDisplayed() {
		page.setUsername("xyz@yahoo.com");
		page.clickSendLoginLinkButton();
		page.ensureUsernameNotExistsIsDisplayed();
		page.ensureLoginPageIsNotDisplayed();
	}
	
	@Test
	public void forgotPassword_BackToLogin_LoginFormIsDisplayed() {
		page.clickLoginLink();
		page.ensureLoginFormIsDisplayed();
	}
}
