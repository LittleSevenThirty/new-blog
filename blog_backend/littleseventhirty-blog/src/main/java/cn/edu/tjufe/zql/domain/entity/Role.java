package cn.edu.tjufe.zql.domain.entity;


import cn.edu.tjufe.zql.domain.ViewObjectConvertible;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/3/6
 * @github https://github.com/little-seven-thirty
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("sys_role")
public class Role implements ViewObjectConvertible {
    //角色id
    @TableId("role_id")
    private Long roleId;
    // 角色名称
    private String roleName;
    //角色字符
    private String roleKey;
    //是否删除（0：未删除，1：已删除）
    private Integer isDeleted;
    // 状态（0：正常，1：停用）
    private Integer status;
    // 顺序
    private Long orderNum;
    // 备注
    private String remark;
    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    //更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
