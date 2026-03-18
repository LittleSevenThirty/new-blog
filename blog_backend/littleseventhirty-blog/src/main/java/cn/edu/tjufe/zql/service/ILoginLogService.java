package cn.edu.tjufe.zql.service;

import cn.edu.tjufe.zql.domain.dto.LoginLogDTO;
import cn.edu.tjufe.zql.domain.dto.LoginLogDeleteDTO;
import cn.edu.tjufe.zql.domain.entity.LoginLog;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
import cn.edu.tjufe.zql.domain.vo.LoginLogVO;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

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

    /**
     * 搜索/显示登录日志
     * @param loginLogDTO 查询条件
     * @return  数据列表
     */
    List<LoginLogVO> searchLoginLog(LoginLogDTO loginLogDTO);

    /**
     * 删除登录日志
     * @param loginLogDeleteDTO 删除条件
     * @return 是否成功
     */
    ResponseResult<Void> deleteLoginLog(LoginLogDeleteDTO loginLogDeleteDTO);
}
