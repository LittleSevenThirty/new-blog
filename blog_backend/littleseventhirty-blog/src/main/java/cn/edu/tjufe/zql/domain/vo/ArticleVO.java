package cn.edu.tjufe.zql.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: littleseventhirty
 * @description: 文章展示视图类
 * @date: 2026/1/27-11:05
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Schema(name = "ArticleVO", description = "文章卡片VO")
public class ArticleVO {
    // 文章id
    @Schema(description = "文章id")
    private Long articleId;
    // 分类名称
    @Schema(description = "分类名称")
    private String categoryName;
    // 文章标签
    @Schema(description = "文章标签")
    private List<String> tags;
    // 文章缩略图
    @Schema(description = "文章缩略图")
    private String articleCover;
    // 文章标题
    @Schema(description = "文章标题")
    private String articleTitle;
    // 文章内容
    @Schema(description = "文章内容")
    private String articleContent;
    // 类型（1原创 2转载 3翻译）
    @Schema(description = "类型（1原创 2转载 3翻译）")
    private Integer articleType;
    // 访问量
    @Schema(description = "访问量")
    private Long visitedCount;
    // 评论数
    @Schema(description = "评论数")
    private Long commmentCount;
    // 点赞数
    @Schema(description = "点赞数")
    private Long likeCount;
}
