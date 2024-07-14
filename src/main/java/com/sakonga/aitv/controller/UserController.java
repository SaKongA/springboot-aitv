package com.sakonga.aitv.controller;

import com.sakonga.aitv.pojo.UserPojo;
import com.sakonga.aitv.service.UserServiceImpl;
import com.sakonga.aitv.utils.Result;
import com.sakonga.aitv.utils.UserResult;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class UserController {
    @Autowired
    UserServiceImpl userService;

    @RequestMapping(value = "/api/user/login", method = RequestMethod.POST)
    public String login(@RequestBody UserPojo.UserRequest userRequest) {
        UserPojo user = userService.login(userRequest.getMobile(), userRequest.getPassword());
        if (user != null) {
            return UserResult.loginSuccess(user);
        } else {
            return UserResult.loginFail();
        }
    }

    @RequestMapping(value = "/api/user/register", method = RequestMethod.POST)
    public String register(@RequestBody UserPojo.UserRequest userRequest) {
        UserPojo newUser = userService.register(userRequest.getMobile(), userRequest.getPassword());
        if (newUser == null) {
            return UserResult.mobileExists();
        } else {
            return UserResult.registerSunccess(newUser);
        }
    }

    @PostMapping("/api/user/index")
    public String index(@RequestBody Map<String, String> request, HttpServletRequest httpRequest) {
        String token = httpRequest.getHeader("token");
        UserPojo user = userService.getUserByToken(token);
        if (user != null) {
            return UserResult.indexMessage(user);
        } else {
            return Result.errorGetString("Invalid token");
        }
    }
}
