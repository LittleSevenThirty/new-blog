package cn.edu.tjufe.zql.mapper;

import cn.edu.tjufe.zql.domain.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: littleseventhirty
 * @description: 实体映射接口
 * @date: 2025/12/15-16:31
 **/
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
