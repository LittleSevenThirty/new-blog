package cn.edu.tjufe.zql.intercepter.rabbit;


import cn.edu.tjufe.zql.constants.RedisConst;
import cn.edu.tjufe.zql.domain.email.CommentEmail;
import cn.edu.tjufe.zql.domain.email.LeaveWordEmail;
import cn.edu.tjufe.zql.domain.email.ReplyCommentEmail;
import cn.edu.tjufe.zql.domain.entity.Comment;
import cn.edu.tjufe.zql.domain.entity.LeaveWord;
import cn.edu.tjufe.zql.domain.entity.User;
import cn.edu.tjufe.zql.enums.MailBoxAlertEnum;
import cn.edu.tjufe.zql.mapper.CommentMapper;
import cn.edu.tjufe.zql.mapper.LeaveWordMapper;
import cn.edu.tjufe.zql.mapper.UserMapper;
import cn.edu.tjufe.zql.utils.DateUtils;
import cn.edu.tjufe.zql.utils.RedisCache;
import cn.edu.tjufe.zql.utils.TimeUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.validation.ObjectError;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 *
 * @authro 钟奇林
 * @description email消息队列监听者（消费者）-对应生产者是PublicServiceImpl
 * @date 2026/3/14
 * @github https://github.com/little-seven-thirty
 */
@Component
@Slf4j
public class EmailQueueListener {
    @Resource
    private JavaMailSender mailSender;
    @Resource
    private RedisCache redisCache;
    @Resource
    private CommentMapper commentMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private LeaveWordMapper leaveWordMapper;
    @Resource
    private TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String username;
    @Value("${web.index.path}")
    private String webIndexPath;
    @Value("${mail.link.apply.redirect-uri}")
    private String linkApplyRedirectUri;


