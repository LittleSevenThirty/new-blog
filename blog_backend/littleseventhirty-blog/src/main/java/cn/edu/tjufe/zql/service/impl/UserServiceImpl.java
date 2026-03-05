package cn.edu.tjufe.zql.service.impl;

import cn.edu.tjufe.zql.constants.*;
import cn.edu.tjufe.zql.domain.dto.UpdateEmailDTO;
import cn.edu.tjufe.zql.domain.dto.UserUpdateDTO;
import cn.edu.tjufe.zql.domain.entity.*;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
import cn.edu.tjufe.zql.domain.vo.UserAccountVO;
import cn.edu.tjufe.zql.enums.*;
import cn.edu.tjufe.zql.mapper.*;
import cn.edu.tjufe.zql.service.IIpService;
import cn.edu.tjufe.zql.service.IUserService;
import cn.edu.tjufe.zql.utils.HttpUtils;
import cn.edu.tjufe.zql.utils.IpUtils;
import cn.edu.tjufe.zql.utils.RedisCache;
import cn.edu.tjufe.zql.utils.SecurityUtils;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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
    private RedisTemplate redisTemplate;

    @Resource
    private IIpService ipService;

    @Resource
    private PasswordEncoder bCryptPasswordEncoder;    // 编码

    @Resource
    private RedisCache redisCache;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RolePermissionMapper rolePermissionMapper;

    @Resource
    private PermissionMapper permissionMapper;

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
    public void logout() {
        // 获取当前用户的认证信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof LoginUser loginUser) {
            // 从Redis中删除用户信息
            String token = SecurityContextHolder.getContext().getAuthentication().getName();
            redisTemplate.delete("login:" + token);
        }
    }

    @Override
    public void userLoginStatus(Long id, Integer type) {
        // ip地址
        String ipAddr = IpUtils.getIpAddr(SecurityUtils.getCurrentHttpRequest());
        if (IpUtils.isUnknown(ipAddr)) {
            ipAddr = IpUtils.getHostIp();
        }
        User user = User.builder()
                .userId(id)
                .loginTime(new Date())
                .loginType(type)
                .loginIp(ipAddr)
                .build();
        if (updateById(user)) {
            ipService.refreshIpDetailAsyncByUidAndLogin(user.getUserId());
        }
    }

    @Override
    public ResponseResult<Void> updateUser(UserUpdateDTO userUpdateDTO) {
        Long userId = SecurityUtils.getUserId();
        User user = userUpdateDTO.asViewObject(User.class, v -> v.setUserId(userId));
        if (this.updateById(user)) {
            return ResponseResult.success();
        }
        return ResponseResult.failure();
    }

    @Override
    public ResponseResult<Void> updateEmailAndVerify(UpdateEmailDTO updateEmailDTO) {
        // 1.验证密码是否正确
        User user = userMapper.selectById(SecurityUtils.getUserId());
        // 邮箱是否改变
        if (user.getEmail().equals(updateEmailDTO.getEmail())) return ResponseResult.failure("邮箱未更改");
        // 是否已经存在该邮箱
        if (userIsExist(null, updateEmailDTO.getEmail())) return ResponseResult.failure("该邮箱已被注册");
        if (bCryptPasswordEncoder.matches(updateEmailDTO.getPassword(), user.getPassword())) {
            // 2.验证码是否正确
            ResponseResult<Void> verifyCode = verifyCode(updateEmailDTO.getEmail(), updateEmailDTO.getCode(), RedisConst.RESET_EMAIL);
            if (verifyCode == null){
                // 3.修改
                user.setEmail(updateEmailDTO.getEmail());
                userMapper.updateById(user);
                return ResponseResult.success();
            }
        }
        return ResponseResult.failure("密码或验证码错误");
    }

    @Override
    public ResponseResult<Void> thirdUpdateEmail(UpdateEmailDTO updateEmailDTO) {
        // 1.验证密码是否正确
        User user = userMapper.selectById(SecurityUtils.getUserId());
        // 邮箱是否改变
        if (user.getEmail() != null && user.getEmail().equals(updateEmailDTO.getEmail())) return ResponseResult.failure("邮箱未更改");
        // 2.验证码是否正确
        ResponseResult<Void> verifyCode = verifyCode(updateEmailDTO.getEmail(), updateEmailDTO.getCode(), RedisConst.RESET_EMAIL);
        // 是否已经存在该邮箱
        if (userIsExist(null, updateEmailDTO.getEmail())) return ResponseResult.failure("该邮箱已被注册");
        if (verifyCode == null){
            // 3.修改
            user.setEmail(updateEmailDTO.getEmail());
            userMapper.updateById(user);
            return ResponseResult.success();
        }
        return ResponseResult.failure("密码或验证码错误");
    }

    /**
     * 判断用户名或邮箱是否已存在
     *
     * @param username 用户名
     * @param email    邮箱
     * @return boolean
     */
    private boolean userIsExist(String username, String email) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserName, username).or().eq(User::getEmail, email);
        return this.userMapper.selectOne(wrapper) != null;
    }

    /**
     * 判断验证码是否正确
     */
    private ResponseResult<Void> verifyCode(String email, String code, String type) {
        String redisCode = redisCache.getCacheObject(RedisConst.VERIFY_CODE + type + RedisConst.SEPARATOR + email);
        if (redisCode == null)
            return ResponseResult.failure(ResponseEnum.VERIFY_CODE_ERROR.getCode(), RespConst.VERIFY_CODE_NULL_MSG);
        if (!redisCode.equals(code))
            return ResponseResult.failure(ResponseEnum.VERIFY_CODE_ERROR.getCode(), ResponseEnum.VERIFY_CODE_ERROR.getMessage());
        return null;
    }

