package com.sakonga.aitv.controller;

import com.sakonga.aitv.pojo.Reply;
import com.sakonga.aitv.pojo.VlChild;
import com.sakonga.aitv.pojo.VoiceLibrary;
import com.sakonga.aitv.service.LibraryServiceImpl;
import com.sakonga.aitv.utils.LibraryResult;
import com.sakonga.aitv.utils.ReplyResult;
import com.sakonga.aitv.utils.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class LibraryController {

    @Autowired
    private LibraryServiceImpl libraryServiceImpl;

    @PostMapping("/api/index/getLibrary")
    public String getLibrary(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("token");
        List<VoiceLibrary> voiceLibraries = libraryServiceImpl.getLibrariesByToken(token);

        return LibraryResult.getLibrarySuccess(voiceLibraries);
    }

    @PostMapping("/api/index/editLibrary")
    private String editLibrary(@RequestBody Map<String, String> request, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("token");
        String name = request.get("name");
        String memo = request.get("memo");
        String idStr = request.get("id");

        if (idStr != null) {
            Long id = Long.parseLong(idStr);
            String result = libraryServiceImpl.updateLibraryNameById(id, name, token);
            if ("Success".equals(result)) {
                return LibraryResult.editLibrarySuccessRe();
            } else {
                return LibraryResult.editLibraryFail();
            }
        } else {
            String result = libraryServiceImpl.editLibrary(name, memo, token);
            if ("NameAlreadyExists".equals(result)) {
                return LibraryResult.editLibraryFail();
            } else {
                return LibraryResult.editLibrarySuccess();
            }
        }
    }
    @PostMapping("/api/index/delLibrary")
    private String delLibrary(@RequestBody Map<String, String> request, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("token");
        String id = request.get("id");
        boolean exists = libraryServiceImpl.existsLibraryWithId(Long.valueOf(id));
        if (!exists) {
            return Result.errorGetString("未找到该条目");
        }
        boolean tokenMatches = libraryServiceImpl.tokenMatchesLibrary(Long.valueOf(id), token);
        if (!tokenMatches) {
            return Result.errorGetString("验证不通过");
        }
        libraryServiceImpl.deleteLibraryEntry(Long.valueOf(id));
        return LibraryResult.deleteLibrarySuccess();
    }

    @PostMapping("/api/index/getVoice")
    public String getVlChildByLibraryId(@RequestBody Map<String, Object> request) {
        Long libraryId = Long.valueOf(request.get("library_id").toString());
        List<VlChild> vlChildList = libraryServiceImpl.getVlChildByLibraryId(libraryId);
        return LibraryResult.getSuccessResult(vlChildList);
    }

    @PostMapping("/api/index/addVoice")
    public String addVoice(@RequestParam("library_id") Long libraryId,
                           @RequestParam("name") String name,
                           @RequestParam("voice") MultipartFile voiceFile) {
        try {
            libraryServiceImpl.addVoice(libraryId, name, voiceFile);
            return LibraryResult.addSuccessResult(null);
        } catch (IOException e) {
            return LibraryResult.addFailureResult("上传失败");
        }
    }
}
