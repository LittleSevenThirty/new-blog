package cn.edu.tjufe.zql.service.impl;

import cn.edu.tjufe.zql.constants.RedisConst;
import cn.edu.tjufe.zql.service.IPublicService;
import cn.edu.tjufe.zql.utils.RedisCache;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author: littleseventhirty
 * @description: 公共服务实现类(包含rabbitmq邮件生产者)
 * @date: 2026/2/26
 **/
@Slf4j
@Service(value = "publicService")
public class PublicServiceImpl implements IPublicService {

    @Resource
    private RedisCache redisCache;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.routingKey.email}")
    private String routingKey;

    @Value("${spring.rabbitmq.exchange.email}")
    private String exchange;

    /**
     * 发送邮箱验证码
     * @param type  邮箱类型
     * @param email  邮箱地址
     * @return
     */
    @Override
    public String registerEmailVerifyCode(String type, String email) {
        // 加锁，防止同一时间被同一人调用多次
        synchronized (email.intern()) {
            // 生成验证码
            String verifyCode = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
            // 保存到redis，设置过期时间为5分钟
            redisCache.setCacheObject(RedisConst.VERIFY_CODE + type + RedisConst.SEPARATOR + email, verifyCode, RedisConst.VERIFY_CODE_EXPIRATION, TimeUnit.MINUTES);
            // 发送邮件
            Map<String, Object> senEmail = Map.of("email", email, "code", verifyCode, "type", type);
            rabbitTemplate.convertAndSend(exchange, routingKey, senEmail);

            return "验证码已发送，请注意查收！";
        }
    }

    @Override
    public void sendEmail(String type, String email, Map<String, Object> content) {
        // 发送邮件
        if (content != null) {
            content.put("email", email);
            content.put("type", type);
            rabbitTemplate.convertAndSend(exchange, routingKey, content);
        } else rabbitTemplate.convertAndSend(exchange, routingKey, Map.of("email", email, "type", type));

        log.info("邮件通知消息发送完毕，发送时间为：{}，发送的消息类型：{}，发送的邮箱：{}", new Date(), type, email);
    }
}
