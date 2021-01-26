package ftn.kts.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

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

    @FindBy(tagName = "app-single-post")
    private List<WebElement> posts;
    
    @FindBy(tagName = "app-single-review")
    private List<WebElement> reviews;

    public OfferPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickWriteReview(){
        reviewButton.click();
        ensureDialogIsDisplayed();
    }

    public void confirmReview(){
        this.clickReviewDialogButton("button-option-main");
    }

    public void cancelReview(){
        this.clickReviewDialogButton("button-option-alt");
    }

    private void clickReviewDialogButton(String buttonClass){
        dialog.findElement(By.className(buttonClass)).click();
        ensureDialogIsNotDisplayed();
    }

    public WebElement getTopReview(){
        return reviews.get(0);
    }

    public int getTopReviewRating() {
        int rating = 0;
        List<WebElement> stars = getTopReview().findElements(By.tagName("mat-icon"));
        for(WebElement star : stars){
            if(star.getCssValue("color").equals("grey"))
                rating++;
        }
        return rating;
    }

    public String getTopReviewContent() {
        return getTopReview().findElement(By.className("review-content")).getText();
    }

    public String getTopReviewUser() {
        return getTopReview().findElement(By.className("review-user")).getText();
    }

    public void setNewReviewRating(int rating){
        dialog.findElements(By.tagName("mat-icon")).get(rating - 1).click();
    }

    public void setReviewContent(String content){
        reviewContent.sendKeys(content);
    }

    public int getNumberOfReviews(){
        return driver.findElements(By.tagName("app-single-review")).size();
    }

    public void ensureReviewButtonIsDisplayed() {
        (new WebDriverWait(driver, 2)).until(ExpectedConditions.visibilityOfElementLocated(By.className("review-btn")));
    }

    public void ensureDialogIsDisplayed() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions
                .visibilityOfElementLocated(By.tagName("mat-dialog-container")));
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

	public void ensurePostsAreDisplayed() {
		(new WebDriverWait(driver, 2)).until(ExpectedConditions.visibilityOfElementLocated(By.tagName("app-single-post")));
	}

    public WebElement getSubscribeButton() {
        return subscribeButton;
    }

    public WebElement getUnsubscribeButton() {
        return unsubscribeButton;
    }

    public WebElement getReviewButton() { return reviewButton; }

    public WebElement getDialog() { return dialog; }

	public List<WebElement> getPosts() {
		return posts;
	}

}
