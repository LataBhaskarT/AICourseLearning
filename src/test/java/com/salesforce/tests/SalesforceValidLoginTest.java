package com.salesforce.tests;

import org.testng.Assert;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.salesforce.base.BaseTest;

public class SalesforceValidLoginTest extends BaseTest {

    @Test
    @Parameters({"validUsername", "validPassword"})
    public void verifyValidLogin(@Optional("valid.user@salesforce.com") String validUsername,
                                 @Optional("ValidPassword123") String validPassword) {
        try {
            String username = System.getProperty("sf.valid.username", validUsername);
            String password = System.getProperty("sf.valid.password", validPassword);
            loginPage.enterUsername(username).enterPassword(password).selectRememberMe().clickLogin();
            loginPage.waitUntilUrlNotContains("/?locale=in");
            String currentUrl = loginPage.getCurrentUrl();
            Assert.assertFalse(currentUrl.contains("login.salesforce.com/?locale=in"), "User should navigate away from login URL after valid login");
            Assert.assertTrue(currentUrl.contains("salesforce.com"), "User should remain within Salesforce domain after login");
        } catch (Exception e) {
            Assert.fail("Valid login verification failed", e);
        }
    }
}
