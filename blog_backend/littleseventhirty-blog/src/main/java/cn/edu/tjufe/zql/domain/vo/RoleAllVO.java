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
public class RoleAllVO {
    //角色id
    private Long roleId;
    // 角色名称
    private String roleName;
    //角色字符
    private String roleKey;
    // 状态（0：正常，1：停用）
    private Integer status;
    // 顺序
    private Long orderNum;
    //创建时间
    private Date createTime;
}
