package com.dk.ui;

import com.dk.utils.ProjectProperties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import java.util.HashMap;

/**
 * @author Vivek Lande
 */
public class CreditCardPage extends BasePage {
    private WebDriver driver;

    public CreditCardPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        // driver.switchTo().frame("snap-midtrans");
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, Integer.parseInt(ProjectProperties.getRequestTimeOut())), this);
        // driver.switchTo().defaultContent();
    }

    @FindBy(className = "text-amount-amount")
    private WebElement amountValueLabel;

    @FindBy(name = "cardnumber")
    private WebElement cardNumberTextBox;

    @FindBy(xpath = "//input[@placeholder=\"MM / YY\"]")
    private WebElement expiryDateTextBox;

    @FindBy(xpath = "//input[@inputmode=\"numeric\"]")
    private WebElement cvvTextBox;

    @FindBy(name = "promo")
    private WebElement promoCheckBox;

    @FindBy(xpath = "//a[@class='button-main-content']")
    private WebElement payNowButton;

    public void fillCreditCardDetails(HashMap<Object, Object> data) {
        driver.switchTo().frame("snap-midtrans");

        System.out.println("Enter card number");
        elementUtils.sendKeys(cardNumberTextBox, data.get("cardNumber").toString(), false);

        System.out.println("Enter card expiry date");
        elementUtils.sendKeys(expiryDateTextBox, data.get("cardExpiryDate").toString(), false);

        System.out.println("Enter card CVV number");
        elementUtils.sendKeys(cvvTextBox, data.get("cardCvv").toString(), false);

        elementUtils.clickElementAfterFocus(payNowButton);
        driver.switchTo().defaultContent();
    }

    public void navigateToPayNowPage() {
        driver.switchTo().frame("snap-midtrans");
        elementUtils.clickElementAfterFocus(payNowButton);
        driver.switchTo().defaultContent();
    }
}
