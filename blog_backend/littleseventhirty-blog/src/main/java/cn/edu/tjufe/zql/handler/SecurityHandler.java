package cn.edu.tjufe.zql.handler;


import cn.edu.tjufe.zql.constants.Const;
import cn.edu.tjufe.zql.constants.RespConst;
import cn.edu.tjufe.zql.domain.entity.LoginUser;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
import cn.edu.tjufe.zql.domain.vo.AuthorizeVO;
import cn.edu.tjufe.zql.enums.ResponseEnum;
import cn.edu.tjufe.zql.service.ILoginLogService;
import cn.edu.tjufe.zql.service.IUserService;
import cn.edu.tjufe.zql.utils.JwtUtils;
import cn.edu.tjufe.zql.utils.StringUtils;
import cn.edu.tjufe.zql.utils.WebUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/3/2
 * @github https://github.com/little-seven-thirty
 */
@Component
public class SecurityHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler {
    @Resource
    private JwtUtils jwtUtils;

    @Resource
    private IUserService userService;

    @Resource
    private ILoginLogService loginLogService;

    private static final String USER_NAME="username";

    /**
     * 登录成功处理
     *
     * @param request        请求
     * @param response       响应
     * @param authentication 认证信息
     */
    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        handlerOnAuthenticationSuccess(request,response,(LoginUser)authentication.getPrincipal());
    }

    public void handlerOnAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,LoginUser user) {
        String typeHeader = request.getHeader(Const.TYPE_HEADER);
        if ((!StringUtils.matches(typeHeader, List.of(Const.BACKEND_REQUEST, Const.FRONTEND_REQUEST)) && user.getUser().getRegisterType() == 1)) {
            throw new BadCredentialsException("非法请求");
        }
        Long id = user.getUser().getUserId();
        String name = user.getUser().getUsername();
        // UUID做jwt的id
        String uuid = UUID.randomUUID().toString();
        // 生成jwt
        String token = jwtUtils.createJwt(uuid, user, id, name);

        // 转换VO
        AuthorizeVO authorizeVO = user.getUser().asViewObject(AuthorizeVO.class, v -> {
            v.setToken(token);
            v.setExpire(jwtUtils.expireTime());
        });
        userService.userLoginStatus(user.getUser().getUserId(), user.getUser().getRegisterType());
        loginLogService.loginLog(request, request.getParameter(USER_NAME), 0, RespConst.SUCCESS_LOGIN_MSG);
        WebUtil.renderString(response, ResponseResult.success(authorizeVO, RespConst.SUCCESS_LOGIN_MSG).asJsonString());
    }

    /**
     * 登录失败处理
     */
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception
    ){
        loginLogService.loginLog(request, request.getParameter(USER_NAME), 1, exception.getMessage());
        WebUtil.renderString(response, ResponseResult.failure(ResponseEnum.USERNAME_OR_PASSWORD_ERROR.getCode(), exception.getMessage()).asJsonString());
    }

    /**
     * 退出登录处理
     */
    public void onLogoutSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        boolean invalidateJwt = jwtUtils.invalidateJwt(request.getHeader("Authorization"));
        if (invalidateJwt) {
            WebUtil.renderString(response, ResponseResult.success().asJsonString());
            return;
        }
        WebUtil.renderString(response, ResponseResult.failure(ResponseEnum.NOT_LOGIN.getCode(), ResponseEnum.NOT_LOGIN.getMessage()).asJsonString());
    }

    /**
     * 没有登录处理
     */
    public void onUnAuthenticated(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception
    ) throws IOException {
        WebUtil.renderString(response, ResponseResult.failure(ResponseEnum.NOT_LOGIN.getCode(), ResponseEnum.NOT_LOGIN.getMessage()).asJsonString());
    }

    /**
     * 没有权限处理
     */
    public void onAccessDeny(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException exception
    ) {
        WebUtil.renderString(response, ResponseResult.failure(ResponseEnum.NO_PERMISSION.getCode(), ResponseEnum.NO_PERMISSION.getMessage()).asJsonString());
    }
}
