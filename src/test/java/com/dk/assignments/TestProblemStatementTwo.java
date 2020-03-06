package com.dk.assignments;

import com.dk.utils.CommonUtils;
import com.dk.utils.CompareResponseThread;
import com.dk.utils.IComparator;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Vivek Lande
 */
public class TestProblemStatementTwo {

    /**
     * This is to verify the API list mapping from firstfile.txt file to
     * secondFile.txt has the same response
     */
    @Test
    public void verifyIfApiAreSame() {
        SoftAssert softAssert = new SoftAssert();
        String fs = System.getProperty("file.separator");
        String fistFilePath = "apifiles" + fs + "firstFile.txt";
        String secondFilePath = "apifiles" + fs + "secondFile.txt";
        System.out.println(fistFilePath);
        File firstFile = new File(CommonUtils.getResourcePathAsUri(fistFilePath));
        File secondFile = new File(CommonUtils.getResourcePathAsUri(secondFilePath));
        BufferedReader firstFileReader;
        BufferedReader secondFileReader;
        ArrayList<Thread> threadList = new ArrayList<>();
        try {
            firstFileReader = new BufferedReader(new FileReader(firstFile));
            secondFileReader = new BufferedReader(new FileReader(secondFile));

            String firstFileApi, secondFileApi = null;
            int i = 0;
            while ((firstFileApi = firstFileReader.readLine()) != null && (secondFileApi = secondFileReader.readLine()) != null) {
                i++;
                System.out.println("Verifying responses of  '" + firstFileApi + "' :vs: " + "'" + secondFileApi + "'");
                Thread name = new Thread("name" + i);
                name = new CompareResponseThread(firstFileApi, secondFileApi, softAssert);//hitting the API's and comparing their responses
                name.start();
                threadList.add(name);
            }

            //reaching at end of first file
            if (firstFileApi != null) {
                softAssert.assertTrue(false, String.format("API '%s' from first file doesn't have mapping in second file", firstFileApi));
                while ((firstFileApi = firstFileReader.readLine()) != null) {
                    softAssert.assertTrue(false, String.format("API '%s' from first file doesn't have mapping in second file", firstFileApi));
                }
            }

            //reaching at end of second file
            if (secondFileApi != null) {
                softAssert.assertTrue(false, String.format("API '%s' from second file doesn't have mapping in first file", secondFileApi));
                while ((secondFileApi = secondFileReader.readLine()) != null) {
                    softAssert.assertTrue(false, String.format("API '%s' from second file doesn't have mapping in first file", secondFileApi));
                }
            }

            //Checking that all thread has done their work
            while (threadList.size() > 0) {
                threadList.removeIf(thread -> !thread.isAlive());
            }

            //asserting all comparison
            softAssert.assertAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Response getApiResponseViaGet(String api) {
        RestAssured.useRelaxedHTTPSValidation();
        return RestAssured.request(Method.GET, api);
    }

    public void compareResponse(String firstApi, String secondApi, SoftAssert softAssert) {
        IComparator iComparator = new IComparator();
        ExecutorService es = Executors.newCachedThreadPool();
        es.execute(new Runnable() {
            @Override
            public void run() {
                Response firstApiResponse = getApiResponseViaGet(firstApi);
                Response secondApiResponse = getApiResponseViaGet(secondApi);
                if (firstApiResponse.getStatusCode() != secondApiResponse.getStatusCode()) {
                    System.out.println(firstApiResponse.getStatusCode() + ":" + secondApiResponse.getStatusCode());
                    softAssert.assertTrue(false, String.format("'%s' is not equal with '%s'", firstApi, secondApi));
                } else {
                    JSONObject firstJSON = new JSONObject((Map) firstApiResponse.getBody().jsonPath().getJsonObject("$"));
                    JSONObject secondJSON = new JSONObject((Map) secondApiResponse.getBody().jsonPath().getJsonObject("$"));
                    CommonUtils.parseJSONObjectToMap(firstJSON);

                    System.out.println("Comparing both api response");
                    boolean isSame = iComparator.compareMap(CommonUtils.parseJSONObjectToMap(firstJSON), CommonUtils.parseJSONObjectToMap(secondJSON));
                    softAssert.assertTrue(isSame, String.format("'%s' is not equal with '%s'", firstApi, secondApi));

                }
            }
        });
    }
}


