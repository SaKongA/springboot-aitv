package com.sakonga.aitv.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
public class AppServiceImpl {

    public Map<String, Object> getVersionData() throws IOException {
        String userName = System.getProperty("user.name");
        String VERSION_FILE_PATH = "C:/Users/" + userName + "/server/version.json";
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(VERSION_FILE_PATH);
        return objectMapper.readValue(file, Map.class);
    }
}
