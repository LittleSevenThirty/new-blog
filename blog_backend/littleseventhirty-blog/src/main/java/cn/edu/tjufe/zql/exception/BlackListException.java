package cn.edu.tjufe.zql.exception;


/**
 *
 * @authro 钟奇林
 * @description 黑名单异常
 * @date 2026/3/16
 * @github https://github.com/little-seven-thirty
 */
public class BlackListException extends Exception {
    public BlackListException() {
        super("黑名单异常");
    }
    public BlackListException(String message) {
        super(message);
    }
}
