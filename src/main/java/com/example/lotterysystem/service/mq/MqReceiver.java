package com.example.lotterysystem.service.mq;
import com.example.lotterysystem.common.exception.ServiceException;
import com.example.lotterysystem.common.utils.JacksonUtil;

import com.example.lotterysystem.controller.param.DrawPrizeParam;
import com.example.lotterysystem.controller.param.MessageWrapper;
import com.example.lotterysystem.service.DrawPrizeService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListeners;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Map;

import static com.example.lotterysystem.common.config.DirectRabbitConfig.QUEUE_NAME;

@Slf4j
@Component
@Service
@RabbitListener(queues = QUEUE_NAME)
@EnableRabbit
public class MqReceiver {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private DrawPrizeService drawPrizeService;
//
//    @RabbitListener(queues = QUEUE_NAME)
//    public void process(MessageWrapper wrapper) {
//        try {
////            log.info("MQ成功接收到消息,message:{}", messageJson);
////            Map<String, String> message = JacksonUtil.readValue(messageJson, Map.class);
////            String paramStr = message.get("messageData");
////            DrawPrizeParam param = JacksonUtil.readValue(paramStr, DrawPrizeParam.class);
//            log.info("MQ接收到消息: {}", JacksonUtil.writeValueAsString(wrapper));
//            DrawPrizeParam param = wrapper.getMessageData();
//            log.info("抽奖参数已解析: {}", param);
//            // 继续处理...
//        } catch (Exception e) {
//            log.error("处理消息时出现异常", e);
//        }
//    }


    @RabbitListener(queues = QUEUE_NAME)
    public void process(Map<String, String> message) {

            log.info("MQ成功接收到消息,message:{}", message);

            // 直接从 Map 中取 JSON 字符串并解析
            String paramStr = message.get("messageData");
            DrawPrizeParam param = JacksonUtil.readValue(paramStr, DrawPrizeParam.class);

            log.info("抽奖参数解析成功: {}", param);
            // 后续业务处理逻辑
            //处理抽奖流程

            try {
                //校验抽奖请求是否有效
                drawPrizeService.checkDrawPrize(param);
                //状态扭转!!!!!!!

                //保存中奖者名单

                //通知中奖者
            }catch (ServiceException e){
                log.error("处理MQ消息异常！{}：{}",e.getCode(),e.getMessage(),e);
            }catch (Exception e){
                log.error("处理MQ消息异常！:{}",e);
            }

            //如果异常，需要保证事物一致性（回滚），抛出异常
    }

}