package cn.edu.tjufe.zql.service.impl;

import cn.edu.tjufe.zql.domain.dto.CommentDTO;
import cn.edu.tjufe.zql.domain.entity.Comment;
import cn.edu.tjufe.zql.domain.entity.User;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
import cn.edu.tjufe.zql.domain.vo.CommentVO;
import cn.edu.tjufe.zql.domain.vo.PageVO;
import cn.edu.tjufe.zql.mapper.CommentMapper;
import cn.edu.tjufe.zql.mapper.UserMapper;
import cn.edu.tjufe.zql.service.ICommentService;
import cn.edu.tjufe.zql.utils.SecurityUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    @Resource
    private UserMapper userMapper;

    @Override
    public ResponseResult<Void> addComment(CommentDTO commentDTO) {
        Long userId = SecurityUtils.getUserId();
        Comment comment = Comment.builder()
                .type(commentDTO.getType())
                .typeId(commentDTO.getTypeId())
                .parentId(commentDTO.getParentId())
                .replyId(commentDTO.getReplyId())
                .commentContent(commentDTO.getCommentContent())
                .commentUserId(userId)
                .isCheck(1) // Default approved
                .isDeleted(0)
                .build();
        
        if (commentDTO.getReplyId() != null) {
            Comment replyComment = getById(commentDTO.getReplyId());
            if (replyComment != null) {
                comment.setReplyUserId(replyComment.getCommentUserId());
            }
        }
        
        save(comment);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult<PageVO<List<CommentVO>>> getCommentList(Integer type, Long typeId, Integer pageNum, Integer pageSize) {
        // Query root comments (parentId is null)
        Page<Comment> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getType, type)
                    .eq(Comment::getTypeId, typeId)
                    .isNull(Comment::getParentId)
                    .orderByDesc(Comment::getCreateTime);
        
        page(page, queryWrapper);
        
        List<Comment> rootComments = page.getRecords();
        List<CommentVO> commentVOS = new ArrayList<>();
        
        for (Comment root : rootComments) {
            CommentVO vo = convertToVO(root);
            // Get children
            List<CommentVO> children = getChildren(root.getCommentId());
            vo.setChildComment(children);
            commentVOS.add(vo);
        }
        
        return ResponseResult.success(new PageVO<>(commentVOS, page.getTotal()));
    }

    private List<CommentVO> getChildren(Long parentId) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getParentId, parentId)
                    .orderByAsc(Comment::getCreateTime);
        List<Comment> children = list(queryWrapper);
        return children.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    private CommentVO convertToVO(Comment comment) {
        CommentVO vo = new CommentVO();
        vo.setId(comment.getCommentId());
        vo.setUserId(comment.getCommentUserId());
        vo.setContent(comment.getCommentContent());
        vo.setCreateTime(comment.getCreateTime());
        vo.setParentId(comment.getParentId());
        vo.setReplyId(comment.getReplyId());
        
        User user = userMapper.selectById(comment.getCommentUserId());
        if (user != null) {
            vo.setUserAvatar(user.getAvatar());
            vo.setUserNickname(user.getNickname());
        }
        
        if (comment.getReplyUserId() != null) {
            User replyUser = userMapper.selectById(comment.getReplyUserId());
            if (replyUser != null) {
                vo.setReplyUserNickname(replyUser.getNickname());
            }
        }
        
        return vo;
    }
}
