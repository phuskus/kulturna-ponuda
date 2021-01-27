package ftn.kts.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ResetPasswordPage {
	private WebDriver driver;
	
	@FindBy(id = "mat-input-0")
	WebElement newPasswordField;
	
	@FindBy(id = "mat-input-1")
	WebElement confirmNewPasswordField;
	
	@FindBy(css = "button[type=\"submit\"]")
	WebElement resetButton;
	
	public ResetPasswordPage(WebDriver driver) {
		this.driver = driver;
	}

	public WebDriver getDriver() {
		return driver;
	}

	public WebElement getNewPasswordField() {
		return newPasswordField;
	}

	public WebElement getConfirmNewPasswordField() {
		return confirmNewPasswordField;
	}

	public WebElement getResetButton() {
		return resetButton;
	}
	
	public void setNewPasswordField(String newPassword) {
		newPasswordField.sendKeys(newPassword);
	}
	
	public void setConfirmNewPasswordField(String confirmPassword) {
		confirmNewPasswordField.sendKeys(confirmPassword);
	}
	
	public void clickResetButton() {
		resetButton.click();
	}
		
	public void ensureResetMessageIsShowed() {
		(new WebDriverWait(driver, 30)).until(
				ExpectedConditions.visibilityOfElementLocated(By.className("mat-simple-snackbar")));
	}
	
	
	
	
	
	

}
