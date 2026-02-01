package cn.edu.tjufe.zql.utils;

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
        return 0L;
    }

    /**
     * 获取用户角色以及权限列表
     *
     * @return 角色列表
     */
    public static List<String> getUserRolesAndPermissions() {
        return Collections.emptyList(); // 没法获取用户角色，返回空列表
    }
}
