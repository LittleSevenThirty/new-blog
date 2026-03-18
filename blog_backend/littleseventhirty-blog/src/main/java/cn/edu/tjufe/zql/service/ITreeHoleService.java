package cn.edu.tjufe.zql.service;


import cn.edu.tjufe.zql.domain.dto.SearchTreeHoleDTO;
import cn.edu.tjufe.zql.domain.dto.TreeHoleIsCheckDTO;
import cn.edu.tjufe.zql.domain.entity.TreeHole;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
import cn.edu.tjufe.zql.domain.vo.TreeHoleListVO;
import cn.edu.tjufe.zql.domain.vo.TreeHoleVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/2/19
 * @github https://github.com/little-seven-thirty
 */

public interface ITreeHoleService extends IService<TreeHole> {
    /**
     * 新增树洞
     * @param content 树洞内容
     * @return
     */
    ResponseResult<Void> addTreeHole(String content);

    /**
     * 获取所有树洞留言内容
     * @return
     */
    List<TreeHoleVO> getTreeHole();

    /**
     * 后台树洞列表
     * @return 结果
     */
    List<TreeHoleListVO> getBackTreeHoleList(SearchTreeHoleDTO searchDTO);

    /**
     * 是否通过树洞
     * @param isCheckDTO 是否通过
     * @return 是否成功
     */
    ResponseResult<Void> isCheckTreeHole(TreeHoleIsCheckDTO isCheckDTO);

    /**
     * 删除树洞
     * @param ids id 列表
     * @return 是否成功
     */
    ResponseResult<Void> deleteTreeHole(List<Long> ids);
}
