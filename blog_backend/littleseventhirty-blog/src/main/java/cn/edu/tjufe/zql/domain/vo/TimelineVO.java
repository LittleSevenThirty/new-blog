package cn.edu.tjufe.zql.domain.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/2/13
 * @github https://github.com/little-seven-thirty
 */
@Schema(description = "时间轴VO")
@Data
public class TimelineVO {
    @Schema(description = "文章id")
    private Long articleId;
    @Schema(description="文章缩略图")
    private String articleCover;
    @Schema(description="文章内容")
    private String articleContent;
    @Schema(description="创建时间")
    private Date createTime;
}
