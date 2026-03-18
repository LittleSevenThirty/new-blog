package cn.edu.tjufe.zql.domain.dto;

import cn.edu.tjufe.zql.domain.ViewObjectConvertible;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserCommentDTO implements ViewObjectConvertible {
    //评论类型 (1文章 2留言板)
    @Schema(description = "评论类型 (1文章 2留言板)")
    @NotNull
    private Integer type;
    //类型id
    @Schema(description = "类型id")
    @NotNull
    private Integer typeId;
    //父评论id
    @Schema(description = "父评论id")
    private Long parentId;
    //回复评论id
    @Schema(description = "回复评论id")
    private Long replyId;
    //评论的内容
    @Schema(description = "评论的内容")
    @NotNull
    private String commentContent;
    //回复用户的id
    @Schema(description = "回复用户的id")
    private Long replyUserId;
}