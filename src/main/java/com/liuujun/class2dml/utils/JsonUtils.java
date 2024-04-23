package com.liuujun.class2dml.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author liujun
 */
public class JsonUtils {

    private static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
    }


    public static String toJsonString(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
