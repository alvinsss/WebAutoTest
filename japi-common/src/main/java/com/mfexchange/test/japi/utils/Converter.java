package com.elong.test.japi.utils;

import java.util.HashMap;
import java.util.Map;

public class Converter {
    public static Map<String, String> toMap(String[] keys, String[] values) {
        if (keys.length != values.length) {
            throw new IllegalArgumentException("Input keys' length not equals to values' length");
        }

        Map<String, String> kvs = new HashMap<String, String>();
        for (int i = 0; i < keys.length; ++i) {
            if (kvs.containsKey(keys[i])) {
                throw new IllegalArgumentException(String.format("Duplicate key %s is found", keys[i]));
            }
            kvs.put(keys[i], values[i]);
        }
        return kvs;
    }
}
