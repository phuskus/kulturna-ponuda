package ftn.kts.e2e.tests;

import static ftn.kts.e2e.constants.AppConstants.BASE_URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import ftn.kts.e2e.pages.ChangePasswordPage;
import ftn.kts.e2e.pages.LoginPage;
import ftn.kts.e2e.pages.MainPage;
import ftn.kts.util.E2EUtil;

public class ChangePasswordE2E {
	
	protected WebDriver driver;
	protected MainPage mainPage;
	protected ChangePasswordPage page;
	protected LoginPage loginPage;
	
	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
        
        driver.navigate().to(BASE_URL + "/");

        driver.manage().window().maximize();
        mainPage = PageFactory.initElements(driver, MainPage.class);
        page = PageFactory.initElements(driver, ChangePasswordPage.class);
        loginPage = PageFactory.initElements(driver, LoginPage.class);
	}
	
	@After
    public void tearDown() {
        driver.quit();
    }
	
	@Test
	public void changePassword_Success() {
		String oldPassword = "12345";
		String newPassword = "54321";
		String username = "yahoo@yahoo.com";
		
		E2EUtil.loginUser(driver);
		driver.navigate().to(BASE_URL);
		
		mainPage.ensureMainPageIsDisplayed();
		mainPage.ensureProfileButtonIsDisplayed();
		mainPage.clickProfileButton();
		mainPage.ensurePasswordButtonDisplayed();
		mainPage.clickChangePasswordButton();
		
		page.ensurePasswordIsDisplayed();
		page.ensureNewPasswordIsDisplayed();
		page.ensureConfirmPasswordIsDisplayed();
		page.setPasswordField(oldPassword);
		page.setNewPasswordField(newPassword);
		page.setConfirmNewPassword(newPassword);
		page.clickChangePassword();
		
		mainPage.ensureMainPageIsDisplayed();
		
		driver.navigate().to(BASE_URL + "/login");
		loginPage.ensureLoginFormIsShowed();
		loginPage.setUsername(username);
		loginPage.setPassword(oldPassword);
		loginPage.clickLogInButton();
		loginPage.ensureInvalidPasswordIsShowed();
		loginPage.clearPassword();
		loginPage.setPassword(newPassword);
		loginPage.clickLogInButton();
		
		mainPage.ensureMainPageIsDisplayed();
		
		//clean up
		mainPage.ensureProfileButtonIsDisplayed();
		mainPage.clickProfileButton();
		mainPage.ensurePasswordButtonDisplayed();
		mainPage.clickChangePasswordButton();
		
		page.ensurePasswordIsDisplayed();
		page.ensureNewPasswordIsDisplayed();
		page.ensureConfirmPasswordIsDisplayed();
		
		page.setPasswordField(newPassword);
		page.setNewPasswordField(oldPassword);
		page.setConfirmNewPassword(oldPassword);
		page.clickChangePassword();
		
	}
	
	@Test
	public void changePassword_InvalidOldPassword_ErrorMessage() {
		String oldPassword = "54321";
		String newPassword = "password";
		
		E2EUtil.loginUser(driver);
		driver.navigate().to(BASE_URL);
		
		mainPage.ensureMainPageIsDisplayed();
		mainPage.ensureProfileButtonIsDisplayed();
		mainPage.clickProfileButton();
		mainPage.ensurePasswordButtonDisplayed();
		mainPage.clickChangePasswordButton();
		
		page.ensurePasswordIsDisplayed();
		page.ensureNewPasswordIsDisplayed();
		page.ensureConfirmPasswordIsDisplayed();
		page.setPasswordField(oldPassword);
		page.setNewPasswordField(newPassword);
		page.setConfirmNewPassword(newPassword);
		page.clickChangePassword();
		page.ensureErrorMessageIsDisplayed();
	}

}
