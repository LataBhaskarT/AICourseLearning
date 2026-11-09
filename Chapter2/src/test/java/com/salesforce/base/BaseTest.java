package com.salesforce.base;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import com.salesforce.pages.LoginPage;

public class BaseTest {
    protected WebDriver driver;
    protected LoginPage loginPage;
    protected String baseUrl;
    protected String validUsername;
    protected String validPassword;

    @BeforeTest(alwaysRun = true)
    @Parameters({"baseUrl", "validUsername", "validPassword"})
    public void beforeTest(@Optional("https://login.salesforce.com/?locale=in") String baseUrl,
                           @Optional("") String validUsername,
                           @Optional("") String validPassword) {
        try {
            this.baseUrl = baseUrl;
            this.validUsername = resolveValue(validUsername, "validUsername", "sf.valid.username");
            this.validPassword = resolveValue(validPassword, "validPassword", "sf.valid.password");
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize base test configuration", e);
        }
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        try {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            options.addArguments("--disable-notifications");
            driver = new ChromeDriver(options);
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
            loginPage = new LoginPage(driver).navigateTo(baseUrl);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize WebDriver and open login page", e);
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        try {
            if (driver != null) {
                driver.quit();
                driver = null;
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to quit WebDriver", e);
        }
    }

    @AfterTest(alwaysRun = true)
    public void afterTest() {
        try {
            baseUrl = null;
            validUsername = null;
            validPassword = null;
            loginPage = null;
        } catch (Exception e) {
            throw new RuntimeException("Failed to complete base test cleanup", e);
        }
    }

    protected String resolveValue(String parameterValue, String systemPropertyKey, String alternateSystemPropertyKey) {
        try {
            String fromSystem = System.getProperty(systemPropertyKey);
            if (fromSystem != null && !fromSystem.trim().isEmpty()) {
                return fromSystem.trim();
            }
            String fromAlternate = System.getProperty(alternateSystemPropertyKey);
            if (fromAlternate != null && !fromAlternate.trim().isEmpty()) {
                return fromAlternate.trim();
            }
            if (parameterValue != null) {
                return parameterValue.trim();
            }
            return "";
        } catch (Exception e) {
            return parameterValue == null ? "" : parameterValue.trim();
        }
    }
}
