package cn.edu.tjufe.zql.constants;


/**
 *
 * @authro 钟奇林
 * @description 期望值常量
 * @date 2026/3/3
 * @github https://github.com/little-seven-thirty
 */
public class RespConst {
    /**
     * Token 前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 登录成功提示
     */
    public static final String SUCCESS_LOGIN_MSG="登录成功";

    /**
     * 账号被停用
     */
    public static final String ACCOUNT_DISABLED_MSG = "该账号已被停用，无法登录";

    /**
     * 测试账号
     */
    public static final String TEST_ACCOUNT_MSG = "该账号为后台测试账号，无法登录前台";

    /**
     * 账号无权限（后台）
     */
    public static final String NO_PERMISSION_MSG = "该账号不具备任何权限，无法登录";

    /**
     * 请先获取验证码
     */
    public static final String VERIFY_CODE_NULL_MSG = "请先获取验证码";

    /**
     * Token 请求头
     */
    public static final String TOKEN_HEADER = "Authorization";

    /**
     * 用户名或密码错误
     */
    public static final String USERNAME_OR_PASSWORD_ERROR_MSG = "用户名或密码错误";
}
