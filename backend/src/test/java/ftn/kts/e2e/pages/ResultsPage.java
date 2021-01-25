package ftn.kts.e2e.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ResultsPage {

	private WebDriver driver;

	@FindBy(xpath = "//*[@id=\"search-bar\"]")
	private WebElement searchBar;

	@FindBy(xpath = "//*[@id=\"no-results\"]")
	private WebElement noResultsMessage;

	@FindBy(className = "result-card")
	private List<WebElement> offerList;

	@FindBy(xpath = "//*[@id=\"pagionation-control\"]")
	private WebElement paginationControl;

	/*
	 * @FindBy(xpath = "//*[@id=\"category-h3\"]") private WebElement categoryP;
	 * 
	 * @FindBy(xpath = "//*[@id=\"name\"]") private WebElement name;
	 * 
	 * @FindBy(xpath = "//*[@id=\"submit-button\"]") private WebElement
	 * submitButton;
	 */
	public ResultsPage() {
	}

	public ResultsPage(WebDriver driver) {
		this.driver = driver;
	}

	public void ensureIsDisplayedSearchBar() {
		(new WebDriverWait(driver, 30)).until(ExpectedConditions.visibilityOfElementLocated(By.id("search-bar")));
	}

	public void ensureIsVisibleNoResults() {
		(new WebDriverWait(driver, 30)).until(ExpectedConditions.visibilityOfElementLocated(By.id("no-results")));
	}

	public void ensureIsNotVisibleNoResults() {
		(new WebDriverWait(driver, 30)).until(ExpectedConditions.invisibilityOfElementLocated(By.id("no-results")));
	}

	public void ensureIsVisibleFirstResult() {
		(new WebDriverWait(driver, 30)).until(ExpectedConditions.visibilityOfElementLocated(By.id("no-results")));
	}

	public void ensureIsNotVisibleFirstResult() {
		(new WebDriverWait(driver, 30)).until(ExpectedConditions.invisibilityOfElementLocated(By.id("no-results")));
	}

	public void ensureIsVisiblePaginationControl() {
		(new WebDriverWait(driver, 30))
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("pagionation-control")));
	}

	public void ensureIsNotVisiblePaginationControl() {
		(new WebDriverWait(driver, 30))
				.until(ExpectedConditions.invisibilityOfElementLocated(By.id("pagionation-control")));
	}

	/*
	 * public void ensureIsDisplayedName() { (new WebDriverWait(driver,
	 * 30)).until(ExpectedConditions.visibilityOfElementLocated(By.id("name"))); }
	 * 
	 * public void ensureIsNotVisibleSubmitButton() { (new WebDriverWait(driver,
	 * 10)).until(ExpectedConditions.invisibilityOfElementLocated(By.id(
	 * "submit-button"))); }
	 * 
	 */
	public WebElement getNoResultsMessage() {
		return noResultsMessage;
	}
	/*
	 * public WebElement getCategoryP() { return categoryP; }
	 * 
	 * public WebElement getName() { return name; }
	 * 
	 * public WebElement getSubmitButton() { return submitButton; }
	 */

	public List<WebElement> getOfferList() {
		return offerList;
	}

	public WebElement getPaginationControl() {
		return paginationControl;
	}
}
