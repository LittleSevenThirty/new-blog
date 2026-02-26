package cn.edu.tjufe.zql.domain.entity;

import cn.edu.tjufe.zql.domain.ViewObjectConvertible;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author: littleseventhirty
 * @description:
 * @date: 2026/1/9-16:42
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_comment")
public class Comment implements ViewObjectConvertible {
    // 评论id
    private Long commentId;
    // 评论类型（1文章，2留言）
    private Integer type;
    // 对应类型的id
    private Long typeId;
    // 父评论id
    private Long parentId;
    // 回复id
    private Long replyId;
    // 评论内容
    private String commentContent;
    // 评论用户id
    private Long commentUserId;
    // 回复用户id
    private Long replyUserId;
    // 是否通过（0否，1是）
    private Integer isCheck;
    // 创建时间
    @TableField(fill= FieldFill.INSERT)
    private Date createTime;
    // 更新时间
    @TableField(fill= FieldFill.INSERT_UPDATE)
    private Date updateTime;
    // 是否删除（0未删除，1删除）
    private Integer isDeleted;
}
