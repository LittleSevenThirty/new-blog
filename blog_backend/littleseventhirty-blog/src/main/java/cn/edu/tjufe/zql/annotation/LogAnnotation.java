package cn.edu.tjufe.zql.annotation;

import java.lang.annotation.*;

/**
 * @author: littleseventhirty
 * @description:
 * @date: 2026/1/22-13:18
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {
    String module() default ""; // 模块类型

    String operation() default "";   // 操作
}
