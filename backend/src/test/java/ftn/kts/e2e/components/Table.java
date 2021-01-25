package ftn.kts.e2e.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Table {

    private String name;

    private WebDriver driver;

    public Table(WebDriver driver, String name) {
        this.name = name;
        this.driver = driver;
    }

    public String getName() {
        return name;
    }

    private WebElement findTable() {
        return driver.findElement(By.id(name + "-table"));
    }

    public int getTotalElements() {
        String length = findTable().findElement(By.tagName("mat-paginator"))
                .getAttribute("ng-reflect-length");
        return Integer.valueOf(length);
    }

    public void writeToSearch(String query) {
        findTable().findElement(By.tagName("input")).sendKeys(query);

        // explicitly wait for request to be sent
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

    public void getNewButton() {
        findTable().findElement(By.cssSelector(".new-item-btn"));
    }

    public List<WebElement> getRows() {
        return findTable().findElements(By.cssSelector("tbody tr"));
    }

    public WebElement getActions(int rowIndex) {
        List<WebElement> rows = getRows();
        List<WebElement> tds = rows.get(rowIndex).findElements(By.tagName("td"));
        return tds.get(tds.size() - 1);
    }

    public WebElement getDialog() {
        return driver.findElement(By.className("mat-dialog-container"));
    }

    public WebElement getConfirmDialog() {
        return getDialog().findElement(By.className("button-option-main"));
    }

    public WebElement getCancelDialog() {
        return getDialog().findElement(By.className("button-option-alt"));
    }
}
