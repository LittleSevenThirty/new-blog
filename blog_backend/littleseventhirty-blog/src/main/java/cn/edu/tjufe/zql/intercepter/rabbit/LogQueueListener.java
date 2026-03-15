package cn.edu.tjufe.zql.intercepter.rabbit;


import cn.edu.tjufe.zql.domain.entity.Log;
import cn.edu.tjufe.zql.domain.entity.LoginLog;
import cn.edu.tjufe.zql.mapper.LogMapper;
import cn.edu.tjufe.zql.mapper.LoginLogMapper;
import cn.edu.tjufe.zql.service.IIpService;
import cn.edu.tjufe.zql.service.ILogService;
import cn.edu.tjufe.zql.service.ILoginLogService;
import com.rabbitmq.client.Channel;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 *
 * @authro 钟奇林
 * @description rabbitmq日志队列监听者（消费者）
 * @date 2026/3/15
 * @github https://github.com/little-seven-thirty
 */
@Component
@Slf4j
public class LogQueueListener {
    @Resource
    private LoginLogMapper loginLogMapper;
    @Resource
    private IIpService ipService;
    @Resource
    private LogMapper logMapper;

    @RabbitListener(queues="${spring.rabbitmq.queue.log-login}",
            concurrency="5-10",
            errorHandler = "rabbitListenerErrorHandler"
    )
    public void LoginLogQueueListener(LoginLog loginLog, Channel channel,@Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        log.info("监听登录日志队列，标识：{}，数据：{}",tag,loginLog);
        if (loginLog.getBrowser().startsWith("Unknown")) {
            loginLog.setBrowser("未知");
        }
        if (loginLog.getOs().startsWith("Unknown")) {
            loginLog.setOs("未知");
        }
        if (loginLog.getType() == null) {
            loginLog.setType(2);
        }
        if (loginLogMapper.insert(loginLog) > 0) {
            ipService.refreshIpDetailAsyncByLogIdAndLogin(loginLog.getLoginLogId());
        }
        log.info("登录日志标识:{}，数据库添加成功", tag);
    }

    @RabbitListener(queues="${spring.rabbitmq.queue.log-system}",
            concurrency="5-10",
            errorHandler = "rabbitListenerErrorHandler"
    )
    public void SystemLogQueueListener(Log systemLog) {
        log.info("-------------------系统日志记录-----------------------");
        if(logMapper.insert(systemLog)>0){
            ipService.refreshIpDetailAsyncByLogId(systemLog.getLogId());
        }
        log.info("-------------系统操作日志插入数据库成功-----------------");
    }
}
