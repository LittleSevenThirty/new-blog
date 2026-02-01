package cn.edu.tjufe.zql.domain.vo;

import lombok.Data;

/**
 * @author: littleseventhirty
 * @description:
 * @date: 2026/1/5-11:49
 **/
@Data
public class InitSearchTitleVO {
    // 文章ID；
    private Long articleId;
    // 文章分类名
    private String categoryName;
    // 文章标题
    private String articleTitle;
    // 访问数量
    private Long visitedCount;
}
