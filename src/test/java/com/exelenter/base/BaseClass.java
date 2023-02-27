package com.exelenter.base;

// NOTE: THIS CLASS IS USED TO LAUNCH AND QUITE THE BROWSER


import com.exelenter.utils.CommonMethods;
import com.exelenter.utils.ConfigsReader;
import com.exelenter.utils.Constants;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;


public class BaseClass extends CommonMethods {
    public static WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public static void setUp() {
        ConfigsReader.loadProperties(Constants.CONFIGURATION_FILEPATH); // Replaced hard-coded filePath with Constants
        switch (ConfigsReader.getProperties("browser").toLowerCase()) {
            case "chrome" -> {
                System.setProperty("webdriver.chrome.driver", Constants.CHROME_DRIVER_PATH);
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--headless");     // <== Run in headless mode(suitable for regression)
                driver = new ChromeDriver(options);
             // driver = new ChromeDriver();
            }

            case "firefox" -> {
                System.setProperty("webdriver.gecko.driver", Constants.GECKO_DRIVER_PATH);
                driver = new FirefoxDriver();
            }
            default -> throw new RuntimeException("Browser is not supported");
        }
        driver.get(ConfigsReader.getProperties("url"));
        driver.manage().window().maximize();
        // driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Constants.IMPLICIT_WAIT_TIME));
        initialize();
    }

    @AfterMethod(alwaysRun = true)
    public static void tearDown() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.getStackTrace();
        }
        if (driver != null) {     // This line is optional. We only use it to prevent NullPointerException.
            driver.quit();
        }

    }


}