package cn.edu.tjufe.zql.domain.entity;


import cn.edu.tjufe.zql.domain.ViewObjectConvertible;
import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
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
@TableName("sys_permission")
public class Permission implements ViewObjectConvertible {
    //权限表id
    @TableId("permission_id")
    private Integer permissionId;
    //描述
    private String permissionDesc;
    //权限字符
    private String permissionKey;
    // 菜单id
    private Long menuId;
    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    //更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    //是否删除（0：未删除，1：已删除）
    private Integer isDeleted;
}
