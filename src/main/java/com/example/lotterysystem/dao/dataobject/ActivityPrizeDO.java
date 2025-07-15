package com.example.lotterysystem.dao.dataobject;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ActivityPrizeDO extends BaseDO{
    //活动id
    private Long activityId;
    //奖品id
    private Long prizeId;
    private Long prizeAmount;
    //奖品等级
    private String prizeTiers;
    //奖品状态
    private String status;

}
