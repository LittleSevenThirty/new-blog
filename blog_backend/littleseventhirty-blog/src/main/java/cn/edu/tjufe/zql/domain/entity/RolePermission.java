package cn.edu.tjufe.zql.domain.entity;


import cn.edu.tjufe.zql.domain.ViewObjectConvertible;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Builder
@TableName("sys_role_permission")
public class RolePermission implements ViewObjectConvertible {
    //关系表id
    @TableId("role_permission_id")
    private Integer rolePermissionId;
    //角色id
    @TableId
    private Long roleId;
    //权限id
    private Long permissionId;
}
