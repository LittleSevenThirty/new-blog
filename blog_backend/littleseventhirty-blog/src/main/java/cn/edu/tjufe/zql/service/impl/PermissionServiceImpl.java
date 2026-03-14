package cn.edu.tjufe.zql.service.impl;


import cn.edu.tjufe.zql.domain.dto.PermissionDTO;
import cn.edu.tjufe.zql.domain.entity.Menu;
import cn.edu.tjufe.zql.domain.entity.Permission;
import cn.edu.tjufe.zql.domain.entity.RolePermission;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
import cn.edu.tjufe.zql.domain.vo.PermissionMenuVO;
import cn.edu.tjufe.zql.domain.vo.PermissionVO;
import cn.edu.tjufe.zql.mapper.MenuMapper;
import cn.edu.tjufe.zql.mapper.PermissionMapper;
import cn.edu.tjufe.zql.mapper.RolePermissionMapper;
import cn.edu.tjufe.zql.service.IPermissionService;
import cn.edu.tjufe.zql.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/3/11
 * @github https://github.com/little-seven-thirty
 */
@Service("permissionService")
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {
    @Resource
    private PermissionMapper permissionMapper;
    @Resource
    private MenuMapper menuMapper;
    @Resource
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public List<PermissionVO> selectPermission(String permissionDesc, String permissionKey, Long permissionMenuId) {
        // 构造查询sql
        LambdaQueryWrapper<Permission> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Objects.nonNull(permissionDesc), Permission::getPermissionDesc, permissionDesc)
                .like(Objects.nonNull(permissionKey), Permission::getPermissionKey, permissionKey)
                .eq(Objects.nonNull(permissionMenuId), Permission::getMenuId, permissionMenuId);
        // select * from xxx where [permission_desc like { permissionDesc }] and [permission_key like { permissionKey }] and [menu_id  = { menuId }]
        List<Permission> permissions = permissionMapper.selectList(wrapper);

        if (!permissions.isEmpty()) {
            List<Menu> menus = menuMapper.selectBatchIds(permissions.stream().map(Permission::getMenuId).toList());
            return permissions.stream().map(permission -> permission.asViewObject(PermissionVO.class, v -> {
                Optional<Menu> menu = menus.stream().filter(m -> m.getMenuId().equals(permission.getMenuId())).findFirst();
                menu.ifPresent(m -> v.setMenuName(m.getTitle()));
            })).toList();
        }
        return new ArrayList<>();
    }

    @Override
    public List<PermissionMenuVO> selectPermissionMenu() {
        List<Permission> permissions = permissionMapper.selectList(null);
        if (!permissions.isEmpty()) {
            List<Menu> menus = menuMapper.selectBatchIds(permissions.stream().map(Permission::getMenuId).toList());
            List<PermissionMenuVO> vos = permissions.stream().map(permission -> permission.asViewObject(PermissionMenuVO.class, v -> {
                Optional<Menu> menu = menus.stream().filter(m -> m.getMenuId().equals(permission.getMenuId())).findFirst();
                menu.ifPresent(m -> {
                    v.setMenuName(m.getTitle());
                    v.setMenuId(m.getMenuId());
                });
            })).toList();
            return vos.stream().distinct().toList();
        }
        return new ArrayList<>();
    }

    @Transactional
    @Override
    public ResponseResult<Void> updateOrInsertPermission(PermissionDTO permissionDTO) {
        // 权限字符是否重复
        Permission isPermission = permissionMapper.selectOne(new LambdaQueryWrapper<Permission>().eq(Permission::getPermissionKey, permissionDTO.getPermissionKey().trim()));
        if (StringUtils.isNotNull(isPermission) && !isPermission.getPermissionId().equals(permissionDTO.getPermissionId())) {
            return ResponseResult.failure("权限字符不可重复");
        }
        Permission permission = permissionDTO.asViewObject(Permission.class, v -> v.setMenuId(permissionDTO.getPermissionMenuId()));
        if (this.saveOrUpdate(permission)) {
            return ResponseResult.success();
        }
        return ResponseResult.failure();
    }

    @Override
    public PermissionDTO getPermission(Long id) {
        Permission permission = getById(id);
        return permission.asViewObject(PermissionDTO.class, v -> v.setPermissionMenuId(permission.getMenuId()));

    }

    @Transactional
    @Override
    public ResponseResult<Void> deletePermission(Long id) {
        if (permissionMapper.deleteById(id) > 0) {
            // 删除关系
            rolePermissionMapper.delete(new LambdaQueryWrapper<RolePermission>().eq(RolePermission::getPermissionId, id));
            return ResponseResult.success();
        }
        return ResponseResult.failure();
    }
}
