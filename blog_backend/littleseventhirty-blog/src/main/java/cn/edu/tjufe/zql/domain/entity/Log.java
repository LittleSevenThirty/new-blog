package cn.edu.tjufe.zql.domain.entity;


import cn.edu.tjufe.zql.domain.ViewObjectConvertible;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/3/11
 * @github https://github.com/little-seven-thirty
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(value = "sys_log")
public class Log implements Serializable, ViewObjectConvertible {
    @Serial
    private static final long serialVersionUID = 1L;

    //编号
    @TableId("log_id")
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
    //操作状态(0：成功，1：失败，2：异常)
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
    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    //更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    //是否删除（0：未删除，1：已删除）
    private Integer isDeleted;
}
