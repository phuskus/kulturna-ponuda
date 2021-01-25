package ftn.kts.e2e.pages;

import ftn.kts.e2e.components.Table;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DashboardPage {

    private Table table;
    private WebDriver driver;

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
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

    public Table getTable() {
        return table;
    }

    public void setTable(String name){
        this.table = new Table(this.driver,  name);
    }
}
