package com.dk.utils;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.asserts.SoftAssert;

import java.util.Map;

/**
 * @author Vivek Lande
 */
public class CompareResponseThread extends Thread {
    String firstApi, secondApi;
    SoftAssert softAssert;
    private IComparator iComparator;

    public CompareResponseThread(String firstApi, String secondApi, SoftAssert softAssert) {
        this.firstApi = firstApi;
        this.secondApi = secondApi;
        this.softAssert = softAssert;
        iComparator = new IComparator();
    }

    @Override
    public void run() {
        Response firstApiResponse = getApiResponseViaGet(firstApi);
        Response secondApiResponse = getApiResponseViaGet(secondApi);
        if (firstApiResponse.getStatusCode() != secondApiResponse.getStatusCode()) {
            System.out.println(firstApiResponse.getStatusCode() + ":" + secondApiResponse.getStatusCode());
            softAssert.assertTrue(false, String.format("%s is not equal with %s", firstApi, secondApi));
        } else {
            JSONObject firstJSON = new JSONObject((Map) firstApiResponse.getBody().jsonPath().getJsonObject("$"));
            JSONObject secondJSON = new JSONObject((Map) secondApiResponse.getBody().jsonPath().getJsonObject("$"));
            CommonUtils.parseJSONObjectToMap(firstJSON);

            System.out.println(String.format("Comparing API '%s' adn '%s' responses", firstApi, secondApi));
            boolean isSame = iComparator.compareMap(CommonUtils.parseJSONObjectToMap(firstJSON), CommonUtils.parseJSONObjectToMap(secondJSON));
            softAssert.assertTrue(isSame, String.format("%s is not equal with %s", firstApi, secondApi));

        }
    }

    /**
     * Hitting api via GET method
     *
     * @param api
     * @return Response
     */
    public Response getApiResponseViaGet(String api) {
        RestAssured.useRelaxedHTTPSValidation();
        return RestAssured.request(Method.GET, api);
    }
}
