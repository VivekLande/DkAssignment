package com.dk.assignments;

import com.dk.utils.ProjectProperties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

/**
 * @author Vivek Lande
 */
public class SelectPaymentPage extends BasePage {
    private WebDriver driver;

    public SelectPaymentPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, Integer.parseInt(ProjectProperties.getRequestTimeOut())), this);
    }

    @FindBy(xpath = "//div[text()='Credit Card' ]")
    private WebElement creditCardLink;

    public void navigateToCreditCardPage() {
        driver.switchTo().frame("snap-midtrans");
        elementUtils.clickElementAfterFocus(creditCardLink);
        elementUtils.waitForWebElementIsDisplay(elementFinder.findElementByName("cardnumber"));
        driver.switchTo().defaultContent();
    }


}
