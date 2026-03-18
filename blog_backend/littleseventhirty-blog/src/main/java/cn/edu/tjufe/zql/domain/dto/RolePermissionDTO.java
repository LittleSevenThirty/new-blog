package cn.edu.tjufe.zql.domain.dto;


import jakarta.validation.constraints.NotNull;
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
public class RolePermissionDTO {
    @NotNull(message = "角色对应权限id")
    private List<Long> permissionId;

    @NotNull(message = "角色对应id")
    private List<Long> roleId;
}
