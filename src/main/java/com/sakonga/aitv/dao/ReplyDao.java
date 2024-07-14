package com.sakonga.aitv.dao;

import com.sakonga.aitv.pojo.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ReplyDao {
    @Select("SELECT * FROM reply WHERE token = #{token}")
    List<Reply> getReplyByToken(@Param("token") String token);

    @Select("SELECT COUNT(*) FROM reply WHERE name = #{name} AND token = #{token}")
    int countByNameAndToken(@Param("name") String name, @Param("token") String token);

    @Select("SELECT MAX(number) FROM reply WHERE token = #{token}")
    Integer findMaxNumberByToken(@Param("token") String token);

    @Insert("INSERT INTO reply (name, memo, token, number) VALUES (#{name}, #{memo}, #{token}, #{number})")
    void insertReply(@Param("name") String name, @Param("memo") String memo, @Param("token") String token, @Param("number") int number);

    @Select("<script>" +
            "SELECT * FROM replyChild WHERE type_id IN " +
            "<foreach item='typeId' index='index' collection='typeIds' open='(' separator=',' close=')'>" +
            "#{typeId}" +
            "</foreach>" +
            "</script>")
    List<ReplyChild> getChildListByTypeIds(@Param("typeIds") List<Long> typeIds);

    @Select("SELECT * FROM replyChild WHERE type_id = #{typeId}")
    List<ReplyChild> getReplyChildByTypeId(@Param("typeId") Long typeId);

    @Select("<script>" +
            "SELECT * FROM replySon WHERE reply_id IN " +
            "<foreach item='replyId' index='index' collection='replyIds' open='(' separator=',' close=')'>" +
            "#{replyId}" +
            "</foreach>" +
            "</script>")
    List<ReplySon> getSonListByReplyIds(@Param("replyIds") List<Long> replyIds);

    @Insert("INSERT INTO replyChild (type_id, name, keywords, number, miao) " +
            "VALUES (#{typeId}, #{name}, #{keyWords}, #{number}, #{miao})")
    void insertReplyChild(ReplyChild replyChild);

    @Select("SELECT COUNT(*) FROM replyChild WHERE type_id = #{typeId}")
    int countByTypeId(@Param("typeId") Long typeId);

    @Select("SELECT COUNT(*) > 0 FROM reply WHERE id = #{id} AND token = #{token}")
    boolean tokenMatchesReply(@Param("id") Long id, @Param("token") String token);

    @Delete("DELETE FROM replyChild WHERE type_id = #{typeId} AND id = #{id}")
    void deleteReplyChild(@Param("typeId") Long typeId, @Param("id") Long id);

    @Insert("INSERT INTO replySon (reply_id, file_name, name) " +
            "VALUES (#{replyId}, #{fileName}, #{name})")
    void addReplyvoice_more(ReplySon replySon);

    @Insert("INSERT INTO replySon (reply_id, name, file_name) " +
            "VALUES (#{replyId}, #{name}, #{fileName})")
    void insertReplySon(ReplySon replySon);

    @Delete("DELETE FROM replySon WHERE id = #{id} AND reply_id = #{replyId}")
    void deleteReplySon(@Param("id") Long id, @Param("replyId") Long replyId);

    @Select("SELECT file_name FROM replySon WHERE id = #{id} AND reply_id = #{replyId}")
    String getFileNameByIdAndReplyId(@Param("id") Long id, @Param("replyId") Long replyId);

    @Delete("DELETE FROM reply WHERE id = #{id}")
    void delReplycategory(@Param("id") Long id);

    @Update("UPDATE reply SET name = #{name} WHERE id = #{id}")
    void updateReplyNameById(@Param("id") Long id, @Param("name") String name);

    @Update("UPDATE replyChild SET name = #{name}, keywords = #{keyWords} WHERE id = #{id}")
    void updateReplySon(@Param("id") Long id, @Param("name") String name, @Param("keyWords") String keyWords);
}