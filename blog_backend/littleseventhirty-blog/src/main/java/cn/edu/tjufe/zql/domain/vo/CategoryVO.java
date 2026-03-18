package cn.edu.tjufe.zql.domain.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/2/17
 * @github https://github.com/little-seven-thirty
 */
@Data
@Schema(name="categoryVO",description = "分类视图类")
public class CategoryVO {
    @Schema(description = "分类id")
    private Long categoryId;
    @Schema(description="分类名")
    private String categoryName;
    @Schema(description="分类下的文章数量")
    private Long articleCount;
    @Schema(description = "分类标签创建时间")
    private Date createTime;
    @Schema(description="分类标签更新时间")
    private Date updateTime;
}
