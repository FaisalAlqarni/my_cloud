package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import java.util.List;
import org.apache.ibatis.annotations.*;

@Mapper
public interface NoteMapper {
  @Insert(
    "INSERT INTO NOTES (notetitle, notedescription, userid) VALUES(#{noteTitle}, #{noteDescription}, #{userId})"
  )
  @Options(useGeneratedKeys = true, keyProperty = "noteId")
  int insert(Note note);

  @Select("SELECT * FROM NOTES WHERE noteid = #{noteId}")
  Note getNote(Integer noteId);

  @Select("SELECT * FROM NOTES WHERE userid = #{userId}")
  List<Note> findNotesByUserId(Integer userId);

  @Delete("DELETE FROM NOTES WHERE noteid = #{noteId}")
  int delete(Integer noteId);

  @Update(
    "UPDATE NOTES SET notetitle = #{noteTitle}, notedescription = #{noteDescription} WHERE noteid = #{noteId}"
  )
  int updateNote(Integer noteId, String noteTitle, String noteDescription);
}
