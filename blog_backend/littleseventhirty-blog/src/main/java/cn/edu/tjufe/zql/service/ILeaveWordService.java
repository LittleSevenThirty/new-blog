package cn.edu.tjufe.zql.service;

import cn.edu.tjufe.zql.domain.dto.LeaveWordIsCheckDTO;
import cn.edu.tjufe.zql.domain.dto.SearchLeaveWordDTO;
import cn.edu.tjufe.zql.domain.entity.LeaveWord;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
import cn.edu.tjufe.zql.domain.vo.LeaveWordListVO;
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

    /**
     * 后台留言列表
     * @return 结果
     */
    List<LeaveWordListVO> getBackLeaveWordList(SearchLeaveWordDTO searchDTO);

    /**
     * 是否通过留言
     * @param isCheckDTO 是否通过
     * @return 是否成功
     */
    ResponseResult<Void> isCheckLeaveWord(LeaveWordIsCheckDTO isCheckDTO);

    /**
     * 删除留言
     * @param ids id 列表
     * @return 是否成功
     */
    ResponseResult<Void> deleteLeaveWord(List<Long> ids);
}
