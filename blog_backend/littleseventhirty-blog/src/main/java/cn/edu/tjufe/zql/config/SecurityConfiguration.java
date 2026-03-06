package cn.edu.tjufe.zql.config;

import cn.edu.tjufe.zql.constants.SecurityConst;
import cn.edu.tjufe.zql.filter.JwtAuthorizeFilter;
import cn.edu.tjufe.zql.handler.SecurityHandler;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author: littleseventhirty
 * @description: Spring Security配置类
 * @date: 2026/2/26
 **/
@Configuration
public class SecurityConfiguration  {
    @Resource
    private SecurityHandler securityHandler;

    @Resource
    private JwtAuthorizeFilter jwtAuthorizeFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                // 配置CORS
                .authorizeHttpRequests(config->
                    config.requestMatchers(SecurityConst.AUTH_CHECK_ARRAY).authenticated()  // SecurityConst.AUTH_CHECK_ARRAY里的请求都需要经过认证
                            .anyRequest().permitAll()   // 非SecurityConst.AUTH_CHECK_ARRAY里的请求全部通过
                )// 路由控制权限，控制路由访问相关
                .formLogin(config->
                    config.loginProcessingUrl(SecurityConst.LOGIN_PAGE)
                            .successHandler(securityHandler::onAuthenticationSuccess)
                            .failureHandler(securityHandler::onAuthenticationFailure)
                )// 登录认证流程
                .logout(config->
                    config.logoutUrl(SecurityConst.LOGOUT_PAGE)
                            // 退出登录处理
                            .logoutSuccessHandler(securityHandler::onLogoutSuccess)
                )// 登出认证流程
                .exceptionHandling(config->                        // 没有登录处理
                        config.authenticationEntryPoint(securityHandler::onUnAuthenticated)
                        // 没有权限处理
                        .accessDeniedHandler(securityHandler::onAccessDeny))
                // 禁用 csrf
                .csrf(AbstractHttpConfigurer::disable)
                // 不处理 session ，使用token
                .sessionManagement(conf -> conf.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // token 校验添加过滤器
                .addFilterBefore(jwtAuthorizeFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}

//                .cors(cors -> cors
//        .configurationSource(request -> {
//var config = new org.springframework.web.cors.CorsConfiguration();
//                            config.setAllowedOrigins(List.of("*"));
//        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//        config.setAllowedHeaders(List.of("*"));
//        config.setExposedHeaders(List.of("*"));
//        config.setAllowCredentials(true);
//                            return config;
//                        })
//                                )
