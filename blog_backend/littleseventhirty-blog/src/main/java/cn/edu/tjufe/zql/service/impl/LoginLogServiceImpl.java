package cn.edu.tjufe.zql.service.impl;


import cn.edu.tjufe.zql.constants.Const;
import cn.edu.tjufe.zql.domain.entity.LoginLog;
import cn.edu.tjufe.zql.mapper.LoginLogMapper;
import cn.edu.tjufe.zql.service.ILoginLogService;
import cn.edu.tjufe.zql.utils.BrowserUtil;
import cn.edu.tjufe.zql.utils.IpUtils;
import cn.edu.tjufe.zql.utils.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/3/3
 * @github https://github.com/little-seven-thirty
 */
@Slf4j
@Service("loginLogService")
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements ILoginLogService {
    @Resource
    private RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.exchange.log}")
    private String exchange;    // 日志交换机的名称

    @Value("${spring.rabbitmq.routingKey.log-login}")
    private String routingKey;  // 路由到的地址，或者说邮寄包裹送的地址

    @Override
    public void loginLog(HttpServletRequest request, String userName, Integer state, String message) {
        String browserName = BrowserUtil.browserName(request);
        String ipAddress = IpUtils.getIpAddr(request);
        String os = BrowserUtil.osName(request);
        int requestType;
        String typeHeader = request.getHeader(Const.TYPE_HEADER);   // 自定义请求头
        if (StringUtils.isNotEmpty(typeHeader) && typeHeader.equals(Const.FRONTEND_REQUEST)) {
            requestType = 0;
        } else if (StringUtils.isNotEmpty(typeHeader) && typeHeader.equals(Const.BACKEND_REQUEST)) {
            requestType = 1;
        } else {
            requestType = 2;
        }
        if (userName == null) {
            userName = "未知用户";
        }
        LoginLog logEntity = LoginLog.builder()
                .userName(userName)
                .ip(ipAddress)
                .browser(browserName)
                .os(os)
                .type(requestType)
                .state(state)
                .message(message)
                .build();
        // 非高并发使用rabbit
        rabbitTemplate.convertAndSend(exchange, routingKey, logEntity);
        log.info("{}", "发送登录日志信息--rabbitMQ");
    }
}
