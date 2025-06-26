package com.diaoyn.provider.job;

import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.MessageConst;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author diaoyn
 * @ClassName DevTaskJob
 * @Date 2024/7/15 9:53
 */
@Slf4j
@Component
public class MessageJob {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    public static final String ORDER_TOPIC = "order_topic";
    public static final String ORDER_TOPIC_ORDER = "ORDER_TOPIC_ORDER";
    public static final String ORDER_TOPIC_TRAN = "ORDER_TOPIC_TRAN";


    @Async
    @Scheduled(cron = "0/30 * * * * ?")
    public void execute() {
        // 1. 创建订单
        String orderId = IdUtil.getSnowflake().nextIdStr();
//         redisUtil.set("order_" + id, DateUtil.now(),5);

        Message<String> message = MessageBuilder.withPayload("异步消息内容")
                .setHeader(MessageConst.PROPERTY_TAGS, "order")
                .setHeader(MessageConst.PROPERTY_KEYS, orderId)
                .setHeader("content", "异步消息内容")
                .build();

        // 2. 发送延迟消息(对应延迟级别16=30分钟)
//        rocketMQTemplate.syncSend(ORDER_TOPIC,
//                message,
//                rocketMQTemplate.getProducer().getSendMsgTimeout(),
//                2 // 延迟级别16对应30分钟
//        );
//        log.info("订单创建成功，已发送延迟消息，订单ID：{}", orderId);
//         3. 顺序消息
        rocketMQTemplate.asyncSendOrderly(ORDER_TOPIC_ORDER, message, orderId, new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        System.out.println("异步发送成功: " + sendResult.getMsgId());
                    }

                    @Override
                    public void onException(Throwable e) {
                        System.err.println("异步发送失败: " + e.getMessage());
                    }
                },
                rocketMQTemplate.getProducer().getSendMsgTimeout(),
                2);
        log.info("订单创建成功，已发送顺序消息，订单ID：{}", orderId);

//        // 3. 发送事务消息
//        rocketMQTemplate.sendMessageInTransaction(
//                ORDER_TOPIC_TRAN,
//                message,
//                null
//        );
//        log.info("订单创建成功，已发送事务消息，订单ID：{}", orderId);

    }
}
