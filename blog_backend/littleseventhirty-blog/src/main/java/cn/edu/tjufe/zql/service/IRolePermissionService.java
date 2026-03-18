package cn.edu.tjufe.zql.service;

import cn.edu.tjufe.zql.domain.dto.RolePermissionDTO;
import cn.edu.tjufe.zql.domain.entity.RolePermission;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
import cn.edu.tjufe.zql.domain.vo.RoleAllVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/3/11
 * @github https://github.com/little-seven-thirty
 */
public interface IRolePermissionService extends IService<RolePermission> {
    /**
     * 所有使用该权限的角色
     *
     * @param permissionId 权限id
     * @param roleName 角色名称
     * @param roleKey 角色字符
     * @param type 角色类型,0：该角色的使用用户 1：该角色的未使用用户
     * @return 角色列表
     */
    List<RoleAllVO> selectRoleByPermissionId(Long permissionId, String roleName, String roleKey, Integer type);

    /**
     * 给多个角色添加某个权限
     * @param rolePermissionDTO 角色权限数据
     * @return 是否成功
     */
    ResponseResult<Void> addRolePermission(RolePermissionDTO rolePermissionDTO);

    /**
     * 批量或单个取消授权
     * @param rolePermissionDTO 角色权限数据
     * @return 是否成功
     */
    ResponseResult<Void> deleteRolePermission(RolePermissionDTO rolePermissionDTO);
}
