package cn.edu.tjufe.zql.annotation;

import java.lang.annotation.*;

/**
 * @author: littleseventhirty
 * @description: Redis限流注解-目前使用不到
 * @date: 2026/1/4-9:40AM
 **/
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessIntercepter {
    /**
     * 限制周期（秒）
     */
    int seconds();

    /**
     * 周期内的限制次数
     */
    int maxCount();

    /**
     * 触发限制时，返回的提示信息
     */
    String message() default "操作过于频繁，请稍后再试";
}
