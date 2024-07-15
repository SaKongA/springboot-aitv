package com.sakonga.aitv.dao;

import com.sakonga.aitv.pojo.VoiceLibrary;
import com.sakonga.aitv.pojo.VlChild;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LibraryDao {
    @Select("SELECT * FROM voicelibrary WHERE token = #{token}")
    List<VoiceLibrary> getVoiceLibraryByToken(@Param("token") String token);

    @Select("<script>" +
            "SELECT * FROM vlchild WHERE library_id IN " +
            "<foreach item='item' index='index' collection='libraryIds' open='(' separator=',' close=')'>" +
            "#{item}" +
            "</foreach>" +
            "</script>")
    List<VlChild> getVlChildListByLibraryIds(@Param("libraryIds") List<Long> libraryIds);

    @Select("SELECT COUNT(*) FROM voicelibrary WHERE name = #{name} AND token = #{token}")
    int countByNameAndToken(@Param("name") String name, @Param("token") String token);


    @Select("SELECT * FROM vlchild WHERE library_id = #{libraryId}")
    List<VlChild> getVlChildByLibraryId(@Param("libraryId") Long libraryId);

    @Insert("INSERT INTO vlchild (library_id, name, file_name, image, image_type, number, createtime, status, miao) " +
            "VALUES (#{libraryId}, #{name}, #{fileName}, #{image}, #{imageType}, #{number}, #{createtime}, #{status}, #{miao})")
    void insertVlChild(VlChild vlChild);

    @Select("SELECT COUNT(*) FROM vlchild WHERE library_id = #{libraryId}")
    int countByLibraryId(@Param("libraryId") Long libraryId);

    @Select("SELECT COUNT(*) > 0 FROM voicelibrary WHERE id = #{id}")
    boolean existsLibraryWithId(@Param("id") Long id);

    @Select("SELECT COUNT(*) > 0 FROM voicelibrary WHERE id = #{id} AND token = #{token}")
    boolean tokenMatchesLibrary(@Param("id") Long id, @Param("token") String token);

    @Delete("DELETE FROM voicelibrary WHERE id = #{id}")
    void deleteLibraryEntry(@Param("id") Long id);

    @Select("SELECT MAX(number) FROM voicelibrary WHERE token = #{token}")
    Integer findMaxNumberByToken(@Param("token") String token);

    @Insert("INSERT INTO voicelibrary (name, memo, token, number) VALUES (#{name}, #{memo}, #{token}, #{number})")
    void insertVoiceLibrary(@Param("name") String name, @Param("memo") String memo, @Param("token") String token, @Param("number") int number);

    @Update("UPDATE voicelibrary SET name = #{name} WHERE id = #{id}")
    void updateLibraryNameById(@Param("id") Long id, @Param("name") String name);

    @Select("SELECT file_name FROM vlchild WHERE id = #{id} AND library_id = #{libraryId}")
    String findVoiceByLibraryIdAndId(@Param("id") Long id, @Param("libraryId") Long libraryId);

    @Delete("DELETE FROM vlchild WHERE id = #{id} AND library_id = #{libraryId}")
    void deleteVoice(@Param("id") Long id, @Param("libraryId") Long libraryId);
}