package com.diaoyn.consumer.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageConst;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

/**
 * @author diaoyn
 * @ClassName OrderCancelConsumer
 * @Date 2025/6/26 13:10
 */
@Slf4j
@Service
@RocketMQMessageListener(
        topic = "order_topic",
        consumerGroup = "send-consumer",
        selectorExpression = "*",
        consumeTimeout = 60, // 消费超时时间，单位为秒
        maxReconsumeTimes = 3 // 最大重试次数
)
public class SyncSendConsumer2 implements RocketMQListener<MessageExt> {

    @Override
    public void onMessage(MessageExt message) {
        String orderId = message.getProperty(MessageConst.PROPERTY_KEYS);
        String content = message.getProperty("content");
        log.info("SyncSendConsumer2延迟消息被处理了，id = {}, orderId = {}, content = {}", message.getMsgId(), orderId, content);
        boolean b = Long.parseLong(orderId) % 2 == 0;

    }


}
