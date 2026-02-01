package cn.edu.tjufe.zql.constants;

/**
 * @author: littleseventhirty
 * @description: Redis相关常量
 * @date: 2026/1/28-09:37
 **/
public class RedisConst {
    /**
     * 存放文章评论数的键
     */
    public static final String ARTICLE_COMMENT_COUNT = "article:comment:count";
    /**
     * 存放文章点赞的键
     */
    public static final String ARTICLE_LIKE_COUNT = "article:like:count";
    /**
     * 存放文章收藏的键
     */
    public static final String ARTICLE_FAVORITE_COUNT = "article:favorite:count";
}
