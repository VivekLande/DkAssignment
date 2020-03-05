package com.dk.ui;

import com.dk.utils.ProjectProperties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.testng.Assert;

/**
 * @author Vivek Lande
 */
public class ShoppingCartPage extends BasePage {
    private WebDriver driver;

    public ShoppingCartPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, Integer.parseInt(ProjectProperties.getRequestTimeOut())), this);
    }

    @FindBy(css = ".label.label-primary")
    private WebElement cartItemLabel;

    @FindBy(className = "text-right")
    private WebElement pillowPriceTextBox;

    @FindBy(xpath = "//td[@class='amount']")
    private WebElement totalValueLabel;

    @FindBy(xpath = "//td[contains(text(),'Name')]//following::td[1]")
    private WebElement nameTextBox;

    @FindBy(xpath = "//input[@type='email']")
    private WebElement emailTextBox;

    @FindBy(xpath = "//td[contains(text(),'Phone no')]//following::td[1]")
    private WebElement phoneNoTextBox;

    @FindBy(xpath = "//td[contains(text(),'City')]//following::td[1]")
    private WebElement cityTextBox;

    @FindBy(xpath = "//td[contains(text(),'Address')]//following::td[1]")
    private WebElement addressTextBox;

    @FindBy(xpath = "//td[contains(text(),'Postal Code')]//following::td[1]")
    private WebElement postalCodeTextBox;

    @FindBy(className = "cart-checkout")
    private WebElement cartCheckoutButton;

    public void fillupShoppingCartDetailsAndCheckOut() {
        elementUtils.waitForWebElementIsEnabled(cartCheckoutButton);
        int totalItemInCart = Integer.parseInt(cartItemLabel.getText().trim());
        System.out.println("Cart have total items: " + totalItemInCart);
        /*System.out.println("change pillow price to 1000 and verify total price");
        elementUtils.sendKeys(pillowPriceTextBox, "1000", true);*/

        System.out.println("Verify total price is accordingly cart item");
        Assert.assertEquals(totalValueLabel.getText().trim().replaceAll(",", ""), String.valueOf(totalItemInCart * 20000), "Total cart value is not accordingly cart items");

       /* elementUtils.sendKeys(nameTextBox, "Vivek", false);
        elementUtils.sendKeys(emailTextBox, "viveklande33@gmail.com", true);
        elementUtils.sendKeys(phoneNoTextBox, "1111111111", true);
        elementUtils.sendKeys(cityTextBox, "Pune", true);
        elementUtils.sendKeys(addressTextBox, "Yerwada", true);
        elementUtils.sendKeys(postalCodeTextBox, "411006", true);*/

        elementUtils.clickElementAfterFocus(cartCheckoutButton);
        elementUtils.waitForWebElementIsDisplay(elementFinder.findElementByXpath("//iframe[@id='snap-midtrans']"));
    }


}
