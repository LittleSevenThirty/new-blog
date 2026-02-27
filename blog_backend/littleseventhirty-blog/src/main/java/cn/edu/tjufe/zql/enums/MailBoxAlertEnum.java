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
    MESSAGE_NOTIFICATION_EMAIL("messageNotificationEmail","70--你有新的留言","message-email-template","新的留言提醒"),
    COMMENT_NOTIFICATION_EMAIL("commentNotificationEmail","70--有新的评论", "comment-email-template","有新的评论的邮箱提醒"),
    REPLY_COMMENT_NOTIFICATION_EMAIL("replyCommentNotifacationEmail","70-你有新的回复","reply-comment-email-template","有新的回复邮箱提醒");

    // 字符标识
    private final String codeStr;
    // 邮箱主题
    private final String subject;
    // 邮件模板名称
    private final String templateName;
    // 描述
    private final String desc;
}
