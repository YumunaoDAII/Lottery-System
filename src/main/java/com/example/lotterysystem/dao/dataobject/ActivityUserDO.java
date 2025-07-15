package com.example.lotterysystem.dao.dataobject;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ActivityUserDO extends BaseDO{
    private Long activityId;
    private Long userId;
    private String userName;
    //关联人员状态
    private String status;

}
