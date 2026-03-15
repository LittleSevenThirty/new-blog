package cn.edu.tjufe.zql.service.impl;


import cn.edu.tjufe.zql.constants.ThirdPartyInterfaceConst;
import cn.edu.tjufe.zql.domain.dto.IpResultDTO;
import cn.edu.tjufe.zql.domain.entity.Log;
import cn.edu.tjufe.zql.domain.entity.LoginLog;
import cn.edu.tjufe.zql.domain.entity.User;
import cn.edu.tjufe.zql.domain.ip.IpDetail;
import cn.edu.tjufe.zql.handler.GlobalUncaughtExceptionHandler;
import cn.edu.tjufe.zql.mapper.LogMapper;
import cn.edu.tjufe.zql.mapper.LoginLogMapper;
import cn.edu.tjufe.zql.mapper.UserMapper;
import cn.edu.tjufe.zql.service.IIpService;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.thread.NamedThreadFactory;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.*;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/3/2
 * @github https://github.com/little-seven-thirty
 */
@Slf4j
@Service
public class IpServiceImpl implements IIpService, DisposableBean {
    private static final ExecutorService EXECUTOR=new ThreadPoolExecutor(1,1,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(500),
            new NamedThreadFactory("refresh-ipDetail",null,false, GlobalUncaughtExceptionHandler.getINSTANCE())
    );

    @Resource
    private UserMapper userMapper;
    @Resource
    private LoginLogMapper loginLogMapper;
    @Autowired
    private LogMapper logMapper;

    @Override
    public void destroy() throws Exception {

    }

    @Override
    public void refreshIpDetailAsyncByUidAndLogin(Long uid) {
        EXECUTOR.execute(() -> {
            User user = userMapper.selectById(uid);
            if (Objects.isNull(user)) {
                return;
            }
            String ip = user.getLoginIp();      // 获取登录ip
            if (StrUtil.isBlank(ip)) {
                return;
            }
            IpDetail ipDetail = TryGetIpDetailOrNullTreeTimes(ip);
            if (Objects.nonNull(ipDetail)) {
                 user.setLoginAddress(buildAddr(ipDetail.getRegion(), ipDetail.getCity(), ipDetail.getCountry()));
            } else {
                user.setRegisterAddress("未知");
                log.error("login get ip detail fail ip:{},uid:{}", ip, uid);
            }
            userMapper.updateById(user);
        });
    }

    @Override
    public void refreshIpDetailAsyncByUidAndRegister(Long uid) {
        EXECUTOR.execute(() -> {
            User user = userMapper.selectById(uid);
            if (Objects.isNull(user)) {
                return;
            }
            String ip = user.getRegisterIp();   // 获取注册ip
            if (StrUtil.isBlank(ip)) {
                return;
            }
            IpDetail ipDetail = TryGetIpDetailOrNullTreeTimes(ip);
            if (Objects.nonNull(ipDetail)) {
                 user.setRegisterAddress(buildAddr(ipDetail.getRegion(), ipDetail.getCity(), ipDetail.getCountry()));
            } else {
                user.setRegisterAddress("未知");
                log.error("register get ip detail fail ip:{},uid:{}", ip, uid);
            }
            userMapper.updateById(user);
        });
    }

    @Override
    public void refreshIpDetailAsyncByLogIdAndLogin(Long loginLogId) {
        EXECUTOR.execute(() -> {
            LoginLog loginLog = loginLogMapper.selectById(loginLogId);
            if (Objects.isNull(loginLog)) {
                return;
            }
            String ip = loginLog.getIp();
            if (StrUtil.isBlank(ip)) {
                return;
            }
            IpDetail ipDetail = TryGetIpDetailOrNullTreeTimes(ip);
            if (Objects.nonNull(ipDetail)) {
                loginLog.setAddress(buildAddr(ipDetail.getRegion(), ipDetail.getCity(), ipDetail.getCountry()));
            } else {
                loginLog.setAddress("未知");
                log.error("loginLog get ip detail fail ip:{},loginLogId:{}", ip, loginLogId);
            }
            loginLogMapper.updateById(loginLog);
        });
    }

    /**
     * 异步刷新操作日志ip详情获取
     *
     * @param logId 操作日志id
     */
    @Override
    public void refreshIpDetailAsyncByLogId(Long logId) {
        EXECUTOR.execute(() -> {
            Log log = logMapper.selectById(logId);
            if (Objects.isNull(log)) {
                return;
            }
            String ip = log.getIp();
            if (StrUtil.isBlank(ip)) {
                return;
            }
            IpDetail ipDetail = TryGetIpDetailOrNullTreeTimes(ip);
            if (Objects.nonNull(ipDetail)) {
                log.setAddress(buildAddr(ipDetail.getRegion(), ipDetail.getCity(), ipDetail.getCountry()));
            } else {
                log.setAddress("未知");
                IpServiceImpl.log.error("log get ip detail fail ip:{},log:{}", ip, log);
            }
            logMapper.updateById(log);
        });
    }

    /**
     * 尝试三次获取ip所属地址信息
     * @param ip
     * @return
     */
    private static IpDetail TryGetIpDetailOrNullTreeTimes(String ip) {
        for (int i = 0; i < 3; i++) {
            IpDetail ipDetail = getIpDetailOrNull(ip);
            if (Objects.nonNull(ipDetail)) {
                return ipDetail;
            }
            log.info("进行重试：{}", i);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取对应ip地址所属信息
     * @param ip
     * @return
     */
    private static IpDetail getIpDetailOrNull(String ip) {
        String body = HttpUtil.get(StrUtil.format(ThirdPartyInterfaceConst.TAOBAO_IP_DETAIL, ip));
        try {
            IpResultDTO<IpDetail> result = JSONUtil.toBean(body, new TypeReference<IpResultDTO<IpDetail>>() {
            }, false);
            if (result.isSuccess()) {
                // 获取存在的IpDetail数据
                return result.getData();
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * 构建地址
     * @param region 区域
     * @param city 城市
     * @param country 国家
     * @return 地址
     */
    private String buildAddr(String region, String city, String country) {

        if ("内网IP".equals(city)) {
            return "内网IP";
        }

        if (!"中国".equals(country)) {
            return country;
        }

        if ("XX".equals(region) && "XX".equals(city)) {
            return "未知";
        }

        if ("XX".equals(region)){
            return city;
        }

        if ("XX".equals(city)){
            return region;
        }

        if (region.equals(city)) {
            return region;
        }

        return region + " " + city;
    }
}
