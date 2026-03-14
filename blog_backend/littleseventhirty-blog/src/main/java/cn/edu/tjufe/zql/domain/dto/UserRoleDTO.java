package cn.edu.tjufe.zql.domain.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 *
 * @authro 钟奇林
 * @description 为某一角色添加新用户
 * @date 2026/3/12
 * @github https://github.com/little-seven-thirty
 */
@Data
public class UserRoleDTO {
    @NotNull(message = "角色不能为空")
    private Long roleId;
    @NotNull(message = "选择的用户不能为空")
    private List<Long> userId;
}
