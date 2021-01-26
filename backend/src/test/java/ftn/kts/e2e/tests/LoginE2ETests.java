package ftn.kts.e2e.tests;

import static ftn.kts.e2e.constants.AppConstants.LOGIN_URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import ftn.kts.e2e.pages.ForgotPasswordPage;
import ftn.kts.e2e.pages.LoginPage;
import ftn.kts.e2e.pages.MainPage;
import ftn.kts.e2e.pages.RegistrationPage;

public class LoginE2ETests {
	
	protected WebDriver driver;
	protected LoginPage loginPage;
	protected MainPage mainPage;
	protected ForgotPasswordPage forgotPasswordPage;
	protected RegistrationPage registerPage;
	
	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.navigate().to(LOGIN_URL);
        loginPage = PageFactory.initElements(driver, LoginPage.class);
        mainPage = PageFactory.initElements(driver, MainPage.class);
        forgotPasswordPage = PageFactory.initElements(driver, ForgotPasswordPage.class);
        registerPage = PageFactory.initElements(driver, RegistrationPage.class);
	}
	
	@After
	public void tearDown() {
		driver.quit();
	}
	
	@Test
	public void loginUser_ValidCredentials_RedirectsToMainPage() {
		login("yahoo@yahoo.com", "12345");
		
		mainPage.ensureMainPageIsDisplayed();
		mainPage.ensureProfileButtonIsDisplayed();
		mainPage.ensureAdminButtonIsNotDisplayed();
		
	}
	
	@Test
	public void loginAdmin_ValidCredentials_RedirectsToMainPage() {
		login("covid19.clinic.llc@gmail.com", "12345");
		
		mainPage.ensureMainPageIsDisplayed();
		mainPage.ensureProfileButtonIsDisplayed();
		mainPage.ensureAdminButtonIsDisplayed();
	}
	
	@Test
	public void loginUser_UsernameNotExists_ErrorMessage() {
		login("hello@yahoo.com", "12345");
		
		loginPage.ensureUsernameNotExistsIsShowed();
		mainPage.ensureMainPageIsNotDisplayed();
	}
	
	@Test
	public void loginUser_InvalidPassword_ErrorMessage() {
		login("yahoo@yahoo.com", "hehehe");
		
		loginPage.ensureInvalidPasswordIsShowed();
		mainPage.ensureMainPageIsNotDisplayed();		
	}
	
	@Test
	public void forgotPassword_RedirectForgotPasswordForm() {
		loginPage.clickForgotPasswordLink();
		forgotPasswordPage.ensureForgotPasswordFormIsShowed();
	}
	
	@Test
	public void register_RedirectRegisterForm() {
		loginPage.clickRegisterLink();
		registerPage.ensureRegistrationFormIsDisplayed();
	}
	
	@Test
	public void loginUser_NotEnabledAccount_ErrorMessage() {
		login("yahoo1@yahoo.com", "12345");
		
		loginPage.ensureActivationMessageIsShowed();
		mainPage.ensureMainPageIsNotDisplayed();
	}
	
	
	
	private void login(String username, String password) {
		loginPage.setUsername(username);
		loginPage.setPassword(password);
		loginPage.clickLogInButton();
	}
	
	
}
