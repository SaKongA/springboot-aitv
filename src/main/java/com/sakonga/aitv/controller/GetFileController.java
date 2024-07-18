package com.sakonga.aitv.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class GetFileController {

    @GetMapping("/download/voice/{filename}")
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

    @GetMapping("/download/replyvoice/{filename}")
    public ResponseEntity<Resource> downloadReplyVoice(@PathVariable String filename) throws IOException {
        String userName = System.getProperty("user.name");
        String VOICE_DIRECTORY = "C:/Users/" + userName + "/server/sql/replyvoice";
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

    @GetMapping("//uploads/{datepath}/{filename}")
    public ResponseEntity<Resource> downloadUploads(
            @PathVariable String datepath,
            @PathVariable String filename) throws IOException {

        String userName = System.getProperty("user.name");
        String UPLOADS_DIRECTORY = "C:/Users/" + userName + "/server/uploads";
        Path filePath = Paths.get(UPLOADS_DIRECTORY, datepath, filename);

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

    @GetMapping("/download/app/{filename}")
    public ResponseEntity<Resource> downloadAppRes(@PathVariable String filename) throws IOException {
        String userName = System.getProperty("user.name");
        String FILE_DIRECTORY = "C:/Users/" + userName + "/server/app_res";
        Path filePath = Paths.get(FILE_DIRECTORY, filename);
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