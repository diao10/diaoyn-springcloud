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
@RocketMQMessageListener(topic = "%DLQ%send-consumer2", consumerGroup = "dlq-handler-group")
public class DlqConsumer implements RocketMQListener<MessageExt> {


    @Override
    public void onMessage(MessageExt message) {
        String orderId = message.getProperty(MessageConst.PROPERTY_KEYS);
        String content = message.getProperty("content");
        // 获取重试次数
        int reconsumeTimes = message.getReconsumeTimes();
        log.info("死信消息被处理了，id = {}, orderId = {}, content = {}, 重试次数 = {}", message.getMsgId(), orderId, content,
                reconsumeTimes);
    }


}
