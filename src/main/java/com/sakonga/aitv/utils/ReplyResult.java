package com.sakonga.aitv.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.sakonga.aitv.pojo.Reply;
import com.sakonga.aitv.pojo.ReplyChild;
import com.sakonga.aitv.pojo.VlChild;
import com.sakonga.aitv.pojo.VoiceLibrary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReplyResult {
    public static final String urlPrefix = "http://localhost:8080/download/voice/";

    public static String getReplySuccess(List<Reply> reply) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 1);
        map.put("msg", "获取成功");
        map.put("time", System.currentTimeMillis() / 1000L);
        map.put("data", reply);
        return JSONObject.toJSONString(map);
    }

    public static String editReplySuccess() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 1);
        map.put("msg", "添加成功");
        map.put("time", System.currentTimeMillis() / 1000L);
        return JSONObject.toJSONString(map);
    }

    public static String editReplySuccessRe() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 1);
        map.put("msg", "维护成功");
        map.put("time", System.currentTimeMillis() / 1000L);
        return JSONObject.toJSONString(map);
    }

    public static String editReplyFail() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("msg", "库名已存在");
        map.put("time", System.currentTimeMillis() / 1000L);
        return JSONObject.toJSONString(map);
    }

    public static String deleteReplyFail() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("msg", "删除失败");
        map.put("time", System.currentTimeMillis() / 1000L);
        return JSONObject.toJSONString(map);
    }

    public static String getChildSuccess(List<ReplyChild> childs) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 1);
        map.put("msg", "获取成功");
        map.put("time", System.currentTimeMillis() / 1000L);
        map.put("data", childs);
        return JSONObject.toJSONString(map);
    }

    public static String deleteReplySuccess() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 1);
        map.put("msg", "删除成功");
        map.put("time", System.currentTimeMillis() / 1000L);
        map.put("data", null);
        return JSONObject.toJSONString(map);
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
}
