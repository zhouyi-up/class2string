package com.liuujun.class2dml.utils;

import java.util.Arrays;

/**
 * @author liujun
 */
public class ClassCheckUtils {

    private static String[] BASIC = new String[]{
            "java.lang.String",
            "java.lang.Integer",
            "java.lang.Long",
            "java.lang.Short",
            "java.lang.Double",
            "java.lang.Float",
            "int",
            "long",
            "double",
            "short",
            "float"
    };

    public static boolean isBasicClass(String className){
        return Arrays.asList(BASIC).contains(className);
    }
}
