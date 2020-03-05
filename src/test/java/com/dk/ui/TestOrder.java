package com.dk.ui;

import com.dk.BaseTest;
import com.dk.testdata.TestOrderData;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Vivek Lande
 */
public class TestOrder extends BaseTest {

    @Test(dataProvider = "successfulPaymentFlowData", dataProviderClass = TestOrderData.class)
    public void successfulPaymentFlow(HashMap<Object, Object> data) {
        HomePage homePage = new HomePage(driver);
        homePage.navigateToShoppingCartPage();
        ShoppingCartPage shoppingCartPage = new ShoppingCartPage(driver);
        shoppingCartPage.fillupShoppingCartDetailsAndCheckOut();

        OrderSummaryPage orderSummaryPage = new OrderSummaryPage(driver);
        orderSummaryPage.verifyOrderSummary();

        System.out.println("select credit card as a payment option");
        SelectPaymentPage selectPaymentPage = new SelectPaymentPage(driver);
        selectPaymentPage.navigateToCreditCardPage();

        System.out.println("Fill credit card details");
        CreditCardPage creditCardPage = new CreditCardPage(driver);
        creditCardPage.fillCreditCardDetails(data);

        System.out.println("Navigate to payment confirmation page");
        creditCardPage.navigateToPayNowPage();

        System.out.println("Verify payment details");
        PaymentConfirmationPage paymentConfirmationPage = new PaymentConfirmationPage(driver);
        paymentConfirmationPage.verifyPaymentDetails(data);

        System.out.println("Enter bank OTP and confirm the payment");
        paymentConfirmationPage.enterPasswordAndConfirmPayment(data);

        System.out.println("Verify the payment successfully done");
        paymentConfirmationPage.verifyPaymentSuccessfullyDone();

    }
}
