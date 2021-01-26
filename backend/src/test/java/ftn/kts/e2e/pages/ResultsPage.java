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

	
	@FindBy(id = "sort-button")
	private WebElement sortButton;

	@FindBy(css = "mat-radio-button")
	private List<WebElement> sortOptions;

	@FindBy(id = "sort-confirm")
	private WebElement sortConfirm;
	
	@FindBy(id = "sort-direction-button")
	private WebElement sortDirectionButton;
	
	
	@FindBy(id = "filter-button")
	private WebElement filterButton;

	@FindBy(css = "mat-checkbox")
	private List<WebElement> filterOptions;

	@FindBy(id = "filter-confirm")
	private WebElement filterConfirm;
	
	
	@FindBy(className = "result-card")
	private List<WebElement> offerList;
	
	@FindBy(css = "div[name='result-location']")
	private List<WebElement> locationList;

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

	public WebElement getSearchBar() {
		return searchBar;
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

	public WebElement getSortButton() {
		return sortButton;
	}

	public WebElement getFilterButton() {
		return filterButton;
	}

	public List<WebElement> getSortOptions() {
		return sortOptions;
	}

	public WebElement getSortConfirm() {
		return sortConfirm;
	}

	public List<WebElement> getFilterOptions() {
		return filterOptions;
	}

	public WebElement getFilterConfirm() {
		return filterConfirm;
	}

	public List<WebElement> getLocationList() {
		return locationList;
	}

	public WebElement getSortDirectionButton() {
		return sortDirectionButton;
	}

	public void ensureAreVisibleSortOptions() {
		(new WebDriverWait(driver, 2)).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("mat-radio-button")));
	}

	public void ensureAreVisibleFilterOptions() {
		(new WebDriverWait(driver, 2)).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("mat-checkbox")));
	}
	
	public void ensureIsVisibleSubscribeButton() {
		(new WebDriverWait(driver, 2)).until(ExpectedConditions.visibilityOfElementLocated(By.id("subscribe-button")));
	}

	public void ensureIsVisibleUnsubscribeButton() {
		(new WebDriverWait(driver, 2)).until(ExpectedConditions.visibilityOfElementLocated(By.id("unsubscribe-button")));
	}

	public void ensureIsDisplayedFilterApply() {
		(new WebDriverWait(driver, 2)).until(ExpectedConditions.visibilityOfElementLocated(By.id("filter-confirm")));
	}

	public void ensureIsNotDisplayedFilterApply() {
		(new WebDriverWait(driver, 2)).until(ExpectedConditions.invisibilityOfElementLocated(By.id("filter-confirm")));
	}

	public void ensureIsDisplayedSortApply() {
		(new WebDriverWait(driver, 2)).until(ExpectedConditions.visibilityOfElementLocated(By.id("sort-confirm")));
	}

	public void ensureIsNotDisplayedSortApply() {
		(new WebDriverWait(driver, 2)).until(ExpectedConditions.invisibilityOfElementLocated(By.id("sort-confirm")));
	}
}
