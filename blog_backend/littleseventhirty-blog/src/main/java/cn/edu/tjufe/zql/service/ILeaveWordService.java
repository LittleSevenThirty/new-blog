package cn.edu.tjufe.zql.service;

import cn.edu.tjufe.zql.domain.entity.LeaveWord;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
import cn.edu.tjufe.zql.domain.vo.LeaveWordVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 * @authro 钟奇林
 * @description 表服务接口
 * @date 2026/2/24
 * @github https://github.com/little-seven-thirty
 */
public interface ILeaveWordService extends IService<LeaveWord> {

    /**
     * 查询留言板
     * @return 留言列表
     */
    List<LeaveWordVO> getAllLeaveWordList(String id);

    /**
     * 添加新用户留言
     * @param content
     * @return
     */
    ResponseResult<Void> addUserLeaveWord(String content);
}
