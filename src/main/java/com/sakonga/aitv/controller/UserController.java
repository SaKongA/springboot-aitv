package com.sakonga.aitv.controller;

import com.alibaba.fastjson2.JSONObject;
import com.sakonga.aitv.pojo.UserPojo;
import com.sakonga.aitv.service.UserServiceImpl;
import com.sakonga.aitv.utils.Result;
import com.sakonga.aitv.utils.UserResult;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    private UserServiceImpl userServiceImpl;

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

    @PostMapping("/api/user/ybzk_saveCdk")
    public ResponseEntity<String> saveCdk(@RequestBody Map<String, String> request, HttpServletRequest httpRequest) {
        String code = request.get("code");
        String token = httpRequest.getHeader("token");

        if (code != null && token != null) {
            try {
                userService.saveCdk(code, token);
                return ResponseEntity.ok(UserResult.applyCdk());
            } catch (IllegalArgumentException e) {
                return ResponseEntity.ok(UserResult.applyCdkFaild());
            } catch (IOException e) {
                return ResponseEntity.ok(UserResult.applyCdkFaild());
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Result.errorGetString("Missing code or token"));
        }
    }

    @PostMapping("/api/common/checkAccount")
    public ResponseEntity<?> checkAccount(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (token != null) {
            int result = userService.checkAccount(token);
            return ResponseEntity.ok().body(UserResult.checkAccount(result));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Result.errorGetString("Token is missing"));
        }
    }
}
