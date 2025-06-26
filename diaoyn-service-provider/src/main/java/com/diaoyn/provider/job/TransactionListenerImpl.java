package com.diaoyn.provider.job;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;

/**
 * @author diaoyn
 * @ClassName RocketMQLocalTransactionListener1
 * @Date 2025/6/26 14:04
 */
@Slf4j
@RocketMQTransactionListener
public class TransactionListenerImpl implements RocketMQLocalTransactionListener {
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        try {
            // 这里执行本地数据库操作等业务逻辑
            // 如果执行成功，返回COMMIT
            return RocketMQLocalTransactionState.COMMIT;

        } catch (Exception e) {
            log.error("本地事务执行失败", e);
            return RocketMQLocalTransactionState.ROLLBACK;
        }


    }

    /**
     * 检查本地事务状态
     * @param msg 消息
     * @return 事务状态
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        // 根据业务状态检查事务是否成功
        // 例如：查询数据库判断事务是否完成
        return RocketMQLocalTransactionState.COMMIT;
    }


}
