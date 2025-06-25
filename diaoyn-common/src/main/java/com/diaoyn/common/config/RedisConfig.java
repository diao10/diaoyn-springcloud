package com.diaoyn.common.config;

import com.diaoyn.common.listener.ExpiryListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author diaoyn
 * redis序列化配置
 */
@Configuration
public class RedisConfig<K, V> {
    /**
     * RedisTemplate配置
     *
     * @param redisConnectionFactory redis连接工厂对象
     * @return RedisTemplate
     */
    @Bean
    public RedisTemplate<K, V> redisTemplate(RedisConnectionFactory redisConnectionFactory) {

        RedisTemplate<K, V> redisTemplate = new RedisTemplate<>();
        //设置redis连接工厂对象
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        //设置 redis key 的序列化器
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //设置 redis 值的序列化器
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));

        return redisTemplate;
    }


    /**
     * 监听redis的过期事件
     *
     * @param connectionFactory redis连接工厂对象
     * @param listenerAdapter   监听适配器对象
     * @return RedisMessageListenerContainer
     */
    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                            MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, new PatternTopic("__keyevent@0__:expired"));
        return container;
    }

    /**
     * 监听适配器
     *
     * @param listener 监听器对象
     * @return MessageListenerAdapter
     */
    @Bean
    MessageListenerAdapter listenerAdapter(ExpiryListener listener) {
        return new MessageListenerAdapter(listener, "handleMessage");
    }

}
