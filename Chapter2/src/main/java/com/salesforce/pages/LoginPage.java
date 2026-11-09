package com.salesforce.pages;

import java.time.Duration;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(xpath = "//input[@id='username']")
    private WebElement username;

    @FindBy(xpath = "//input[@id='password']")
    private WebElement password;

    @FindBy(xpath = "//input[@id='Login']")
    private WebElement loginButton;

    @FindBy(xpath = "//input[@id='rememberUn']")
    private WebElement rememberMe;

    @FindBy(xpath = "//div[@id='error']")
    private WebElement errorMessage;

    @FindBy(xpath = "//label[@for='rememberUn']")
    private WebElement rememberMeLabel;

    public LoginPage(WebDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("WebDriver instance must not be null");
        }
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }

    public LoginPage navigateTo(String url) throws IllegalStateException {
        try {
            driver.get(url);
            wait.until(ExpectedConditions.visibilityOf(username));
            wait.until(ExpectedConditions.visibilityOf(loginButton));
            return this;
        } catch (TimeoutException e) {
            throw new IllegalStateException("Login page failed to load for URL: " + url, e);
        } catch (WebDriverException e) {
            throw new IllegalStateException("Navigation failed for URL: " + url, e);
        }
    }

    public LoginPage enterUsername(String user) throws IllegalStateException {
        try {
            wait.until(ExpectedConditions.visibilityOf(username));
            username.clear();
            if (user != null) {
                username.sendKeys(user);
            }
            return this;
        } catch (Exception e) {
            throw new IllegalStateException("Failed to enter username", e);
        }
    }

    public LoginPage enterPassword(String pass) throws IllegalStateException {
        try {
            wait.until(ExpectedConditions.visibilityOf(password));
            password.clear();
            if (pass != null) {
                password.sendKeys(pass);
            }
            return this;
        } catch (Exception e) {
            throw new IllegalStateException("Failed to enter password", e);
        }
    }

    public LoginPage setRememberMe(boolean select) throws IllegalStateException {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(rememberMe));
            if (rememberMe.isSelected() != select) {
                rememberMe.click();
            }
            return this;
        } catch (Exception e) {
            throw new IllegalStateException("Failed to set Remember Me option", e);
        }
    }

    public void clickLogin() throws IllegalStateException {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(loginButton));
            loginButton.click();
        } catch (Exception e) {
            throw new IllegalStateException("Failed to click Login button", e);
        }
    }

    public void doLogin(String user, String pass) throws IllegalStateException {
        enterUsername(user);
        enterPassword(pass);
        clickLogin();
    }

    public void doLoginWithRememberMe(String user, String pass, boolean remember) throws IllegalStateException {
        enterUsername(user);
        enterPassword(pass);
        setRememberMe(remember);
        clickLogin();
    }

    public boolean isErrorDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(errorMessage));
            return errorMessage.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public String getErrorMessageText() throws IllegalStateException {
        try {
            wait.until(ExpectedConditions.visibilityOf(errorMessage));
            return errorMessage.getText().trim();
        } catch (TimeoutException e) {
            throw new IllegalStateException("Error message not visible within timeout", e);
        }
    }

    public boolean isLoginButtonDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(loginButton));
            return loginButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isUsernameFieldDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(username));
            return username.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPasswordFieldDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(password));
            return password.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isRememberMeDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(rememberMe));
            return rememberMe.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getCurrentUrl() {
        try {
            return driver.getCurrentUrl();
        } catch (WebDriverException e) {
            throw new IllegalStateException("Failed to retrieve current URL", e);
        }
    }

    public void waitForUrlChangeFrom(String urlFragment) throws IllegalStateException {
        try {
            wait.until(ExpectedConditions.not(ExpectedConditions.urlContains(urlFragment)));
        } catch (TimeoutException e) {
            throw new IllegalStateException("URL did not change from fragment: " + urlFragment, e);
        }
    }

    public boolean waitForErrorToAppear() {
        try {
            wait.until(ExpectedConditions.visibilityOf(errorMessage));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}
