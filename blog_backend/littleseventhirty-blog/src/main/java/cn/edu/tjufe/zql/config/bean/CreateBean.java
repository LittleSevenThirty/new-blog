package cn.edu.tjufe.zql.config.bean;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/3/5
 * @github https://github.com/little-seven-thirty
 */
@Configuration
public class CreateBean {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
