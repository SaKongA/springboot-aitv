package com.sakonga.aitv.controller;

import com.sakonga.aitv.pojo.VlChild;
import com.sakonga.aitv.service.LibraryServiceImpl;
import com.sakonga.aitv.utils.LibraryResult;
import com.sakonga.aitv.utils.LiveResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RestController
public class LiveController {

    @Autowired
    private LibraryServiceImpl libraryServiceImpl;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/api/live/addLive")
    public ResponseEntity<String> addLive(@RequestBody Map<String, Object> request) {
        String targetUrl = "http://ybzk.yxbnet.cn/api/live/addLive";
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request);
        ResponseEntity<String> response = restTemplate.exchange(targetUrl, HttpMethod.POST, entity, String.class);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    @PostMapping("/api/tan/get_is_vogoods")
    public String get_is_vogoods() {
        return LiveResult.getTanInfo();
    }

    @PostMapping("/api/Common/getConfig")
    public String getConfig() {
        return LiveResult.getConfig();
    }

    @PostMapping("/api/news/getNewByLibraryVoice")
    private String getNewByLibraryVoice(@RequestBody Map<String, String> request) {
        Long libraryId = Long.valueOf(request.get("id"));
        List<VlChild> vlChildList = libraryServiceImpl.getVlChildByLibraryId(libraryId);
        return LibraryResult.getSuccessResult(vlChildList);
    }

    @PostMapping("/api/live/getMessage")
    public ResponseEntity<String> getMessage(@RequestBody Map<String, Object> request) {
        String targetUrl = "http://ybzk.yxbnet.cn/api/live/getMessage";
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request);
        ResponseEntity<String> response = restTemplate.exchange(targetUrl, HttpMethod.POST, entity, String.class);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }
}
