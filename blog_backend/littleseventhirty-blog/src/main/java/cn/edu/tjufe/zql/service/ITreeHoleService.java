package cn.edu.tjufe.zql.service;


import cn.edu.tjufe.zql.domain.entity.TreeHole;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
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
}
