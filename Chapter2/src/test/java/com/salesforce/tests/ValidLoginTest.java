package com.salesforce.tests;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;
import com.salesforce.base.BaseTest;

public class ValidLoginTest extends BaseTest {

    @Test
    public void verifyValidLogin() {
        try {
            if (validUsername == null || validUsername.trim().isEmpty() || validPassword == null || validPassword.trim().isEmpty()) {
                throw new SkipException("Valid credentials not provided. Pass -DvalidUsername and -DvalidPassword or TestNG parameters.");
            }
            Assert.assertTrue(loginPage.isUsernameFieldDisplayed(), "Username field should be displayed");
            Assert.assertTrue(loginPage.isPasswordFieldDisplayed(), "Password field should be displayed");
            Assert.assertTrue(loginPage.isRememberMeDisplayed(), "Remember Me option should be displayed");
            loginPage.doLoginWithRememberMe(validUsername, validPassword, true);
            loginPage.waitForUrlChangeFrom("/?locale=in");
            String currentUrl = loginPage.getCurrentUrl();
            Assert.assertFalse(currentUrl.contains("login.salesforce.com/?locale=in"), "User should navigate away from the login URL");
            Assert.assertTrue(currentUrl.contains("salesforce.com"), "User should remain on Salesforce domain after successful login");
        } catch (SkipException e) {
            throw e;
        } catch (AssertionError e) {
            throw e;
        } catch (Exception e) {
            Assert.fail("Valid login test execution failed", e);
        }
    }
}
