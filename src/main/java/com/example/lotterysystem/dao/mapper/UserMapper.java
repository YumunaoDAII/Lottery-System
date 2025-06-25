package com.example.lotterysystem.dao.mapper;

import com.example.lotterysystem.dao.dataobject.Encrypt;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
}
