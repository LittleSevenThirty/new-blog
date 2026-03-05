package cn.edu.tjufe.zql.service;

import cn.edu.tjufe.zql.domain.entity.LoginLog;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/3/3
 * @github https://github.com/little-seven-thirty
 */
public interface ILoginLogService extends IService<LoginLog> {
    /**
     * 登录日志记录
     * @param request 请求
     * @param userName 用户名称
     * @param state 状态（0成功 1失败）
     * @param message 信息
     */
    void loginLog(HttpServletRequest request, String userName, Integer state, String message);
}
