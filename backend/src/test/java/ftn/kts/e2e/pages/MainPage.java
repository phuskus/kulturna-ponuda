package ftn.kts.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage {

    private WebDriver driver;

    @FindBy(xpath = "//*[@id=\"search-bar\"]")
    private WebElement searchBar;

    @FindBy(xpath = "//*[@id=\"login-button\"]")
    private WebElement loginButton;

    @FindBy(xpath = "//*[@id=\"subcat-1\"]")
    private WebElement subcatButton;
    
    public MainPage() {
    }

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }
    
    public void ensureIsDisplayedSearchBar() {
    	(new WebDriverWait(driver, 30)).until(ExpectedConditions.visibilityOfElementLocated(By.id("search-bar")));
    }

    public void ensureIsDisplayedSubcategory() {
    	(new WebDriverWait(driver, 30)).until(ExpectedConditions.visibilityOfElementLocated(By.id("subcat-1")));
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

	public void setSearchBar(WebElement searchBar) {
		this.searchBar = searchBar;
	}

	public WebElement getLoginButton() {
		return loginButton;
	}

	public void setLoginButton(WebElement loginButton) {
		this.loginButton = loginButton;
	}

	public WebElement getSubcatButton() {
		return subcatButton;
	}

	public void setSubcatButton(WebElement subcatButton) {
		this.subcatButton = subcatButton;
	}

}
