package cn.edu.tjufe.zql.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @authro 钟奇林
 * @description 邮箱通知枚举
 * @date 2026/2/24
 * @github https://github.com/little-seven-thirty
 */
@AllArgsConstructor
@Getter
public enum MailBoxAlertEnum {
    MESSAGE_NOTIFICATION_EMAIL("messageNotificationEmail","70--你有新的留言","message-email-template","新的留言提醒");

    // 字符标识
    private final String codeStr;
    // 邮箱主题
    private final String subject;
    // 邮件模板名称
    private final String templateName;
    // 描述
    private final String desc;
}
