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
		String username = "yahoo@yahoo.com";
		String password = "12345";
		
		page.setUsername(username);
		page.setPassword(password);
		page.clickLogInButton();
		
		page.ensureMainPageIsDisplayed();
		page.ensureProfileButtonIsDisplayed();
		page.ensureAdminButtonIsNotDisplayed();
		
	}
	
	@Test
	public void loginAdmin_ValidCredentials_RedirectsToMainPage() {
		String username = "covid19.clinic.llc@gmail.com";
		String password = "12345";
		
		page.setUsername(username);
		page.setPassword(password);
		page.clickLogInButton();
		
		page.ensureMainPageIsDisplayed();
		page.ensureProfileButtonIsDisplayed();
		page.ensureAdminButtonIsDisplayed();
	}
	
	@Test
	public void loginUser_UsernameNotExists_ErrorMessage() {
		String username = "hello@yahoo.com";
		String password = "12345";
		
		page.setUsername(username);
		page.setPassword(password);
		page.clickLogInButton();
		
		page.ensureUsernameNotExistsIsShowed();
		page.ensureMainPageIsNotDisplayed();
	}
	
	@Test
	public void loginUser_InvalidPassword_ErrorMessage() {
		String username = "yahoo@yahoo.com";
		String password = "hehehe";
		
		page.setUsername(username);
		page.setPassword(password);
		page.clickLogInButton();
		
		page.ensureInvalidPasswordIsShowed();
		page.ensureMainPageIsNotDisplayed();		
	}
	
}
