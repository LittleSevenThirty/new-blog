package cn.edu.tjufe.zql.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

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
     *
     * @param key
     * @return
     */
    public boolean isHasKey(final String key) {
        Boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey != null && hasKey) return true;
        else return false;
    }

    /**
     * 获取对应键的值（一个使用map存放的对象）
     *
     * @param key
     * @param <T>
     * @return
     */
    public <T> Map<String, T> getCacheMap(final String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 缓存Map
     *
     * @param key
     * @param
     */
    public <T> void setCacheMap(final String key, final Map<String, T> map) {
        if (null != map) {
            redisTemplate.opsForHash().putAll(key, map);
        }
    }

    public void incrementCacheMapValue(String key,String hkey,int v){
        redisTemplate.opsForHash().increment(key,hkey,v);
    }
}
