package cn.edu.tjufe.zql.config;

import cn.edu.tjufe.zql.domain.entity.LoginUser;
import cn.edu.tjufe.zql.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author: littleseventhirty
 * @description: Spring Security配置类
 * @date: 2026/2/26
 **/
@Configuration
public class SecurityConfig {

    @Resource
    private UserMapper userMapper;

    /**
     * 配置认证管理器
     *
     * @param config 认证配置
     * @return 认证管理器
     * @throws Exception 异常
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * 配置认证提供者
     *
     * @return 认证提供者
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /**
     * 配置用户详情服务
     *
     * @return 用户详情服务
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            // 根据用户名查询用户
            cn.edu.tjufe.zql.domain.entity.User user = userMapper.selectOne(
                    new LambdaQueryWrapper<cn.edu.tjufe.zql.domain.entity.User>()
                            .eq(cn.edu.tjufe.zql.domain.entity.User::getUserName, username)
            );
            if (user == null) {
                throw new UsernameNotFoundException("用户不存在");
            }
            // 返回用户详情
            return new LoginUser(user);
        };
    }

    /**
     * 配置密码编码器
     *
     * @return 密码编码器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // 暂时使用NoOpPasswordEncoder，实际项目中应该使用BCryptPasswordEncoder
        return NoOpPasswordEncoder.getInstance();
    }
}
