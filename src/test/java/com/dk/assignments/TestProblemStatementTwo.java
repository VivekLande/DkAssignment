package com.dk.assignments;

import com.dk.utils.CommonUtils;
import com.dk.utils.IComparator;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Vivek Lande
 */
public class TestProblemStatementTwo {

    @Test
    public void CompareApiResponse() {
        SoftAssert softAssert = new SoftAssert();
        String fs = System.getProperty("file.separator");
        String fistFilePath = "apifiles" + fs + "firstFile.txt";
        String secondFilePath = "apifiles" + fs + "secondFile.txt";
        System.out.println(fistFilePath);
        File firstFile = new File(CommonUtils.getResourcePathAsUri(fistFilePath));
        File secondFile = new File(CommonUtils.getResourcePathAsUri(secondFilePath));
        BufferedReader firstFileReader;
        BufferedReader secondFileReader;
        IComparator iComparator = new IComparator();

        try {
            firstFileReader = new BufferedReader(new FileReader(firstFile));
            secondFileReader = new BufferedReader(new FileReader(secondFile));

            String firstFileLine, secondFileLine = null;
            while ((firstFileLine = firstFileReader.readLine()) != null && (secondFileLine = secondFileReader.readLine()) != null) {
                System.out.println(firstFileLine + " : " + secondFileLine);

                Response firstApiResponse = getApiResponseViaGet(firstFileLine);
                Response secondApiResponse = getApiResponseViaGet(secondFileLine);
                if (firstApiResponse.getStatusCode() != secondApiResponse.getStatusCode()) {
                    System.out.println(firstApiResponse.getStatusCode() + ":" + secondApiResponse.getStatusCode());
                    softAssert.assertTrue(false, String.format("%s is not equal with %s", firstFileLine, secondFileLine));
                } else {
                    JSONObject firstJSON = new JSONObject((Map) firstApiResponse.getBody().jsonPath().getJsonObject("$"));
                    JSONObject secondJSON = new JSONObject((Map) secondApiResponse.getBody().jsonPath().getJsonObject("$"));
                    CommonUtils.parseJSONObjectToMap(firstJSON);

                    System.out.println("Comparing both api response");
                    boolean isSame = iComparator.compareMap(CommonUtils.parseJSONObjectToMap(firstJSON), CommonUtils.parseJSONObjectToMap(secondJSON));
                    softAssert.assertTrue(isSame, String.format("%s is not equal with %s", firstFileLine, secondFileLine));

                }
            }
            softAssert.assertAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Response getApiResponseViaGet(String api) {
        RestAssured.useRelaxedHTTPSValidation();
        return RestAssured.request(Method.GET, api);
    }
}


