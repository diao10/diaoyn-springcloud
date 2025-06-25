package com.diaoyn.common.listener;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

/**
 * @author diaoyn
 * 监听Redis键过期事件的监听器
 * 当Redis中的键过期时，此监听器会接收到通知，并打印过期的键和相关信息。
 * @ClassName ExpiryListener
 * @Date 2025/6/24 15:59
 */
@Component
public class ExpiryListener implements MessageListener {

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expiredKey = new String(message.getBody());
        String channel = new String(message.getChannel());
        System.out.println("Expired key: " + expiredKey);
        System.out.println("Channel: " + channel);
    }

}
