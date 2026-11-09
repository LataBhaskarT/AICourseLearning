package com.salesforce.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.salesforce.base.BaseTest;

public class InvalidLoginTest extends BaseTest {

    @DataProvider(name = "invalidLoginData")
    public Object[][] invalidLoginData() {
        return new Object[][]{
                {"invalid.user@example.com", "WrongPassword123", false},
                {"", "SomePassword123", false},
                {"invalid.user@example.com", "", false},
                {"", "", false},
                {"invalid.user@example.com", "WrongPassword123", true}
        };
    }

    @Test(dataProvider = "invalidLoginData")
    public void verifyInvalidLogin(String username, String password, boolean rememberMe) {
        try {
            Assert.assertTrue(loginPage.isUsernameFieldDisplayed(), "Username field should be displayed");
            Assert.assertTrue(loginPage.isPasswordFieldDisplayed(), "Password field should be displayed");
            Assert.assertTrue(loginPage.isLoginButtonDisplayed(), "Login button should be displayed");
            Assert.assertTrue(loginPage.isRememberMeDisplayed(), "Remember Me option should be displayed");
            if (rememberMe) {
                loginPage.doLoginWithRememberMe(username, password, true);
            } else {
                loginPage.doLogin(username, password);
            }
            Assert.assertTrue(loginPage.waitForErrorToAppear(), "Error message should appear for invalid login");
            Assert.assertTrue(loginPage.isErrorDisplayed(), "Error message should be displayed");
            Assert.assertFalse(loginPage.getErrorMessageText().isEmpty(), "Error message text should not be empty");
            Assert.assertTrue(loginPage.getCurrentUrl().contains("salesforce.com"), "User should remain on Salesforce domain");
            Assert.assertTrue(loginPage.isLoginButtonDisplayed(), "Login button should remain visible");
        } catch (AssertionError e) {
            throw e;
        } catch (Exception e) {
            Assert.fail("Invalid login test execution failed", e);
        }
    }
}
