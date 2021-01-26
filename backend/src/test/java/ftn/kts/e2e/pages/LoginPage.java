package ftn.kts.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
	private WebDriver driver;
	
	@FindBy(id = "username")
	WebElement usernameField;

	@FindBy(id = "password")
	WebElement passwordField;

	@FindBy(css = "button[type=\"submit\"]")
	WebElement loginButton;
	
	@FindBy(id="forgotPassword")
	WebElement forgotPasswordLink;
	
	@FindBy(id="register")
	WebElement registerLink;
	
	@FindBy(css = "mat-simple-snackbar")
	WebElement activationMessage;
	
	
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
	
	public WebElement getForgotPasswordLink() {
		return forgotPasswordLink;
	}
	
	public WebElement getRegisterLink() {
		return registerLink;
	}
	
	public void clickLogInButton() {
		loginButton.click();
	}
	
	public void clickRegisterLink() {
		registerLink.click();
	}
	
	public void clickForgotPasswordLink() {
		forgotPasswordLink.click();
	}
	
	public void setUsername(String username) {
		usernameField.sendKeys(username);
	}
	
	public void setPassword(String password) {
		passwordField.sendKeys(password);
	}
	

	public void ensureUsernameNotExistsIsShowed() {
		(new WebDriverWait(driver, 30)).until(
				ExpectedConditions.visibilityOfElementLocated(By.id("usernameNotExists")));
	}
	
	public void ensureInvalidPasswordIsShowed() {
		(new WebDriverWait(driver, 30)).until(
				ExpectedConditions.visibilityOfElementLocated(By.id("invalidPassword")));
	}
	
	
	public void ensureLoginFormIsShowed() {
		(new WebDriverWait(driver, 30)).until(
				ExpectedConditions.visibilityOfElementLocated(By.tagName("app-login-page")));
	}
	
	public void ensureActivationMessageIsShowed() {
		(new WebDriverWait(driver, 30)).until(
				ExpectedConditions.visibilityOfElementLocated(By.className("mat-simple-snackbar")));
	}
	
	public void ensureUsernameFieldIsShowed() {
		(new WebDriverWait(driver, 30)).until(
				ExpectedConditions.visibilityOfElementLocated(By.id("username")));
	}
	
	public void ensurePasswordFieldIsShowed() {
		(new WebDriverWait(driver, 30)).until(
				ExpectedConditions.visibilityOfElementLocated(By.id("password")));
	}
	
	public void clearPassword() {
		passwordField.clear();
	}
	
}
