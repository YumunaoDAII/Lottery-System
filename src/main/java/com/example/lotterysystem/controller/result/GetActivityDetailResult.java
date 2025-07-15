package com.example.lotterysystem.controller.result;

import com.example.lotterysystem.service.dto.ActivityDetailDTO;
import com.example.lotterysystem.service.enums.ActivityPrizeStatusEnum;
import com.example.lotterysystem.service.enums.ActivityPrizeTiersEnum;
import com.example.lotterysystem.service.enums.ActivityStatusEnum;
import com.example.lotterysystem.service.enums.ActivityUserStatusEnum;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class GetActivityDetailResult implements Serializable {

    // 活动信息

    /**
     * 活动id
     */
    private Long activityId;

    /**
     * 活动名称
     */
    private String activityName;

    /**
     * 活动描述
     */
    private String desc;

    /**
     * 活动状态
     */
    private Boolean valid;


    // 奖品信息（列表）

    private List<Prize> prizeDTOList;

    // 人员信息（列表）

    private List<User> userDTOList;


    @Data
    public static class Prize {
        /**
         * 奖品Id
         */
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

        /**
         * 奖品等奖
         * @see ActivityStatusEnum#getMessage()
         */
        private String tiers;

        /**
         * 奖品数量
         */
        private Long prizeAmount;

        /**
         * 奖品是否有效
         */

        public Boolean valid;
    }

    @Data
    public static class User {
        /**
         * 用户id
         */
        private Long userId;
        /**
         * 姓名
         */
        private String userName;
        /**
         * 人员是否被抽取
         */

        public Boolean valid;
    }
}
