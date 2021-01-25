package ftn.kts.e2e.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage {
	@FindBy(id = "mat-input-0")
	WebElement usernameField;

	@FindBy(id = "mat-input-1")
	WebElement passwordField;

	@FindBy(css = "button[type=\"submit\"]")
	WebElement loginButton;

	public WebElement getUsernameField() {
		return usernameField;
	}

	public WebElement getPasswordField() {
		return passwordField;
	}

	public WebElement getLoginButton() {
		return loginButton;
	}
	
}
