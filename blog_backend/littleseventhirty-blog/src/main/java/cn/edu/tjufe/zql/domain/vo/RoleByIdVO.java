package cn.edu.tjufe.zql.domain.vo;


import lombok.Data;

import java.util.List;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/3/11
 * @github https://github.com/little-seven-thirty
 */
@Data
public class RoleByIdVO {
    //角色id
    private Long roleId;
    // 角色名称
    private String roleName;
    //角色字符
    private String roleKey;
    // 顺序
    private Long orderNum;
    // 备注
    private String remark;
    // 状态（0：正常，1：停用）
    private Integer status;
    // 角色拥有的菜单权限
    private List<Long> menuIds;
}
