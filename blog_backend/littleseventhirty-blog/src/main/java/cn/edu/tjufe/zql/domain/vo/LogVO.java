package cn.edu.tjufe.zql.domain.vo;


import lombok.Data;

import java.util.Date;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/3/11
 * @github https://github.com/little-seven-thirty
 */
@Data
public class LogVO  {
    //编号
    private Long logId;
    //模块名称
    private String module;
    //操作
    private String operation;
    //操作人员
    private String userName;
    //ip地址
    private String ip;
    //操作地点
    private String address;
    //操作状态(0：成功，1：失败)
    private Integer state;
    //操作方法
    private String method;
    // 请求方式
    private String reqMapping;
    //请求参数
    private String reqParameter;
    // 异常信息
    private String exception;
    //返回参数
    private String returnParameter;
    //请求地址
    private String reqAddress;
    //消耗时间(ms)
    private Long time;
    // 操作描述
    private String description;
    //时间
    private Date loginTime;
}

