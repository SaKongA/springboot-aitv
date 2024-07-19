package com.sakonga.aitv.service;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sakonga.aitv.dao.LibraryDao;
import com.sakonga.aitv.dao.UserDao;
import com.sakonga.aitv.pojo.UserPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;

@Service
public class UserServiceImpl {

    @Autowired
    UserDao userDao;

    public UserPojo login(String mobile, String password) {
        return userDao.selectByMobileAndPassword(mobile, password);
    }

    public UserPojo register(String mobile, String password) {
        UserPojo existingUser = userDao.selectByMobile(mobile);
        if (existingUser != null) {
            return null;
        }

        String username = mobile;
        String nickname = mobile.substring(0, 3) + "****" + mobile.substring(7);
        int score = 0;
        String token = UUID.randomUUID().toString();
        Long createtime = System.currentTimeMillis() / 1000L; // 当前时间戳
        Long activedate = System.currentTimeMillis() / 1000L; // 当前时间戳

        UserPojo newUser = new UserPojo(null, username, password, mobile, nickname, score, token, createtime, activedate);
        userDao.insert(newUser);

        return newUser;
    }

    public UserPojo getUserByToken(String token) {
        return userDao.getUserByToken(token);
    }

    public void saveCdk(String code, String token) throws IOException {
        File file = new File("C:/Users/SaKongA/server/cdk.json");
        String jsonString;

        try (Scanner scanner = new Scanner(file, StandardCharsets.UTF_8.name())) {
            jsonString = scanner.useDelimiter("\\A").next();
        }

        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        String cdkType = getCdkType(code, jsonObject);

        if (cdkType != null) {
            UserPojo user = userDao.getUserByToken(token);
            if (user != null) {
                long currentTime = System.currentTimeMillis();
                long activeDate = 0;

                if (cdkType.equals("cdk_week")) {
                    activeDate = currentTime + 7 * 24 * 60 * 60 * 1000L; // 1 week from now
                } else if (cdkType.equals("cdk_month")) {
                    activeDate = currentTime + 30 * 24 * 60 * 60 * 1000L; // 1 month from now
                }

                user.setActivedate(activeDate);
                userDao.updateById(user);

                JSONArray cdkArray = jsonObject.getJSONArray(cdkType);
                if (cdkArray != null) {
                    cdkArray.remove(code);
                }

                // 将更新后的 JSON 写回文件
                try (FileWriter fileWriter = new FileWriter(file, StandardCharsets.UTF_8, false)) {
                    fileWriter.write(jsonObject.toJSONString());
                }
            }
        } else {
            // CDK 不存在，返回适当的错误响应
            throw new IllegalArgumentException("Invalid CDK");
        }
    }

    private String getCdkType(String code, JSONObject jsonObject) {
        JSONArray cdkWeekArray = jsonObject.getJSONArray("cdk_week");
        if (cdkWeekArray != null && cdkWeekArray.contains(code)) {
            return "cdk_week";
        }

        JSONArray cdkMonthArray = jsonObject.getJSONArray("cdk_month");
        if (cdkMonthArray != null && cdkMonthArray.contains(code)) {
            return "cdk_month";
        }

        return null;
    }

    public int checkAccount(String token) {
        UserPojo user = userDao.getUserByToken(token);
        if (user != null) {
            long currentTime = System.currentTimeMillis();
            if (user.getActivedate() > currentTime) {
                return 1; // Account is valid
            }
        }
        return 0; // Account is invalid
    }
}
