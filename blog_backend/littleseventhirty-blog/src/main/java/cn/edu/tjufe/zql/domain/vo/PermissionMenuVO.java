package cn.edu.tjufe.zql.domain.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/3/12
 * @github https://github.com/little-seven-thirty
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissionMenuVO {
    // 权限表id
    private Long premissionId;
    // 菜单id
    private Long menuId;
    // 菜单名称
    private String menuName;

    @Override
    public boolean equals(Object o) {
        if(this==o)return true;
        if(o==null||this.getClass()!=o.getClass())return false;
        PermissionMenuVO vo=(PermissionMenuVO)o;
        return Objects.equals(menuId,vo.menuId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuId);
    }
}
