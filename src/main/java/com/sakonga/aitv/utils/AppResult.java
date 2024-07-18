package com.sakonga.aitv.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.sakonga.aitv.pojo.UserPojo;
import com.sakonga.aitv.pojo.VlChild;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppResult {
    public static String getAvatar() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 1);
        map.put("msg", "请求成功");
        map.put("time", System.currentTimeMillis() / 1000L);
        map.put("data", "/download/app/avatar.jpg");

        return JSONObject.toJSONString(map);
    }

    public static String getFavicon() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 1);
        map.put("msg", "请求成功");
        map.put("time", System.currentTimeMillis() / 1000L);
        map.put("data", "/download/app/favicon.jpg");

        return JSONObject.toJSONString(map);
    }
}