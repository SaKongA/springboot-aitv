package com.sakonga.aitv.controller;

import com.sakonga.aitv.pojo.ReplyChild;
import com.sakonga.aitv.pojo.ReplySon;
import com.sakonga.aitv.pojo.VlChild;
import com.sakonga.aitv.service.LibraryServiceImpl;
import com.sakonga.aitv.service.LiveServiceImpl;
import com.sakonga.aitv.utils.AppResult;
import com.sakonga.aitv.utils.LiveResult;
import com.sakonga.aitv.utils.ReplyResult;
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
import java.util.List;
import java.util.Map;

@RestController
public class LiveController {

    @Autowired
    private LibraryServiceImpl libraryServiceImpl;

    @Autowired
    private LiveServiceImpl liveServiceImpl;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/api/live/addLive")
    public ResponseEntity<String> addLive(@RequestBody Map<String, Object> request) {
        // 修改请求体中的 library_id 和 replytype_id
        request.put("library_id", 6048);
        request.put("replytype_id", 4834);

        String targetUrl = "http://ybzk.yxbnet.cn/api/live/addLive";
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request);
        ResponseEntity<String> response = restTemplate.exchange(targetUrl, HttpMethod.POST, entity, String.class);

        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    @PostMapping("/api/tan/get_is_vogoods")
    public String get_is_vogoods() {
        return LiveResult.getTanInfo();
    }

    @PostMapping("/api/news/getNewByLibraryVoice")
    private String getNewByLibraryVoice(@RequestBody Map<String, String> request) {
        Long libraryId = Long.valueOf(request.get("id"));
        List<VlChild> vlChildList = libraryServiceImpl.getVlChildByLibraryId(libraryId);
        return LiveResult.getSuccessLiveResult(vlChildList);
    }

    @PostMapping("/api/news/getNewReplayVoiceByTypeId")
    private String getNewByReplyVoice(@RequestBody Map<String, String> request) {
        Long typeId = Long.valueOf(request.get("id"));
        List<ReplyChild> childs = liveServiceImpl.getReplyChildByTypeId(typeId);
        return ReplyResult.getChildSuccess(childs);
    }

    @PostMapping("/api/live/getMessage")
    public ResponseEntity<String> getMessage(@RequestBody Map<String, Object> request) {
        String targetUrl = "http://ybzk.yxbnet.cn/api/live/getMessage";
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request);
        ResponseEntity<String> response = restTemplate.exchange(targetUrl, HttpMethod.POST, entity, String.class);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    @GetMapping("//download/voice/{filename}")
    public ResponseEntity<Resource> downloadVoice(@PathVariable String filename) throws IOException {
        String userName = System.getProperty("user.name");
        String VOICE_DIRECTORY = "C:/Users/" + userName + "/server/sql/voice";
        Path filePath = Paths.get(VOICE_DIRECTORY, filename);
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
