package com.sakonga.aitv.controller;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import org.apache.commons.io.FilenameUtils;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RestController
public class RetoController {
    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/api/live/getLiveInfonew")
    public ResponseEntity<String> getLiveInfonew(
            @RequestHeader HttpHeaders headers,
            @RequestBody String body) {
        String targetUrl = "http://ybzk.yxbnet.cn/api/live/getLiveInfonew"; // 目标URL
        HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                targetUrl,
                HttpMethod.POST,
                requestEntity,
                String.class);
        return responseEntity;
    }


    @PostMapping("/api/Operation/savewav_new")
    public ResponseEntity<String> savewav_new(
            @RequestHeader HttpHeaders headers,
            @RequestBody String body) {
        String targetUrl = "http://ybzk.yxbnet.cn/api/Operation/savewav_new"; // 目标URL
        HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                targetUrl,
                HttpMethod.POST,
                requestEntity,
                String.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            String responseData = responseEntity.getBody();
            String data = parseDataField(responseData);
            if (data != null) {
                downloadFile(data);
            }
        }

        return responseEntity;
    }

    private String parseDataField(String responseData) {
        String data = null;
        try {
            int dataIndex = responseData.indexOf("\"data\":\"");
            if (dataIndex != -1) {
                int start = dataIndex + "\"data\":\"".length();
                int end = responseData.indexOf("\"", start);
                data = responseData.substring(start, end);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    private void downloadFile(String data) {
        String fileUrl = "http://ybzk.yxbnet.cn" + data.replace("\\", "");
        try {
            URI uri = new URI(fileUrl);
            String fileName = Paths.get(uri.getPath()).getFileName().toString();
            Path targetPath = Paths.get("C:/Users/SaKongA/sql/cache/", fileName);
            Files.copy(uri.toURL().openStream(), targetPath);
            System.out.println("File downloaded to: " + targetPath);
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }
}
