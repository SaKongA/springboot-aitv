package com.sakonga.aitv.utils;

import com.alibaba.fastjson2.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Result {
    public static String okGetString() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("msg", "success");
        return JSONObject.toJSONString(map);
    }

    public static String errorGetString(String message) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 401);
        map.put("msg", message);
        return JSONObject.toJSONString(map);
    }

}
