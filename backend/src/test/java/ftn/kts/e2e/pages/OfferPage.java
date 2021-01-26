package ftn.kts.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OfferPage {
    private WebDriver driver;

    @FindBy(id = "subscribe-button")
    private WebElement subscribeButton;

    @FindBy(id = "unsubscribe-button")
    private WebElement unsubscribeButton;

    public OfferPage() {}
    public OfferPage(WebDriver driver) {
        this.driver = driver;
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
