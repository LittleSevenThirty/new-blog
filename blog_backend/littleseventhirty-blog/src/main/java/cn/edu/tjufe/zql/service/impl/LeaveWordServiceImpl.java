package cn.edu.tjufe.zql.service.impl;


import cn.edu.tjufe.zql.constants.FunctionConst;
import cn.edu.tjufe.zql.constants.SQLConst;
import cn.edu.tjufe.zql.domain.entity.*;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
import cn.edu.tjufe.zql.domain.vo.LeaveWordVO;
import cn.edu.tjufe.zql.enums.CommentEnum;
import cn.edu.tjufe.zql.enums.LikeEnum;
import cn.edu.tjufe.zql.enums.MailBoxAlertEnum;
import cn.edu.tjufe.zql.mapper.*;
import cn.edu.tjufe.zql.service.ILeaveWordService;
import cn.edu.tjufe.zql.service.IPublicService;
import cn.edu.tjufe.zql.utils.SecurityUtils;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/2/24
 * @github https://github.com/little-seven-thirty
 */
@Service("leave_word_service")
public class LeaveWordServiceImpl extends ServiceImpl<LeaveWordMapper, LeaveWord> implements ILeaveWordService{
    @Resource
    private CommentMapper commentMapper;
    @Resource
    private FavoriteMapper favoriteMapper;
    @Resource
    private LeaveWordMapper leaveWordMapper;
    @Resource
    private LikeMapper likeMapper;
    @Resource
    private IPublicService publicService;
    @Resource
    private UserMapper userMapper;
    @Value("${spring.mail.username}")
    private String mail;
    @Value("${mail.message-mail-notice}")
    private Boolean mailNotice;

    @Override
    public List<LeaveWordVO> getAllLeaveWordList(String id) {
        List<LeaveWord> words=this.query()
                .eq(SQLConst.IS_CHECK,SQLConst.IS_CHECK_YES)
                .eq(id!=null,"leave_word_id",id)
                .list();
        List<Long> userIds=words.stream().map(LeaveWord::getUserId).collect(Collectors.toList());
        Map<Long,User> userMap=userMapper.selectByIds(userIds).stream().collect(Collectors.toMap(User::getUserId,user->user));

        return words.stream().map(leaveWord->{
            User user=userMap.get(leaveWord.getUserId());
            return leaveWord.asViewObject(LeaveWordVO.class,leaveWordVO->{
                leaveWordVO.setAvatar(user.getAvatar())
                        .setCommentCount(commentMapper.selectCount(new LambdaQueryWrapper<Comment>().eq(Comment::getType, CommentEnum.COMMENT_TYPE_LEAVE_WORD.getType()).eq(Comment::getIsCheck, SQLConst.IS_CHECK_YES).eq(Comment::getTypeId, leaveWord.getLeaveWordId())))
                        .setLikeCount(likeMapper.selectCount(new LambdaQueryWrapper<Like>().eq(Like::getType, LikeEnum.LIKE_TYPE_LEAVE_WORD.getType()).eq(Like::getTypeId, leaveWord.getLeaveWordId())))
                        .setFavoriteCount(favoriteMapper.selectCount(new LambdaQueryWrapper<Favorite>().eq(Favorite::getType, CommentEnum.COMMENT_TYPE_LEAVE_WORD.getType()).eq(Favorite::getTypeId, leaveWord.getLeaveWordId())));
            });
        }).collect(Collectors.toList());
    }

    @Override
    public ResponseResult<Void> addUserLeaveWord(String content) {
        String parse= JSON.parse(content).toString();
        if(parse.length()> FunctionConst.LEAVE_WORD_CONTENT_LENGTH){
            return ResponseResult.failure("留言内容过长");
        }
        LeaveWord leaveWord =LeaveWord.builder()
                .content(parse)
                .userId(SecurityUtils.getUserId())
                .build();
        if(this.save(leaveWord)){
            // 查看是否是站点创建人
            User user=userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUserId,SecurityUtils.getUserId()));
            if(Objects.equals(user.getEmail(),mail)||!mailNotice){
                return ResponseResult.success();
            }
            // 留言成功，并且需要邮件提醒
            Map<String,Object> map=new HashMap<>();
            map.put("messageId",leaveWord.getLeaveWordId());
            publicService.sendEmail(MailBoxAlertEnum.MESSAGE_NOTIFICATION_EMAIL.getCodeStr(), mail,map);

            return ResponseResult.success();
        }

        return ResponseResult.failure();
    }
}
