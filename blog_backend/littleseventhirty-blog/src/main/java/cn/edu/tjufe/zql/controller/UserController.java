package cn.edu.tjufe.zql.controller;

import cn.edu.tjufe.zql.annotation.AccessIntercepter;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
import cn.edu.tjufe.zql.domain.vo.UserAccountVO;
import cn.edu.tjufe.zql.service.IUserService;
import cn.edu.tjufe.zql.utils.ResponseWrapper;
import cn.edu.tjufe.zql.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

}
