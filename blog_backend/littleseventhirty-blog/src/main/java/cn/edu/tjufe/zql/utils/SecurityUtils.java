package cn.edu.tjufe.zql.utils;

import cn.edu.tjufe.zql.domain.entity.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.List;

/**
 * @author: littleseventhirty
 * @description: Security获取相关信息工具类
 * @date: 2026/1/13-14:09
 **/
public final class SecurityUtils {
    private SecurityUtils() {
    }

    /**
     * 获取当前用户登陆ID
     *
     * @return 用户ID
     */
    public static Long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication!=null&&authentication.getPrincipal() instanceof LoginUser loginUser){
            return loginUser.getUser().getUserId();
        }
        return 0L;  // 无法获取用户ID，返回默认值
    }

    /**
     * 获取用户角色以及权限列表
     *
     * @return 角色列表
     */
    public static List<String> getUserRolesAndPermissions() {
        return Collections.emptyList(); // 没法获取用户角色，返回空列表
    }

    /**
     * 判断用户是否登录
     * @return是否登录
     */
    public static boolean isLogin() {
        // 获取认证
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication!=null&&authentication.getPrincipal() instanceof LoginUser;
    }
}
