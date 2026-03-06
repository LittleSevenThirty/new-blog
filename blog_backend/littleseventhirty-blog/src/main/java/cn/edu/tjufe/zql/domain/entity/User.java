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
import lombok.experimental.Accessors;

import java.util.Date;

/**
 *
 * @authro 钟奇林
 * @description 用户登录实体
 * @date 2026/1/12
 * @github https://github.com/little-seven-thirty
 */
@TableName(value = "sys_user")
@Accessors(chain = true)  // 修改已有实例对象时可链式调用setter函数
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class User implements ViewObjectConvertible {
    // 用户标识
    @TableId("user_id")
    private Long userId;
    // 用户昵称
    @TableField("nickname")
    private String nickname;
    // 用户名
    @TableField("username")
    private String username;
    // 密码
    private String password;
    // 用户性别(0男，1女，2其它）
    private Integer gender;
    // 用户头像
    private String avatar;
    // 个人简介
    @TableField("intro")
    private String introduce;
    // 个人邮箱
    private String email;
    // 注册方式(0邮箱/姓名，1gitee，2github)
    private Integer registerType;
    // 注册ip
    private String registerIp;
    // 注册地址
    private String registerAddress;
    //用户登录方式(0邮箱/姓名 1Gitee 2Github)
    private Integer loginType;
    // 用户登录ip
    private String loginIp;
    // 登录地址
    private String loginAddress;
    //是否禁用 0 否 1 是
    private Integer isDisable;
    // 登录时间
    private Date loginTime;
    // 创建时间
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;
    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    // 是否删除
    @TableField("is_deleted")
    private Integer isDeleted;
}
