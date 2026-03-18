package cn.edu.tjufe.zql.service;

import cn.edu.tjufe.zql.domain.dto.RoleUserDTO;
import cn.edu.tjufe.zql.domain.dto.UserRoleDTO;
import cn.edu.tjufe.zql.domain.entity.UserRole;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
import cn.edu.tjufe.zql.domain.vo.RoleAllVO;
import cn.edu.tjufe.zql.domain.vo.RoleUserVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/3/12
 * @github https://github.com/little-seven-thirty
 */
public interface IUserRoleService extends IService<UserRole> {
    /**
     * @param roleId 角色id
     * @param username 用户名
     * @param email 邮箱
     * @param type 角色类型,0：有该角色卡的用户 1：未有该角色卡的用户
     * @return 角色用户列表
     */
    List<RoleUserVO> selectRoleUser(Long roleId, String username, String email, Integer type);

    /**
     * 给角色授权用户
     * @param userRoleDTO 用户角色对象
     * @return 是否成功
     */
    ResponseResult<Void> addUserRole(UserRoleDTO userRoleDTO);

    /**
     * 取消授权
     * @param userRoleDTO 用户角色对象
     * @return 是否成功
     */
    ResponseResult<Void> deleteUserRole(UserRoleDTO userRoleDTO);

    /**
     * 有或无该用户的 角色卡
     *
     * @param userId 用户id
     * @param roleName 角色名称
     * @param roleKey 角色字符
     * @param type 角色类型,0：该角色的使用用户 1：该角色的未使用用户
     * @return 角色列表
     */
    List<RoleAllVO> selectRoleByUserId(Long userId, String roleName, String roleKey, Integer type);

    /**
     * 给多个角色添加某个用户
     * @param roleUserDTO 角色用户数据
     * @return 是否成功
     */
    ResponseResult<Void> addRoleUser(RoleUserDTO roleUserDTO);

    /**
     * 批量或单个取消授权
     * @param roleUserDTO 角色用户数据
     * @return 是否成功
     */
    ResponseResult<Void> deleteRoleUser(RoleUserDTO roleUserDTO);
}
