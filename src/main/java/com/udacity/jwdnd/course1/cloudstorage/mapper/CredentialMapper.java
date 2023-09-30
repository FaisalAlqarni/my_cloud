package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;
import java.util.List;
import org.apache.ibatis.annotations.*;

@Mapper
public interface CredentialMapper {
  @Select("SELECT * FROM CREDENTIALS WHERE username = #{username}")
  Credential getCredential(String username);

  @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId}")
  List<Credential> getCredentialsByUser(Integer userId);

  @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialId}")
  int delete(Integer credentialId);

  @Insert(
    "INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES( #{url}, #{username}, #{key}, #{password}, #{userId})"
  )
  @Options(useGeneratedKeys = true, keyProperty = "credentialId")
  int insert(Credential credential);

  @Update(
    "UPDATE CREDENTIALS SET url = #{url}, username = #{username}, key = #{key}, password = #{password} WHERE credentialid = #{credentialId}"
  )
  int update(
    Integer credentialId,
    String url,
    String username,
    String key,
    String password
  );
}
