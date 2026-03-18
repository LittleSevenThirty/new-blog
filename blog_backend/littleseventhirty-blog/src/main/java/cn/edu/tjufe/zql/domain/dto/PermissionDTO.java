package cn.edu.tjufe.zql.domain.dto;


import cn.edu.tjufe.zql.domain.ViewObjectConvertible;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/3/12
 * @github https://github.com/little-seven-thirty
 */
@Accessors(chain = true)
@Data
public class PermissionDTO implements ViewObjectConvertible {
    //权限表id
    private Integer permissionId;
    //描述
    @NotNull(message = "权限描述不能为空")
    private String permissionDesc;
    //权限字符
    @NotNull(message = "权限字符不能为空")
    private String permissionKey;
    // 所在菜单
    @NotNull(message = "所在菜单不能为空")
    private Long permissionMenuId;
}
