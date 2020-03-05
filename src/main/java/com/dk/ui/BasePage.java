package com.dk.ui;

import com.dk.selenium.ElementFinder;
import com.dk.selenium.ElementUtils;
import org.openqa.selenium.WebDriver;

/**
 * @author Vivek Lande
 */
public class BasePage {
    protected ElementUtils elementUtils;
    protected ElementFinder elementFinder;

    public BasePage(WebDriver driver) {
        elementUtils = new ElementUtils(driver);
        elementFinder=new ElementFinder(driver);
    }
}
