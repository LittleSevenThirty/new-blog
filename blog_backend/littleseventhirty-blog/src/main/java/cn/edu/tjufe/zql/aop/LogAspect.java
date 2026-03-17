package cn.edu.tjufe.zql.aop;


import cn.edu.tjufe.zql.annotation.LogAnnotation;
import cn.edu.tjufe.zql.constants.FunctionConst;
import cn.edu.tjufe.zql.domain.entity.Log;
import cn.edu.tjufe.zql.domain.entity.User;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
import cn.edu.tjufe.zql.mapper.UserMapper;
import cn.edu.tjufe.zql.utils.IpUtils;
import cn.edu.tjufe.zql.utils.SecurityUtils;
import cn.edu.tjufe.zql.utils.StringUtils;
import com.alibaba.fastjson2.JSON;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @authro 钟奇林
 * @description 操作日志 aop
 * @date 2026/3/15
 * @github https://github.com/little-seven-thirty
 */
@Component
@Slf4j
@Aspect
public class LogAspect {
    @Resource
    private UserMapper userMapper;
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Value("${spring.rabbitmq.exchange.log}")
    private String exchange;
    @Value("${spring.rabbitmq.routingKey.log-system}")
    private String routingKey;


    /**
     * 切点，使用了LogAnnotation注解的都是切点
     */
    @Pointcut("@annotation(cn.edu.tjufe.zql.annotation.LogAnnotation)")
    public void point(){}

    // 环绕通知，在方法执行前后执行
    @Around("point()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        try{
            // ①执行方法前可以插入其它操作
            // 。。。
            // ②执行方法
            Object result = joinPoint.proceed();
            // ③执行方法后操作
            long time = System.currentTimeMillis() - startTime;
            // 记录日志
            recordLog(joinPoint,time,result);
            // 打印日志
            log.info("【{}】类执行【{}】方法，耗时【{}】毫秒",joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(), time);
            return result;
        }catch(Throwable e){
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);
            long time = System.currentTimeMillis() - startTime;
            // 获取 request 设置IP地址
            HttpServletRequest request = SecurityUtils.getCurrentHttpRequest();
            // 请求的方法名
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = signature.getName();
            assert request != null;
            // 是否前台
            String ipAddr = IpUtils.getIpAddr(request);
            User user = userMapper.selectById(SecurityUtils.getUserId());

            Object[] args = joinPoint.getArgs();
            List<Object> multipartFile = new ArrayList<>();
            for (Object arg : args) {
                if (arg instanceof MultipartFile) {
                    // 这个arg是MultipartFile类型
                    multipartFile.add(arg);
                }
            }
            Log logEntity = Log.builder()
                    .module(logAnnotation.module())
                    .operation(logAnnotation.operation())
                    .ip(ipAddr)
                    .exception(e.getMessage())
                    .reqMapping(request.getMethod())
                    .username(StringUtils.isNull(user) ? FunctionConst.UNKNOWN_USER : user.getUsername())
                    .state(2)
                    .exception(e.getMessage())
                    .method(className + "." + methodName + "()")
                    .reqParameter(!multipartFile.isEmpty() ? multipartFile.toString() : JSON.toJSONString(joinPoint.getArgs()))
                    .reqAddress(request.getRequestURI())
                    .time(time)
                    .build();
            rabbitTemplate.convertAndSend(exchange,routingKey,logEntity);
            log.error("【{}】执行方法【{}】异常", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(), e);
            // 抛出异常，被全局异常处理器拦截进行处理
            throw e;
        }
    }

    /**
     * 记录日志
     * @param joinPoint 切面拦截的目标方法
     * @param time 方法执行时间
     * @param result 方法执行返回值
     */
    private void recordLog(ProceedingJoinPoint joinPoint,long time,Object result){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        // 获取注解
        LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);
        // 获取操作信息
        Operation operation = method.getAnnotation(Operation.class);

        // 获取http请求
        HttpServletRequest request = SecurityUtils.getCurrentHttpRequest();
        // 获取信息，类名，方法名，ip地址，当前用户，被切面的目标方法参数
        String className=joinPoint.getTarget().getClass().getName();
        String methodName=signature.getName();
        assert request!=null;
        String ipAddress= IpUtils.getIpAddr(request);
        User user=userMapper.selectById(SecurityUtils.getUserId());

        Object[] args= joinPoint.getArgs();
        List<Object> multipartFiles=new ArrayList<Object>();
        for(Object arg:args){
            if(arg instanceof MultipartFile){
                multipartFiles.add(arg);
            }
        }

        // 构建日志实体
        Log logEntity=Log.builder()
                .module(logAnnotation.module())
                .operation(logAnnotation.operation())
                .ip(ipAddress)
                .address("")
                .description(operation.summary())
                .reqMapping(request.getMethod())
                .username(StringUtils.isNull(user) ? FunctionConst.UNKNOWN_USER : user.getUsername())
                .method(className + "." + methodName + "()")
                .reqParameter(!multipartFiles.isEmpty() ? multipartFiles.toString() : JSON.toJSONString(joinPoint.getArgs()))
                .returnParameter(JSON.toJSONString(result))
                .reqAddress(request.getRequestURI())
                .time(time)
                .build();
        // TODO ResponseResult为空
        ResponseResult responseResult=(ResponseResult)result;
        if(responseResult!=null&&"200".equals(responseResult.getCode())){
            logEntity.setState(0);  // 完成
        }else{
            logEntity.setState(1); // 失败
        }

        rabbitTemplate.convertAndSend(exchange,routingKey,logEntity);
        log.info("耗时：{}毫秒", time);
        log.info("操作时间：{}", new Date());
        log.info("================日志结束=========================");
    }
}
