package com.diaoyn.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author diaoyn
 * redis序列化配置
 */
@Configuration
public class RedisConfig<K, V> {
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


}
