package cn.edu.tjufe.zql.domain.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/3/8
 * @github https://github.com/little-seven-thirty
 */
@Data
public class CommentIsCheckDTO {
    // 收藏id
    @NotNull(message = "评论id不能为空")
    private Long commentId;
    // 是否通过
    @NotNull(message = "是否通过不能为空")
    private Integer isCheck;
}
