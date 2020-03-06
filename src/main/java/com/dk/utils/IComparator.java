package com.dk.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Vivek Lande
 */
public class IComparator {
    public boolean compareMap(Map<String, Object> firstObject, Map<String, Object> secondObject) {
        boolean isMapSame = false;

        for (Map.Entry firstMapEntry : firstObject.entrySet()) {
            if (!secondObject.containsKey(firstMapEntry.getKey())) {
                return false;
            } else if (firstMapEntry.getValue().equals(secondObject.get(firstMapEntry.getKey()))) {
                isMapSame = true;
            } else {
                System.out.println(firstMapEntry.getValue() + "is not equals to " + secondObject.get(firstMapEntry.getKey()));
                return false;
            }
        }
        return isMapSame;
    }
}
