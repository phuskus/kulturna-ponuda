package ftn.kts.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import ftn.kts.e2e.pages.LoginPage;

import static ftn.kts.e2e.constants.AppConstants.BASE_URL;
import static ftn.kts.e2e.constants.AppConstants.LOGIN_URL;

public class E2EUtil {
	public static void loginUser(WebDriver driver) {
		driver.navigate().to(LOGIN_URL);
		LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
		WebElement usernameField = loginPage.getUsernameField();
		usernameField.sendKeys("yahoo@yahoo.com");
		WebElement passwordField = loginPage.getPasswordField();
		passwordField.sendKeys("12345");
		WebElement loginButton = loginPage.getLoginButton();
		loginButton.click();
	}

	public static void loginAdmin(WebDriver driver) {
		driver.navigate().to(LOGIN_URL);
		LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
		WebElement usernameField = loginPage.getUsernameField();
		usernameField.sendKeys("covid19.clinic.llc@gmail.com");
		WebElement passwordField = loginPage.getPasswordField();
		passwordField.sendKeys("12345");
		WebElement loginButton = loginPage.getLoginButton();
		loginButton.click();
	}
}
