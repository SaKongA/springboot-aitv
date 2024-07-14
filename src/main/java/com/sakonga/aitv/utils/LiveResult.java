package com.sakonga.aitv.utils;

import com.alibaba.fastjson2.JSONObject;
import com.sakonga.aitv.pojo.UserPojo;

import java.util.HashMap;
import java.util.Map;

public class LiveResult {
    public static String getTanInfo() {
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> card_ids = new HashMap<>();
        data.put("is_choose", 2);
        data.put("miao", 0);
        data.put("card_ids", card_ids);

        Map<String, Object> map = new HashMap<>();
        map.put("code", 1);
        map.put("msg", "请求成功");
        map.put("time", System.currentTimeMillis() / 1000L);
        map.put("data", data);

        return JSONObject.toJSONString(map);
    }

    public static String getConfig() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 1);
        map.put("msg", "获取成功");
        map.put("time", System.currentTimeMillis() / 1000L);
        map.put("data", "http://112.126.23.80:9880");

        return JSONObject.toJSONString(map);
    }

}
