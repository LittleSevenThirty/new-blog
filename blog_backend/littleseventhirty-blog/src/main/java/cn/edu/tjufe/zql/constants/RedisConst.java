package cn.edu.tjufe.zql.constants;

/**
 * @author: littleseventhirty
 * @description: Redis相关常量
 * @date: 2026/1/28-09:37
 **/
public class RedisConst {
    /**
     * jwt 黑名单（退出登录的用户jwt加入黑名单）
     */
    public static final String JWT_WHITE_LIST = "jwt:white:list:";

    /**
     * 存放文章评论数的键
     */
    public static final String ARTICLE_COMMENT_COUNT = "article:comment:count";
    /**
     * 存放文章点赞的键
     */
    public static final String ARTICLE_LIKE_COUNT = "article:like:count";

    /**
     * 文章访问量限制key
     */
    public static final String ARTICLE_VISIT_COUNT_LIMIT = "article:visit:count:limit:";

    /**
     * 文章访问数
     */
    public static final String ARTICLE_VISIT_COUNT = "article:visit:count:";

    /**
     * 存放文章收藏的键
     */
    public static final String ARTICLE_FAVORITE_COUNT = "article:favorite:count";

    /**
     * 文章访问量新增间隔时间，15分钟,单位秒
     */
    public static final Integer ARTICLE_VISIT_COUNT_INTERVAL = 15 * 60;

    /**
     * 邮箱验证码
     */
    public static final String VERIFY_CODE = "verifyCode:";

    /**
     * 邮箱验证码过期时间
     */
    public static final Integer VERIFY_CODE_EXPIRATION = 5;

    /**
     * 分隔符
     */
    public static final String SEPARATOR = ":";

    /**
     * 重置邮箱
     */
    public static final String RESET_EMAIL = "resetEmail";
}
