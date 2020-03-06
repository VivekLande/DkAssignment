package com.dk.utils;

import java.io.File;
import java.util.Properties;

/**
 * @author Vivek Lande
 */
public class ProjectProperties {

    private static String requestTimeOut;
    private static String browserType;

    /**
     * Initializing project properties
     */
    public static void initProjectProperties() {
        Properties properties = CommonUtils.readProperties(System.getProperty("user.dir") + File.separator + "project.properties");
        requestTimeOut = properties.getProperty("request.timeout.in.seconds");
        browserType = properties.getProperty("browser.type");
    }

    public static String getRequestTimeOut() {
        return requestTimeOut;
    }

    public static String getBrowserType() {
        return browserType;
    }

}
