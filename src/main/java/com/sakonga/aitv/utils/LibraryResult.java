package com.sakonga.aitv.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.sakonga.aitv.pojo.ReplyChild;
import com.sakonga.aitv.pojo.VlChild;
import com.sakonga.aitv.pojo.VoiceLibrary;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LibraryResult {

    public static String urlPrefix;
    static {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            String ipAddress = inetAddress.getHostAddress();
            urlPrefix = "http://" + ipAddress + ":8080/download/voice/";
        } catch (UnknownHostException e) {
            e.printStackTrace();
            urlPrefix = "http://localhost:8080/download/voice/";
        }
        System.out.println(urlPrefix);
    }

    public static String getLibrarySuccess(List<VoiceLibrary> libraries) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 1);
        map.put("msg", "获取成功");
        map.put("time", System.currentTimeMillis() / 1000L);
        map.put("data", libraries);
        return JSONObject.toJSONString(map);
    }

    public static String editLibrarySuccess() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 1);
        map.put("msg", "添加成功");
        map.put("time", System.currentTimeMillis() / 1000L);
        return JSONObject.toJSONString(map);
    }

    public static String editLibrarySuccessRe() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 1);
        map.put("msg", "维护成功");
        map.put("time", System.currentTimeMillis() / 1000L);
        return JSONObject.toJSONString(map);
    }

    public static String editLibraryFail() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("msg", "库名已存在");
        map.put("time", System.currentTimeMillis() / 1000L);
        return JSONObject.toJSONString(map);
    }

    public static String deleteLibrarySuccess() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 1);
        map.put("msg", "删除成功");
        map.put("time", System.currentTimeMillis() / 1000L);
        map.put("data", null);
        return JSONObject.toJSONString(map);
    }

    public static String getSuccessResult(List<VlChild> vlChildList) {
        List<Map<String, Object>> dataList = new ArrayList<>();

        for (VlChild vlChild : vlChildList) {
            Map<String, Object> dataItem = new HashMap<>();
            String fullUrl = urlPrefix + vlChild.getFileName();

            dataItem.put("id", vlChild.getId());
            dataItem.put("library_id", vlChild.getLibraryId());
            dataItem.put("name", vlChild.getName());
            dataItem.put("voice_url", fullUrl);
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

    public static String addSuccessResult(Object data) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 1);
        result.put("msg", "上传成功");
        result.put("time", System.currentTimeMillis() / 1000);
        result.put("data", data);
        return JSON.toJSONString(result);
    }

    public static String addFailureResult(String message) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 0);
        result.put("msg", message);
        result.put("time", System.currentTimeMillis() / 1000);
        result.put("data", null);
        return JSON.toJSONString(result);
    }

    public static String delSuccessResult(Object data) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 1);
        result.put("msg", "删除成功");
        result.put("time", System.currentTimeMillis() / 1000);
        result.put("data", data);
        return JSON.toJSONString(result);
    }

}
