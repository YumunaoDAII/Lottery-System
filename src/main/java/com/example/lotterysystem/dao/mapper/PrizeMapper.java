package com.example.lotterysystem.dao.mapper;

import com.example.lotterysystem.service.dto.PrizeDO;
import org.apache.ibatis.annotations.*;

import java.util.List;

// PrizeMapper.java
@Mapper
public interface PrizeMapper {
    @Insert("insert into prize (name, image_url, price, description) " +
            "values (#{name}, #{imageUrl}, #{price}, #{description})")  // 移除末尾多余的右括号
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(PrizeDO prizeDO);
    @Select("select count(1) from prize")
    int count();
    @Select("select * from prize order by id desc limit #{offset},#{pageSize}")
    List<PrizeDO> selectPrizeList(@Param("offset") Integer offset,@Param("pageSize") Integer pageSize);
}
