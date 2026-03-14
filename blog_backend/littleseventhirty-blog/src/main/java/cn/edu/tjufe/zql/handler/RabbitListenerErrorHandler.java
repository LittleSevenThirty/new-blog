package cn.edu.tjufe.zql.handler;


import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.stereotype.Component;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/3/14
 * @github https://github.com/little-seven-thirty
 */
@Slf4j
@Component("rabbitListenerErrorHandler")
public class RabbitListenerErrorHandler implements MessageRecoverer,org.springframework.amqp.rabbit.listener.api.RabbitListenerErrorHandler {
    /**
     * 错误处理
     * @param message
     * @param channel
     * @param message1
     * @param e
     * @return
     * @throws Exception
     */
    @Override
    public Object handleError(Message message, Channel channel, org.springframework.messaging.Message<?> message1, ListenerExecutionFailedException e) throws Exception {
        log.error("消息处理失败，即将重新处理，{}",e);
        throw e;
    }

    @Override
    public void recover(Message message, Throwable throwable) {
        log.error("消息重新处理失败，即将丢弃，{}",throwable);
        // 送入死序队列处理...
    }
}
