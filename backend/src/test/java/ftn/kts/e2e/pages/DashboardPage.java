package ftn.kts.e2e.pages;

import ftn.kts.e2e.components.Table;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DashboardPage {

    private Table table;
    private WebDriver driver;

    @FindBy(tagName = "mat-dialog-container")
    private WebElement dialog;

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(String name) {
        this.table = new Table(this.driver, name);
    }

    public WebElement getDialog() {
        return dialog;
    }

    // index 0 for edit, 1 for update if both available
    // else delete is 0
    public void openActionDialog(int rowIndex, int actionIndex) {
        WebElement button = table.getActions(rowIndex)
                .findElements(By.tagName("button")).get(actionIndex);
        button.click();
        ensureDialogIsDisplayed();
    }

    public void openNewItemDialog() {
        table.getNewButton().click();
        ensureDialogIsDisplayed();
    }

    public void writeToDialogInput(String inputName, String value) {
        dialog.findElement(By.name(inputName)).sendKeys(value);
    }

    public void clickConfirmDialog() {
        this.clickDialogButton("button-option-main");
    }

    public void clickCancelDialog() {
        this.clickDialogButton("button-option-alt");
    }

    private void clickDialogButton(String buttonClass) {
        WebElement btn = dialog.findElement(By.className(buttonClass));
        btn.click();
        ensureDialogIsNotDisplayed();
    }

    public void ensureTableIsDisplayed() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions
                .visibilityOfElementLocated(By.id(table.getName() + "-table")));
    }

    public void ensureDialogIsDisplayed() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions
                .visibilityOfElementLocated(By.tagName("mat-dialog-container")));
    }

    public void ensureDialogIsNotDisplayed() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions
                .invisibilityOfElementLocated(By.tagName("mat-dialog-container")));
    }
}
