package com.sakonga.aitv.controller;

import com.sakonga.aitv.pojo.Reply;
import com.sakonga.aitv.pojo.ReplyChild;
import com.sakonga.aitv.service.ReplyServiceImpl;
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
public class ReplyController {

    @Autowired
    private ReplyServiceImpl replyServiceImpl;

    @PostMapping("/api/index/getReplycategory")
    public String getReply(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("token");
        List<Reply> replys = replyServiceImpl.getReplysByToken(token);

        return ReplyResult.getReplySuccess(replys);
    }

    @PostMapping("/api/index/editReplycategory")
    private String editReplycategory(@RequestBody Map<String, String> request, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("token");
        String name = request.get("name");
        String memo = request.get("memo");
        String idStr = request.get("id");

        if (idStr != null) {
            Long id = Long.parseLong(idStr);
            String result = replyServiceImpl.updateReplyNameById(id, name, token);
            if ("Success".equals(result)) {
                return ReplyResult.editReplySuccessRe();
            } else {
                return ReplyResult.editReplyFail();
            }
        } else {
            String result = replyServiceImpl.editReplyca(name, memo, token);
            if ("NameAlreadyExists".equals(result)) {
                return ReplyResult.editReplyFail();
            } else {
                return ReplyResult.editReplySuccess();
            }
        }
    }

    @PostMapping("/api/index/delReplycategory")
    public String delReplycategory(@RequestBody Map<String, Object> requestBody) {
        Long id = ((Number) requestBody.get("id")).longValue();
        replyServiceImpl.delReplycategory(id);
        return ReplyResult.deleteReplySuccess();
    }

    @PostMapping("/api/index/getreply")
    public String getReplyChildByTypeId(@RequestBody Map<String, Object> request) {
        Long typeId = Long.valueOf(request.get("type_id").toString());
        List<ReplyChild> childs = replyServiceImpl.getReplyChildByTypeId(typeId);
        return ReplyResult.getChildSuccess(childs);
    }

    @PostMapping("/api/index/delReply")
    private String delReply(@RequestBody Map<String, String> request) {
        Long typeId = Long.valueOf(request.get("type_id"));
        Long id = Long.valueOf(request.get("id"));
        String result = replyServiceImpl.deleteReplyChildById(typeId, id);
        if ("Success".equals(result)) {
            return ReplyResult.deleteReplySuccess();
        } else {
            return ReplyResult.deleteReplyFail();
        }
    }

    @PostMapping("/api/index/editReply")
    private String editReply(@RequestBody Map<String, String> request, HttpServletRequest httpServletRequest) {
        String idStr = request.get("id");
        Long typeId = Long.valueOf(request.get("type_id"));
        String name = request.get("name");
        String keyWords = request.get("keywords");

        if (idStr != null) {
            Long id = Long.valueOf(idStr);
            String result = replyServiceImpl.updateReplySonById(id, name, keyWords);
            if ("Success".equals(result)) {
                return ReplyResult.editReplySuccess();
            } else {
                return ReplyResult.editReplyFail();
            }
        } else {
            String result = replyServiceImpl.editReply(typeId, name, keyWords);
            return ReplyResult.editReplySuccess();
        }
    }

    @PostMapping("/api/Voice/addReplyvoice_more")
    private String addReplyvoice_more(@RequestBody Map<String, String> request, HttpServletRequest httpServletRequest) {
        Long replyId = Long.valueOf(request.get("reply_id"));
        String name = request.get("name");
        String voiceUrl = request.get("voice_url");
        String result = replyServiceImpl.addReplyvoice_more(replyId, name, voiceUrl);
        return ReplyResult.editReplySuccess();
    }

    @PostMapping("/api/index/addReplyvoice")
    public String addReplyvoice(@RequestParam("reply_id") Long replyId,
                                @RequestParam("name") String name,
                                @RequestParam("voice") MultipartFile voiceFile) {
        try {
            replyServiceImpl.addReplyoice(replyId, name, voiceFile);
            return ReplyResult.addSuccessResult(null);
        } catch (IOException e) {
            return ReplyResult.addFailureResult("上传失败");
        }
    }

    @PostMapping("/api/index/delReplyvoice")
    public String deleteReplySon(@RequestBody Map<String, Object> requestBody) {
        Long replyId = ((Number) requestBody.get("reply_id")).longValue();
        Long id = ((Number) requestBody.get("id")).longValue();
        replyServiceImpl.deleteReplySon(id, replyId);
        return ReplyResult.deleteReplySuccess();
    }

}
