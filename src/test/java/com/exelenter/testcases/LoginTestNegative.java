package com.exelenter.testcases;

import com.exelenter.base.BaseClass;
import com.exelenter.utils.Constants;
import com.exelenter.utils.ExcelUtility;
import lombok.Data;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


/*
HW: using TestNG DataProvider, write negative test cases for all the following scenarios:

User Story: As an Invalid User, I should not be able to login using invalid login credentials, and if I try, I should see an error message.

Acceptance Criteria:
 1. When a User enters a valid username and an invalid password
    An 'Invalid credentials' error message is presented.
 2. When a User enters an invalid username and a valid password
    An 'Invalid credentials' error message is presented.
 3. When a User enters an invalid username and invalid password
    An 'Invalid credentials' error message is presented.
 4. When a User enters a valid username and an empty password
    A 'Password cannot be empty' error message is displayed.
 5. When a User enters an invalid username and an empty password
    A 'Password cannot be empty' error message is displayed.
 6. When a User enters an empty username and a valid password
    A 'Username cannot be empty' error message is presented.
 7. When a User enters an empty username and an invalid password
    A 'Username cannot be empty' error message is presented.
 8. When a User enters an empty username and an empty password
    A 'Username cannot be empty' error message is presented. (edited)
[10:23 AM]
Make a matrix to make your job easier
 */
public class LoginTestNegative extends BaseClass {
    // Retrieve data using both local DataProvider OR from Excel by changing the name for DataProvider attribute

    @Test(dataProvider = "loginDataFromExcel")
    public void userLogin(String username, String password, String expectedErrorMessage) {
        loginPage.loginToWebsite(username, password);
        Assert.assertEquals(loginPage.loginErrorMessage.getText(), expectedErrorMessage, "Error message is incorrect");
    }

    @DataProvider
    public Object[][]loginDataFromExcel(){
        return ExcelUtility.readFromExcel(Constants.TESTDATA_FILEPATH,"NegativeLoginTest");
    }

    @DataProvider(name = "negativeLogin")
    public Object[][] getData() {
        return new Object[][]{
                {"Admin", "invalidPass", "Invalid credentials"},
                {"admi123", "Exelent2022Sdet!", "Invalid credentials"},
                {"admi123", "invalidPass", "Invalid credentials"},
                {"Admin", "", "Password cannot be empty"},
                {"Admin123", "", "Password cannot be empty"},
                {"", "invalidPass", "Username cannot be empty"},
                {"", "invalidPass", "Username cannot be empty"},
                {"", "", "Username cannot be empty"},
        };
    }


}
