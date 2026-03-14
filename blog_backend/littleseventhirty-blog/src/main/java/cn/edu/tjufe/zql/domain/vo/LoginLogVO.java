package cn.edu.tjufe.zql.domain.vo;


import lombok.Data;

import java.util.Date;

/**
 *
 * @authro 钟奇林
 * @description 登录日志视图类
 * @date 2026/3/11
 * @github https://github.com/little-seven-thirty
 */
@Data
public class LoginLogVO {
    // 日志编号
    private Long loginLogId;
    // 用户名称
    private String username;
    // 登录ip
    private String ip;
    // 登录地址
    private String address;
    // 浏览器
    private String browser;
    // 操作系统
    private String os;
    // 登录类型（0：前台，1：后台，2：非法登录）
    private Integer type;
    // 登录状态（0：成功，1：失败）
    private Integer status;
    // 登录信息
    private String message;
    // 用户登录时间
    private Date loginTime;
}
