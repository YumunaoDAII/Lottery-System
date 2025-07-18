package com.example.lotterysystem.service.Impl;

import com.example.lotterysystem.common.errorcode.ServiceErrorCodeConstants;
import com.example.lotterysystem.common.exception.ServiceException;
import com.example.lotterysystem.common.utils.JacksonUtil;
import com.example.lotterysystem.controller.param.DrawPrizeParam;
import com.example.lotterysystem.controller.param.MessageWrapper;
import com.example.lotterysystem.dao.dataobject.ActivityDO;
import com.example.lotterysystem.dao.dataobject.ActivityPrizeDO;
import com.example.lotterysystem.dao.mapper.ActivityMapper;
import com.example.lotterysystem.dao.mapper.ActivityPrizeMapper;
import com.example.lotterysystem.service.DrawPrizeService;
import com.example.lotterysystem.service.enums.ActivityPrizeStatusEnum;
import com.example.lotterysystem.service.enums.ActivityStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.example.lotterysystem.common.config.DirectRabbitConfig.EXCHANGE_NAME;
import static com.example.lotterysystem.common.config.DirectRabbitConfig.ROUTING;


@Slf4j
@Service
public class DrawPrizeServiceImpl implements DrawPrizeService {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private ActivityMapper activityMapper;
    @Autowired
    private ActivityPrizeMapper activityPrizeMapper;

    @Override
    public void drawPrize(DrawPrizeParam param) {
//        MessageWrapper wrapper = new MessageWrapper();
//        wrapper.setMessageId(UUID.randomUUID().toString());
//        wrapper.setMessageData(param);
//        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING, wrapper);
//
////        log.info("mq准备消息发送：map={}", JacksonUtil.writeValueAsString(map));
////        // 发消息: 交换机、绑定的key、消息体
////        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING, map);
//        log.info("mq消息发送成功：map={}", JacksonUtil.writeValueAsString(wrapper));

        Map<String, String> map = new HashMap<>();
        map.put("messageId", UUID.randomUUID().toString());
        map.put("messageData", JacksonUtil.writeValueAsString(param));

        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING, map);
        log.info("mq消息发送成功：map={}", JacksonUtil.writeValueAsString(map));
    }

    @Override
    public void checkDrawPrize(DrawPrizeParam param) {
        ActivityDO activityDO = activityMapper.selectById(param.getActivityId());
        //奖品是否可以从activity_prize中查找，保存activityDO时做了本地事物，保证一致性
        ActivityPrizeDO activityPrizeDO= activityPrizeMapper.selectByAPId(param.getActivityId(),param.getPrizeId());
        //活动或奖品是否存在
        if (null==activityDO||null==activityPrizeDO){
            throw new ServiceException(ServiceErrorCodeConstants.ACTIVITY_OR_PRIZE_IS_EMPTY);
        }
        //活动是否有效
        if (activityDO.getStatus().equalsIgnoreCase(ActivityStatusEnum.COMPLETED.name())){
            throw new ServiceException(ServiceErrorCodeConstants.ACTIVITY_COMPLETED);
        }
        //奖品是否有效
        if (activityPrizeDO.getStatus().equalsIgnoreCase(ActivityPrizeStatusEnum.COMPLETED.name())){
            throw new ServiceException(ServiceErrorCodeConstants.ACTIVITY_PRIZE_COMPLETED);
        }
        //中奖者人数是否和设置奖品数合适
        if (activityPrizeDO.getPrizeAmount()!=param.getWinnerList().size()){
            throw new ServiceException(ServiceErrorCodeConstants.WINNER_PRIZE_AMOUNT_ERROR);
        }
    }
}