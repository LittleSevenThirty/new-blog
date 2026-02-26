package cn.edu.tjufe.zql.domain.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/2/24
 * @github https://github.com/little-seven-thirty
 */
@Data
@Builder
@Accessors(chain = true)
public class LeaveWordVO {
    @Schema(description = "留言id")
    private Long leaveWordId;
    @Schema(description = "用户id")
    private Long userId;
    @Schema(description = "留言内容")
    private String content;
    @Schema(description = "用户昵称")
    private String nickName;
    @Schema(description = "创建时间")
    private Date createTime;
    @Schema(description = "用户头像")
    private String avatar;
    @Schema(description = "评论数量")
    private Long commentCount;
    @Schema(description = "点赞数量")
    private Long likeCount;
    @Schema(description = "收藏数量")
    private Long favoriteCount;
}
