package ftn.kts.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ChangePasswordPage {
	
	private WebDriver driver;
	
	@FindBy(id = "password")
	WebElement passwordField;
	
	@FindBy(id = "newPassword")
	WebElement newPasswordField;
	
	@FindBy(id = "confirmPassword")
	WebElement confirmNewPasswordField;
	
	@FindBy(id = "changePassword")
	WebElement changePasswordButton;
	
	public ChangePasswordPage(WebDriver driver) {
		this.driver = driver;
	}

	
	public WebElement getPasswordField() {
		return passwordField;
	}

	public void setPasswordField(String password) {
		passwordField.sendKeys(password);
	}

	public WebElement getNewPasswordField() {
		return newPasswordField;
	}

	public void setNewPasswordField(String newPassword) {
		newPasswordField.sendKeys(newPassword);
	}

	public WebElement getConfirmNewPasswordField() {
		return confirmNewPasswordField;
	}

	public void setConfirmNewPassword(String confirmNewPassword) {
		confirmNewPasswordField.sendKeys(confirmNewPassword);
	}

	public WebElement getChangePasswordButton() {
		return changePasswordButton;
	}
	
	public void clickChangePassword() {
		changePasswordButton.click();
	}
	
	public void ensurePasswordIsDisplayed() {
		(new WebDriverWait(driver, 30)).until(
				ExpectedConditions.visibilityOfElementLocated(By.id("password")));
	}
	
	public void ensureNewPasswordIsDisplayed() {
		(new WebDriverWait(driver, 30)).until(
				ExpectedConditions.visibilityOfElementLocated(By.id("newPassword")));
	}
	
	public void ensureConfirmPasswordIsDisplayed() {
		(new WebDriverWait(driver, 30)).until(
				ExpectedConditions.visibilityOfElementLocated(By.id("confirmPassword")));
	}
	
	public void ensureErrorMessageIsDisplayed() {
		(new WebDriverWait(driver, 30)).until(
				ExpectedConditions.visibilityOfElementLocated(By.className("mat-simple-snackbar")));
	}
	
	
	
	
	

}
