package com.liuujun.class2dml.mapping;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liujun
 */
public class TypeScriptMapping{

    public static Map<String, String> DEFAULT_TYPE_MAPPING = new HashMap<String, String>();

    static {
        DEFAULT_TYPE_MAPPING.put("boolean", "boolean");
        DEFAULT_TYPE_MAPPING.put("int", "number");
        DEFAULT_TYPE_MAPPING.put("long", "number");
        DEFAULT_TYPE_MAPPING.put("float", "number");
        DEFAULT_TYPE_MAPPING.put("double", "number");
        DEFAULT_TYPE_MAPPING.put("short", "number");
        DEFAULT_TYPE_MAPPING.put("char", "number");
        DEFAULT_TYPE_MAPPING.put("java.lang.String", "string");
        DEFAULT_TYPE_MAPPING.put("java.lang.Boolean", "boolean");
        DEFAULT_TYPE_MAPPING.put("java.lang.Integer", "string");
        DEFAULT_TYPE_MAPPING.put("java.lang.Long", "number");
        DEFAULT_TYPE_MAPPING.put("java.lang.Float", "number");
        DEFAULT_TYPE_MAPPING.put("java.lang.Double", "number");
        DEFAULT_TYPE_MAPPING.put("java.lang.Short", "number");

        DEFAULT_TYPE_MAPPING.put("java.time.LocalDate", "string");
        DEFAULT_TYPE_MAPPING.put("java.time.LocalDateTime", "string");
        DEFAULT_TYPE_MAPPING.put("java.util.Date", "string");
        DEFAULT_TYPE_MAPPING.put("java.time.LocalTime", "string");
    }

    public static String type(String type) {
        return DEFAULT_TYPE_MAPPING.getOrDefault(type, "string");
    }
}
