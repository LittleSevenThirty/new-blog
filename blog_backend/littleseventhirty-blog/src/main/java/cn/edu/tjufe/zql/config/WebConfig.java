package cn.edu.tjufe.zql.config;


import cn.edu.tjufe.zql.intercepter.AccessLimitIntercepter;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @authro 钟奇林
 * @description 请求拦截器配置
 * @date 2026/3/16
 * @github https://github.com/little-seven-thirty
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Resource
    private AccessLimitIntercepter accessLimitIntercepter;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(accessLimitIntercepter);
    }
}
