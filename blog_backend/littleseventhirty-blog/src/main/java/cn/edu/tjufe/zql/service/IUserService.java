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
}
