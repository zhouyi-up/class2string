package com.liuujun.class2dml.mapping;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liujun
 */
public class SQLTypeMapping {

    public static Map<String, String> DEFAULT_TYPE_MAPPING = new HashMap<String, String>();

    static {
        DEFAULT_TYPE_MAPPING.put("boolean", "boolean");
        DEFAULT_TYPE_MAPPING.put("int", "int");
        DEFAULT_TYPE_MAPPING.put("long", "bigint");
        DEFAULT_TYPE_MAPPING.put("float", "bigint");
        DEFAULT_TYPE_MAPPING.put("double", "decimal(2,2)");
        DEFAULT_TYPE_MAPPING.put("short", "tinyint");
        DEFAULT_TYPE_MAPPING.put("char", "tinyint");
        DEFAULT_TYPE_MAPPING.put("java.lang.String", "varchar(255)");
        DEFAULT_TYPE_MAPPING.put("java.lang.Boolean", "boolean");
        DEFAULT_TYPE_MAPPING.put("java.lang.Integer", "int");
        DEFAULT_TYPE_MAPPING.put("java.lang.Long", "bigint");
        DEFAULT_TYPE_MAPPING.put("java.lang.Float", "bigint");
        DEFAULT_TYPE_MAPPING.put("java.lang.Double", "decimal(2,2)");
        DEFAULT_TYPE_MAPPING.put("java.lang.Short", "tinyint");

        DEFAULT_TYPE_MAPPING.put("java.time.LocalDate", "date");
        DEFAULT_TYPE_MAPPING.put("java.time.LocalDateTime", "datetime");
        DEFAULT_TYPE_MAPPING.put("java.util.Date", "datetime");
        DEFAULT_TYPE_MAPPING.put("java.time.LocalTime", "timestamp");
    }
}
