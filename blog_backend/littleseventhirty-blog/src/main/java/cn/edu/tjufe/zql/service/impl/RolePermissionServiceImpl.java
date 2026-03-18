package cn.edu.tjufe.zql.service.impl;


import cn.edu.tjufe.zql.domain.dto.RolePermissionDTO;
import cn.edu.tjufe.zql.domain.entity.Role;
import cn.edu.tjufe.zql.domain.entity.RolePermission;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
import cn.edu.tjufe.zql.domain.vo.RoleAllVO;
import cn.edu.tjufe.zql.mapper.RoleMapper;
import cn.edu.tjufe.zql.mapper.RolePermissionMapper;
import cn.edu.tjufe.zql.service.IRolePermissionService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/3/11
 * @github https://github.com/little-seven-thirty
 */
@Service("rolePermissionService")
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements IRolePermissionService {
    @Resource
    private RolePermissionMapper rolePermissionMapper;

    @Resource
    private RoleMapper roleMapper;

    @Override
    public List<RoleAllVO> selectRoleByPermissionId(Long permissionId, String roleName, String roleKey, Integer type) {
        LambdaQueryWrapper<RolePermission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RolePermission::getPermissionId, permissionId);
        List<Long> roleIds;
        if (type == 0) {
            // 使用该权限的角色
            roleIds = rolePermissionMapper.selectList(wrapper).stream().map(RolePermission::getRoleId).toList();
        } else {
            // 查询没有使用权限的角色id
            // 先查询使用改权限的角色
            roleIds = rolePermissionMapper.selectList(wrapper).stream().map(RolePermission::getRoleId).toList();
            // 查询没有该权限的角色
            roleIds = roleMapper.selectList(new LambdaQueryWrapper<Role>().notIn(!roleIds.isEmpty(), Role::getRoleId, roleIds))
                    .stream().map(Role::getRoleId).toList();
        }

        if (!roleIds.isEmpty()) {
            LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
            List<Role> roleList = new ArrayList<>();
            // 查询角色信息
            roleIds.forEach(roleId -> {
                if (Objects.nonNull(roleName)) queryWrapper.like(Role::getRoleName, roleName);
                if (Objects.nonNull(roleKey)) queryWrapper.and(a -> a.like(Role::getRoleKey, roleKey));
                queryWrapper.eq(Role::getRoleId, roleId);
                roleList.addAll(roleMapper.selectList(queryWrapper));
                queryWrapper.clear();
            });
            return roleList.stream().map(role -> role.asViewObject(RoleAllVO.class)).toList();
        }
        return null;
    }

    @Transactional
    @Override
    public ResponseResult<Void> addRolePermission(RolePermissionDTO rolePermissionDTO) {
        List<Long> roleIds = rolePermissionDTO.getRoleId();
        List<Long> permissionIds = rolePermissionDTO.getPermissionId();
        LambdaQueryWrapper<RolePermission> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(RolePermission::getPermissionId, permissionIds)
                .in(RolePermission::getRoleId, roleIds);
        // 如有，先删除
        if (rolePermissionMapper.selectCount(wrapper) > 0) rolePermissionMapper.delete(wrapper);

        List<RolePermission> rolePermissions = new ArrayList<>();
        roleIds.forEach(roleId -> {
            permissionIds.forEach(permissionId -> {
                rolePermissions.add(RolePermission.builder().roleId(roleId).permissionId(permissionId).build());
            });
        });
        if (saveBatch(rolePermissions)) return ResponseResult.success();

        return ResponseResult.failure();
    }

    @Transactional
    @Override
    public ResponseResult<Void> deleteRolePermission(RolePermissionDTO rolePermissionDTO) {
        int isDelete = rolePermissionMapper.delete(new LambdaQueryWrapper<RolePermission>().in(RolePermission::getPermissionId, rolePermissionDTO.getPermissionId()).in(RolePermission::getRoleId, rolePermissionDTO.getRoleId()));
        if (isDelete > 0) {
            return ResponseResult.success();
        }
        return ResponseResult.failure();
    }
}
