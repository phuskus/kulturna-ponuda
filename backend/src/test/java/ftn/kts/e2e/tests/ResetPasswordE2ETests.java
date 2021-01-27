package ftn.kts.e2e.tests;

import static ftn.kts.e2e.constants.AppConstants.BASE_URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import ftn.kts.e2e.pages.LoginPage;
import ftn.kts.e2e.pages.MainPage;
import ftn.kts.e2e.pages.ResetPasswordPage;

public class ResetPasswordE2ETests {
	private WebDriver driver;
	private MainPage mainPage;
	private ResetPasswordPage resetPage;
	private LoginPage loginPage;
	
	@Before
	public void setUp() {
		 System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
	     driver = new ChromeDriver();   
	     driver.navigate().to(BASE_URL + "/reset-password/test-key");
	     resetPage = PageFactory.initElements(driver, ResetPasswordPage.class);
	     mainPage = PageFactory.initElements(driver, MainPage.class);
	     loginPage = PageFactory.initElements(driver, LoginPage.class);
	}
	
	@After
    public void tearDown() {
        driver.quit();
    }
	
	@Test
	public void resetPassword() {
		resetPage.setNewPasswordField("strongpassword");
		resetPage.setConfirmNewPasswordField("strongpassword");
		resetPage.clickResetButton();
		
		resetPage.ensureResetMessageIsShowed();
		mainPage.ensureMainPageIsDisplayed();
		mainPage.ensureProfileButtonIsDisplayed();
		
		mainPage.clickProfileButton();
		mainPage.ensureLogoutButtonIsDisplayed();
		mainPage.clickLogoutButton();
		mainPage.ensureLogoutMessageIsShowed();
		mainPage.ensureIsDisplayedLoginButton();
		mainPage.clickLoginButton();
		loginPage.ensureLoginFormIsShowed();
		
		//check with old password
		loginPage.setUsername("yahoo2@yahoo.com");
		loginPage.setPassword("12345");
		loginPage.clickLogInButton();
		
		loginPage.ensureInvalidPasswordIsShowed();
		mainPage.ensureMainPageIsNotDisplayed();
		
		//check with new password
		loginPage.clearPassword();
		loginPage.setPassword("strongpassword");
		loginPage.clickLogInButton();
		mainPage.ensureMainPageIsDisplayed();
		mainPage.ensureProfileButtonIsDisplayed();
	}

}
