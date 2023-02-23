package com.exelenter.testcases;

import com.exelenter.base.BaseClass;
import com.exelenter.utils.ConfigsReader;
import org.testng.annotations.Test;


public class AddEmployeeTest extends BaseClass {
    @Test(groups = "smoke")
    void addEmployeeTest() {
        loginPage.loginToWebsite("username","password");
        pimPage.navigateToAddEmployee();
        System.out.println("New employee ID: " + addEmployeePage.employeeId.getAttribute("value"));
        addEmployeePage.addEmployee(ConfigsReader.getProperties("empFirstName"),ConfigsReader.getProperties("empLastName"),ConfigsReader.getProperties("filePath")); // This method will add a new employee


    }

}
