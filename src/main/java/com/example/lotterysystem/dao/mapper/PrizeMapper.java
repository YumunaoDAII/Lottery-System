package com.example.lotterysystem.dao.mapper;

import com.example.lotterysystem.service.dto.PrizeDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

// PrizeMapper.java
@Mapper
public interface PrizeMapper {
    @Insert("insert into prize (name, image_url, price, description) " +
            "values (#{name}, #{imageUrl}, #{price}, #{description})")  // 移除末尾多余的右括号
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(PrizeDO prizeDO);
}
