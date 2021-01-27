package ftn.kts.e2e.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage {

    private WebDriver driver;

    @FindBy(id = "search-bar")
    private WebElement searchBar;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(id = "profile-button")
    private WebElement profileButton;

    @FindBy(id = "logout-button")
    private WebElement logoutButton;

    @FindBy(className = "category-button")
    private List<WebElement> subcatButtons;

    @FindBy(id = "edit-subscriptions-button")
    private WebElement editSubscriptionsButton;

    @FindBy(className = "delete-subscription-button")
    private List<WebElement> deleteButtons;
    
    @FindBy(id = "edit-profile-button")
    private WebElement editProfileButton;
    
    @FindBy(id = "change-password-button")
    private WebElement changePasswordButton;
    
    public MainPage() {
    }

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }
    
    public void clickProfileButton() {
    	profileButton.click();
    }
    
    public void clickLogoutButton() {
    	logoutButton.click();
    }
    
    public void clickLoginButton() {
    	loginButton.click();
    }
    
    public void clickEditProfileButton() {
    	editProfileButton.click();
    }
    
    public void clickChangePasswordButton() {
    	changePasswordButton.click();
    }
    
    public void ensureMainPageIsDisplayed() {
		(new WebDriverWait(driver, 30)).until(
				ExpectedConditions.visibilityOfElementLocated(By.tagName("app-landing-page")));
	}
	
	public void ensureMainPageIsNotDisplayed() {
		(new WebDriverWait(driver, 30)).until(ExpectedConditions
                .invisibilityOfElementLocated(By.tagName("app-landing-page")));
	}
	
    
    public void ensureIsDisplayedSearchBar() {
    	(new WebDriverWait(driver, 30)).until(ExpectedConditions.visibilityOfElementLocated(By.id("search-bar")));
    }
    
    public void ensureIsDisplayedSubcategoryList() {
    	(new WebDriverWait(driver, 30)).until(ExpectedConditions.visibilityOfElementLocated(By.className("category-button")));
    }

    public void ensureIsNotDisplayedSubcategoryList() {
    	(new WebDriverWait(driver, 30)).until(ExpectedConditions.invisibilityOfElementLocated(By.className("category-button")));
    }

    public void ensureIsDisplayedLoginButton() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
    }

    public void ensureIsNotVisibleLoginButton() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.invisibilityOfElementLocated(By.id("login-button")));
    }
    
    public void ensureLoginFormIsDisplayed() {
		(new WebDriverWait(driver, 30)).until(
				ExpectedConditions.visibilityOfElementLocated(By.tagName("app-login-page")));
	}
    
    public void ensureLogoutButtonIsDisplayed() {
    	(new WebDriverWait(driver, 30)).until(ExpectedConditions.visibilityOfElementLocated(By.id("logout-button")));
    }
    
    public void ensureLogoutMessageIsShowed() {
		(new WebDriverWait(driver, 30)).until(
				ExpectedConditions.visibilityOfElementLocated(By.className("mat-simple-snackbar")));
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
		(new WebDriverWait(driver, 30)).until(
				ExpectedConditions.invisibilityOfElementLocated(By.id("admin-button")));
	}
      
	public WebElement getSearchBar() {
		return searchBar;
	}

	public WebElement getLoginButton() {
		return loginButton;
	}


	public List<WebElement> getSubcatButtons() {
		return subcatButtons;
	}


    public WebElement getProfileButton() {
        return profileButton;
    }
    
    public WebElement getEditProfileButton() {
    	return editProfileButton;
    }
    
    public WebElement getChangePasswordButton() {
    	return changePasswordButton;
    }

    public WebElement getLogoutButton() {
        return logoutButton;
    }

    public WebElement getEditSubscriptionsButton() {
        return editSubscriptionsButton;
    }

    public void ensureIsDisplayedEditSubscriptions() {
        (new WebDriverWait(driver, 3)).until(ExpectedConditions.visibilityOfElementLocated(By.className("subscription-table-row")));
    }

    public List<WebElement> getDeleteSubscriptionButtons() {
        return deleteButtons;
    }

    public void ensureIsNotDisplayed(WebElement element) {
        (new WebDriverWait(driver, 3)).until(ExpectedConditions.invisibilityOf(element));
    }
    
    public void ensureEditProfileButtonDisplayed() {
    	 (new WebDriverWait(driver, 5)).until(ExpectedConditions.visibilityOfElementLocated(By.id("edit-profile-button")));
    }
    
    public void ensurePasswordButtonDisplayed() {
    	(new WebDriverWait(driver, 5)).until(ExpectedConditions.visibilityOfElementLocated(By.id("change-password-button")));
    }
    
}
