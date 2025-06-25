package com.diaoyn.common.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.data.redis.stream.Subscription;

import java.time.Duration;

/**
 * @author diaoyn
 * @ClassName RedisStreamConfig
 * @Date 2025/6/24 17:20
 */
@Configuration
public class RedisStreamConfig {

    public static final String ORDER_TIMEOUT = "order_timeout";
    public static final String ORDERS_GROUP = "orders_group";

    /**
     * 创建StreamMessageListenerContainer
     */
    @Bean
    public StreamMessageListenerContainer<String, ObjectRecord<String, String>> streamMessageListenerContainer(
            RedisConnectionFactory redisConnectionFactory) {
        StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, ObjectRecord<String, String>> options =
                StreamMessageListenerContainer.StreamMessageListenerContainerOptions
                        .builder()
                        .pollTimeout(Duration.ofSeconds(1))
                        .batchSize(100)
                        .targetType(String.class)
                        .build();

        return StreamMessageListenerContainer.create(redisConnectionFactory, options);
    }

    /**
     * 创建消费者组
     */
    @Bean
    public CommandLineRunner createConsumerGroup(RedisTemplate<String, String> redisTemplate) {
        return args -> {
            try {
                redisTemplate.opsForStream().createGroup(ORDER_TIMEOUT, ORDERS_GROUP);
            } catch (RedisSystemException e) {
                // 消费者组已存在时忽略错误
                if (!e.getCause().getMessage().contains("BUSYGROUP")) {
                    throw e;
                }
            }
        };
    }

    /**
     * 订阅订单超时流
     *
     * @param container      StreamMessageListenerContainer
     * @param streamListener StreamListener
     * @return Subscription
     */
    @Bean
    public Subscription subscription(
            StreamMessageListenerContainer<String, ObjectRecord<String, String>> container,
            StreamListener<String, ObjectRecord<String, String>> streamListener) {

        return container.receive(
                Consumer.from(RedisStreamConfig.ORDERS_GROUP, "consumer1"),
                StreamOffset.create(ORDER_TIMEOUT, ReadOffset.lastConsumed()),
                streamListener);
    }


    @Bean
    public StreamListener<String, ObjectRecord<String, String>> streamListener(RedisTemplate<String, String> redisTemplate) {
        return message -> {
            String orderId = message.getId().getValue();

            String id = message.getValue();
            boolean b = Long.parseLong(id) % 2 == 0;
            if (b) {
                System.out.println("被处理了，id = " + id);
                // 确认消息已处理
                redisTemplate.opsForStream().acknowledge(ORDER_TIMEOUT, ORDERS_GROUP, message.getId());
                // 然后显式删除消息
//                redisTemplate.opsForStream().delete(ORDER_TIMEOUT_STREAM, message.getId());
            } else {
                // 订单未过期，重新放入pending列表

                System.out.println("放回去了，id = " + id);
            }
        };
    }
}
