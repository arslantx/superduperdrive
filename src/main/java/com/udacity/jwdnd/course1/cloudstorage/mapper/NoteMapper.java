package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.List;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface NoteMapper {

    @Select("SELECT * FROM NOTES WHERE userid = #{userid}")
    List<Note> getAllNotes(Integer userid);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid)"
            + " VALUES(#{notetitle}, #{notedescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    Integer insert(Note note);

    @Update("UPDATE NOTES SET notetitle = #{notetitle}, notedescription = #{notedescription}"
            + " WHERE noteid = #{noteid} AND userid = #{userid}")
    Integer update(Note note);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteid} AND userid = #{userid}")
    Integer delete(Integer noteid, Integer userid);
}
