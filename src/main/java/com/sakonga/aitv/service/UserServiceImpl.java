package com.sakonga.aitv.service;

import com.sakonga.aitv.dao.LibraryDao;
import com.sakonga.aitv.dao.UserDao;
import com.sakonga.aitv.pojo.UserPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

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

        UserPojo newUser = new UserPojo(null, username, password, mobile, nickname, score, token, createtime);
        userDao.insert(newUser);

        return newUser;
    }

    public UserPojo getUserByToken(String token) {
        return userDao.getUserByToken(token);
    }
}
