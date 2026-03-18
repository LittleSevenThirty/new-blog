package cn.edu.tjufe.zql.domain.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/2/16
 * @github https://github.com/little-seven-thirty
 */
@Data
@Schema(name="categoryArticleVO",description="分类文章VO")
public class CategoryArticleVO {
    @Schema(description = "文章id")
    private Long articleId;
    @Schema(description="分类id")
    private Long categoryId;
    @Schema(description="标签列表")
    private List<TagVO> tags;
    @Schema(description="文章缩略图")
    private String articleCover;
    @Schema(description="文章标题")
    private String articleTitle;
    @Schema(description="文章访问量")
    private Long visitedCount;
    @Schema(description = "创建时间")
    private Date createTime;
}
