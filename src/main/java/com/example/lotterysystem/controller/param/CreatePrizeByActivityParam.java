package com.example.lotterysystem.controller.param;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
@Data
public class CreatePrizeByActivityParam implements Serializable {
    //关联的奖品ID
    @NotNull(message = "活动关联奖品id不能为空")
    private Long prizeId;
    //奖品数量
    @NotNull(message = "奖品数量不能为空")
    private Long prizeAmount;
    //奖品等级
    @NotBlank(message = "活动等级不能为空")
    private String prizeTiers;
}
