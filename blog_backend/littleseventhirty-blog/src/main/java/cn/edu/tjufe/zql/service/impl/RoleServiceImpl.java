package cn.edu.tjufe.zql.service.impl;


import cn.edu.tjufe.zql.domain.dto.RoleDTO;
import cn.edu.tjufe.zql.domain.dto.RoleSearchDTO;
import cn.edu.tjufe.zql.domain.entity.Role;
import cn.edu.tjufe.zql.domain.entity.RoleMenu;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
import cn.edu.tjufe.zql.domain.vo.RoleAllVO;
import cn.edu.tjufe.zql.domain.vo.RoleByIdVO;
import cn.edu.tjufe.zql.domain.vo.RoleVO;
import cn.edu.tjufe.zql.mapper.RoleMapper;
import cn.edu.tjufe.zql.mapper.RoleMenuMapper;
import cn.edu.tjufe.zql.service.IRoleMenuService;
import cn.edu.tjufe.zql.service.IRoleService;
import cn.edu.tjufe.zql.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/3/11
 * @github https://github.com/little-seven-thirty
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private RoleMenuMapper roleMenuMapper;
    @Resource
    private IRoleMenuService roleMenuService;

    @Override
    public ResponseResult<List<RoleVO>> selectAll() {
        List<Role> roles = roleMapper.selectList(new LambdaQueryWrapper<Role>().eq(Role::getStatus, 0));
        List<RoleVO> vos = roles.stream().map(role -> role.asViewObject(RoleVO.class)).toList();
        if (!vos.isEmpty()) return ResponseResult.success(vos);
        return ResponseResult.failure();
    }

    @Override
    public List<RoleAllVO> selectRole(RoleSearchDTO roleSearchDTO) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        if(Objects.nonNull(roleSearchDTO)){
            queryWrapper.like(Objects.nonNull(roleSearchDTO.getRoleName()),Role::getRoleName,roleSearchDTO.getRoleName())
                    .like(Objects.nonNull(roleSearchDTO.getRoleKey()),Role::getRoleKey,roleSearchDTO.getRoleKey())
                    .eq(Objects.nonNull(roleSearchDTO.getStatus()),Role::getStatus,roleSearchDTO.getStatus());

            if(roleSearchDTO.getCreateTimeStart()!=null&&roleSearchDTO.getCreateTimeEnd()!=null){
                queryWrapper.ge(Role::getCreateTime,roleSearchDTO.getCreateTimeStart()).lt(Role::getCreateTime,roleSearchDTO.getCreateTimeEnd());
            }
        }
        queryWrapper.orderByAsc(Role::getOrderNum);
        return roleMapper.selectList(queryWrapper).stream().map(role->role.asViewObject(RoleAllVO.class)).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public ResponseResult<Void> updateStatus(Long id, Integer status) {
        if (roleMapper.updateById(new Role().setRoleId(id).setStatus(status)) > 0) {
            return ResponseResult.success();
        }
        return ResponseResult.failure();
    }

    @Override
    public ResponseResult<RoleByIdVO> selectRoleById(Long id) {
        List<Long> menuIds = roleMenuMapper
                .selectList(new LambdaQueryWrapper<RoleMenu>().eq(RoleMenu::getRoleId, id))
                .stream().map(RoleMenu::getMenuId).toList();
        Role role = roleMapper.selectById(id);
        if (role != null) {
            RoleByIdVO vo = role.asViewObject(RoleByIdVO.class, v -> v.setMenuIds(menuIds));
            return ResponseResult.success(vo);
        }
        return ResponseResult.failure();
    }

    @Transactional
    @Override
    public ResponseResult<Void> updateOrInsertRole(RoleDTO roleDTO) {
        Role role = roleDTO.asViewObject(Role.class);
        // 角色字符是否重复
        Role isRole = roleMapper.selectOne(new LambdaQueryWrapper<Role>().eq(Role::getRoleKey, role.getRoleKey().trim()));
        if (StringUtils.isNotNull(isRole) && !isRole.getRoleId().equals(role.getRoleId())) {
            return ResponseResult.failure("角色字符不可重复");
        }
        if (this.saveOrUpdate(role)) {
            // 添加与菜单的权限
            roleMenuMapper.delete(new LambdaQueryWrapper<RoleMenu>().eq(RoleMenu::getRoleId, role.getRoleId()));
            List<RoleMenu> roleMenus = roleDTO.getMenuIds().stream().map(menuId -> new RoleMenu(role.getRoleId(), menuId)).toList();
            roleMenuService.saveBatch(roleMenus);
        }
        return ResponseResult.success();
    }

    @Transactional
    @Override
    public ResponseResult<Void> deleteRole(List<Long> ids) {
        if (roleMapper.deleteBatchIds(ids) > 0) {
            roleMenuMapper.deleteBatchIds(ids);
            return ResponseResult.success();
        }
        return ResponseResult.failure();
    }
}
