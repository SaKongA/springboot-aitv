package com.sakonga.aitv.service;

import com.sakonga.aitv.dao.LibraryDao;
import com.sakonga.aitv.pojo.ReplySon;
import com.sakonga.aitv.pojo.VoiceLibrary;
import com.sakonga.aitv.pojo.VlChild;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.stream.Collectors;

@Service
public class LibraryServiceImpl {

    @Autowired
    LibraryDao libraryDao;

    public List<VoiceLibrary> getLibrariesByToken(String token) {
        List<VoiceLibrary> voiceLibraries = libraryDao.getVoiceLibraryByToken(token);

        if (voiceLibraries.isEmpty()) {
            return voiceLibraries; // 返回空结果
        }

        List<Long> libraryIds = voiceLibraries.stream()
                .map(VoiceLibrary::getId)
                .collect(Collectors.toList());

        if (libraryIds.isEmpty()) {
            return voiceLibraries; // 返回空结果
        }

        List<VlChild> vlChildList = libraryDao.getVlChildListByLibraryIds(libraryIds);
        Map<Long, List<VlChild>> vlChildMap = vlChildList.stream()
                .collect(Collectors.groupingBy(VlChild::getLibraryId));

        for (VoiceLibrary voiceLibrary : voiceLibraries) {
            List<VlChild> childList = vlChildMap.getOrDefault(voiceLibrary.getId(), new ArrayList<>());
            for (VlChild childs : childList) {
                String voiceUrlPrefix = "/download/voice/"; // 替换为你的 URL 前缀
                childs.setVoiceUrl(voiceUrlPrefix + childs.getFileName());
            }
            voiceLibrary.setChild_list(childList);
        }
        return voiceLibraries;
    }


    public String editLibrary(String name, String memo, String token) {
        // 检查是否存在相同的 name
        int count = libraryDao.countByNameAndToken(name, token);
        if (count > 0) {
            return "NameAlreadyExists";
        }

        Integer maxNumber = libraryDao.findMaxNumberByToken(token);
        int newNumber = (maxNumber == null) ? 1 : maxNumber + 1;

        libraryDao.insertVoiceLibrary(name, memo, token, newNumber);
        return "Success";
    }

    public String updateLibraryNameById(Long id, String name, String token) {
        boolean exists = libraryDao.tokenMatchesLibrary(id, token);
        if (!exists) {
            return "InvalidToken";
        }
        libraryDao.updateLibraryNameById(id, name);
        return "Success";
    }

    public List<VlChild> getVlChildByLibraryId(Long libraryId) {
        return libraryDao.getVlChildByLibraryId(libraryId);
    }

    public void addVoice(Long libraryId, String name, MultipartFile voiceFile) throws IOException {
        // 创建文件名
        String originalFilename = voiceFile.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFilename = UUID.randomUUID() + fileExtension;

        // 保存文件
        String userName = System.getProperty("user.name");
        String filePath = "C:/Users/" + userName + "/server/sql/voice/" + newFilename;
        File dest = new File(filePath);
        voiceFile.transferTo(dest);

        // 查询该 library_id 的现有条目数量
        int count = libraryDao.countByLibraryId(libraryId);

        // 构建 vlChild 对象
        VlChild vlChild = new VlChild();
        vlChild.setLibraryId(libraryId);
        vlChild.setName(name);
        vlChild.setFileName(newFilename);
        vlChild.setImage(null);
        vlChild.setImageType(0);
        vlChild.setNumber(count + 1);  // 设置 number 值为现有条目数量加一
        vlChild.setCreatetime(System.currentTimeMillis() / 1000);
        vlChild.setStatus(1);  // 设置适当的 status 值
        vlChild.setMiao(0);    // 设置适当的 miao 值

        // 插入到数据库
        libraryDao.insertVlChild(vlChild);
    }

    public boolean existsLibraryWithId(Long Id) {
        return libraryDao.existsLibraryWithId(Id);
    }

    public boolean tokenMatchesLibrary(Long Id, String token) {
        return libraryDao.tokenMatchesLibrary(Id, token);
    }

    public void deleteLibraryEntry(Long Id) {
        libraryDao.deleteLibraryEntry(Id);
    }

    public boolean delVoice(Long id, Long libraryId) {
        try {
            String fileName = libraryDao.findVoiceByLibraryIdAndId(id, libraryId);
            libraryDao.deleteVoice(id, libraryId);
            if (fileName != null && !fileName.isEmpty()) {
                String userName = System.getProperty("user.name");
                String filePath = "C:/Users/" + userName + "/server/sql/voice/" + fileName;
                Files.deleteIfExists(Paths.get(filePath));
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String addVoice_more(Long libraryId, String name, String voiceUrl) {
        VlChild vlChild = new VlChild();
        vlChild.setLibraryId(libraryId);
        vlChild.setName(name);
        String newFileName = moveFileAndRename(voiceUrl);
        String fileNameOnly = newFileName != null ? new File(newFileName).getName() : null;
        vlChild.setFileName(fileNameOnly);
        libraryDao.addVoice_more(vlChild);

        return "Success";
    }

    private String moveFileAndRename(String voiceUrl) {
        int lastSlashIndex = voiceUrl.lastIndexOf('/');
        String fileName = voiceUrl.substring(lastSlashIndex + 1);
        String userName = System.getProperty("user.name");
        String newFilePath = "C:/Users/" + userName + "/server/sql/cache/" + fileName;
        String targetFilePath = "C:/Users/" + userName + "/server/sql/voice" + fileName;
        try {
            Path sourcePath = Paths.get(newFilePath);
            Path targetPath = Paths.get(targetFilePath);
            Files.move(sourcePath, targetPath);
            String newFileName = "C:/Users/" + userName + "/server/sql/voice/" + UUID.randomUUID().toString() + fileName.substring(fileName.lastIndexOf('.'));
            Files.move(targetPath, Paths.get(newFileName));
            return newFileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
