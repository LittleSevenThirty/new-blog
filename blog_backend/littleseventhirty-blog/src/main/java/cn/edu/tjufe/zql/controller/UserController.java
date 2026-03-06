package cn.edu.tjufe.zql.controller;

import cn.edu.tjufe.zql.annotation.AccessIntercepter;
import cn.edu.tjufe.zql.annotation.LogAnnotation;
import cn.edu.tjufe.zql.constants.LogConst;
import cn.edu.tjufe.zql.domain.dto.*;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
import cn.edu.tjufe.zql.domain.vo.UserAccountVO;
import cn.edu.tjufe.zql.service.IUserService;
import cn.edu.tjufe.zql.utils.ResponseWrapper;
import cn.edu.tjufe.zql.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author: littleseventhirty
 * @description:
 * @date: 2026/1/13-13:03
 **/
@RequestMapping(value = "/user")
@Tag(name = "用户控制层")
@RestController
@Validated
public class UserController {

    @Resource
    private IUserService userService;

    /**
     * 获取用户信息
     *
     * @return
     */
    @Operation(summary = "当前用户登录信息")
    @AccessIntercepter(seconds = 60, maxCount = 30)
    @RequestMapping("/auth/info")
    public ResponseResult<UserAccountVO> getUserInfo() {
        return ResponseWrapper.handler(() -> userService.getUserInfoById(SecurityUtils.getUserId()));
    }

    /**
     * 退出登录
     *
     * @return 退出结果
     */
    @Operation(summary = "用户退出登录")
    @RequestMapping("/logout")
    public ResponseResult<Void> logout() {
        userService.logout();
        return ResponseResult.success();
    }

    /**
     * 前台修改用户信息
     *
     * @param userUpdateDTO 用户信息
     * @return 是否成功
     */
    @Operation(summary = "修改用户信息")
    @Parameter(name = "userUpdateDTO", description = "修改用户信息")
    @AccessIntercepter(seconds = 60, maxCount = 30)
    @PostMapping("/auth/update")
    public ResponseResult<Void> updateUser(@RequestBody @Valid UserUpdateDTO userUpdateDTO) {
        return userService.updateUser(userUpdateDTO);
    }

    /**
     * 修改用户绑定邮箱
     * @param updateEmailDTO 所需参数
     * @return 是否成功
     */
    @Operation(summary = "修改用户绑定邮箱")
    @AccessIntercepter(seconds = 60, maxCount = 30)
    @PostMapping("/auth/update/email")
    public ResponseResult<Void> updateEmail(@RequestBody @Valid UpdateEmailDTO updateEmailDTO) {
        return userService.updateEmailAndVerify(updateEmailDTO);
    }

    // 第三方登录用户绑定邮箱
    @Operation(summary = "第三方登录用户绑定邮箱")
    @AccessIntercepter(seconds = 60, maxCount = 30)
    @PostMapping("/auth/third/update/email")
    public ResponseResult<Void> thirdUpdateEmail(@RequestBody @Valid UpdateEmailDTO updateEmailDTO) {
        return userService.thirdUpdateEmail(updateEmailDTO);
    }

    @Operation(summary = "用户注册")
    @AccessIntercepter(seconds = 60, maxCount = 30)
    @LogAnnotation(module = "前台注册", operation = LogConst.INSERT)
    @PostMapping("/register")
    public ResponseResult<Void> register(@RequestBody @Valid UserRegisterDTO userRegisterDTO) {
        return userService.userRegister(userRegisterDTO);
    }

    @Operation(summary = "重置密码-确认邮件")
    @AccessIntercepter(seconds = 60, maxCount = 30)
    @LogAnnotation(module = "邮件确认", operation = LogConst.RESET_CONFIRM)
    @PostMapping("/reset-confirm")
    public ResponseResult<Void> resetConfirm(@RequestBody @Valid UserResetConfirmDTO userResetDTO) {
        return userService.userResetConfirm(userResetDTO);
    }

    @Operation(summary = "重置密码")
    @AccessIntercepter(seconds = 60, maxCount = 30)
    @LogAnnotation(module = "重置密码", operation = LogConst.RESET_PASSWORD)
    @PostMapping("/reset-password")
    public ResponseResult<Void> resetPassword(@RequestBody @Valid UserResetPasswordDTO userResetDTO) {
        return userService.userResetPassword(userResetDTO);
    }

}
