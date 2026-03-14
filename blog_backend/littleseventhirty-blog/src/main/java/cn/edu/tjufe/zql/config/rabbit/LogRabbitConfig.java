package cn.edu.tjufe.zql.config.rabbit;


import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @authro 钟奇林
 * @description 请在已有rabbitmq服务情况下启动会自动配置对应交换机队列，并绑定
 * @date 2026/3/14
 * @github https://github.com/little-seven-thirty
 */
@Configuration
public class LogRabbitConfig {
    /**
     * 日志交换机
     */
    @Value("${spring.rabbitmq.exchange.log}")
    private String LOG_EXCHANGE;
    /**
     * 登录日志交换队列
     */
    @Value("${spring.rabbitmq.queue.log-login}")
    private String LOG_LOGIN_QUEUE;
    /**
     * 统一日志交换队列
     */
    @Value("${spring.rabbitmq.queue.log-system}")
    private String LOG_SYSYTEM_QUEUE;
    /**
     * 登录日志key
     */
    @Value("${spring.rabbitmq.routingKey.log-login}")
    private String LOG_LOGIN_ROUTING_KEY;
    /**
     * 统一日志key
     */
    @Value("${spring.rabbitmq.routingKey.log-system}")
    private String LOG_SYSTEM_ROUTING_KEY;

    /**
     * 声明交换机
     * @return
     */
    @Bean("logExchange")
    public DirectExchange logExchange(){
        return ExchangeBuilder.directExchange(LOG_EXCHANGE).durable(true).build();
    }

    /**
     * 声明登录日志队列
     * @return
     */
    @Bean("logLoginQueue")
    public Queue logLoginQueue(){
        return QueueBuilder.durable(LOG_LOGIN_QUEUE).build();
    }
    /**
     * 绑定交换机和登录日志队列
     */
    @Bean("logLoginBinding")
    public Binding logLoginBinding(@Autowired DirectExchange logExchange,@Autowired Queue logLoginQueue){
        return BindingBuilder.bind(logLoginQueue).to(logExchange).with(LOG_LOGIN_ROUTING_KEY);
    }

    /**
     * 声明统一日志队列
     *
     */
    @Bean("logSystemQueue")
    public Queue logSystemQueue(){
        return QueueBuilder.durable(LOG_SYSYTEM_QUEUE).build();
    }

    /**
     * 绑定
     */
    @Bean("logSystemBinding")
    public Binding logSystemBinding(@Autowired DirectExchange logExchange,@Autowired Queue logSystemQueue){
        return BindingBuilder.bind(logSystemQueue).to(logExchange).with(LOG_SYSTEM_ROUTING_KEY);
    }
}
