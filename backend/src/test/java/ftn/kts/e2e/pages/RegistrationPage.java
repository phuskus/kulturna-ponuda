package ftn.kts.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegistrationPage {
	private WebDriver driver;
	
	@FindBy(id = "mat-input-0")
	WebElement nameField;
	
	@FindBy(id = "mat-input-1")
	WebElement surnameField;
	
	@FindBy(id = "mat-input-2")
	WebElement usernameField;
	
	@FindBy(id = "mat-input-3")
	WebElement passwordField;
	
	@FindBy(id = "mat-input-4")
	WebElement confirmPasswordField;
	
	@FindBy(css = "button[type=\"submit\"]")
	WebElement registerButton;
	
	@FindBy(id = "signIn")
	WebElement loginLink;

	public RegistrationPage(WebDriver driver) {
		this.driver = driver;
	}

	public WebElement getNameField() {
		return nameField;
	}

	public WebElement getSurnameField() {
		return surnameField;
	}

	public WebElement getUsernameField() {
		return usernameField;
	}

	public WebElement getPasswordField() {
		return passwordField;
	}

	public WebElement getConfirmPasswordField() {
		return confirmPasswordField;
	}
	
	public WebElement getLoginLink() {
		return loginLink;
	}
	
	public void setName(String name) {
		nameField.sendKeys(name);
	}
	
	public void setSurname(String surname) {
		surnameField.sendKeys(surname);
	}
	
	public void setUsername(String username) {
		usernameField.sendKeys(username);
	}
	
	public void setPassword(String password) {
		passwordField.sendKeys(password);
	}
	
	public void setConfirmPassword(String confirmPassword) {
		confirmPasswordField.sendKeys(confirmPassword);
	}
	
	public void clickRegisterButton() {
		registerButton.click();
	}
	
	public void clickLoginLink() {
		loginLink.click();
	}
	
	public void ensureActivationMessageIsShowed() {
		(new WebDriverWait(driver, 50)).until(
				ExpectedConditions.visibilityOfElementLocated(By.className("mat-simple-snackbar")));
	}
	
	public void ensureUsernameExistsIsShowed() {
		(new WebDriverWait(driver, 30)).until(
				ExpectedConditions.visibilityOfElementLocated(By.id("usernameExists")));
	}
	
	public void ensureLoginFormIsShowed() {
		(new WebDriverWait(driver, 30)).until(
				ExpectedConditions.visibilityOfElementLocated(By.tagName("app-login-page")));
	}
	
}
