package com.dk.utils;

import org.testng.Assert;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

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
}
