package cn.edu.tjufe.zql.service;

import cn.edu.tjufe.zql.domain.dto.PermissionDTO;
import cn.edu.tjufe.zql.domain.entity.Permission;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
import cn.edu.tjufe.zql.domain.vo.PermissionMenuVO;
import cn.edu.tjufe.zql.domain.vo.PermissionVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/3/11
 * @github https://github.com/little-seven-thirty
 */
public interface IPermissionService extends IService<Permission> {
    /**
     * 查询权限
     * @param permissionDesc 权限描述
     * @param permissionKey 权限字符
     * @return 权限
     */
    List<PermissionVO> selectPermission(String permissionDesc, String permissionKey, Long permissionMenuId);

    /**
     * 查询权限菜单
     * @return 权限所在菜单
     */
    List<PermissionMenuVO> selectPermissionMenu();

    /**
     * 修改或添加
     * @param permissionDTO 权限DTO
     * @return  修改或添加权限
     */
    ResponseResult<Void> updateOrInsertPermission(PermissionDTO permissionDTO);

    /**
     * 获取修改的权限信息
     * @param id 权限id
     * @return 需要的信息
     */
    PermissionDTO getPermission(Long id);

    /**
     * 删除权限
     * @param id 权限id
     * @return 是否成功
     */
    ResponseResult<Void> deletePermission(Long id);
}
