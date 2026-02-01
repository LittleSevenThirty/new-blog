package cn.edu.tjufe.zql.domain.vo;

import lombok.Data;

/**
 * @author: littleseventhirty
 * @description: 这个对接前端数据结构
 * @date: 2025/12/15-11:24
 **/
@Data
public class SearchArticleByContentVO {
    /**
     * 文章id
     */
    private String articleId;

    /**
     * 文章标题
     */
    private String articleTitle;

    /**
     * 文章分类
     */
    private String categoryName;

    /**
     * 文章访问数量
     */
    private Long visitedCount;

    /**
     * 文章内容
     */
    private String articleContent;
}
