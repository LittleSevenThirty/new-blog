package cn.edu.tjufe.zql.domain.dto;


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
public class RoleSearchDTO {
    // 角色名称
    private String roleName;
    //角色字符
    private String roleKey;
    // 状态（0：正常，1：停用）
    private Integer status;
    //创建时间，开始时间
    private Date createTimeStart;
    //创建时间，结束时间
    private Date createTimeEnd;
}
