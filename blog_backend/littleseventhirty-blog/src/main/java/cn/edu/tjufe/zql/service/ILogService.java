package cn.edu.tjufe.zql.service;

import cn.edu.tjufe.zql.domain.dto.LogDTO;
import cn.edu.tjufe.zql.domain.dto.LogDeleteDTO;
import cn.edu.tjufe.zql.domain.entity.Log;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
import cn.edu.tjufe.zql.domain.vo.PageVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/3/11
 * @github https://github.com/little-seven-thirty
 */
public interface ILogService extends IService<Log> {
    /**
     * 搜索/显示操作日志
     *
     * @param LogDTO  查询条件
     * @param aLong
     * @param current
     * @return 数据列表
     */
    PageVO searchLog(LogDTO logDTO, Long aLong, Long current);

    /**
     *  删除/清空操作日志
     * @param logDeleteDTO id集合
     * @return  响应结果
     */
    ResponseResult<Void> deleteLog(LogDeleteDTO logDeleteDTO);
}
