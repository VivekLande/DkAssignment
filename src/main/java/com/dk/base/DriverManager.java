package com.dk.base;

import com.dk.utils.CommonUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;

import java.io.File;

/**
 * @author Vivek Lande
 */
public class DriverManager {
    static WebDriver webDriver = null;

    /**
     * Setup chrome driver
     *
     * @return WebDriver
     */
    private static WebDriver initWebDriver() {
        webDriver = null;
        try {
            ChromeOptions options = new ChromeOptions();
            System.setProperty("webdriver.chrome.driver", CommonUtils.getResourcePath("drivers") + File.separator + "chromedriver.exe");
            webDriver = new ChromeDriver(options);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Exception while setting up WebDriver", e);
        }
        return webDriver;
    }

    public static WebDriver getWebDriver() {
        if (webDriver != null)
            return webDriver;
        else
            return initWebDriver();
    }
}
