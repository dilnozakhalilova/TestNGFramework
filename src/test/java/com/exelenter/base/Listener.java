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

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class Listener implements ITestListener {
    ExtentSparkReporter reporter;
    ExtentReports reports;
    ExtentTest test;
    Instant startTime;
    Instant endTime;

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

        test.log(Status.PASS, "Test Passed. This is coming from the Log status");
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
        startTime = Instant.now();


    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("\n********************************************\n=== End of Test === " + context.getName() + " |  " + context.getEndDate());
        reports.flush();        // Erases previous (old) data and creates new one.
        Instant endTime = Instant.now();
        Duration totalTime = Duration.between(startTime, endTime);
        int milliseconds = totalTime.getNano()/1_000_000;

       long seconds = TimeUnit.MILLISECONDS.toSeconds(totalTime.toMillis());
        long minutes = TimeUnit.MILLISECONDS.toMinutes(totalTime.toMillis());
        long hours = TimeUnit.MILLISECONDS.toHours(totalTime.toMillis());
        long days = TimeUnit.MILLISECONDS.toDays(totalTime.toMillis());

        // long seconds = totalTime.toSeconds();   <== if you use this one, it will NOT auto wrap after each 60 seconds, instead prints total.
        //long minutes = totalTime.toMinutes();
        //long hours = totalTime.toHours();
        //long days = totalTime.toDays();


        System.out.println("\nTotal Test Completion Time: \nDays: " + days +
                "\nHours: " + hours +
                "\nMinutes: " + minutes +
                "\nSeconds: " + seconds +
                "\nMilliseconds: " + milliseconds);

        System.out.printf("Total Test Completion Time: %d Minutes %d Seconds, and %3d Milliseconds", minutes, seconds, milliseconds);
    }

}

