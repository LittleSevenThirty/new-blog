package cn.edu.tjufe.zql.service.impl;

import cn.edu.tjufe.zql.service.IPublicService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author: littleseventhirty
 * @description: 公共服务实现类
 * @date: 2026/2/26
 **/
@Service(value = "publicService")
public class PublicServiceImpl implements IPublicService {

    @Override
    public String registerEmailVerifyCode(String type, String mail) {
        // 生成随机验证码
        String code = String.valueOf((int) ((Math.random() * 900000) + 100000));
        // TODO: 实现邮件发送逻辑
        System.out.println("发送验证码到邮箱：" + mail + "，验证码：" + code);
        return code;
    }

    @Override
    public void sendEmail(String type, String email, Map<String, Object> content) {
        // TODO: 实现邮件发送逻辑
        System.out.println("发送邮件到邮箱：" + email + "，类型：" + type + "，内容：" + content);
    }
}
