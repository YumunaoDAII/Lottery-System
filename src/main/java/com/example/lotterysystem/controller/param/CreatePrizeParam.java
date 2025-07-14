package com.example.lotterysystem.controller.param;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class CreatePrizeParam implements Serializable {
    @NotBlank(message = "奖品名不能为空")
    private String prizeName;
    private String description;
    @NotNull(message = "价格不能为空")
    private BigDecimal price;

}
