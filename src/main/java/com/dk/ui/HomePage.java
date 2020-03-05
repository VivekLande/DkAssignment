package com.dk.ui;

import com.dk.utils.ProjectProperties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

/**
 * @author Vivek Lande
 */
public class HomePage extends BasePage {
    private WebDriver driver;

    public HomePage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, Integer.parseInt(ProjectProperties.getRequestTimeOut())), this);
    }

    @FindBy(partialLinkText = "SIGN UP")
    private WebElement signUpButton;

    @FindBy(linkText = "BUY NOW")
    private WebElement buyNowButton;


    public void navigateToShoppingCartPage() {
        elementUtils.clickElementAfterFocus(buyNowButton);
    }
}
