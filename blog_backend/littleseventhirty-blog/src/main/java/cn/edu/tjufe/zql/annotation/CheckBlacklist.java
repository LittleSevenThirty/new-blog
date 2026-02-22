package cn.edu.tjufe.zql.annotation;

import java.lang.annotation.*;

/**
 * 封禁验证注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface CheckBlacklist {
}
