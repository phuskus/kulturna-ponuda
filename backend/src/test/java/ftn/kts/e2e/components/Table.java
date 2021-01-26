package ftn.kts.e2e.components;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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
        WebElement table = findTable();
        String length = findTable()
                .findElement(By.className("mat-paginator-range-label"))
                .getText();
        String[] tokens = length.split(" ");
        return Integer.valueOf(tokens[tokens.length - 1]);
    }

    public WebElement getSearchField(){
        return findTable().findElement(By.className("search-field"))
                .findElement(By.tagName("input"));
    }

    public void writeToSearch(String query) {
        getSearchField().sendKeys(query);

        // explicitly wait for request to be sent
        sleep(2000);
    }

    public void clearSearch() {
        WebElement search = getSearchField();
        search.clear();
        search.sendKeys(Keys.BACK_SPACE);
        // explicitly wait for request to be sent
        sleep(2000);
    }

    public WebElement getNewButton() {
        return findTable().findElement(By.cssSelector(".new-item-btn"));
    }

    public WebElement getRow(int index) {
        return findTable().findElements(By.cssSelector("tbody tr")).get(index);
    }

    public String getRowColumnText(int rowIndex, int columnIndex) {
        return getRow(rowIndex)
                .findElements(By.tagName("td")).get(columnIndex).getText();
    }

    public List<WebElement> getAllRows() {
        return findTable().findElements(By.cssSelector("tbody tr"));
    }

    public WebElement getActions(int rowIndex) {
        List<WebElement> tds = getRow(rowIndex).findElements(By.tagName("td"));
        return tds.get(tds.size() - 1);
    }

    private void sleep(int milis) {
        try {
            Thread.sleep(milis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
