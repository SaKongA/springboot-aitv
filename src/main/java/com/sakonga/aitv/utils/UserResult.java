package com.sakonga.aitv.utils;

import com.alibaba.fastjson2.JSONObject;
import com.sakonga.aitv.pojo.UserPojo;

import java.util.HashMap;

import java.util.Map;

public class UserResult {
    /// 以下为自定义回复
    public static String loginFail() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("msg", "帐号或密码错误！");
        String json = JSONObject.toJSONString(map);
        return json;
    }

    public static String loginSuccess(UserPojo user) {
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> userinfo = new HashMap<>();

        userinfo.put("id", user.getId());
        userinfo.put("user_id", user.getId());
        userinfo.put("username", user.getUsername());
        userinfo.put("nickname", user.getNickname());
        userinfo.put("token", user.getToken());
        userinfo.put("mobile", user.getMobile());

        data.put("userinfo", userinfo);

        Map<String, Object> map = new HashMap<>();
        map.put("code", 1);
        map.put("msg", "登录成功");
        map.put("time", System.currentTimeMillis() / 1000L);
        map.put("data", data);

        return JSONObject.toJSONString(map);
    }

    public static String mobileExists() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("msg", "手机号已被注册");
        return JSONObject.toJSONString(map);
    }

    public static String registerSunccess(UserPojo user) {
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> userinfo = new HashMap<>();

        userinfo.put("id", user.getId());
        userinfo.put("username", user.getUsername());
        userinfo.put("nickname", user.getNickname());
        userinfo.put("mobile", user.getMobile());
        userinfo.put("score", user.getScore());
        userinfo.put("token", user.getToken());
        userinfo.put("user_id", user.getId());
        userinfo.put("createtime", user.getCreatetime());

        data.put("userinfo", userinfo);

        Map<String, Object> map = new HashMap<>();
        map.put("code", 1);
        map.put("msg", "注册成功");
        map.put("time", System.currentTimeMillis() / 1000L);
        map.put("data", data);

        return JSONObject.toJSONString(map);
    }

    public static String indexMessage(UserPojo user) {
        Map<String, Object> data = new HashMap<>();
        data.put("id", user.getId());
        data.put("mobile", user.getMobile());
        Map<String, Object> map = new HashMap<>();
        map.put("code", 1);
        map.put("msg", "获取成功");
        map.put("time", System.currentTimeMillis() / 1000L);
        map.put("data", data);
        return JSONObject.toJSONString(map);
    }

}
