package cn.edu.tjufe.zql.constants;

/**
 * @author: littleseventhirty
 * @description: 一些公用常量
 * @date: 2025/12/15-15:59
 **/
public class SQLConst {
    /**
     * 评论是否通过(0,否)
     */
    public static final Integer COMMENT_IS_CHECK = 1;

    /**
     * 是否通过(0,否)
     */
    public static final String IS_CHECK="is_check";

    /**
     * 树洞留言是否通过
     */
    public static final Integer IS_CHECK_YES=1;

    /**
     * 公开文章的字段标识
     */
    public static final Integer PUBLIC_APTICLE = 1;

    /**
     * 选取随机文章数目
     */
    public static final Integer RANDOM_ARTICLE_COUNT = 5;

    /**
     * 推荐相关文章的数目
     */
    public static final Integer RELATED_ARTICLE_COUNT=5;

    /**
     * 推荐文章字段标识
     */
    public static final String RECOMMEND_ARTICLE = "1";

    /**
     * 限制查询一条数据
     */
    public static final String LIMIT_ONE_SQL = "LIMIT 1";
}
