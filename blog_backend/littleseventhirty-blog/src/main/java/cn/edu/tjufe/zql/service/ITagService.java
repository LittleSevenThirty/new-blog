package cn.edu.tjufe.zql.service;

import cn.edu.tjufe.zql.domain.dto.TagDTO;
import cn.edu.tjufe.zql.domain.entity.Tag;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
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
     * 添加标签
     * @param tagDTO 标签DTO
     * @return 是否成功
     */
    ResponseResult<Void> addTag(TagDTO tagDTO);

    /**
     * 查询所有标签
     * @return 标签列表
     */
    List<TagVO> listAllTag();
}
