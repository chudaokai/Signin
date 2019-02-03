package com.chudk.signin.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSONUtil {
    public static <T> T parseFromJSON(String json, Class<T> cls){
        if(json == null || "".equals(json))
            return null;
        return JSON.parseObject(json,cls);
    }

    public static <T> String toJSON(T enty){
        if(enty == null)
            return "{}";
        return JSON.toJSONString(enty);
    }

    public static <T> List<T> parseListFormJSON (String json,Class<T> cls){
        if(json == null || "".equals(json))
            return null;
        List<T> list = JSONObject.parseArray(json,cls);
        return list;
    }
}
