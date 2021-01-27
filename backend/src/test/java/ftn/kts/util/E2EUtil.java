package ftn.kts.util;

import ftn.kts.e2e.pages.MainPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import ftn.kts.e2e.pages.LoginPage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static ftn.kts.e2e.constants.AppConstants.*;

public class E2EUtil {
    public static void loginUser(WebDriver driver) {
        login(driver, "yahoo@yahoo.com", "12345");
    }

    public static void loginAdmin(WebDriver driver) {
        login(driver, EXISTENT_ADMIN_MAIL, EXISTENT_ADMIN_PASS);
    }

    private static void login(WebDriver driver, String user, String pass) {
        driver.navigate().to(LOGIN_URL);
        LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
        WebElement usernameField = loginPage.getUsernameField();
        usernameField.sendKeys(user);
        WebElement passwordField = loginPage.getPasswordField();
        passwordField.sendKeys(pass);
        ensureLoginBtnClickable(driver);
        WebElement loginButton = loginPage.getLoginButton();
        loginButton.click();
        ensureLoginNotDisplayed(driver);
    }

    public static void ensureLoginBtnClickable(WebDriver driver) {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions
                .elementToBeClickable(By.id("login-btn")));
    }
    public static void ensureLoginNotDisplayed(WebDriver driver) {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions
                .invisibilityOfElementLocated(By.id("login-component")));
    }
}
