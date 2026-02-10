package cn.edu.tjufe.zql.domain.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/2/10
 * @github https://github.com/little-seven-thirty
 */
@Data
@Schema(name="tagVO",description = "标签VO")
public class TagVO {
    @Schema(description = "标签id")
    private Long tagId;
    @Schema(description = "标签名")
    private String tagName;
    @Schema(description = "文章数目")
    private Long articleCount;
    @Schema(description = "创建时间")
    private Date createTime;
    @Schema(description = "更新时间")
    private Date updateTime;
}
