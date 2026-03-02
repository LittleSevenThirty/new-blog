package cn.edu.tjufe.zql.service;


/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/3/2
 * @github https://github.com/little-seven-thirty
 */
public interface IIpService {
    /**
     * 异步刷新ip详情获取--登录
     *
     * @param uid 用户id
     */
    void refreshIpDetailAsyncByUidAndLogin(Long uid);
}