// - 用户提交登录表单 （用户名和密码）
// - Spring Security 拦截请求 ，调用 Filter
//- Filter 创建 Authentication 对象 ，包含用户名和密码
//- AuthenticationManager 验证 ，调用 UserDetailsService.loadUserByUsername()
//- 验证密码 ，Spring Security 会比较用户输入的密码和 UserDetails 返回的密码
//- 认证成功 ，生成 Authentication 对象并存入 SecurityContext
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        HttpServletRequest request=SecurityUtils.getCurrentHttpRequest();
        String equipmentHeader=null;
        String typeHeader=null;
        String accessToken=null;

        if(request!=null){
            equipmentHeader=request.getHeader(Const.TYPE_HEADER);
            typeHeader=request.getHeader(Const.FRONTEND_LOGIN_TYPE);
            accessToken=request.getHeader(Const.FRONTEND_THIRD_LOGIN_TOKEN);
        }
        User user = null;
        // 判断是否第三方登录
        if (typeHeader != null) {
            // getee
            if (typeHeader.equals(RegisterOrLoginTypeEnum.GITEE.getStrategy())) {
                String result = HttpUtils.sendGet(UrlEnum.GITEE_USER_INFO.getUrl(), "access_token=" + accessToken);
                JSONObject jsonObject = JSON.parseObject(result);
                Integer uuid = (Integer) jsonObject.get(SQLConst.ID);
                user = userMapper.selectById(uuid);
            }
            // github
            if (typeHeader.equals(RegisterOrLoginTypeEnum.GITHUB.getStrategy())) {
                OkHttpClient client = new OkHttpClient();
                Headers headers = new Headers.Builder()
                        .add(RequestHeaderEnum.GITHUB_USER_INFO.getHeader(), RequestHeaderEnum.GITHUB_USER_INFO.getContent())
                        .add(RespConst.TOKEN_HEADER, RespConst.TOKEN_PREFIX + accessToken)
                        .build();
                Request getRequest = new Request.Builder()
                        .url(UrlEnum.GITHUB_USER_INFO.getUrl())
                        .method(UrlEnum.GITHUB_USER_INFO.getMethod(), null)
                        .headers(headers)
                        .build();
                try (Response response = client.newCall(getRequest).execute()) {
                    JSONObject jsonObject;
                    if (response.body() != null) {
                        jsonObject = JSON.parseObject(response.body().string());
                        Integer uuid = (Integer) jsonObject.get(SQLConst.ID);
                        user = userMapper.selectById(uuid);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        } else {
            user = findAccountByNameOrEmail(username);
        }
        // 2. 判断用户是否存在
        if (ObjectUtils.isEmpty(user)) {
            // 不存在，抛出异常
            throw new UsernameNotFoundException(RespConst.USERNAME_OR_PASSWORD_ERROR_MSG);
        }
        return handlerLogin(user, equipmentHeader);
    }

    @Override
    public User findAccountByNameOrEmail(String text) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserName, text).or().eq(User::getEmail, text).eq(User::getRegisterType, RegisterOrLoginTypeEnum.EMAIL.getRegisterType());
        return userMapper.selectOne(wrapper);
    }

    private LoginUser handlerLogin(User user, String equipmentHeader) {
        HttpServletRequest request = SecurityUtils.getCurrentHttpRequest();
        String header = null;
        if (request != null) {
            header = request.getHeader(Const.TYPE_HEADER);
        }
        // 查询用户角色
        List<UserRole> userRoles = userRoleMapper.selectList(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, user.getUserId()));
        List<Role> roles = userRoles.stream().map(role -> roleMapper.selectById(role.getRoleId())).filter(role -> Objects.equals(role.getStatus(), RoleEnum.Role_STATUS_ARTICLE.getStatus())).toList();
        // 用户是否被禁用
        if (user.getIsDisable() == 1) {
            throw new BadCredentialsException(RespConst.ACCOUNT_DISABLED_MSG);
        }
        // 是否测试账号前台
        if (header == null || (roles.stream().anyMatch(role -> role.getRoleKey().equals(SecurityConst.ROLE_TESTER)) && !header.equals(Const.BACKEND_REQUEST))) {
            throw new BadCredentialsException(RespConst.TEST_ACCOUNT_MSG);
        }

        // 判断用户是否具备任何权限,
        if ((equipmentHeader != null && equipmentHeader.equals(Const.BACKEND_REQUEST) && ObjectUtils.isEmpty(roles))) {
            throw new BadCredentialsException(RespConst.NO_PERMISSION_MSG);
        }
        if (!roles.isEmpty()) {
            // 查询权限关系表
            List<RolePermission> rolePermissions = rolePermissionMapper.selectBatchIds(roles.stream().map(Role::getRoleId).toList());
            // 查询角色权限
            List<Long> pIds = rolePermissions.stream().map(RolePermission::getPermissionId).toList();
            List<Permission> permissions = permissionMapper.selectBatchIds(pIds);
            // 组合角色，权限
            List<String> list = permissions.stream().map(Permission::getPermissionKey).collect(Collectors.toList());
            roles.forEach(role -> list.add(SecurityConst.ROLE_PREFIX + role.getRoleKey()));
            return new LoginUser(user, list);
        }
        return new LoginUser(user, List.of());
    }
}
