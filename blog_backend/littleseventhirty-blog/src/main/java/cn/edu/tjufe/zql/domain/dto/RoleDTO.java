package cn.edu.tjufe.zql.domain.dto;


import cn.edu.tjufe.zql.domain.ViewObjectConvertible;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/3/11
 * @github https://github.com/little-seven-thirty
 */
@Accessors(chain = true)
@Data
public class RoleDTO implements Serializable, ViewObjectConvertible {
    //角色id
    private Long roleId;
    // 角色名称
    @NotNull
    private String roleName;
    //角色字符
    @NotNull
    private String roleKey;
    // 状态（0：正常，1：停用）
    @NotNull
    private Integer status;
    // 顺序
    @NotNull
    private Long orderNum;
    // 备注
    private String remark;
    // 权限菜单id
    private List<Long> menuIds;
}
