package cn.edu.tjufe.zql.service.impl;

import cn.edu.tjufe.zql.constants.ThirdPartyInterfaceConst;
import cn.edu.tjufe.zql.domain.dto.IpResultDTO;
import cn.edu.tjufe.zql.domain.ip.IpDetail;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class IpServiceImplTest {
    @Test
    public void test(){
        String str=StrUtil.format(ThirdPartyInterfaceConst.TAOBAO_IP_DETAIL, "127.0.0.1");
        System.out.println(str);
        String body = HttpUtil.get(StrUtil.format(ThirdPartyInterfaceConst.TAOBAO_IP_DETAIL, "127.0.0.1"));
        System.out.println(body);
        IpResultDTO<IpDetail> result = JSONUtil.toBean(body, new TypeReference<IpResultDTO<IpDetail>>() {
        }, false);
        System.out.println(result);
    }
}