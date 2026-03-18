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

    /**
     * 异步刷新ip详情获取--注册
     *
     * @param uid 用户id
     */
    void refreshIpDetailAsyncByUidAndRegister(Long uid);

    /**
     * 异步刷新登录日志ip详情获取
     *
     * @param loginLogId 登录日志id
     */
    void refreshIpDetailAsyncByLogIdAndLogin(Long loginLogId);

    /**
     * 异步刷新操作日志ip详情获取
     *
     * @param logId 操作日志id
     */
    void refreshIpDetailAsyncByLogId(Long logId);
}
