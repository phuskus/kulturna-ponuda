package ftn.kts.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
	private WebDriver driver;
	
	@FindBy(id = "mat-input-0")
	WebElement usernameField;

	@FindBy(id = "mat-input-1")
	WebElement passwordField;


	@FindBy(css = "button[type=\"submit\"]")
	WebElement loginButton;
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}

	public WebElement getUsernameField() {
		return usernameField;
	}

	public WebElement getPasswordField() {
		return passwordField;
	}

	public WebElement getLoginButton() {
		return loginButton;
	}
	
	public void clickLogInButton() {
		loginButton.click();
	}
	
	public void setUsername(String username) {
		usernameField.sendKeys(username);
	}
	
	public void setPassword(String password) {
		passwordField.sendKeys(password);
	}
	
	public void ensureMainPageIsDisplayed() {
		(new WebDriverWait(driver, 30)).until(
				ExpectedConditions.visibilityOfElementLocated(By.tagName("app-landing-page")));
	}
	
	public void ensureMainPageIsNotDisplayed() {
		(new WebDriverWait(driver, 30)).until(ExpectedConditions
                .invisibilityOfElementLocated(By.tagName("app-landing-page")));
	}
	
	public void ensureProfileButtonIsDisplayed() {
		(new WebDriverWait(driver, 30)).until(
				ExpectedConditions.visibilityOfElementLocated(By.id("profile-button")));
	}
	
	public void ensureAdminButtonIsDisplayed() {
		(new WebDriverWait(driver, 30)).until(
				ExpectedConditions.visibilityOfElementLocated(By.id("admin-button")));
	}
	
	public void ensureAdminButtonIsNotDisplayed() {
		(new WebDriverWait(driver, 30)).until(ExpectedConditions
                .invisibilityOfElementLocated(By.id("admin-button")));
	}
	
	public void ensureUsernameNotExistsIsShowed() {
		(new WebDriverWait(driver, 30)).until(
				ExpectedConditions.visibilityOfElementLocated(By.id("usernameNotExists")));
	}
	
	public void ensureInvalidPasswordIsShowed() {
		(new WebDriverWait(driver, 30)).until(
				ExpectedConditions.visibilityOfElementLocated(By.id("invalidPassword")));
	}
	
}
