package cn.edu.tjufe.zql.domain.entity;

import cn.edu.tjufe.zql.utils.RedisCache;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/2/21
 * @github https://github.com/little-seven-thirty
 */
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Data
public class LoginUser implements UserDetails {
    @Resource
    private RedisCache redisCache;

    private User user;

    //存储权限信息
    private List<String> permissions;

    public LoginUser(User user) {
        this.user = user;
    }

    public LoginUser(User user, List<String> permissions) {
        this.user = user;
        this.permissions = permissions;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getIsDelete() == 0;
    }
}
