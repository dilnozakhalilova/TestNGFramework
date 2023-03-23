package com.exelenter.testcases;


import com.exelenter.base.BaseClass;
import com.exelenter.utils.ConfigsReader;
import org.testng.Assert;
import org.testng.annotations.Test;


/**
 * User stories
 * US 16457: As an Admin user I should be able to login to the admin page using valid login credentials.
 * US 23869: As a User, I should be able to login to the dashbaord page using valid login credentials.
 * US 83764: As a Business User, here goes your request...
 */

public class LoginTest extends BaseClass {

    @Test(groups = "smoke")
    public void validAdminLogin() {
        // Happy path
        loginPage.loginToWebsite(ConfigsReader.getProperties("username"), ConfigsReader.getProperties("password"));
        String expectedText = "Welcome Admin";
        String actualText = dashboardPage.welcome.getText();
        Assert.assertEquals(expectedText, actualText, "'Welcome Admin' text is incorrect");
    }

    @Test(groups = "smoke", dependsOnMethods = "validUserAnEmptyPassword")
    public void validUserInvalidPassword() {
        String invalidPassword = "Pass1234";
        String expectedErrorMessage = "Invalid credentials";
        // Negative testing
        sendText(loginPage.username, ConfigsReader.getProperties("username"));    // Valid username
        sendText(loginPage.password, invalidPassword);                                // Invalid password
        clickButWaitForClickability(loginPage.loginBtn);
        Assert.assertEquals(loginPage.loginErrorMessage.getText(), expectedErrorMessage, "Error message is incorrect. Test failed.");
    }

    @Test(groups = "smoke")
    public void validUserAnEmptyPassword() {
        String expectedErrorMessage = "Password cannot be empty";
        sendText(loginPage.username, ConfigsReader.getProperties("username"));   // Valid username, Password empty(skipped)
        clickButWaitForClickability(loginPage.loginBtn);
        //Assert.fail();  // < I am intentionally failing this test for the report.
        Assert.assertEquals(loginPage.loginErrorMessage.getText(), expectedErrorMessage, "Error message is incorrect. Test failed.");
    }


@Test
public void printHi(){
    System.out.println("Hi");
}
}
