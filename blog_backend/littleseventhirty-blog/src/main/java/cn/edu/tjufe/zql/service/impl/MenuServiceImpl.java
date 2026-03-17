package cn.edu.tjufe.zql.service.impl;


import cn.edu.tjufe.zql.domain.dto.MenuDTO;
import cn.edu.tjufe.zql.domain.entity.*;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
import cn.edu.tjufe.zql.domain.vo.MenuByIdVO;
import cn.edu.tjufe.zql.domain.vo.MenuVO;
import cn.edu.tjufe.zql.enums.ResponseEnum;
import cn.edu.tjufe.zql.mapper.*;
import cn.edu.tjufe.zql.service.IMenuService;
import cn.edu.tjufe.zql.service.IRoleMenuService;
import cn.edu.tjufe.zql.utils.SecurityUtils;
import cn.edu.tjufe.zql.utils.ServiceUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 *
 * @authro 钟奇林
 * @description 服务表实现类
 * @date 2026/3/17
 * @github https://github.com/little-seven-thirty
 */
@Slf4j
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {
    @Resource
    private MenuMapper menuMapper;

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    private IRoleMenuService roleMenuService;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private PermissionMapper permissionMapper;

    @Override
    public ResponseResult<List<MenuVO>> getMenuList(Integer typeId, String username, Integer status) {
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(Menu::getOrderNum);
        // 路由菜单
        if (typeId == 0) {
            // 1.使用用户id查询用户角色，获取当前用户的所有角色
            List<Long> roleIds = userRoleMapper
                    .selectList(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, SecurityUtils.getUserId()))
                    .stream().map(UserRole::getRoleId).toList();
            // 2.使用角色id查询对应拥有的菜单
            List<Long> menuIds = roleMenuMapper.selectBatchIds(roleIds).stream().map(RoleMenu::getMenuId).toList();
            // 关系表里面所有的菜单id
            List<Long> roleMenuAllIds = roleMenuService.list().stream().map(RoleMenu::getMenuId).toList();
            // 所有的菜单id
            List<Long> menuAllIds = this.list().stream().map(Menu::getMenuId).toList();
            // 没有设置权限的菜单
            List<Long> noRoleByMenuIds = menuAllIds.stream().filter(menuId -> !roleMenuAllIds.contains(menuId)).toList();
            // 组合list
            menuIds.addAll(noRoleByMenuIds);

            // 显示的菜单必须是用户角色有的或者没有设置权限的菜单
            if (!menuIds.isEmpty()) {
                wrapper.in(Menu::getMenuId, menuIds);
            }
            wrapper.eq(Menu::getIsDisable, 0);
        }
        if (typeId == 1 && (username != null || status != null)) {
            wrapper.eq(status != null, Menu::getIsDisable, status).and(o -> o.like(Menu::getTitle, username));
        }
        List<Menu> menus = menuMapper.selectList(wrapper);
        List<MenuVO> list = menus.stream().map(menu -> MenuVO.builder()
                .menuId(menu.getMenuId())
                .title(menu.getTitle())
                .icon(menu.getIcon())
                .path(menu.getPath())
                .component(menu.getComponent())
                .redirect(menu.getRedirect())
                .affix(ServiceUtil.isFalseOrTrue(menu.getAffix()))
                .parentId(menu.getParentId())
                .name(menu.getName())
                .hideInMenu(ServiceUtil.isFalseOrTrue(menu.getHideInMenu()))
                .url(menu.getUrl())
                .keepAlive(ServiceUtil.isFalseOrTrue(menu.getKeepAlive()))
                .target(menu.getTarget())
                .isDisable(!ServiceUtil.isFalseOrTrue(menu.getIsDisable()))
                .orderNum(menu.getOrderNum())
                .createTime(menu.getCreateTime())
                .build()).toList();

        return ResponseResult.success(list);
    }

    @Override
    public ResponseResult<MenuByIdVO> selectUpdateMenu(Long id) {
        Menu menu = this.getById(id);
        List<Long> roleIds = roleMenuMapper
                .selectList(new LambdaQueryWrapper<RoleMenu>().eq(RoleMenu::getMenuId, menu.getMenuId()))
                .stream().map(RoleMenu::getRoleId).toList();
        List<Long> roles;
        if (!roleIds.isEmpty()) roles = roleMapper.selectBatchIds(roleIds).stream().map(Role::getRoleId).toList();
        else roles = null;
        MenuByIdVO vo = menu.asViewObject(MenuByIdVO.class, v -> {
            if (v.getComponent() == null) v.setRouterType(2L);
            else if (v.getComponent().equals("Iframe")) v.setRouterType(1L);
            else v.setRouterType(0L);
            v.setRoleId(roles);
        });
        return ResponseResult.success(vo);
    }

    @Override
    public ResponseResult<Void> addMenu(MenuDTO menuDTO) {
        if (menuDTO.getRouterType() == null) {
            menuDTO.setRouterType(0);
        }
        switch (menuDTO.getRouterType()) {
            // 普通组件
            case 0 -> {
                if (Objects.equals(menuDTO.getComponent(), "")) {
                    menuDTO.setComponent("RouteView");
                }
            }
            case 1 -> menuDTO.setComponent("Iframe");
            case 2 -> menuDTO.setComponent(null);
        }

        Menu menu = menuDTO.asViewObject(Menu.class);
        if (this.save(menu)) {
            log.info("菜单添加成功");
            return ResponseResult.success();
        }
        return ResponseResult.failure();
    }

    @Transactional
    @Override
    public ResponseResult<Void> updateMenu(MenuDTO menuDTO) {
        LambdaUpdateWrapper<Menu> wrapper = new LambdaUpdateWrapper<>();
        if (menuDTO.getRouterType() == 3) {
            // 父菜单
            menuDTO.setComponent("RouteView");
        }
        if (menuDTO.getRouterType() == 0) wrapper.set(Menu::getRedirect, null);
        if (menuDTO.getRouterType() == 0 || menuDTO.getRouterType() == 3) {
            wrapper.set(Menu::getUrl, null);
        }
        if (menuDTO.getRouterType() == 1) {
            menuDTO.setComponent("Iframe");
            wrapper.set(Menu::getTarget, null);
        }
        if (menuDTO.getRouterType() == 2) {
            wrapper.set(Menu::getComponent, null);
            wrapper.set(Menu::getRedirect, null);
        }
        if (menuDTO.getParentId() == null) wrapper.set(Menu::getParentId, null);

        Menu menu = menuDTO.asViewObject(Menu.class);

        // 是否有关联关系
        Long count = roleMenuMapper.selectCount(new LambdaQueryWrapper<RoleMenu>().eq(RoleMenu::getMenuId, menu.getMenuId()));
        if (count > 0) {
            // 删除所有的角色关联
            roleMenuMapper.delete(new LambdaQueryWrapper<RoleMenu>().eq(RoleMenu::getMenuId, menu.getMenuId()));
        }
        if (menuDTO.getRoleId() != null) {
            List<RoleMenu> roleMenus = menuDTO.getRoleId().stream().map(roleId -> new RoleMenu(roleId, menu.getMenuId())).toList();
            // 新增角色关联
            roleMenuService.saveBatch(roleMenus);
        }

        if (menuMapper.update(menu, wrapper.eq(Menu::getMenuId, menu.getMenuId())) > 0) return ResponseResult.success();
        return ResponseResult.failure();
    }

    @Transactional
    @Override
    public ResponseResult<String> deleteMenu(Long id) {
        // 判断是否有未删除的子目录
        if (menuMapper.selectCount(new LambdaQueryWrapper<Menu>().eq(Menu::getParentId, id)) > 0) {
            return ResponseResult.failure(ResponseEnum.NO_DELETE_CHILD_MENU.getCode(), ResponseEnum.NO_DELETE_CHILD_MENU.getMessage());
        }
        if (this.removeById(id)) {
            // 删除相关菜单角色关系
            roleMenuMapper.delete(new LambdaQueryWrapper<RoleMenu>().eq(RoleMenu::getMenuId, id));
            // 删除菜单对应权限
            permissionMapper.delete(new LambdaQueryWrapper<Permission>().eq(Permission::getMenuId, id));
            return ResponseResult.success();
        }
        return ResponseResult.failure();
    }
}
