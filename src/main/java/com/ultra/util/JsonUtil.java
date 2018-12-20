package com.ultra.util;

import java.util.Collection;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonUtil {

    JsonUtil() {
    }

    /**
     * 
     * @Title objectToString
     * @Description 对象,map,集合转json串
     * @return String
     */
    public static String objectToString(Object object) {
        if (object instanceof Collection<?>) {
            return JSONArray.fromObject(object).toString();
        } else {
            return JSONObject.fromObject(object).toString();
        }
    }

}
