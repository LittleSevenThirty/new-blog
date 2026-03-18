package cn.edu.tjufe.zql.config.rabbit;


import jakarta.annotation.Resource;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @authro 钟奇林
 * @description 请在已有rabbitmq服务情况下配置对应交换机队列，并绑定
 * @date 2026/3/14
 * @github https://github.com/little-seven-thirty
 */
@Configuration
public class EmailRabbitConfig {
    /**
     * 邮件交换机
     */
    @Value("${spring.rabbitmq.exchange.email}")
    private String MAIL_EXCHANGE;
    /**
     * 邮件队列
     */
    @Value("${spring.rabbitmq.queue.email}")
    private String MAIL_QUEUE;
    /**
     * 邮件路由键
     */
    @Value("${spring.rabbitmq.routingKey.email}")
    private String MAIL_ROUTING_KEY;

    /**
     * 交换机声明
     * @return
     */
    @Bean("emailExchange")
    public DirectExchange emailExchange() {
        // durable持久化
        return ExchangeBuilder.directExchange(MAIL_EXCHANGE).durable(true).build();
    }

    /**
     * 队列声明
     * @return
     */
    @Bean("emailQueue")
    public Queue emailQueue() {
        return QueueBuilder.durable(MAIL_QUEUE).build();
    }

    @Bean("emailBinding")
    public Binding emailBinding(@Autowired DirectExchange emailExchange,@Autowired Queue emailQueue) {
        return BindingBuilder.bind(emailQueue).to(emailExchange).with(MAIL_ROUTING_KEY);
    }
}
