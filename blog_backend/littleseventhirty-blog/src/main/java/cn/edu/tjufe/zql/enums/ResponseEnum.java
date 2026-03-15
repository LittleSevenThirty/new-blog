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
     * 用户名或密码错误
     */
    USERNAME_OR_PASSWORD_ERROR("1001", "用户名或密码错误"),
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
     * 用户名或邮箱已存在
     */
    USERNAME_OR_EMAIL_EXIST("1006", "用户名或邮箱已存在"),
    /**
     * 参数错误提示
     */
    PARAM_ERROR("1007", "参数错误"),
    /**
     * 其它故障
     */
    OTHER_ERROR("1008", "其他故障"),
    /**
     * 文件上传错误
     */
    FILE_UPLOAD_ERROR("1011", "文件上传错误"),
    ;

    private final String code;

    private final String message;
}
