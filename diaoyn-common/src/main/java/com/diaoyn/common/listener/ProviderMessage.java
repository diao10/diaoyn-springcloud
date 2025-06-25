package com.diaoyn.common.listener;

import cn.hutool.core.util.IdUtil;
import com.diaoyn.common.config.RedisStreamConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.stereotype.Service;

/**
 * @author diaoyn
 * @ClassName ProviderMessage
 * @Date 2025/6/25 17:33
 */
@Service
public class ProviderMessage {


    /**
     * 执行方法，向Redis Stream中添加订单超时信息
     *
     * @param listenerContainer 监听容器
     * @param redisTemplate     Redis模板
     */
    @Bean
    public void execute(StreamMessageListenerContainer<String, ObjectRecord<String, String>> listenerContainer,
                        RedisTemplate<String, String> redisTemplate) {
        // 启动监听容器
        listenerContainer.start();
        String id = IdUtil.getSnowflake().nextIdStr();
//         redisUtil.set("order_" + id, DateUtil.now(),5);

        // 将订单ID和超时时间写入Stream
        ObjectRecord<String, String> record = StreamRecords.objectBacked(id)
                .withStreamKey(RedisStreamConfig.ORDERS_GROUP);
        // 添加记录到Redis Stream
        redisTemplate.opsForStream().add(record);

    }
}
