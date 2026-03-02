package cn.edu.tjufe.zql.constants;

/**
 * @author: littleseventhirty
 * @description: security相关常量
 * @date: 2026/1/13-15:28
 **/
public class SecurityConst {
    // 角色标签-前缀
    public static String ROLE_PREFIX = "ROLE_";

    // 用户接口校验
    public static final String AUTH_CHECK="/user/auth/**";

    /**
     * 需要校验的文章接口
     */
    public static final String ARTICLE_CHECK = "article/auth/**";

    /**
     * 需要校验的树洞接口
     */
    public static final String TREE_HOLE_CHECK = "/treeHole/auth/**";

    /**
     * 需要校验的评论接口
     */
    public static final String COMMENT_CHECK = "/comment/auth/**";
    /**
     * 需要校验的收藏接口
     */
    public static final String FAVORITE_CHECK = "/favorite/auth/**";
    /**
     * 需要校验的点赞接口
     */
    public static final String LIKE_CHECK = "/like/auth/**";
    /**
     * 需要校验的留言板接口
     */
    public static final String LEAVE_WORD_CHECK = "/leaveWord/auth/**";

    // 路由数组
    public static final String[] AUTH_CHECK_ARRAY={
            AUTH_CHECK,
            ARTICLE_CHECK,
            TREE_HOLE_CHECK,
            COMMENT_CHECK,
            FAVORITE_CHECK,
            LIKE_CHECK,
            LEAVE_WORD_CHECK,
    };

    /**
     * 登录网页
     */
    public static final String LOGIN_PAGE="/user/login";

    /**
     * 退出登录页面
     */
    public static final String LOGOUT_PAGE = "/user/logout";
}
