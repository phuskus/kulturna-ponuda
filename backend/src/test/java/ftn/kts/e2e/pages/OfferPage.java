package ftn.kts.e2e.pages;

import ftn.kts.dto.ReviewDTO;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static ftn.kts.e2e.constants.AppConstants.REVIEW_STAR_NO_COLOR;

public class OfferPage {
    private WebDriver driver;

    @FindBy(id = "subscribe-button")
    private WebElement subscribeButton;

    @FindBy(id = "unsubscribe-button")
    private WebElement unsubscribeButton;

    @FindBy(className = "review-btn")
    private WebElement reviewButton;

    @FindBy(tagName = "mat-dialog-container")
    private WebElement dialog;

    @FindBy(tagName = "app-star-rating")
    private WebElement starRating;

    @FindBy(tagName = "textArea")
    private WebElement reviewContent;

    public OfferPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickWriteReview() {
        reviewButton.click();
        ensureDialogIsDisplayed();
    }

    public void confirmReview() {
        this.clickReviewDialogButton("button-option-main");
    }

    public void cancelReview() {
        this.clickReviewDialogButton("button-option-alt");
    }

    private void clickReviewDialogButton(String buttonClass) {
        dialog.findElement(By.className(buttonClass)).click();
        ensureDialogIsNotDisplayed();
    }

    public WebElement getReview(int index) {
        WebElement element = driver.findElements(By.tagName("app-single-review")).get(index);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        return element;
    }

    public WebElement getTopReview() {
        return getReview(0);
    }

    public int getReviewRating(WebElement review) {
        int rating = 0;
        List<WebElement> stars = review.findElements(By.tagName("mat-icon"));
        for (WebElement star : stars) {
            if (star.getCssValue("color").equals(REVIEW_STAR_NO_COLOR))
                break;
            rating++;
        }
        return rating;
    }

    public String getReviewContent(WebElement review) {
        return review.findElement(By.className("review-content")).getText();
    }

    public String getReviewUser(WebElement review) {
        return review.findElement(By.cssSelector(".review-data span")).getText();
    }

    public void setNewReviewRating(int rating) {
        dialog.findElements(By.tagName("mat-icon")).get(rating - 1).click();
    }

    public void setReviewContent(String content) {
        reviewContent.sendKeys(content);
    }

    public int getNumberOfReviews() {
        return driver.findElements(By.tagName("app-single-review")).size();
    }

    public int getNumberOfPicturesForReview(WebElement review){
        return review.findElements(By.tagName("img")).size();
    }

    public void uploadImage(String path){
        WebElement inputFile = dialog.findElement(By.id("image-upload-input"));
        inputFile.sendKeys(path);
    }

    public void ensureReviewButtonIsDisplayed() {
        (new WebDriverWait(driver, 2)).until(ExpectedConditions.visibilityOfElementLocated(By.className("review-btn")));
    }

    public void ensureDialogIsDisplayed() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions
                .visibilityOfElementLocated(By.tagName("mat-dialog-container")));
    }

    public void ensureOfferContentIsDisplayed() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions
                .visibilityOfElementLocated(By.id("main-content")));
    }

    public void ensureDialogIsNotDisplayed() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions
                .invisibilityOfElementLocated(By.tagName("mat-dialog-container")));
    }

    public void ensureIsVisibleSubscribeButton() {
        (new WebDriverWait(driver, 2)).until(ExpectedConditions.visibilityOfElementLocated(By.id("subscribe-button")));
    }

    public void ensureIsVisibleUnsubscribeButton() {
        (new WebDriverWait(driver, 2)).until(ExpectedConditions.visibilityOfElementLocated(By.id("unsubscribe-button")));
    }

    public WebElement getSubscribeButton() {
        return subscribeButton;
    }

    public WebElement getUnsubscribeButton() {
        return unsubscribeButton;
    }

    public WebElement getReviewButton() {
        return reviewButton;
    }

    public WebElement getDialog() {
        return dialog;
    }
}
