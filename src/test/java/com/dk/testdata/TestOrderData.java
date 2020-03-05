package com.dk.testdata;

import org.testng.annotations.DataProvider;

import java.util.HashMap;

/**
 * @author Vivek Lande
 */
public class TestOrderData {

    @DataProvider(name = "successfulPaymentFlowData")
    public Object[][] successfulPaymentFlowData() {
        HashMap data = new HashMap<>();
        data.put("cardNumber", "4811111111111114");
        data.put("cardExpiryDate", "02/21");
        data.put("cardCvv", "123");
        data.put("bankOTP", "112233");

        Object object[][] = new Object[1][1];
        object[0][0] = data;
        return object;
    }
}
