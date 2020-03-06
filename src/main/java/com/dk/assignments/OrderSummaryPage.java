package com.dk.assignments;

import com.dk.utils.ProjectProperties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.testng.asserts.SoftAssert;

/**
 * @author Vivek Lande
 */
public class OrderSummaryPage extends BasePage {

    private WebDriver driver;

    public OrderSummaryPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        //  driver.switchTo().frame("snap-midtrans");
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, Integer.parseInt(ProjectProperties.getRequestTimeOut())), this);
        // driver.switchTo().defaultContent();
    }

    @FindBy(className = "text-amount-amount")
    private WebElement amountValueLabel;

    @FindBy(xpath = "//strong[contains(text(),'Order ID')]//following::div[@class='pull-right']")
    private WebElement orderIdValueLabel;

    @FindBy(xpath = "//span[contains(text(),'shipping details')]")
    private WebElement shippingDetailsTab;

    @FindBy(xpath = "//div[contains(text(),'Name')]/following-sibling::div")
    private WebElement nameValueLabel;

    @FindBy(xpath = "//div[contains(text(),'Address')]/following-sibling::div")
    private WebElement addressValueLabel;

    @FindBy(xpath = "//div[contains(text(),'Phone number')]/following-sibling::div")
    private WebElement phoneNoValueLabel;

    @FindBy(xpath = "//div[contains(text(),'Email')]/following-sibling::div")
    private WebElement emailValueLabel;

    @FindBy(xpath = "//a[@class='button-main-content']")
    private WebElement continueButton;

    public void verifyOrderSummary() {
        driver.switchTo().frame("snap-midtrans");

        SoftAssert softAssert = new SoftAssert();
        System.out.println("Verify amount value");
        softAssert.assertEquals(amountValueLabel.getText().trim().replaceAll(",", ""), "20000", "Order amount doesn't matched");

        System.out.println("Verify oder id should not be blank");
        softAssert.assertNotEquals(orderIdValueLabel.getText(), "", "Order id is not assigned");
        softAssert.assertAll();

        driver.switchTo().defaultContent();
        verifyShippingDetails();
        navigateToSelectPaymentPage();
    }

    public void navigateToSelectPaymentPage() {
        driver.switchTo().frame("snap-midtrans");
        elementUtils.clickElementAfterFocus(continueButton);
        elementUtils.waitForWebElementIsDisplay(elementFinder.findElementByXpath("//p[contains(text(),'Select Payment')]"));
        driver.switchTo().defaultContent();
    }

    public void verifyShippingDetails() {
        driver.switchTo().frame("snap-midtrans");
        elementUtils.clickElementAfterFocus(shippingDetailsTab);

        SoftAssert softAssert = new SoftAssert();
        System.out.println("Verify Name");
        softAssert.assertEquals(nameValueLabel.getText().trim(), "Budi", "Name doesn't matched");

        System.out.println("Verify Address");
        softAssert.assertEquals(addressValueLabel.getText().trim(), "MidPlaza 2, 4th Floor Jl.Jend.Sudirman Kav.10-11 Jakarta 10220", "Address doesn't matched");

        System.out.println("Verify phone number");
        softAssert.assertEquals(phoneNoValueLabel.getText().trim(), "+6281808466410", "Phone number doesn't matched");

        System.out.println("Verify Email");
        softAssert.assertEquals(emailValueLabel.getText().trim(), "budi@utomo.com", "Email doesn't matched");

        softAssert.assertAll();
        driver.switchTo().defaultContent();
    }
}
