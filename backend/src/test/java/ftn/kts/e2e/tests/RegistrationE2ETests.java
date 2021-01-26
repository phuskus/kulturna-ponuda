package ftn.kts.e2e.tests;

import static ftn.kts.e2e.constants.AppConstants.REGISTER_URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import ftn.kts.e2e.pages.LoginPage;
import ftn.kts.e2e.pages.RegistrationPage;

public class RegistrationE2ETests {
	
	protected WebDriver driver;
	protected RegistrationPage registrationPage;
	protected LoginPage loginPage;
	
	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.navigate().to(REGISTER_URL);
        registrationPage = PageFactory.initElements(driver, RegistrationPage.class);
        loginPage = PageFactory.initElements(driver, LoginPage.class);
	}
	
	@After
	public void tearDown() {
		driver.quit();
	}
	
	@Test
	public void registerUser_ValidFields_ActivationMessage() {
		register("name", "surname", "littlebirby@yahoo.com", "12345", "12345");
		registrationPage.ensureActivationMessageIsDisplayed();
		
	}
	
	@Test
	public void registerUser_UsernameAlreadyExists_UsernameExistsMessage() {
		register("name", "surname", "yahoo@yahoo.com", "12345", "12345");
		registrationPage.ensureUsernameExistsIsDisplayed();
	}
	
	@Test
	public void registerUser_ReturnToLogin_LoginFormDisplayed() {
		registrationPage.clickLoginLink();
		loginPage.ensureLoginFormIsShowed();
	}
	
	private void register(String name, String surname, String username, String password, String confirmPassword) {
		registrationPage.setName(name);
		registrationPage.setSurname(surname);
		registrationPage.setUsername(username);
		registrationPage.setPassword(password);
		registrationPage.setConfirmPassword(confirmPassword);
		
		registrationPage.clickRegisterButton();
	}
	
	
}
