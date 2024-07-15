package com.sakonga.aitv.service;

import com.sakonga.aitv.dao.LibraryDao;
import com.sakonga.aitv.dao.ReplyDao;
import com.sakonga.aitv.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class ReplyServiceImpl {

    @Autowired
    ReplyDao replyDao;

    public List<Reply> getReplysByToken(String token) {
        List<Reply> replys = replyDao.getReplyByToken(token);

        if (replys.isEmpty()) {
            return replys; // 返回空结果
        }

        List<Long> typeIds = replys.stream()
                .map(Reply::getId)
                .collect(Collectors.toList());

        if (typeIds.isEmpty()) {
            return replys; // 返回空结果
        }

        List<ReplyChild> replyChildList = replyDao.getChildListByTypeIds(typeIds);
        Map<Long, List<ReplyChild>> replyChildMap = replyChildList.stream()
                .collect(Collectors.groupingBy(ReplyChild::getTypeId));

        for (Reply reply : replys) {
            List<ReplyChild> childList = replyChildMap.getOrDefault(reply.getId(), new ArrayList<>());
            reply.setChild_list(childList);
        }

        return replys;
    }


    public String editReplyca(String name, String memo, String token) {
        int count = replyDao.countByNameAndToken(name, token);
        if (count > 0) {
            return "NameAlreadyExists";
        }
        Integer maxNumber = replyDao.findMaxNumberByToken(token);
        int newNumber = (maxNumber == null) ? 1 : maxNumber + 1;
        replyDao.insertReply(name, memo, token, newNumber);
        return "Success";
    }

    public String updateReplyNameById(Long id, String name, String token) {
        boolean exists = replyDao.tokenMatchesReply(id, token);
        if (!exists) {
            return "InvalidToken";
        }
        replyDao.updateReplyNameById(id, name);
        return "Success";
    }

    public List<ReplyChild> getReplyChildByTypeId(Long typeId) {
        List<ReplyChild> childs = replyDao.getReplyChildByTypeId(typeId);

        if (childs.isEmpty()) {
            return childs; // 返回空结果
        }

        List<Long> replyIds = childs.stream()
                .map(ReplyChild::getId)
                .collect(Collectors.toList());

        if (replyIds.isEmpty()) {
            return childs; // 返回空结果
        }

        List<ReplySon> replySonList = replyDao.getSonListByReplyIds(replyIds);
        Map<Long, List<ReplySon>> replySonMap = replySonList.stream()
                .collect(Collectors.groupingBy(ReplySon::getReplyId));

        for (ReplyChild replyChild : childs) {
            List<ReplySon> sonList = replySonMap.getOrDefault(replyChild.getId(), new ArrayList<>());
            for (ReplySon son : sonList) {
                String voiceUrlPrefix = "http://yourdomain.com/files/"; // 替换为你的 URL 前缀
                son.setVoiceUrl(voiceUrlPrefix + son.getFileName());
            }
            replyChild.setSon(sonList);
        }
        return childs;
    }

    public String updateReplySonById(Long id, String name, String keyWords) {
        replyDao.updateReplySon(id, name, keyWords);
        return "Success";
    }

    public String deleteReplyChildById(Long typeId, Long id) {
        replyDao.deleteReplyChild(typeId, id);
        return "Success";
    }

    public String editReply(Long typeId, String name, String keyWords) {
        int count = replyDao.countByTypeId(typeId);

        ReplyChild replyChild = new ReplyChild();
        replyChild.setTypeId(typeId);
        replyChild.setName(name);
        replyChild.setKeyWords(keyWords);
        replyChild.setNumber(String.valueOf(count + 1));
        replyChild.setSon(null);
        replyChild.setMiao(0);
        replyChild.setWordList(null);
        replyChild.setTextList(null);

        replyDao.insertReplyChild(replyChild);

        return "Success";
    }

    public String addReplyvoice_more(Long replyId, String name, String voiceUrl) {
        ReplySon replySon = new ReplySon();
        replySon.setReplyId(replyId);
        replySon.setName(name);
        String newFileName = moveFileAndRename(voiceUrl);
        String fileNameOnly = newFileName != null ? new File(newFileName).getName() : null;
        replySon.setFileName(fileNameOnly);
        replyDao.addReplyvoice_more(replySon);

        return "Success";
    }

    private String moveFileAndRename(String voiceUrl) {
        int lastSlashIndex = voiceUrl.lastIndexOf('/');
        String fileName = voiceUrl.substring(lastSlashIndex + 1);
        String newFilePath = "C:/Users/SaKongA/sql/cache/" + fileName;
        String targetFilePath = "C:/Users/SaKongA/sql/replyvoice" + fileName;
        try {
            Path sourcePath = Paths.get(newFilePath);
            Path targetPath = Paths.get(targetFilePath);
            Files.move(sourcePath, targetPath);
            String newFileName = "C:/Users/SaKongA/sql/replyvoice/" + UUID.randomUUID().toString() + fileName.substring(fileName.lastIndexOf('.'));
            Files.move(targetPath, Paths.get(newFileName));
            return newFileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addReplyoice(Long replyId, String name, MultipartFile voiceFile) throws IOException {
        // 创建文件名
        String originalFilename = voiceFile.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFilename = UUID.randomUUID() + fileExtension;

        // 保存文件
        String filePath = "C:/Users/SaKongA/sql/replyvoice/" + newFilename;
        File dest = new File(filePath);
        voiceFile.transferTo(dest);

        // 构建 vlChild 对象
        ReplySon replySon = new ReplySon();
        replySon.setReplyId(replyId);
        replySon.setName(name);
        replySon.setFileName(newFilename);

        // 插入到数据库
        replyDao.insertReplySon(replySon);
    }

    public boolean deleteReplySon(Long id, Long replyId) {
        try {
            String fileName = replyDao.getFileNameByIdAndReplyId(id, replyId);
            replyDao.deleteReplySon(id, replyId);
            if (fileName != null && !fileName.isEmpty()) {
                String filePath = "C:/Users/SaKongA/sql/replyvoice/" + fileName;
                Files.deleteIfExists(Paths.get(filePath));
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void delReplycategory(Long id) {
        replyDao.delReplycategory(id);
    }
}
