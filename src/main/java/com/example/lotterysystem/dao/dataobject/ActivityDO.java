package com.example.lotterysystem.dao.dataobject;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ActivityDO extends BaseDO{
    private String activityName;
    private String description;
    //活动状态
    private String status;
}
