package com.salesforce.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.salesforce.base.BaseTest;

public class SalesforceInvalidLoginTest extends BaseTest {

    @DataProvider(name = "invalidCredentials")
    public Object[][] invalidCredentials() {
        return new Object[][]{
                {"invalid.user@salesforce.com", "InvalidPassword123"},
                {"", "InvalidPassword123"},
                {"invalid.user@salesforce.com", ""},
                {"", ""}
        };
    }

    @Test(dataProvider = "invalidCredentials")
    public void verifyInvalidLoginScenarios(String username, String password) {
        try {
            loginPage.login(username, password);
            Assert.assertTrue(loginPage.isErrorDisplayed(), "Error message should be displayed for invalid login");
            Assert.assertTrue(loginPage.getErrorMessage().length() > 0, "Error message should contain text");
            Assert.assertTrue(loginPage.getCurrentUrl().contains("salesforce.com"), "User should remain on Salesforce domain after invalid login");
            Assert.assertTrue(loginPage.isLoginButtonDisplayed(), "Login button should remain visible after invalid login");
        } catch (Exception e) {
            Assert.fail("Invalid login verification failed", e);
        }
    }
}
