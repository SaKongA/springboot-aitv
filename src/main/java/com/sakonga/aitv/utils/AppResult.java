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

    public static String updateVersionResult(Map<String, Object> data) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 1);
        map.put("msg", "存在新版本");
        map.put("time", System.currentTimeMillis() / 1000L);
        map.put("data", data);

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

    public static String latestVersionResult() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("msg", "已是最新版");
        map.put("time", System.currentTimeMillis() / 1000L);
        map.put("data", new HashMap<>()); // data 为空

        return JSONObject.toJSONString(map);
    }

    public static String index() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 1);
        map.put("msg", "获取成功");
        map.put("time", System.currentTimeMillis() / 1000L);
        map.put("data", new HashMap<>()); // data 为空

        return JSONObject.toJSONString(map);
    }

    public static String squareList() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 1);
        map.put("msg", "获取成功");
        map.put("time", System.currentTimeMillis() / 1000L);
        map.put("data", new HashMap<>()); // data 为空

        return JSONObject.toJSONString(map);
    }

    public static String bannerList() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 1);
        map.put("msg", "获取成功");
        map.put("time", String.valueOf(System.currentTimeMillis() / 1000L));

        List<Map<String, Object>> dataList = new ArrayList<>();

        Map<String, Object> item1 = new HashMap<>();
        item1.put("id", 4);
        item1.put("image", "http://192.168.170.188:8080//download/app/banner1.jpg");
        dataList.add(item1);

        Map<String, Object> item2 = new HashMap<>();
        item2.put("id", 6);
        item2.put("image", "http://192.168.170.188:8080//download/app/banner2.jpg");
        dataList.add(item2);

        Map<String, Object> item3 = new HashMap<>();
        item3.put("id", 7);
        item3.put("image", "http://192.168.170.188:8080//download/app/banner3.jpg");
        dataList.add(item3);

        map.put("data", dataList);

        return JSONObject.toJSONString(map);
    }

    public static String getWxNumber() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 1);
        map.put("msg", "请求成功");
        map.put("time", System.currentTimeMillis() / 1000L);
        map.put("data", "12345678");

        return JSONObject.toJSONString(map);
    }

    public static String getDlMobile() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 1);
        map.put("msg", "请求成功");
        map.put("time", System.currentTimeMillis() / 1000L);
        map.put("data", "12345678901");

        return JSONObject.toJSONString(map);
    }

    public static String getGrMonile() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 1);
        map.put("msg", "请求成功");
        map.put("time", System.currentTimeMillis() / 1000L);
        map.put("data", "12345678");

        return JSONObject.toJSONString(map);
    }
}