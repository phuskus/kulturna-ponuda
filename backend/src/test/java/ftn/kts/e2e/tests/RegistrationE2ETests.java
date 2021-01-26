package ftn.kts.e2e.tests;

import static ftn.kts.e2e.constants.AppConstants.REGISTER_URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import ftn.kts.e2e.pages.RegistrationPage;

public class RegistrationE2ETests {
	
	protected WebDriver driver;
	protected RegistrationPage page;
	
	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.navigate().to(REGISTER_URL);
        page = PageFactory.initElements(driver, RegistrationPage.class);
	}
	
	@After
	public void tearDown() {
		driver.quit();
	}
	
	@Test
	public void registerUser_ValidFields_ActivationMessage() {
		register("name", "surname", "littlebirb@yahoo.com", "12345", "12345");
		page.ensureActivationMessageIsShowed();
		
	}
	
	@Test
	public void registerUser_UsernameAlreadyExists_UsernameExistsMessage() {
		register("name", "surname", "yahoo@yahoo.com", "12345", "12345");
		page.ensureUsernameExistsIsShowed();
	}
	
	@Test
	public void registerUser_ReturnToLogin_LoginFormDisplayed() {
		page.clickLoginLink();
		page.ensureLoginFormIsShowed();
	}
	
	private void register(String name, String surname, String username, String password, String confirmPassword) {
		page.setName(name);
		page.setSurname(surname);
		page.setUsername(username);
		page.setPassword(password);
		page.setConfirmPassword(confirmPassword);
		
		page.clickRegisterButton();
	}
	
	
}
