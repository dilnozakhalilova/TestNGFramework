package com.exelenter.base;

// NOTE: THIS CLASS IS USED TO LAUNCH AND QUITE THE BROWSER


import com.exelenter.utils.CommonMethods;
import com.exelenter.utils.ConfigsReader;
import com.exelenter.utils.Constants;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;


public class BaseClass extends CommonMethods {
    public static WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public static void setUp() {
        System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY,"true");
        ConfigsReader.loadProperties(Constants.CONFIGURATION_FILEPATH); // Replaced hard-coded filePath with Constants
        String headless = ConfigsReader.getProperties("headless");

        switch (ConfigsReader.getProperties("browser").toLowerCase()) {
            case "chrome" -> {
                System.setProperty("webdriver.chrome.driver", Constants.CHROME_DRIVER_PATH);
                if (headless.equalsIgnoreCase("true")) {   // in ConfigsReader class
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments("--headless");     // <== Run in headless mode(suitable for regression)
                    driver = new ChromeDriver(options);
                } else {
                    driver = new ChromeDriver();
                }
            }
            case "firefox" -> {
                System.setProperty("webdriver.gecko.driver", Constants.GECKO_DRIVER_PATH);
                if(headless.equalsIgnoreCase("true")){
                    FirefoxOptions options = new FirefoxOptions();
                    options.addArguments("--headless");
                    driver=new FirefoxDriver(options);
                }else{
                driver = new FirefoxDriver();
            }
            }
            case "edge" -> {
                System.setProperty("webdriver.edge.driver", Constants.EDGE_DRIVER_PATH);
                if (headless.equalsIgnoreCase("true")) {
                    EdgeOptions options = new EdgeOptions();
                    options.addArguments("--headless");
                    driver = new EdgeDriver(options);
                } else {
                    driver = new EdgeDriver();              // <== if headless=false this line will run
                }
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