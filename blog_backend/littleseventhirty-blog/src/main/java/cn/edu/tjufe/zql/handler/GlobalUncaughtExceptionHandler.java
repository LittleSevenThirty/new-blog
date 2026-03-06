package cn.edu.tjufe.zql.handler;


import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @authro 钟奇林
 * @description 并发线程发生错误异常时，不可使用try-catch，使用该类统一处理，
 * @date 2026/3/6
 * @github https://github.com/little-seven-thirty
 */
@Slf4j
public class GlobalUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    // 饿汉
    @Getter
    private static final GlobalUncaughtExceptionHandler INSTANCE = new GlobalUncaughtExceptionHandler();
    private GlobalUncaughtExceptionHandler() {
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        log.error("Exception in thread {} ", t.getName(), e);
    }
}
