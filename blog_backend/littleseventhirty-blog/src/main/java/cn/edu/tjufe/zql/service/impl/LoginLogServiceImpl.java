package cn.edu.tjufe.zql.service.impl;

import cn.edu.tjufe.zql.constants.Const;
import cn.edu.tjufe.zql.domain.dto.LoginLogDTO;
import cn.edu.tjufe.zql.domain.dto.LoginLogDeleteDTO;
import cn.edu.tjufe.zql.domain.entity.LoginLog;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
import cn.edu.tjufe.zql.domain.vo.LoginLogVO;
import cn.edu.tjufe.zql.mapper.LoginLogMapper;
import cn.edu.tjufe.zql.service.ILoginLogService;
import cn.edu.tjufe.zql.utils.BrowserUtil;
import cn.edu.tjufe.zql.utils.IpUtils;
import cn.edu.tjufe.zql.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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

    @Resource
    private LoginLogMapper loginLogMapper;

    @Value("${spring.rabbitmq.exchange.log}")
    private String exchange; // 日志交换机的名称

    @Value("${spring.rabbitmq.routingKey.log-login}")
    private String routingKey; // 路由到的地址，或者说邮寄包裹送的地址

    @Override
    public void loginLog(HttpServletRequest request, String userName, Integer state, String message) {
        String browserName = BrowserUtil.browserName(request);
        String ipAddress = IpUtils.getIpAddr(request);
        String os = BrowserUtil.osName(request);
        int requestType;
        String typeHeader = request.getHeader(Const.TYPE_HEADER); // 自定义请求头
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
                .username(userName)
                .ip(ipAddress)
                .browser(browserName)
                .os(os)
                .type(requestType)
                .state(state)
                .message(message)
                .createTime(new Date())
                .updateTime(new Date())
                .isDeleted(0)
                .build();
        // 非高并发使用rabbit
        try {
            rabbitTemplate.convertAndSend(exchange, routingKey, logEntity);
            log.info("{}", "发送登录日志信息--rabbitMQ");
        } catch (Exception e) {
            // 捕获RabbitMQ连接异常，确保登录功能不受影响
            log.error("RabbitMQ连接失败，登录日志发送失败: {}", e.getMessage());
            // 可以选择将日志保存到数据库或其他方式
            this.save(logEntity);
            log.info("登录日志已保存到数据库");
        }
    }

    @Override
    public List<LoginLogVO> searchLoginLog(LoginLogDTO loginLogDTO) {
        LambdaQueryWrapper<LoginLog> wrapper = new LambdaQueryWrapper<>();
        if (Objects.nonNull(loginLogDTO)) {
            wrapper.like(StringUtils.isNotEmpty(loginLogDTO.getAddress()), LoginLog::getAddress, loginLogDTO.getAddress())
                    .like(StringUtils.isNotEmpty(loginLogDTO.getUsername()), LoginLog::getUsername, loginLogDTO.getUsername())
                    .eq(StringUtils.isNotNull(loginLogDTO.getState()), LoginLog::getState, loginLogDTO.getState());
            if (StringUtils.isNotNull(loginLogDTO.getLoginTimeStart()) && StringUtils.isNotNull(loginLogDTO.getLoginTimeEnd())) {
                wrapper.gt(LoginLog::getCreateTime, loginLogDTO.getLoginTimeStart()).and(a -> a.lt(LoginLog::getCreateTime, loginLogDTO.getLoginTimeEnd()));
            }
        }
        wrapper.orderByDesc(LoginLog::getCreateTime);
        return loginLogMapper.selectList(wrapper).stream().map(loginLog -> loginLog.asViewObject(LoginLogVO.class,v -> v.setLoginTime(loginLog.getCreateTime()))).toList();
    }

    @Override
    public ResponseResult<Void> deleteLoginLog(LoginLogDeleteDTO loginLogDeleteDTO) {
        if (this.removeByIds(loginLogDeleteDTO.getIds())) {
            return ResponseResult.success();
        }
        return ResponseResult.failure();
    }
}
