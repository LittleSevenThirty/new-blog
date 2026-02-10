package cn.edu.tjufe.zql.service;

import cn.edu.tjufe.zql.domain.entity.Tag;
import cn.edu.tjufe.zql.domain.vo.TagVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author: littleseventhirty
 * @description:
 * @date: 2026/1/28-16:52
 **/
public interface ITagService extends IService<Tag> {
    /**
     * 查询所有标签
     * @return 标签列表
     */
    List<TagVO> listAllTag();
}
