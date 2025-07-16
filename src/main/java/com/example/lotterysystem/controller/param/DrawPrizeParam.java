package com.example.lotterysystem.controller.param;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class DrawPrizeParam {
    @NotNull(message = "活动id不能为空")
    private Long activityId;
    @NotNull(message = "奖品id不能为空")
    private Long prizeId;
    @NotBlank(message = "奖品等级不能为空")
    private String prizeTiers;
    @NotNull(message = "中将时间不能为空")
    private Date winningTime;
    @NotEmpty(message = "中奖者列表不能为空")
    @Valid
    private List<winner> winnerList;
    @Data
    public static class winner{
        @NotNull(message = "中奖者id不能为空")
        private Long userId;
        @NotBlank(message = "中奖者姓名不能为空")
        private String userName;
    }
}
