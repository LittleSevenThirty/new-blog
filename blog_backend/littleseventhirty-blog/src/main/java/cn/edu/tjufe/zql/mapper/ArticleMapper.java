package cn.edu.tjufe.zql.mapper;

import cn.edu.tjufe.zql.domain.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: littleseventhirty
 * @description: 文章映射层
 * @date: 2025/12/12-13:59
 **/
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
}
