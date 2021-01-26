package ftn.kts.e2e.tests;

import static ftn.kts.e2e.constants.AppConstants.LOGIN_URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import ftn.kts.e2e.pages.LoginPage;

public class LoginE2ETests {
	
	protected WebDriver driver;
	protected LoginPage page;
	
	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.navigate().to(LOGIN_URL);
        page = PageFactory.initElements(driver, LoginPage.class);
	}
	
	@After
	public void tearDown() {
		driver.quit();
	}
	
	@Test
	public void loginUser_ValidCredentials_RedirectsToMainPage() {
		login("yahoo@yahoo.com", "12345");
		
		page.ensureMainPageIsDisplayed();
		page.ensureProfileButtonIsDisplayed();
		page.ensureAdminButtonIsNotDisplayed();
		
	}
	
	@Test
	public void loginAdmin_ValidCredentials_RedirectsToMainPage() {
		login("covid19.clinic.llc@gmail.com", "12345");
		
		page.ensureMainPageIsDisplayed();
		page.ensureProfileButtonIsDisplayed();
		page.ensureAdminButtonIsDisplayed();
	}
	
	@Test
	public void loginUser_UsernameNotExists_ErrorMessage() {
		login("hello@yahoo.com", "12345");
		
		page.ensureUsernameNotExistsIsShowed();
		page.ensureMainPageIsNotDisplayed();
	}
	
	@Test
	public void loginUser_InvalidPassword_ErrorMessage() {
		login("yahoo@yahoo.com", "hehehe");
		
		page.ensureInvalidPasswordIsShowed();
		page.ensureMainPageIsNotDisplayed();		
	}
	
	@Test
	public void forgotPassword_RedirectForgotPasswordForm() {
		page.clickForgotPasswordLink();
		page.ensureForgotPasswordFormIsShowed();
	}
	
	@Test
	public void register_RedirectRegisterForm() {
		page.clickRegisterLink();
		page.ensureRegistrationFormIsShowed();
	}
	
	@Test
	public void loginUser_NotEnabledAccount_ErrorMessage() {
		login("yahoo1@yahoo.com", "12345");
		
		page.ensureActivationMessageIsShowed();
		page.ensureMainPageIsNotDisplayed();
	}
	
	
	
	private void login(String username, String password) {
		page.setUsername(username);
		page.setPassword(password);
		page.clickLogInButton();
	}
	
	
}
