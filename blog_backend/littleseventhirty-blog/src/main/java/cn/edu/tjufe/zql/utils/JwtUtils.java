package cn.edu.tjufe.zql.utils;


import cn.edu.tjufe.zql.constants.RedisConst;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import cn.edu.tjufe.zql.domain.entity.LoginUser;
import cn.edu.tjufe.zql.domain.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/3/2
 * @github https://github.com/little-seven-thirty
 */
@Component
public class JwtUtils {

    @Value("${spring.security.jwt.key}")
    private String key;

    @Value("${spring.security.jwt.expire}")
    private int expire;

    @Resource
    private RedisCache redisCache;

    /**
     * 是否成功让令牌失效
     *
     * @param headerToken 请求头中的token
     * @return boolean
     */
    public boolean invalidateJwt(String headerToken) {
        String token = this.convertToken(headerToken);
        if (token == null) return false;
        // 验证令牌
        Algorithm algorithm = Algorithm.HMAC256(key);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        try {
            DecodedJWT jwt = jwtVerifier.verify(token);
            String id = jwt.getId();
            return deleteToken(id);
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    /**
     * 删除 token
     *
     * @param uuid 令牌的id
     * @return boolean
     */
    private boolean deleteToken(String uuid) {
        // token 是否失效
        if (this.isInvalidToken(uuid))
            return false;
        // 删除
        redisCache.deleteObject(RedisConst.JWT_WHITE_LIST + uuid);
        return true;
    }

    /**
     * 处理Token
     *
     * @param headerToken 请求头中的token
     * @return String
     */
    private String convertToken(String headerToken) {
        if (headerToken == null || !headerToken.startsWith("Bearer "))
            return null;
        return headerToken.substring(7);
    }

    /**
     * 是否是一个过期的令牌
     *
     * @param uuid 令牌的id
     * @return boolean
     */
    private boolean isInvalidToken(String uuid) {
        // 判断是否在redis中(白名单)
        return !Boolean.TRUE.equals(redisCache.isHasKey(RedisConst.JWT_WHITE_LIST + uuid));
    }

    /**
     * 创建 jwt
     *
     * @param details  用户信息
     * @param id       用户id
     * @param username 用户名
     * @return String jwt
     */
    public String createJwt(String uuid, UserDetails details, Long id, String username) {
        Algorithm algorithm = Algorithm.HMAC256(key);
        Date expire = expireTime();
        // 当前时间
        Date now = new Date();
        String jwt = JWT.create()
                .withJWTId(uuid)
                .withClaim("id", id)
                .withClaim("name", username)
//                .withClaim("authorities", details.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .withExpiresAt(expire)
                .withIssuedAt(now)
                .sign(algorithm);
        // 存入redis
        redisCache.setCacheObject(RedisConst.JWT_WHITE_LIST + uuid, jwt, (int) (expire.getTime() - now.getTime()), TimeUnit.MILLISECONDS);
        return jwt;
    }

    /**
     * 到期时间
     *
     * @return Date
     */
    public Date expireTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, expire * 24);
        return calendar.getTime();
    }

    /**
     * 解析jwt
     *
     * @param headerToken 请求头中的token
     * @return {@link DecodedJWT} 解析后的jwt
     */
    public DecodedJWT resolveJwt(String headerToken) {
        String token = this.convertToken(headerToken);
        if (token == null) return null;
        Algorithm algorithm = Algorithm.HMAC256(key);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        try {
            // 是否合法，不合法会抛出一个运行时异常（需要自己捕获）
            DecodedJWT verify = jwtVerifier.verify(token);
            // 如果不在白名单中
            if (this.isInvalidToken(verify.getId()))
                return null;
            Date expiresAt = verify.getExpiresAt();
            // 判断是否过期
            return new Date().after(expiresAt) ? null : verify;
        } catch (JWTVerificationException e) {
            return null;
        }
    }

    /**
     * 从JWT中提取用户信息
     *
     * @param jwt 解析后的JWT
     * @return LoginUser
     */
    public UserDetails toUser(DecodedJWT jwt) {
        // 从JWT中提取用户ID和用户名
        Long userId = jwt.getClaim("id").asLong();
        String username = jwt.getClaim("name").asString();
        
        // 这里应该从数据库或Redis中获取完整的用户信息
        // 为了简化，这里创建一个基本的LoginUser对象
        User user = new User();
        user.setUserId(userId);
        user.setUsername(username);
        user.setIsDeleted(0); // 假设用户未删除
        
        return new LoginUser(user);
    }


}
