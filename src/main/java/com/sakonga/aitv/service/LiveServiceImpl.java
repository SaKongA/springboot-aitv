package com.sakonga.aitv.service;

import com.sakonga.aitv.dao.ReplyDao;
import com.sakonga.aitv.pojo.ReplyChild;
import com.sakonga.aitv.pojo.ReplySon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LiveServiceImpl {
    @Autowired
    ReplyDao replyDao;

    public List<ReplyChild> getReplyChildByTypeId(Long typeId) {
        String urlPrefix = "/download/replyvoice/";
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
                son.setVoiceUrl(urlPrefix + son.getFileName());
            }
            replyChild.setSon(sonList);
        }
        return childs;
    }
}
