package com.dk.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;

import java.io.File;
import java.io.FileReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * @author Vivek Lande
 */
public class CommonUtils {

    /**
     * This will return the path of resource file or directory
     *
     * @param resourceName
     * @return
     */
    public static String getResourcePath(String resourceName) {
        return CommonUtils.class.getClassLoader().getResource(resourceName).getPath().trim();
    }

    public static URI getResourcePathAsUri(String resourceName) {
        URI uri = null;
        try {
            uri = CommonUtils.class.getClassLoader().getResource(resourceName).toURI();
        } catch (URISyntaxException e) {
            Assert.fail("failed to get resource path: " + resourceName);
        }
        return uri;
    }

    public static Properties readProperties(String filePath) {
        Properties properties = null;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                Assert.fail("Can't find file at: " + file.getAbsolutePath());
            }
            FileReader reader = new FileReader(filePath);
            properties = new Properties();
            properties.load(reader);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Failed to read properties file: " + filePath);
        }
        return properties;
    }

    public static Map<String, Object> parseJSONObjectToMap(JSONObject jsonObject) throws JSONException {
        Map<String, Object> mapData = new HashMap<String, Object>();
        Iterator<String> keysItr = jsonObject.keys();
        while (keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = jsonObject.get(key);

            if (value instanceof JSONArray) {
                value = parseJSONArrayToList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = parseJSONObjectToMap((JSONObject) value);
            }
            mapData.put(key, value);
        }
        return mapData;
    }

    public static List<Object> parseJSONArrayToList(JSONArray array) throws JSONException {
        List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < array.length(); i++) {
            Object value = array.get(i);
            if (value instanceof JSONArray) {
                value = parseJSONArrayToList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = parseJSONObjectToMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }

}
