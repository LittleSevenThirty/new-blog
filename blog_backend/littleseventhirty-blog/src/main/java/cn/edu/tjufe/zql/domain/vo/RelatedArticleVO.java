package cn.edu.tjufe.zql.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * @author: littleseventhirty
 * @description: 相关文章视图类
 * @date: 2026/2/4-16:16
 **/
@Data
@Schema(name="relatedArticleVO",description = "相关文章视图类")
public class RelatedArticleVO {
    // 文章id
    @Schema(description = "文章id")
    private Long articleId;
    // 文章标题
    @Schema(description = "文章标题")
    private String articleTitle;
    // 文章缩略图
    @Schema(description="文章缩略图")
    private String articleCover;
    // 创建时间
    @Schema(description="创建时间")
    private Date createTime;
}
