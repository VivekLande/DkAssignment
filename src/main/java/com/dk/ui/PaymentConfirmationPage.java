package com.dk.ui;

import com.dk.utils.ProjectProperties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.testng.Assert;

import java.sql.SQLOutput;
import java.util.Map;

/**
 * @author Vivek Lande
 */
public class PaymentConfirmationPage extends BasePage {
    private WebDriver driver;

    public PaymentConfirmationPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, Integer.parseInt(ProjectProperties.getRequestTimeOut())), this);
    }

    @FindBy(id = "merchant_name")
    private WebElement merchantNameLabel;

    @FindBy(id = "txn_amount")
    private WebElement amountLabel;

    @FindBy(id = "card_number")
    private WebElement cardNumberLabel;

    @FindBy(id = "PaRes")
    private WebElement passwordTextBox;

    @FindBy(name = "ok")
    private WebElement okButton;

    public void verifyPaymentDetails(Map<Object, Object> data) {
        driver.switchTo().frame("snap-midtrans");
        elementUtils.waitForWebElementIsDisplay(elementFinder.findElementByXpath("//iframe[contains(@src,'https://api.sandbox.veritrans.co.id/v2/token/rba/redirect')]"));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.switchTo().frame(elementFinder.findElementByXpath("//iframe[contains(@src,'https://api.sandbox.veritrans.co.id/v2/token/rba/redirect')]"));
        System.out.println("Verify Merchant name");
        Assert.assertEquals(merchantNameLabel.getText().trim(), "Sample Store");

        System.out.println("Verify Amount");
        Assert.assertEquals(amountLabel.getText().trim(), "19000.00", "amount doesn't matched");

        System.out.println("Verify card number");
        Assert.assertEquals(cardNumberLabel.getText().trim().replaceAll("-", ""), data.get("cardNumber"), "card number doesn't matched");
        driver.switchTo().defaultContent();
        driver.switchTo().defaultContent();
    }

    public void enterPasswordAndConfirmPayment(Map<Object, Object> data) {
        driver.switchTo().frame("snap-midtrans");
        elementUtils.waitForWebElementIsDisplay(elementFinder.findElementByXpath("//iframe[contains(@src,'https://api.sandbox.veritrans.co.id/v2/token/rba/redirect')]"));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.switchTo().frame(elementFinder.findElementByXpath("//iframe[contains(@src,'https://api.sandbox.veritrans.co.id/v2/token/rba/redirect')]"));
        elementUtils.sendKeys(passwordTextBox, data.get("bankOTP").toString(), false);
        elementUtils.clickElementAfterFocus(okButton);
        driver.switchTo().defaultContent();
        driver.switchTo().defaultContent();
    }

    public void verifyPaymentSuccessfullyDone() {
        driver.switchTo().frame("snap-midtrans");
        driver.switchTo().frame(elementFinder.findElementByXpath("//iframe[contains(@src,'https://api.sandbox.veritrans.co.id/v2/token/rba/redirect')]"));

        driver.switchTo().defaultContent();
        driver.switchTo().defaultContent();
    }
}
