package com.sakonga.aitv.controller;

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
public class AiVoice {

    private final RestTemplate restTemplate;

    public AiVoice(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/api/Index/exit_message")
    public Map<String, Object> handleExitMessage(@RequestBody Map<String, String> requestBody) {
        String text = requestBody.get("text");
        String[] ids = text.split("\\|\\|", -1);
        List<Map<String, String>> dataList = new ArrayList<>();
        for (String id : ids) {
            Map<String, String> dataEntry = new HashMap<>();
            dataEntry.put("id", id);
            dataList.add(dataEntry);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("code", 1);
        response.put("msg", "请求成功");
        response.put("time", String.valueOf(Instant.now().getEpochSecond()));
        response.put("data", dataList);
        return response;
    }

    @PostMapping("/api/Voice/get_voice_model_list")
    public ResponseEntity<String> forwardRequest(
            @RequestHeader HttpHeaders headers,
            @RequestBody String body) {
        String targetUrl = "http://ybzk.yxbnet.cn/api/Voice/get_voice_model_list"; // 目标URL
        HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                targetUrl,
                HttpMethod.POST,
                requestEntity,
                String.class);
        return responseEntity;
    }

    @GetMapping("//wavvoice/1/{var}/{filename}")
    public ResponseEntity<Resource> downloadUploads(@PathVariable String filename) throws IOException {
        String UPLOADS_DIRECTORY = "C:/Users/SaKongA/sql/cache";
        Path filePath = Paths.get(UPLOADS_DIRECTORY, filename);
        byte[] data = Files.readAllBytes(filePath);
        ByteArrayResource resource = new ByteArrayResource(data);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(data.length)
                .body(resource);
    }
}
