package cn.edu.tjufe.zql.service;

import java.util.Map;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/2/24
 * @github https://github.com/little-seven-thirty
 */
public interface IPublicService {
    /**
     * 邮箱验证码发送
     * @param type  邮箱类型
     * @param mail  邮箱地址
     * @return
     */
    String registerEmailVerifyCode(String type,String mail);

    /**
     *邮箱通知
     * @param type 邮箱类型
     * @param email 邮箱地址
     * @param content 邮件内容
     */
    void sendEmail(String type, String email, Map<String,Object> content);
}
