package cn.edu.tjufe.zql.service;

import cn.edu.tjufe.zql.domain.dto.*;
import cn.edu.tjufe.zql.domain.entity.User;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
import cn.edu.tjufe.zql.domain.vo.UserAccountVO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author: littleseventhirty
 * @description:
 * @date: 2026/1/13-12:59
 **/
public interface IUserService extends IService<User>, UserDetailsService {
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

    /**
     *  修改用户信息
     * @param userUpdateDTO 参数
     * @return 是否成功
     */
    ResponseResult<Void> updateUser(UserUpdateDTO userUpdateDTO);

    /**
     *  修改邮箱
     * @param updateEmailDTO 参数
     * @return 是否成功
     */
    ResponseResult<Void> updateEmailAndVerify(UpdateEmailDTO updateEmailDTO);

    /**
     * 第三方登录修改用户邮箱
     * @param updateEmailDTO 参数
     * @return 是否成功
     */
    ResponseResult<Void> thirdUpdateEmail(UpdateEmailDTO updateEmailDTO);

    /**
     * 根据用户名或者密码查询用户
     *
     * @param text 用户名或者邮箱
     * @return 用户信息
     */
    User findAccountByNameOrEmail(String text);

    /**
     * 用户注册
     * @param userRegisterDTO 参数
     * @return 是否成功
     */
    ResponseResult<Void> userRegister(UserRegisterDTO userRegisterDTO);

    /**
     * 用户重置密码，步骤一
     * @param userResetDTO 参数
     * @return 是否成功
     */
    ResponseResult<Void> userResetConfirm(UserResetConfirmDTO userResetDTO);

    /**
     * 重置密码，已经确认邮箱
     * @param userResetDTO 参数
     * @return 是否成功
     */
    ResponseResult<Void> userResetPassword(UserResetPasswordDTO userResetDTO);
}
