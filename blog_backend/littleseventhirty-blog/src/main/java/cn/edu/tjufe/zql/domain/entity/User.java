package cn.edu.tjufe.zql.domain.entity;


import cn.edu.tjufe.zql.domain.ViewObjectConvertible;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
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
    // 表标识
    private Long id;
    // 用户标识
    private Long userId;
    // 用户昵称
    private String nickName;
    // 用户名
    private String userName;
    // 密码
    private String password;
    // 用户性别(0男，1女，2其它）
    private Integer gender;
    // 用户头像
    private String avatar;
    // 个人简介
    private String introduce;
    // 个人邮箱
    private String email;
    // 注册方式(0邮箱/姓名，1gitee，2github)
    private Integer registerType;
    // 登录时间
    private Date loginTime;
    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date createTIme;
    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    // 是否删除
    private Integer isDelete;
}
