package cn.edu.tjufe.zql.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/1/27
 * @github https://github.com/little-seven-thirty
 */
@Component
public class RedisCache {
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * Redis是否存在对应key
     * @param key
     * @return
     */
    public boolean isHasKey(final String key){
        Boolean hasKey=redisTemplate.hasKey(key);
        if(hasKey!=null&&hasKey)return true;
        else return false;
    }
}
