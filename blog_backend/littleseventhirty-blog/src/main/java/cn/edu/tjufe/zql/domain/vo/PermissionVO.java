package cn.edu.tjufe.zql.domain.vo;


import lombok.Data;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/3/12
 * @github https://github.com/little-seven-thirty
 */
@Data
public class PermissionVO {
    //权限表id
    private Integer permissionId;
    //描述
    private String permissionDesc;
    //权限字符
    private String permissionKey;
    // 菜单名称
    private String menuName;
}
