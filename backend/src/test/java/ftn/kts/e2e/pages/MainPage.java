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

    @FindBy(className = "category-button")
    private List<WebElement> subcatButtons;
    
    public MainPage() {
    }

    public MainPage(WebDriver driver) {
        this.driver = driver;
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

	public WebElement getSearchBar() {
		return searchBar;
	}

	public WebElement getLoginButton() {
		return loginButton;
	}


	public List<WebElement> getSubcatButtons() {
		return subcatButtons;
	}


}
