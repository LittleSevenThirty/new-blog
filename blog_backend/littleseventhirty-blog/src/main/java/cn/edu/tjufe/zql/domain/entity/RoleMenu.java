package cn.edu.tjufe.zql.domain.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @authro 钟奇林
 * @description 角色-菜单对实体
 * @date 2026/3/11
 * @github https://github.com/little-seven-thirty
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_role_menu")
public class RoleMenu {
    //主键
    @TableId("role_menu_id")
    private Long roleMenuId;
    //角色id
    private Long roleId;
    //菜单id
    private Long menuId;

    public RoleMenu(Long roleId, Long menuId) {
        this.roleId = roleId;
        this.menuId = menuId;
    }
}
