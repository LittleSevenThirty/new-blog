package cn.edu.tjufe.zql.service.impl;

import cn.edu.tjufe.zql.constants.SecurityConst;
import cn.edu.tjufe.zql.domain.entity.User;
import cn.edu.tjufe.zql.domain.vo.UserAccountVO;
import cn.edu.tjufe.zql.mapper.UserMapper;
import cn.edu.tjufe.zql.service.IUserService;
import cn.edu.tjufe.zql.utils.SecurityUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: littleseventhirty
 * @description: 用户服务层
 * @date: 2026/1/13-13:00
 **/
@Slf4j
@Service(value = "userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public UserAccountVO getUserInfoById(Long id) {
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUserId, id));
        // 用户角色列表
        List<String> userRoles = SecurityUtils.getUserRolesAndPermissions();
        // 存放用户角色
        List<String> roles = new ArrayList<>();
        // 存放权限
        List<String> permissions = new ArrayList<>();
        userRoles.forEach(role -> {
            if (role.startsWith(SecurityConst.ROLE_PREFIX)) {
                // 包含角色前缀的是角色，去除添加对应角色
                roles.add(role.substring(SecurityConst.ROLE_PREFIX.length()));
            } else {
                permissions.add(role);
            }
        });

        return user.asViewObject(UserAccountVO.class, (role) -> {
            role.setRoles(roles);
            role.setPermissions(permissions);
        });
    }
}
