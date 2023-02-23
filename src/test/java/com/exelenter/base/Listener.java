package com.exelenter.base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.exelenter.utils.CommonMethods;
import com.exelenter.utils.Constants;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class Listener implements ITestListener {
    ExtentSparkReporter reporter;
    ExtentReports reports;
    ExtentTest test;

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("Test Started: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("Test Passed: " + result.getName());

        test = reports.createTest(result.getName());
        test.pass(" Test Case Passed: " + result.getName());

        // Optionally you can capture a screenshot for each success test case(Not recommended!).
        // Only failed cases are recommended to avoid too many images( if 500 test cases for example)
        test.addScreenCaptureFromPath(CommonMethods.takeScreenshot("PASS/" + result.getName()));

        test.log(Status.PASS,"Test Passed. This is coming from the Log status");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("Test Failed: " + result.getName());

        test = reports.createTest(result.getName());
        test.fail("Test Case Failed: " + result.getName());
        test.addScreenCaptureFromPath(CommonMethods.takeScreenshot("FAIL/" + result.getName()));
        // code you can take a screenshot or do something else
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("Test Skipped: " + result.getName());
        test = reports.createTest(result.getName());
        test.skip("Test Case Skipped: " + result.getName());
    }

    @Override
    public void onStart(ITestContext context) {
        System.out.println("===== Test Started ==== " + context.getName() + " | " + context.getStartDate());

        // Adding Reports(start tracking & recording when test starts). ExtentReports Library is required.
        // ExtentHTMLReporter <=== Deprecated, no longer supported after   or above
        reporter = new ExtentSparkReporter(Constants.REPORT_FILEPATH);
        reporter.config().setTheme(Theme.DARK);
        reporter.config().setDocumentTitle("Exelenter Test Report");  //This will show on the browser Tab, like page Title (driver.getTitle()
        reporter.config().setReportName("Exelenter Project Test report");  // This will show in the top right corner of report Dashboard.
        reports = new ExtentReports();
        reports.attachReporter(reporter);


    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("\n********************************************\n=== End of Test === " + context.getName() + " |  " + context.getEndDate());
        reports.flush();        // Erases previous (old) data and creates new one.
    }

}

