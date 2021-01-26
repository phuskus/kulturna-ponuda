package ftn.kts.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ForgotPasswordPage {
	private WebDriver driver;
	
	@FindBy(id = "mat-input-0")
	WebElement usernameField;
	
	@FindBy(css = "button[type=\"submit\"]")
	WebElement sendLoginLinkButton;
	
	@FindBy(id = "loginLink")
	WebElement loginLink;
	
	public WebElement getUsernameField() {
		return usernameField;
	}

	public WebElement getSendLoginLinkButton() {
		return sendLoginLinkButton;
	}

	public WebElement getLoginLink() {
		return loginLink;
	}

	public ForgotPasswordPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void clickSendLoginLinkButton() {
		sendLoginLinkButton.click();
	}
	
	public void clickLoginLink() {
		loginLink.click();
	}
	
	public void setUsername(String username) {
		usernameField.sendKeys(username);
	}
	
	public void ensureMessageIsDisplayed() {
		(new WebDriverWait(driver, 30)).until(
				ExpectedConditions.visibilityOfElementLocated(By.className("mat-simple-snackbar")));
	}
	
	public void ensureLoginFormIsDisplayed() {
		(new WebDriverWait(driver, 30)).until(
				ExpectedConditions.visibilityOfElementLocated(By.tagName("app-login-page")));
	}
	
	public void ensureUsernameNotExistsIsDisplayed() {
		(new WebDriverWait(driver, 30)).until(
				ExpectedConditions.visibilityOfElementLocated(By.id("usernameNotExists")));
	}
	
	public void ensureLoginPageIsNotDisplayed() {
		(new WebDriverWait(driver, 30)).until(ExpectedConditions
                .invisibilityOfElementLocated(By.tagName("app-login-page")));
	}
	
	public void ensureForgotPasswordFormIsShowed() {
		(new WebDriverWait(driver, 30)).until(
				ExpectedConditions.visibilityOfElementLocated(By.tagName("app-forgot-password")));
	}
	
}
