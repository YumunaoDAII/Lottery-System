package com.example.lotterysystem.service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PrizeDTO {
    private Long prizeId;
    /**
     * 奖品名
     */
    private String name;
    /**
     * 图片索引
     */
    private String imageUrl;
    /**
     * 价格
     */
    private BigDecimal price;
    /**
     * 描述
     */
    private String description;
}