    /**
     * 监听邮件队列，发送邮件
     */
    @RabbitListener(queues="${spring.rabbitmq.queue.email}",
        errorHandler = "rabbitListenerErrorHandler",
        containerFactory = "rabbitListenerContainerFactory"
    )
    public void handlerMapMessage(Map<String, Object> data) {
        String email = (String)data.get("email");
        String code=(String)data.get("code");
        String type=(String)data.get("type");

        String encode="";

        // 友链申请通知
        if(type.equals(MailBoxAlertEnum.FRIEND_LINK_APPLICATION.getCodeStr())){
            // 本次会话token
            encode=String.valueOf(System.currentTimeMillis());
            // 存入Redis，7天有效
            redisCache.setCacheObject(RedisConst.EMAIL_VERIFICATION_LINK+encode,data.get("linkId")+" "+data.get("linkEmail"),7, TimeUnit.DAYS);
        }
        // 用户文章评论通知
        CommentEmail commentEmail=null;
        if(type.equals(MailBoxAlertEnum.COMMENT_NOTIFICATION_EMAIL.getCodeStr())){
            commentEmail=commentMapper.getCommentAsEmail((Long)data.get("commentId"),(Integer)data.get("commentType"));
            String url= Objects.equals(commentEmail.getType(),1)?webIndexPath+"article/"+commentEmail.getTypeId():webIndexPath+"message/detail/"+commentEmail.getTypeId();
            commentEmail.setUrl(url);
            // 留言标题为空
            if(Objects.isNull(commentEmail.getTitle())) commentEmail.setTitle("");
        }

        // 回复评论，通知用户
        ReplyCommentEmail replyCommentEmail = null;
        if (type.equals(MailBoxAlertEnum.REPLY_COMMENT_NOTIFICATION_EMAIL.getCodeStr())) {
            // 回复评论数据
            CommentEmail commentEmailOne = commentMapper.getCommentAsEmail((Long)data.get("commentId"),(Integer)data.get("commentType"));
            // 被回复评论数据
            Comment replyComment = commentMapper.selectOne(new LambdaQueryWrapper<Comment>().eq(Comment::getCommentId, data.get("replyCommentId")));
            User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUserId, replyComment.getCommentUserId()));
            replyCommentEmail = commentEmailOne.asViewObject(ReplyCommentEmail.class, reply -> {
                reply.setReplyAvatar(user.getAvatar());
                reply.setReplyNickname(user.getNickname());
                reply.setReplyContent(replyComment.getCommentContent());
                reply.setReplyTime(TimeUtils.format(replyComment.getCreateTime(), DateUtils.YYYY_MM_DD_HH_MM_SS));
                String url = Objects.equals(commentEmailOne.getType(), 1) ? webIndexPath + "article/" + commentEmailOne.getTypeId() : webIndexPath + "message/detail/" + commentEmailOne.getTypeId();
                reply.setUrl(url);
            });
            // 如果是留言 title就为null
            if (Objects.isNull(replyCommentEmail.getTitle())) replyCommentEmail.setTitle("");
            log.info("信息:{}", replyCommentEmail);
        }

        // 新的留言提醒
        LeaveWordEmail leaveWordEmail = null;
        if (type.equals(MailBoxAlertEnum.MESSAGE_NOTIFICATION_EMAIL.getCodeStr())) {
            LeaveWord messageUser = leaveWordMapper.selectOne(new LambdaQueryWrapper<LeaveWord>().eq(LeaveWord::getLeaveWordId, data.get("messageId")));
            User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUserId, messageUser.getUserId()));
            leaveWordEmail = messageUser.asViewObject(LeaveWordEmail.class, message -> {
                message.setUrl(webIndexPath + "message/detail/" + messageUser.getLeaveWordId());
                message.setAvatar(user.getAvatar());
                message.setNickname(user.getNickname());
                message.setTime(TimeUtils.format(messageUser.getCreateTime(), DateUtils.YYYY_MM_DD_HH_MM_SS));
            });
        }
        // 发送邮件 ①构建邮件内容
        MimeMessage mimeMessage = null;
        if(MailBoxAlertEnum.REGISTER.getCodeStr().equals(type)){
            // 注册邮件
            mimeMessage=sendHTMLMail(email,MailBoxAlertEnum.REGISTER.getSubject(),MailBoxAlertEnum.REGISTER.getTemplateName(),Map.of(
                    "expirationTime","5分钟",
                    "code",code,
                    "toUrl",webIndexPath,
                    "openSourceAddress",""
            ));
        }else if (MailBoxAlertEnum.RESET.getCodeStr().equals(type)) {
            // 重置密码邮件
            mimeMessage = sendHTMLMail(email, MailBoxAlertEnum.RESET.getSubject(), MailBoxAlertEnum.RESET.getTemplateName(), Map.of(
                    "expirationTime", "5分钟",
                    "code", code,
                    "toUrl", webIndexPath,
                    "openSourceAddress", "https://gitee.com/kuailemao/ruyu-blog"
            ));
        } else if (MailBoxAlertEnum.RESET_EMAIL.getCodeStr().equals(type)) {
            // 重置邮箱邮件
            mimeMessage = sendHTMLMail(email, MailBoxAlertEnum.RESET_EMAIL.getSubject(), MailBoxAlertEnum.RESET_EMAIL.getTemplateName(), Map.of(
                    "expirationTime", "5分钟",
                    "code", code,
                    "toUrl", webIndexPath,
                    "openSourceAddress", "https://gitee.com/kuailemao/ruyu-blog"
            ));
        }else if(MailBoxAlertEnum.FRIEND_LINK_APPLICATION.getCodeStr().equals(type)){
            // 友链申请邮件
            mimeMessage=sendHTMLMail(email,MailBoxAlertEnum.FRIEND_LINK_APPLICATION.getSubject(), MailBoxAlertEnum.FRIEND_LINK_APPLICATION.getTemplateName(), Map.of(
                    "name",data.get("name"),
                    "url",data.get("url"),
                    "description",data.get("description"),
                    "background",data.get("background"),
                    "linkEmail",data.get("linkEmail"),
                    "toUrl",webIndexPath,
                    "verifyCode",linkApplyRedirectUri+"?verifyCode"+encode
            ));
        }else if(MailBoxAlertEnum.FRIEND_LINK_APPLICATION_PASS.getCodeStr().equals(type)){
            // 友链申请通过邮件
            mimeMessage=sendHTMLMail(email,MailBoxAlertEnum.FRIEND_LINK_APPLICATION_PASS.getSubject(), MailBoxAlertEnum.FRIEND_LINK_APPLICATION_PASS.getTemplateName(), Map.of(
                    "toUrl", webIndexPath + "link",
                    "openSourceAddress", ""
            ));
        }else if(MailBoxAlertEnum.COMMENT_NOTIFICATION_EMAIL.getCodeStr().equals(type)){
            // 有新评论邮件
            mimeMessage = sendHTMLMail(email, MailBoxAlertEnum.COMMENT_NOTIFICATION_EMAIL.getSubject(), MailBoxAlertEnum.COMMENT_NOTIFICATION_EMAIL.getTemplateName(), Map.of(
                    "toUrl", commentEmail.getUrl(),
                    "type", commentEmail.getType(),
                    "title", commentEmail.getTitle(),
                    "url", commentEmail.getUrl(),
                    "avatar", commentEmail.getAvatar(),
                    "nickname", commentEmail.getNickname(),
                    "content", commentEmail.getContent(),
                    "time", commentEmail.getTime()
            ));
        }else if (MailBoxAlertEnum.REPLY_COMMENT_NOTIFICATION_EMAIL.getCodeStr().equals(type)) {
            // 有评论回复邮件
            mimeMessage = sendHTMLMail(email, MailBoxAlertEnum.REPLY_COMMENT_NOTIFICATION_EMAIL.getSubject(), MailBoxAlertEnum.REPLY_COMMENT_NOTIFICATION_EMAIL.getTemplateName(), toReplyMap(replyCommentEmail));
        }else if (MailBoxAlertEnum.MESSAGE_NOTIFICATION_EMAIL.getCodeStr().equals(type)) {
            mimeMessage = sendHTMLMail(email, MailBoxAlertEnum.MESSAGE_NOTIFICATION_EMAIL.getSubject(), MailBoxAlertEnum.MESSAGE_NOTIFICATION_EMAIL.getTemplateName(), Map.of(
                    "toUrl", leaveWordEmail.getUrl(),
                    "avatar", leaveWordEmail.getAvatar(),
                    "nickname", leaveWordEmail.getNickname(),
                    "content", leaveWordEmail.getContent(),
                    "time", leaveWordEmail.getTime()
            ));
        } else mimeMessage = null;

        if(Objects.isNull(mimeMessage))return;
        mailSender.send(mimeMessage);
        log.info("{}邮件发送成功",email);
    }

    /**
     * 构建回复评论邮件内容
     * @param replyCommentEmail 回复评论实体信息
     * @return
     */
    private Map<String,Object> toReplyMap(ReplyCommentEmail replyCommentEmail) {
        return Map.ofEntries(
                Map.entry("toUrl", replyCommentEmail.getUrl()),
                Map.entry("type", replyCommentEmail.getType()),
                Map.entry("title", replyCommentEmail.getTitle()),
                Map.entry("url", replyCommentEmail.getUrl()),
                Map.entry("avatar", replyCommentEmail.getAvatar()),
                Map.entry("nickname", replyCommentEmail.getNickname()),
                Map.entry("content", replyCommentEmail.getContent()),
                Map.entry("time", replyCommentEmail.getTime()),
                Map.entry("replyAvatar", replyCommentEmail.getReplyAvatar()),
                Map.entry("replyNickname", replyCommentEmail.getReplyNickname()),
                Map.entry("replyContent", replyCommentEmail.getReplyContent()),
                Map.entry("replyTime", replyCommentEmail.getReplyTime())
        );
    }


    /**
     * 构造HTML邮件
     * @param toMail 送达方
     * @param subject 主题
     * @param template 邮件模板名称
     * @param model 邮件内容
     * @return
     */
    private MimeMessage sendHTMLMail(String toMail, String subject, String template, Map<String, Object> model){
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        try{
            helper.setTo(toMail);
            helper.setSubject(subject);
            helper.setFrom(username);
            Context context = new Context();
            context.setVariables(model);
            String htmlContent=templateEngine.process(template,context);
            helper.setText(htmlContent, true);
        }catch(MessagingException e){
            // 处理异常
            log.error("发送邮件失败：{}", e.getMessage());
        }
        return helper.getMimeMessage();
    }
}
