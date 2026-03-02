package cn.edu.tjufe.zql.service.impl;


import cn.edu.tjufe.zql.domain.entity.User;
import cn.edu.tjufe.zql.service.IIpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Service;

import java.util.Objects;

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
    @Override
    public void destroy() throws Exception {

    }

    @Override
    public void refreshIpDetailAsyncByUidAndLogin(Long uid) {
//        EXECUTOR.execute(() -> {
//            User user = userMapper.selectById(uid);
//            if (Objects.isNull(user)) {
//                return;
//            }
//            String ip = user.getLoginIp();
//            if (StrUtil.isBlank(ip)) {
//                return;
//            }
//            IpDetail ipDetail = TryGetIpDetailOrNullTreeTimes(ip);
//            if (Objects.nonNull(ipDetail)) {
//                user.setLoginAddress(buildAddr(ipDetail.getRegion(), ipDetail.getCity(), ipDetail.getCountry()));
//            } else {
//                user.setRegisterAddress("未知");
//                log.error("login get ip detail fail ip:{},uid:{}", ip, uid);
//            }
//            userMapper.updateById(user);
//        });
    }
}
