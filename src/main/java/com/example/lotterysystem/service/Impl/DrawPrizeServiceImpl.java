package com.example.lotterysystem.service.Impl;

import com.example.lotterysystem.common.utils.JacksonUtil;
import com.example.lotterysystem.controller.param.DrawPrizeParam;
import com.example.lotterysystem.controller.param.MessageWrapper;
import com.example.lotterysystem.service.DrawPrizeService;
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
}