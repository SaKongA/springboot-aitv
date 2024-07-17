package com.sakonga.aitv.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.sakonga.aitv.pojo.UserPojo;
import com.sakonga.aitv.pojo.VlChild;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        map.put("data", "NoneData!");

        return JSONObject.toJSONString(map);
    }

    public static String getVoiceIpConfig() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 1);
        map.put("msg", "获取成功");
        map.put("time", System.currentTimeMillis() / 1000L);
        map.put("data", "http://112.126.23.80:9880");

        return JSONObject.toJSONString(map);
    }

    public static String getWebSocketConfig() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 1);
        map.put("msg", "获取成功");
        map.put("time", System.currentTimeMillis() / 1000L);
        map.put("data", "1");

        return JSONObject.toJSONString(map);
    }

    public static String getSuccessLiveResult(List<VlChild> vlChildList) {
        List<Map<String, Object>> dataList = new ArrayList<>();
        String urlPrefix = "/download/voice/";

        for (VlChild vlChild : vlChildList) {
            Map<String, Object> dataItem = new HashMap<>();
            String fullUrl = vlChild.getFileName();

            dataItem.put("id", vlChild.getId());
            dataItem.put("library_id", vlChild.getLibraryId());
            dataItem.put("name", vlChild.getName());
            dataItem.put("voice_url", urlPrefix+fullUrl);
            dataItem.put("image", vlChild.getImage());
            dataItem.put("image_type", vlChild.getImageType());
            dataItem.put("number", vlChild.getNumber());
            dataItem.put("createtime", vlChild.getCreatetime());
            dataItem.put("status", vlChild.getStatus());
            dataItem.put("miao", vlChild.getMiao());
            dataList.add(dataItem);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("code", 1);
        result.put("msg", "获取成功");
        result.put("data", dataList);
        result.put("time", System.currentTimeMillis() / 1000);

        return JSON.toJSONString(result);
    }

}
