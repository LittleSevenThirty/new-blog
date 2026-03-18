package cn.edu.tjufe.zql.domain.dto;


import cn.edu.tjufe.zql.domain.ViewObjectConvertible;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/3/17
 * @github https://github.com/little-seven-thirty
 */
@Data
public class MenuDTO implements Serializable, ViewObjectConvertible {
    // 菜单id
    private Long menuId;
    // 父级菜单id
    private Long parentId;
    // 菜单名
    private String title;
    // 可获取当前菜单的角色id
    private List<Long> roleId;
    // 排序
    private Integer orderNum;
    // 菜单图标
    private String icon;
    private Integer routerType;
    private String component;
    private String redirect;
    private String path;
    private String url;
    private String target;
    private Integer affix;
    private Integer keepAlive;
    private Integer hideInMenu;
    private Integer isDisable;
}
