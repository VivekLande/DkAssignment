package com.dk.base;

import com.dk.utils.CommonUtils;
import com.dk.utils.ProjectProperties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
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
            switch (ProjectProperties.getBrowserType()) {
                case "chrome":
                    ChromeOptions chromeOptions = new ChromeOptions();
                    System.setProperty("webdriver.chrome.driver", CommonUtils.getResourcePath("drivers") + File.separator + "chromedriver.exe");
                    webDriver = new ChromeDriver(chromeOptions);
                    break;
                case "firefox":
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    firefoxOptions.addPreference("capability.policy.default.Window.frameElement.get", "allAccess");
                    System.setProperty("webdriver.gecko.driver", CommonUtils.getResourcePath("drivers") + File.separator + "geckodriver.exe");
                    webDriver = new FirefoxDriver(firefoxOptions);
            }
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
