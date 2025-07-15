package com.example.lotterysystem.dao.mapper;

import com.example.lotterysystem.dao.dataobject.Encrypt;
import com.example.lotterysystem.dao.dataobject.UserDO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    /**
     * 查询邮箱绑定的人数
     * @param email
     * @return
     */
    @Select("select count(*) from user where email =#{email}")
    int countByMail(@Param("email") String email);
    @Select("select count(*) from user where phone_number =#{phoneNumber}")
    int countByPhone(@Param("phoneNumber") Encrypt phoneNumber);
    @Insert("insert into user (user_name,email,phone_number,password,identity)" +
            "values (#{userName},#{email},#{phoneNumber},#{password},#{identity})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    void insert(UserDO userDO);
    @Select("select * from user where email=#{email}")
    UserDO selectByMail(@Param("email") String email);
    @Select("select * from user where phone_number=#{phoneNumber}")
    UserDO selectByPhoneNumber(@Param("phoneNumber") Encrypt phoneNumber);


      @Select("<script>" +  // 开始标签
              " select * from user " +
              " <if test=\"identity != null\"> " +  // 补充空格：identity!null 改为 identity != null
              " where identity=#{identity} " +
              " </if> " +
              " order by id desc" +
              " </script>")  // 修正为闭合标签
      List<UserDO> selectUserListByIdentity(@Param("identity")String identity);

      @Select("<script>" +
              " select id from user where id in  " +
              " <foreach item='items' collection='items' open='(' separator=',' clos=')'>" +
              " #{item}" +
              " </foreach>"+
              " </script>")
      List<Long> selectExistsByIds(@Param("items") List<Long> ids);
}
