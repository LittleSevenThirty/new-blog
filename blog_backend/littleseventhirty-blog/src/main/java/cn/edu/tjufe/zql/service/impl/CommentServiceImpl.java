package cn.edu.tjufe.zql.service.impl;

import cn.edu.tjufe.zql.constants.RedisConst;
import cn.edu.tjufe.zql.constants.SQLConst;
import cn.edu.tjufe.zql.domain.dto.CommentIsCheckDTO;
import cn.edu.tjufe.zql.domain.dto.SearchCommentDTO;
import cn.edu.tjufe.zql.domain.dto.UserCommentDTO;
import cn.edu.tjufe.zql.domain.entity.Comment;
import cn.edu.tjufe.zql.domain.entity.LeaveWord;
import cn.edu.tjufe.zql.domain.entity.Like;
import cn.edu.tjufe.zql.domain.entity.User;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
import cn.edu.tjufe.zql.domain.vo.CommentListVO;
import cn.edu.tjufe.zql.domain.vo.CommentVO;
import cn.edu.tjufe.zql.domain.vo.PageVO;
import cn.edu.tjufe.zql.enums.CommentEnum;
import cn.edu.tjufe.zql.enums.LikeEnum;
import cn.edu.tjufe.zql.enums.MailBoxAlertEnum;
import cn.edu.tjufe.zql.mapper.CommentMapper;
import cn.edu.tjufe.zql.mapper.LeaveWordMapper;
import cn.edu.tjufe.zql.mapper.LikeMapper;
import cn.edu.tjufe.zql.mapper.UserMapper;
import cn.edu.tjufe.zql.service.ICommentService;
import cn.edu.tjufe.zql.service.ILikeService;
import cn.edu.tjufe.zql.service.IPublicService;
import cn.edu.tjufe.zql.utils.RedisCache;
import cn.edu.tjufe.zql.utils.SecurityUtils;
import cn.edu.tjufe.zql.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private CommentMapper commentMapper;
    @Resource
    private LeaveWordMapper leaveWordMapper;
    @Resource
    private ILikeService likeService;
    @Resource
    private IPublicService publicService;
    @Resource
    private LikeMapper likeMapper;
    @Resource
    private RedisCache redisCache;
    @Value("${mail.article-email-notice}")
    private Boolean articleEmailNotice;
    @Value("${mail.message-email-notice}")
    private Boolean messageEmailNotice;
    @Value("${spring.mail.username}")
    private String fromUser;
    @Value("${mail.article-reply-notice}")
    private Boolean articleReplyNotice;
    @Value("${mail.message-reply-notice}")
    private Boolean messageReplyNotice;


    @Override
    public ResponseResult<String> userComment(UserCommentDTO commentDTO) {
        Comment comment = commentDTO.asViewObject(Comment.class, item -> item.setCommentUserId(SecurityUtils.getUserId()));
        if (this.save(comment)) {
            // 判断用是否为第三方登录没有邮箱
            User user = userMapper.selectById(SecurityUtils.getUserId());
            if (StringUtils.isEmpty(user.getEmail())) {
                // 提示绑定邮箱
                return ResponseResult.success("检测到您尚未绑定邮箱,无法开启邮箱提醒，请先绑定邮箱");
            }
            return this.commentEmailReminder(commentDTO, user, comment);
        }
        return ResponseResult.failure();
    }

    @Override
    public PageVO<List<CommentVO>> getCommentList(Integer type, Long typeId, Integer pageNum, Integer pageSize) {
        // 查询父评论
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .orderByDesc(Comment::getCreateTime)
                .eq(Comment::getType, type)
                .eq(Comment::getTypeId, typeId)
                .eq(Comment::getIsCheck, SQLConst.COMMENT_IS_CHECK)
                .isNull(Comment::getParentId);
        Page<Comment> page = new Page<>(pageNum, pageSize);
        IPage<Comment> commentIPage = commentMapper.selectPage(page, queryWrapper);
        List<Comment> comments = commentIPage.getRecords();
        // 查询所有子评论
        LambdaQueryWrapper<Comment> childQueryWrapper = new LambdaQueryWrapper<>();
        childQueryWrapper
                .orderByDesc(Comment::getCreateTime)
                .eq(Comment::getType, type)
                .eq(Comment::getTypeId, typeId)
                .eq(Comment::getIsCheck, SQLConst.COMMENT_IS_CHECK)
                .isNotNull(Comment::getParentId);
        List<Comment> childComment = commentMapper.selectList(childQueryWrapper);
        if (!childComment.isEmpty()) comments.addAll(childComment);
        List<CommentVO> commentsVOS = comments.stream().map(comment -> comment.asViewObject(CommentVO.class)).toList();
        List<CommentVO> parentComments = commentsVOS.stream().filter(comment -> comment.getParentId() == null).toList();
        List<CommentVO> collect = parentComments.stream().peek(comment -> {
                    comment.setChildComment(getChildComment(commentsVOS, comment.getCommentId()));
                    comment.setChildCommentCount(getChildCommentCount(commentsVOS, comment.getCommentId()));
                    comment.setParentCommentCount(this.count(new LambdaQueryWrapper<Comment>()
                            .eq(Comment::getType, type)
                            .eq(Comment::getTypeId, typeId)
                            .eq(Comment::getIsCheck, SQLConst.COMMENT_IS_CHECK)
                            .isNull(Comment::getParentId)));
                }
        ).toList();
        // 总评论数量
        LambdaQueryWrapper<Comment> countWrapper = new LambdaQueryWrapper<>();
        countWrapper
                .eq(Comment::getType, type)
                .eq(Comment::getTypeId, typeId)
                .eq(Comment::getIsCheck, SQLConst.COMMENT_IS_CHECK);
        return new PageVO<>(collect, commentMapper.selectCount(countWrapper));
    }

    @Override
    public List<CommentListVO> getBackCommentList(SearchCommentDTO searchDTO) {
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotNull(searchDTO)) {
            List<User> users = userMapper.selectList(new LambdaQueryWrapper<User>().like(User::getUsername, searchDTO.getCommentUserName()));
            if (!users.isEmpty())
                wrapper.in(StringUtils.isNotEmpty(searchDTO.getCommentUserName()), Comment::getCommentUserId, users.stream().map(User::getUserId).collect(Collectors.toList()));
            else
                wrapper.eq(StringUtils.isNotNull(searchDTO.getCommentUserName()), Comment::getCommentUserId, null);

            wrapper.like(StringUtils.isNotEmpty(searchDTO.getCommentContent()), Comment::getCommentContent, searchDTO.getCommentContent())
                    .eq(StringUtils.isNotNull(searchDTO.getType()), Comment::getType, searchDTO.getType())
                    .eq(StringUtils.isNotNull(searchDTO.getIsCheck()), Comment::getIsCheck, searchDTO.getIsCheck());
        }

        return commentMapper.selectList(wrapper.orderByDesc(Comment::getCreateTime)).stream().map(comment -> comment.asViewObject(CommentListVO.class,
                v -> v.setCommentUserName(userMapper.selectById(comment.getCommentUserId()).getUsername()))).collect(Collectors.toList());

    }

    @Override
    public ResponseResult<Void> isCheckComment(CommentIsCheckDTO isCheckDTO) {
        LambdaUpdateWrapper<Comment> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Comment::getCommentId, isCheckDTO.getCommentId()).or().eq(Comment::getParentId, isCheckDTO.getCommentId());
        int updateCount = commentMapper.update(Comment.builder().commentId(isCheckDTO.getCommentId()).isCheck(isCheckDTO.getIsCheck()).build(), wrapper);
        if (updateCount > 0) {
            // 同步redis评论数量
            // 如果是文章评论，则改变redis中文章数量
            // 1.查询评论所在的文章id
            Long articleId = commentMapper
                    .selectOne(
                            new LambdaQueryWrapper<Comment>()
                                    .eq(Comment::getCommentId, isCheckDTO.getCommentId())
                                    .eq(Comment::getType, CommentEnum.COMMENT_TYPE_ARTICLE.getType())).getTypeId();
            // 2.修改redis数量
            if (Objects.equals(isCheckDTO.getIsCheck(), SQLConst.COMMENT_IS_CHECK)) {
                redisCache.incrementCacheMapValue(RedisConst.ARTICLE_COMMENT_COUNT, articleId.toString(), updateCount);
            } else {
                redisCache.incrementCacheMapValue(RedisConst.ARTICLE_COMMENT_COUNT, articleId.toString(), -updateCount);
            }
            return ResponseResult.success();
        }

        return ResponseResult.failure();
    }

    @Override
    public ResponseResult<Void> deleteComment(Long id) {
        // 是否还有子评论
        if (commentMapper.selectCount(new LambdaQueryWrapper<Comment>().eq(Comment::getParentId, id)) > 0) {
            return ResponseResult.failure("该评论还有子评论");
        }
        if (commentMapper.deleteById(id) > 0) {
            // 删除评论的点赞
            likeMapper.delete(new LambdaQueryWrapper<Like>().eq(Like::getType, LikeEnum.LIKE_TYPE_COMMENT.getType()).and(a -> a.in(Like::getTypeId, id)));
            return ResponseResult.success();
        }
        return ResponseResult.failure();
    }

    // 获取子评论
    private List<CommentVO> getChildComment(List<CommentVO> comments, Long parentId) {
        return comments.stream()
                .filter(comment -> {
                    if (Objects.isNull(comment.getParentId())) {
                        User user = userMapper.selectById(comment.getCommentUserId());
                        comment.setCommentUserNickname(user.getNickname())
                                .setCommentUserAvatar(user.getAvatar())
                                .setLikeCount(likeService.getLikeCount(LikeEnum.LIKE_TYPE_COMMENT.getType(), comment.getCommentId()));
                    }
                    return Objects.nonNull(comment.getParentId()) && Objects.equals(comment.getParentId(), parentId);
                })
                .peek(comment -> {
                    User user = userMapper.selectById(comment.getCommentUserId());
                    comment.setChildComment(getChildComment(comments, comment.getCommentId()))
                            .setCommentUserNickname(user.getNickname())
                            .setCommentUserAvatar(user.getAvatar())
                            .setReplyUserNickname(userMapper.selectById(comment.getReplyUserId()).getNickname())
                            .setLikeCount(likeService.getLikeCount(LikeEnum.LIKE_TYPE_COMMENT.getType(), comment.getCommentId()));
                }).toList();
    }

    // 获取父评论底下的评论数量
    private Long getChildCommentCount(List<CommentVO> comments, Long parentId) {
        // 递归获取父评论的子评论数
        return comments.stream()
                .filter(comment -> Objects.nonNull(comment.getParentId()) && Objects.equals(comment.getParentId(), parentId))
                .peek(comment -> {
                    // 回复子评论的数量
                    Long count = commentMapper.selectCount(new LambdaQueryWrapper<Comment>().eq(Comment::getReplyId, comment.getCommentId()).eq(Comment::getIsCheck, SQLConst.COMMENT_IS_CHECK));
                    comment.setChildCommentCount(count);
                })
                .mapToLong(comment -> {
                    if (!comment.getChildComment().isEmpty()) {
                        return (1 + getChildCommentCount(comment.getChildComment(), comment.getCommentId()));
                    } else {
                        return 1;
                    }
                })
                .sum();
    }

    /**
     * 评论邮箱提醒
     *
     * @param commentDTO 前端DTO
     * @param user       用户
     * @param comment    新增评论消息
     * @return ResponseResult
     */
    public ResponseResult<String> commentEmailReminder(UserCommentDTO commentDTO, User user, Comment comment) {
        // 缓存评论数量+1
        redisCache.incrementCacheMapValue(RedisConst.ARTICLE_COMMENT_COUNT, commentDTO.getTypeId().toString(), 1);
        // 评论
        if (StringUtils.isNull(commentDTO.getReplyId())) {

            if ((commentDTO.getType() == 1 && !articleEmailNotice) || commentDTO.getType() == 2 && !messageEmailNotice)
                return ResponseResult.success();

            Map<String, Object> selectWhereMap = new HashMap<>();
            selectWhereMap.put("commentType", commentDTO.getType());
            selectWhereMap.put("commentId", comment.getCommentId());

            // 留言提示对应发布留言的用户
            if (commentDTO.getType() == 1) {
                if (Objects.equals(fromUser, user.getEmail())) return ResponseResult.success();
                // 发邮箱给站长
                publicService.sendEmail(MailBoxAlertEnum.COMMENT_NOTIFICATION_EMAIL.getCodeStr(), fromUser, selectWhereMap);
            }

            if (commentDTO.getType() == 2) {
                // 查出回复的该留言用户的邮箱
                LeaveWord leaveWord = leaveWordMapper.selectOne(new LambdaQueryWrapper<LeaveWord>().eq(LeaveWord::getLeaveWordId, commentDTO.getTypeId()));
                User replyUser = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUserId, leaveWord.getUserId()));
                // 用户没绑定邮箱，或者回复的留言是自己
                if (Objects.equals(replyUser.getEmail(), null) || Objects.equals(replyUser.getEmail(), user.getEmail()))
                    return ResponseResult.success();
                // 发送邮箱给该留言的用户
                publicService.sendEmail(MailBoxAlertEnum.COMMENT_NOTIFICATION_EMAIL.getCodeStr(), replyUser.getEmail(), selectWhereMap);
            }
        }
        // 回复评论
        if (Objects.nonNull(commentDTO.getReplyId())) {
            User replyUser = userMapper.selectById(commentDTO.getReplyUserId());
            if ((commentDTO.getType() == 1 && !articleReplyNotice) || (commentDTO.getType() == 2 && !messageReplyNotice))
                return ResponseResult.success();

            // 如果用户回复自己并且回复人是站长就无需提醒
            if (Objects.equals(replyUser.getEmail(), user.getEmail()) && Objects.equals(fromUser, user.getEmail())) {
                return ResponseResult.success();
            }

            Map<String, Object> selectWhereMap = new HashMap<>();
            selectWhereMap.put("commentType", commentDTO.getType());
            selectWhereMap.put("commentId", comment.getCommentId());
            selectWhereMap.put("replyCommentId", commentDTO.getReplyId());

            // 回复人与被回复人不是站长本人的话就发送新增评论邮箱给站长
            if (!Objects.equals(user.getEmail(), fromUser) && !Objects.equals(replyUser.getEmail(), fromUser)) {
                publicService.sendEmail(MailBoxAlertEnum.COMMENT_NOTIFICATION_EMAIL.getCodeStr(), fromUser, selectWhereMap);
            }

            // 回复人不是站长本人并且不是自己回复自己，就发送回复通知
            if (!Objects.equals(user.getEmail(), replyUser.getEmail())) {
                publicService.sendEmail(MailBoxAlertEnum.REPLY_COMMENT_NOTIFICATION_EMAIL.getCodeStr(), replyUser.getEmail(), selectWhereMap);
            }
        }
        return ResponseResult.success();
    }

//    private List<CommentVO> getChildren(Long parentId) {
//        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(Comment::getParentId, parentId)
//                    .orderByAsc(Comment::getCreateTime);
//        List<Comment> children = list(queryWrapper);
//        return children.stream().map(this::convertToVO).collect(Collectors.toList());
//    }
//
//    private CommentVO convertToVO(Comment comment) {
//        CommentVO vo = new CommentVO();
//        vo.setId(comment.getCommentId());
//        vo.setUserId(comment.getCommentUserId());
//        vo.setContent(comment.getCommentContent());
//        vo.setCreateTime(comment.getCreateTime());
//        vo.setParentId(comment.getParentId());
//        vo.setReplyId(comment.getReplyId());
//
//        User user = userMapper.selectById(comment.getCommentUserId());
//        if (user != null) {
//            vo.setUserAvatar(user.getAvatar());
//            vo.setUserNickname(user.getNickname());
//        }
//
//        if (comment.getReplyUserId() != null) {
//            User replyUser = userMapper.selectById(comment.getReplyUserId());
//            if (replyUser != null) {
//                vo.setReplyUserNickname(replyUser.getNickname());
//            }
//        }
//
//        return vo;
//    }
}
