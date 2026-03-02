package cn.edu.tjufe.zql.service;

import cn.edu.tjufe.zql.domain.entity.User;
import cn.edu.tjufe.zql.domain.vo.UserAccountVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author: littleseventhirty
 * @description:
 * @date: 2026/1/13-12:59
 **/
public interface IUserService extends IService<User> {
    /**
     * 根据用户id获取用户信息
     *
     * @param id
     * @return 用户信息
     */
    UserAccountVO getUserInfoById(Long id);


    /**
     * 用户退出登录
     */
    void logout();

    /**
     * 用户登录状态
     * @param id 用户id
     * @param type 登录类型
     */
    void userLoginStatus(Long id, Integer type);
}
