package com.dk.assignments;

import com.dk.utils.ProjectProperties;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.testng.Assert;

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

    @FindBy(xpath = "//span[text()='Thank you for your purchase.']")
    private WebElement purchaseSuccessMessageForItem;


    public void navigateToShoppingCartPage() {
        elementUtils.clickElementAfterFocus(buyNowButton);
    }

    public void verifyPurchaseSuccessMegDisplayed() {
        try {
            elementUtils.waitForWebElementIsDisplay(purchaseSuccessMessageForItem);
        } catch (NoSuchElementException ex) {
            Assert.fail("'Thank you for your purchase.' msg is not display");
        }
    }

}
