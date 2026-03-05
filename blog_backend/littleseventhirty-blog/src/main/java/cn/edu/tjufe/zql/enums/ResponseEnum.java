package cn.edu.tjufe.zql.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: littleseventhirty
 * @description: 统一响应枚举类
 * @date: 2025/12/31-14:35
 **/
@AllArgsConstructor
@Getter
public enum ResponseEnum {

    /**
     * 请求成功
     */
    SUCCESS("200","success（成功）"),
    /**
     * 请求失败
     */
    FAILURE("500","failure（失败）"),
    /**
     * 未登录提示
     */
    NOT_LOGIN("1002", "请先登录"),
    /**
     * 没有权限
     */
    NO_PERMISSION("1003", "没有权限"),
    /**
     * 验证码错误
     */
    VERIFY_CODE_ERROR("1005", "验证码错误"),
    /**
     * 用户名或密码错误
     */
    USERNAME_OR_PASSWORD_ERROR("1001", "用户名或密码错误"),
    ;

    private final String code;

    private final String message;
}
