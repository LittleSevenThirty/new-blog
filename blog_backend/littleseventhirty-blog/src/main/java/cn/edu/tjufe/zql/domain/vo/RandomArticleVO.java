package cn.edu.tjufe.zql.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * @author: littleseventhirty
 * @description: 随机文章视图类
 * @date: 2026/1/6-10:05
 **/
@Data
@Schema(name = "RandowmArticleVO", description = "随机文章VO")
public class RandomArticleVO {
    @Schema(description = "文章ID")
    private Long articleId;

    @Schema(description = "文章标题")
    private String articleTitle;

    @Schema(description = "文章被访问数量")
    private Long visitedCount;
    
    @Schema(description = "文章创建时间")
    private Date createTime;
}
