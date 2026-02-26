package cn.edu.tjufe.zql.service.impl;

import cn.edu.tjufe.zql.constants.SecurityConst;
import cn.edu.tjufe.zql.domain.entity.LoginUser;
import cn.edu.tjufe.zql.domain.entity.User;
import cn.edu.tjufe.zql.domain.vo.UserAccountVO;
import cn.edu.tjufe.zql.mapper.UserMapper;
import cn.edu.tjufe.zql.service.IUserService;
import cn.edu.tjufe.zql.utils.RedisCache;
import cn.edu.tjufe.zql.utils.SecurityUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private RedisCache redisCache;

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

    @Override
    public String login(String username, String password) {
        // 创建认证对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
                password);
        // 进行认证
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        // 获取认证成功的用户信息
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        // 生成JWT令牌
        String token = UUID.randomUUID().toString();
        // 将用户信息存入Redis
        redisCache.setCacheObject("login:" + token, loginUser);
        // 返回令牌
        return token;
    }

    @Override
    public void logout() {
        // 获取当前用户的认证信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof LoginUser loginUser) {
            // 从Redis中删除用户信息
            String token = SecurityContextHolder.getContext().getAuthentication().getName();
            redisCache.deleteObject("login:" + token);
        }
    }
}
