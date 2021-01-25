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

	@FindBy(id = "search-bar")
	private WebElement searchBar;

	@FindBy(id = "no-results")
	private WebElement noResultsMessage;

	@FindBy(id = "back-button")
	private WebElement backButton;

	@FindBy(className = "result-card")
	private List<WebElement> offerList;

	@FindBy(css = "leaflet-marker-icon")
	private List<WebElement> markerList;

	@FindBy(className = "pagination-control")
	private WebElement paginationControl;

	@FindBy(id = "subscribe-button")
	private WebElement subscribeButton;

	@FindBy(id = "unsubscribe-button")
	private WebElement unsubscribeButton;

	public ResultsPage() {
	}

	public ResultsPage(WebDriver driver) {
		this.driver = driver;
	}

	public void ensureIsDisplayedSearchBar() {
		(new WebDriverWait(driver, 30)).until(ExpectedConditions.visibilityOfElementLocated(By.id("search-bar")));
	}

	public void ensureIsNotVisibleSearchBar() {
		(new WebDriverWait(driver, 30)).until(ExpectedConditions.invisibilityOfElementLocated(By.id("search-bar")));
	}

	public void ensureIsVisibleNoResults() {
		(new WebDriverWait(driver, 30)).until(ExpectedConditions.visibilityOfElementLocated(By.id("no-results")));
	}

	public void ensureIsNotVisibleNoResults() {
		(new WebDriverWait(driver, 30)).until(ExpectedConditions.invisibilityOfElementLocated(By.id("no-results")));
	}

	public void ensureIsVisibleMarkerList() {
		(new WebDriverWait(driver, 30))
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("img.leaflet-marker-icon")));
	}

	public void ensureIsNotVisibleMarkerList() {
		(new WebDriverWait(driver, 30))
				.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("img.leaflet-marker-icon")));
	}

	public void ensureIsVisibleOfferList() {
		(new WebDriverWait(driver, 30))
				.until(ExpectedConditions.visibilityOfElementLocated(By.className("result-card")));
	}

	public void ensureIsNotVisibleOfferList() {
		(new WebDriverWait(driver, 30))
				.until(ExpectedConditions.invisibilityOfElementLocated(By.className("result-card")));
	}

	public void ensureIsVisiblePaginationControl() {
		(new WebDriverWait(driver, 30))
				.until(ExpectedConditions.visibilityOfElementLocated(By.className("pagination-control")));
	}

	public void ensureIsNotVisiblePaginationControl() {
		(new WebDriverWait(driver, 30))
				.until(ExpectedConditions.invisibilityOfElementLocated(By.id("pagionation-control")));
	}

	public WebElement getBackButton() {
		return backButton;
	}

	public WebElement getNoResultsMessage() {
		return noResultsMessage;
	}

	public List<WebElement> getOfferList() {
		return offerList;
	}

	public WebElement getPaginationControl() {
		return paginationControl;
	}

	public List<WebElement> getMarkerList() {
		return markerList;
	}

    public WebElement getSubscribeButton() {
		return subscribeButton;
    }

	public WebElement getUnsubscribeButton() {
		return unsubscribeButton;
	}

	public void ensureIsVisibleSubscribeButton() {
		(new WebDriverWait(driver, 2)).until(ExpectedConditions.visibilityOfElementLocated(By.id("subscribe-button")));
	}

	public void ensureIsVisibleUnsubscribeButton() {
		(new WebDriverWait(driver, 2)).until(ExpectedConditions.visibilityOfElementLocated(By.id("unsubscribe-button")));
	}
}
