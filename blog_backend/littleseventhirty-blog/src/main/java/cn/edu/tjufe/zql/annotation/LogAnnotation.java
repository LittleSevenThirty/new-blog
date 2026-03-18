package cn.edu.tjufe.zql.annotation;

import java.lang.annotation.*;

/**
 * @author: littleseventhirty
 * @description: 操作日志注解，该注解只适用于方法放回结果是cn.edu.tjufe.zql.domain.response.ResponseReuslt
 * @date: 2026/1/22-13:18
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {
    String module() default ""; // 模块类型

    String operation() default "";   // 操作
}
