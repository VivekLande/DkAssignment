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
import java.util.concurrent.TimeUnit;

/**
 * @author Vivek Lande
 */
public class TestProblemStatementTwo {

    //@Test
    public void verifyIfApiIsSameBackUp() {
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

    @Test
    public void verifyIfApiIsSame() {
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
                System.out.println("Verifying '" + firstFileApi + "' : " + "'" + secondFileApi + "'");
                Thread name = new Thread("name" + i);
                name = new CompareResponseThread(firstFileApi, secondFileApi, softAssert);
                name.start();
                threadList.add(name);
            }
            if (firstFileApi != null) {
                softAssert.assertTrue(false, String.format("API '%s' from first file doesn't have mapping in second file", firstFileApi));
                while ((firstFileApi = firstFileReader.readLine()) != null) {
                    softAssert.assertTrue(false, String.format("API '%s' from first file doesn't have mapping in second file", firstFileApi));
                }
            }

            if (secondFileApi != null) {
                softAssert.assertTrue(false, String.format("API '%s' from second file doesn't have mapping in first file", secondFileApi));
                while ((secondFileApi = secondFileReader.readLine()) != null) {
                    softAssert.assertTrue(false, String.format("API '%s' from second file doesn't have mapping in first file", secondFileApi));
                }
            }

            while (threadList.size() > 0) {
                threadList.removeIf(thread -> !thread.isAlive());
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


