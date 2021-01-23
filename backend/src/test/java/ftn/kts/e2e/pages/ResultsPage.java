package ftn.kts.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ResultsPage {

    private WebDriver driver;

    @FindBy(xpath = "//*[@id=\"category-h3\"]")
    private WebElement categoryP;

    @FindBy(xpath = "//*[@id=\"name\"]")
    private WebElement name;

    @FindBy(xpath = "//*[@id=\"submit-button\"]")
    private WebElement submitButton;

    @FindBy(xpath = "//*[@id=\"offer-1\"]")
    private WebElement offerButton;

    public ResultsPage() {
    }

    public ResultsPage(WebDriver driver) {
        this.driver = driver;
    }

    public void ensureIsDisplayedName() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));
    }

    public void ensureIsNotVisibleSubmitButton() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.invisibilityOfElementLocated(By.id("submit-button")));
    }

    public WebElement getCategoryP() {
        return categoryP;
    }

    public WebElement getName() {
        return name;
    }

    public WebElement getSubmitButton() {
        return submitButton;
    }
}
