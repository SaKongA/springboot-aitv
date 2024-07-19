package com.sakonga.aitv.controller;

import com.sakonga.aitv.pojo.UserPojo;
import com.sakonga.aitv.service.AppServiceImpl;
import com.sakonga.aitv.utils.AppResult;
import com.sakonga.aitv.utils.LiveResult;
import com.sakonga.aitv.utils.Result;
import com.sakonga.aitv.utils.UserResult;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AppController {

    @Autowired AppServiceImpl appServiceImpl;

    @PostMapping("/api/Common/getConfig")
    public String getConfig(@RequestBody Map<String, Object> request) {
        String name = (String) request.get("name");
        if ("voice_ip".equals(name)) {
            return LiveResult.getVoiceIpConfig();
        } else if ("websocket".equals(name)) {
            return LiveResult.getWebSocketConfig();
        } else if ("favicon".equals(name)) {
            return AppResult.getFavicon();
        } else if ("avatar_ybzk".equals(name)) {
            return AppResult.getAvatar();
        } else if ("wx_number".equals(name)) {
            return AppResult.getWxNumber();
        } else if ("dl_mobile".equals(name)) {
            return AppResult.getDlMobile();
        } else if ("gr_monile".equals(name)) {
            return AppResult.getGrMonile();
        } else {
            return LiveResult.getConfig();
        }
    }

    @PostMapping("/api/Version/getList_ybzk")
    public String getListYbzk(@RequestBody Map<String, Object> request) {
        String version = (String) request.get("version");
        try {
            Map<String, Object> versionData = appServiceImpl.getVersionData();
            String fileVersion = (String) versionData.get("version");

            if (version.equals(fileVersion)) {
                return AppResult.latestVersionResult();
            } else {
                Map<String, Object> dataMap = new HashMap<>();
                dataMap.put("newversion", versionData.get("version"));
                dataMap.put("packagesize", versionData.get("packagesize"));
                dataMap.put("content", versionData.get("content"));
                dataMap.put("downloadurl", versionData.get("downloadurl"));
                return AppResult.updateVersionResult(dataMap);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return AppResult.latestVersionResult();
        }
    }

    @PostMapping("/api/index/index")
    public String index(@RequestBody Map<String, String> request, HttpServletRequest httpRequest) {
        return AppResult.index();
    }

    @PostMapping("/api/index/getSquareList")
    public String getSquareList(@RequestBody Map<String, String> request, HttpServletRequest httpRequest) {
        return AppResult.squareList();
    }

    @PostMapping("/api/index/getBannerList")
    public String getBannerList(@RequestBody Map<String, String> request, HttpServletRequest httpRequest) {
        return AppResult.bannerList();
    }
}
