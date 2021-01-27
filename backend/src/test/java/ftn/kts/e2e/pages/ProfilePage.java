package ftn.kts.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProfilePage {
	
	private WebDriver driver;
	
	@FindBy(id = "name")
	WebElement nameField;
	
	@FindBy(id = "surname")
	WebElement surnameField;
	
	@FindBy(id = "editProfile")
	WebElement editProfileButton;
	
	@FindBy(id = "cancelProfile")
	WebElement cancelProfileButton;
	
	public ProfilePage(WebDriver driver) {
		this.driver = driver;
	}

	public WebElement getNameField() {
		return nameField;
	}

	public void setNameField(String name) {
		nameField.sendKeys(name);
	}

	public WebElement getSurnameField() {
		return surnameField;
	}

	public void setSurnameField(String surname) {
		surnameField.sendKeys(surname);
	}

	public WebElement getEditProfileButton() {
		return editProfileButton;
	}

	public WebElement getCancelProfileButton() {
		return cancelProfileButton;
	}
	
	public void ensureNameIsDisplayed() {
		(new WebDriverWait(driver, 30)).until(
				ExpectedConditions.visibilityOfElementLocated(By.id("name")));
	}
	
	public void ensureSurnameIsDisplayed() {
		(new WebDriverWait(driver, 30)).until(
				ExpectedConditions.visibilityOfElementLocated(By.id("surname")));
	}
	
	public void clickEditProfileButton() {
		editProfileButton.click();
	}
	
	public void clearNameField() {
		nameField.clear();
	}
	
	public void clearSurnameField() {
		surnameField.clear();
	}
	
	public String getNameValue() {
		return nameField.getAttribute("value");
	}
	
	public String getSurnameValue() {
		return surnameField.getAttribute("value");
	}
	
	
}
