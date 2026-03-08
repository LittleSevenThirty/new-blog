package cn.edu.tjufe.zql.service.impl;


import cn.edu.tjufe.zql.constants.FunctionConst;
import cn.edu.tjufe.zql.constants.SQLConst;
import cn.edu.tjufe.zql.domain.dto.LeaveWordIsCheckDTO;
import cn.edu.tjufe.zql.domain.dto.SearchLeaveWordDTO;
import cn.edu.tjufe.zql.domain.entity.*;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
import cn.edu.tjufe.zql.domain.vo.LeaveWordListVO;
import cn.edu.tjufe.zql.domain.vo.LeaveWordVO;
import cn.edu.tjufe.zql.enums.CommentEnum;
import cn.edu.tjufe.zql.enums.FavoriteEnum;
import cn.edu.tjufe.zql.enums.LikeEnum;
import cn.edu.tjufe.zql.enums.MailBoxAlertEnum;
import cn.edu.tjufe.zql.mapper.*;
import cn.edu.tjufe.zql.service.ILeaveWordService;
import cn.edu.tjufe.zql.service.IPublicService;
import cn.edu.tjufe.zql.utils.SecurityUtils;
import cn.edu.tjufe.zql.utils.StringUtils;
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
@Service("leaveWordService")
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
    @Value("${mail.message-email-notice}")
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

    @Override
    public List<LeaveWordListVO> getBackLeaveWordList(SearchLeaveWordDTO searchDTO) {
        LambdaQueryWrapper<LeaveWord> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotNull(searchDTO)) {
            // 搜索
            List<User> users = userMapper.selectList(new LambdaQueryWrapper<User>().like(User::getUsername, searchDTO.getUserName()));
            if (!users.isEmpty())
                wrapper.in(StringUtils.isNotEmpty(searchDTO.getUserName()), LeaveWord::getUserId, users.stream().map(User::getUserId).collect(Collectors.toList()));
            else
                wrapper.eq(StringUtils.isNotNull(searchDTO.getUserName()), LeaveWord::getUserId, null);

            wrapper.eq(StringUtils.isNotNull(searchDTO.getIsCheck()), LeaveWord::getIsCheck, searchDTO.getIsCheck());
            if (StringUtils.isNotNull(searchDTO.getStartTime()) && StringUtils.isNotNull(searchDTO.getEndTime()))
                wrapper.between(LeaveWord::getCreateTime, searchDTO.getStartTime(), searchDTO.getEndTime());
        }
        List<LeaveWord> leaveWords = leaveWordMapper.selectList(wrapper);
        if (!leaveWords.isEmpty()) {
            return leaveWords.stream().map(leaveWord -> leaveWord.asViewObject(LeaveWordListVO.class,
                    v -> v.setUserName(userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUserId, leaveWord.getUserId()))
                            .getUsername()))).toList();
        }
        return null;
    }

    @Override
    public ResponseResult<Void> isCheckLeaveWord(LeaveWordIsCheckDTO isCheckDTO) {
        if (leaveWordMapper.updateById(LeaveWord.builder().leaveWordId(isCheckDTO.getId()).isCheck(isCheckDTO.getIsCheck()).build()) > 0)
            return ResponseResult.success();

        return ResponseResult.failure();
    }

    @Override
    public ResponseResult<Void> deleteLeaveWord(List<Long> ids) {
        if (leaveWordMapper.deleteBatchIds(ids) > 0) {
            // 删除点赞、收藏、评论
            likeMapper.delete(new LambdaQueryWrapper<Like>().eq(Like::getType, LikeEnum.LIKE_TYPE_LEAVE_WORD.getType()).and(a -> a.in(Like::getTypeId, ids)));
            favoriteMapper.delete(new LambdaQueryWrapper<Favorite>().eq(Favorite::getType, FavoriteEnum.FAVORITE_TYPE_LEAVE_WORD.getType()).and(a -> a.in(Favorite::getTypeId, ids)));
            commentMapper.delete(new LambdaQueryWrapper<Comment>().eq(Comment::getType, CommentEnum.COMMENT_TYPE_LEAVE_WORD.getType()).and(a -> a.in(Comment::getTypeId, ids)));
            return ResponseResult.success();
        }
        return ResponseResult.failure();
    }
}
