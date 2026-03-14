package cn.edu.tjufe.zql.mapper;

import cn.edu.tjufe.zql.domain.email.CommentEmail;
import cn.edu.tjufe.zql.domain.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author: littleseventhirty
 * @description:
 * @date: 2026/1/9-16:42
 **/
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
    /**
     * 查询用户评论
     * @param commentId 评论id
     * @param type 评论类型
     * @return
     */
    CommentEmail getCommentAsEmail(@Param("commentId") Long commentId,@Param("type") Integer type);
}
