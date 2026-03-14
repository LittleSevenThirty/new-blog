package cn.edu.tjufe.zql.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/1/27
 * @github https://github.com/little-seven-thirty
 */
@Component("redisCache")
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
     * redis进行自增
     *
     * @return 自增后的值
     */
    public Long increment(String key, Long v) {
        return redisTemplate.opsForValue().increment(key, v);
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

    /**
     * 删除单个对象
     *
     * @param key
     */
    public boolean deleteObject(final String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public <T> T getCacheObject(String key) {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key      缓存的键值
     * @param value    缓存的值
     * @param timeout  时间
     * @param timeUnit 时间颗粒度
     */
    public <T> void setCacheObject(final String key, final T value, final Integer timeout, final TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key   缓存的键值
     * @param value 缓存的值
     */
    public <T> void setCacheObject(final String key, final T value) {
        redisTemplate.opsForValue().set(key, value);
    }
}
