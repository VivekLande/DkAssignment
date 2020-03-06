package com.dk;

import com.dk.base.DriverManager;
import com.dk.utils.ProjectProperties;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

/**
 * @author Vivek Lande
 */
public class BaseTest {
    protected WebDriver driver;

    @BeforeSuite
    public void beforeSuite() {
        ProjectProperties.initProjectProperties();
    }

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("Setting up driver");
        driver = DriverManager.getWebDriver();
        driver.get("https://demo.midtrans.com");
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void afterMethod() {
        driver.quit();
    }

}
