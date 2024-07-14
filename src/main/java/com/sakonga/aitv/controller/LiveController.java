package com.sakonga.aitv.controller;

import com.sakonga.aitv.pojo.VlChild;
import com.sakonga.aitv.service.LibraryServiceImpl;
import com.sakonga.aitv.utils.LibraryResult;
import com.sakonga.aitv.utils.LiveResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class LiveController {

    @Autowired
    private LibraryServiceImpl libraryServiceImpl;

    @PostMapping("/api/tan/get_is_vogoods")
    public String get_is_vogoods() {
        return LiveResult.getTanInfo();
    }

    @PostMapping("/api/Common/getConfig")
    public String getConfig() {
        return LiveResult.getConfig();
    }

    @PostMapping("/api/news/getNewByLibraryVoice")
    public String getNewByLibraryVoice(@RequestBody Map<String, Object> request) {
        Long libraryId = Long.valueOf(request.get("library_id").toString());
        List<VlChild> vlChildList = libraryServiceImpl.getVlChildByLibraryId(libraryId);
        return LibraryResult.getSuccessResult(vlChildList);
    }

}
