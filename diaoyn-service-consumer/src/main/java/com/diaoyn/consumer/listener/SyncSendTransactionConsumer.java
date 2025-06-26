package com.diaoyn.consumer.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageConst;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
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
        topic = "order_topic_tran",
        consumerGroup = "send-consumer2",
        selectorExpression = "*",
        consumeTimeout = 60, // 消费超时时间，单位为秒
        consumeMode = ConsumeMode.ORDERLY,// 消费模式，支持 CONCURRENTLY（并发消费）和 ORDERLY（顺序消费）
        maxReconsumeTimes = 3 // 最大重试次数

)
public class SyncSendTransactionConsumer implements RocketMQListener<MessageExt> {

    @Override
    public void onMessage(MessageExt message) {
        String orderId = message.getProperty(MessageConst.PROPERTY_KEYS);
        String content = message.getProperty("content");
        log.info("SyncSendTransactionConsumer事务消息被处理了，id = {}, orderId = {}, content = {}", message.getMsgId(),
                orderId, content);
        boolean b = Long.parseLong(orderId) % 2 == 0;
    }


}
