package com.exelenter.base;

import com.exelenter.pages.*;


public class PageInitializer {

    public static LoginPage loginPage;
    public static DashboardPage dashboardPage;
    public static PIMPage pimPage;
    public static AddEmployeePage addEmployeePage;

    public static PersonalDetailsPage personalDetailsPage;


    public static void initialize() {
        loginPage = new LoginPage();
        dashboardPage = new DashboardPage();
        pimPage = new PIMPage();
        addEmployeePage = new AddEmployeePage();
        personalDetailsPage = new PersonalDetailsPage();

    }
}
