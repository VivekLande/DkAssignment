package com.dk.utils;

import java.io.File;
import java.util.Properties;

/**
 * @author Vivek Lande
 */
public class ProjectProperties {

    private static String requestTimeOut;

    public static void intitProjectProperties() {
        Properties properties = CommonUtils.readProperties(System.getProperty("user.dir") + File.separator + "project.properties");
        requestTimeOut = properties.getProperty("request.timeout.in.seconds");
    }

    public static String getRequestTimeOut() {
        return requestTimeOut;
    }

}
