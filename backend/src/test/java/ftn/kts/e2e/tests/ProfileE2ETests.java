package ftn.kts.e2e.tests;

import static ftn.kts.e2e.constants.AppConstants.BASE_URL;
import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import ftn.kts.e2e.pages.MainPage;
import ftn.kts.e2e.pages.ProfilePage;
import ftn.kts.util.E2EUtil;

public class ProfileE2ETests {
	
	protected WebDriver driver;
	protected MainPage mainPage;
	protected ProfilePage profilePage;
	
	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
        
        driver.navigate().to(BASE_URL + "/");

        driver.manage().window().maximize();
        mainPage = PageFactory.initElements(driver, MainPage.class);
        profilePage = PageFactory.initElements(driver, ProfilePage.class);
	}
	
	@After
    public void tearDown() {
        driver.quit();
    }
	
	@Test
	public void editProfile_Success() {
		
		String name = "HAKUNA";
		String surname = "MATATA";
		
		E2EUtil.loginUser(driver);
		driver.navigate().to(BASE_URL);
		
		mainPage.ensureMainPageIsDisplayed();
		mainPage.ensureProfileButtonIsDisplayed();
		mainPage.clickProfileButton();
		mainPage.ensureEditProfileButtonDisplayed();
		mainPage.clickEditProfileButton();
		
		profilePage.ensureNameIsDisplayed();
		profilePage.ensureSurnameIsDisplayed();
		profilePage.clearNameField();
		profilePage.setNameField(name);
		profilePage.clearSurnameField();
		profilePage.setSurnameField(surname);
		profilePage.clickEditProfileButton();
		
		mainPage.ensureMainPageIsDisplayed();
		mainPage.ensureProfileButtonIsDisplayed();
		mainPage.clickProfileButton();
		mainPage.ensureEditProfileButtonDisplayed();
		mainPage.clickEditProfileButton();
		
		profilePage.ensureNameIsDisplayed();
		profilePage.ensureSurnameIsDisplayed();
		
		String updatedName = profilePage.getNameValue();
		String updatedSurname = profilePage.getSurnameValue();
		
		assertEquals(updatedName, name);
		assertEquals(updatedSurname, surname);
		
		profilePage.getCancelProfileButton();
		mainPage.ensureMainPageIsDisplayed();
	}

}
