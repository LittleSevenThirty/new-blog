package cn.edu.tjufe.zql.domain.vo;

import lombok.Data;

/**
 * @author: littleseventhirty
 * @description: 热点文章视图类
 * @date: 2026/1/5-09:53
 **/
@Data
public class HotArticleVO {
    // 文章ID
    private Long articleId;

    // 文章标题
    private String articleTitle;

    // 被访问次数
    private Long visitedCount;
}
