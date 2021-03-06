package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.List;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FileMapper {

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId} AND userid = #{userid}")
    File getFile(Integer fileId, Integer userid);

    @Select("SELECT fileId, filename FROM FILES WHERE userid = #{userid}")
    @MapKey("fileId")
    List<File> getFiles(Integer userid);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata)"
            + " VALUES(#{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    Integer insert(File file);

    @Select("SELECT EXISTS(SELECT 1 FROM FILES WHERE filename = #{filename} AND userid = #{userid})")
    boolean isExistingFile(String filename, Integer userid);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId} AND userid = #{userid}")
    Integer deleteFile(Integer fileId, Integer userid);
}
