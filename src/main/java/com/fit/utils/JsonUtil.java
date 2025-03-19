package com.fit.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @AUTO JSON工具类
 * @FILE JsonUtil.java
 * @DATE 2017-8-27 下午1:24:06
 * @Author AIM
 */
public class JsonUtil {

    private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);
    private static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * 如果JSON字符串为Null或"null"字符串,返回Null. 如果JSON字符串为"[]",返回空集合.
     */
    public static <T> T fromJson(String jsonString, Class<T> clazz) {
        if (isEmpty(jsonString)) {
            return null;
        }

        try {
            return mapper.readValue(jsonString, clazz);
        } catch (Exception e) {
            logger.warn("parse json string error:" + jsonString, e);
            return null;
        }
    }

    /**
     * 如果JSON字符串为Null或"null"字符串,返回Null. 如果JSON字符串为"[]",返回空集合.
     * <p>
     * 如需读取集合如List/Map,且不是List<String>这种简单类型时使用如下语句: List<MyBean> beanList =
     * JsonUtil.fromJson(listString, new TypeReference<List<MyBean>>() {});
     */
    public static <T> T fromJson(String jsonString, TypeReference<?> valueTypeRef) {
        if (isEmpty(jsonString)) {
            return null;
        }
        try {
            return (T) mapper.readValue(jsonString, valueTypeRef);
        } catch (Exception e) {
            logger.warn("parse json string error:" + jsonString, e);
            return null;
        }
    }

    /**
     * 如果对象为Null,返回"null". 如果集合为空集合,返回"[]".
     */
    public static String toJson(Object object) {

        try {
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            logger.warn("write to json string error:" + object, e);
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println(toJson(null));
    }
}
