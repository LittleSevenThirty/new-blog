package cn.edu.tjufe.zql.mapper;

import cn.edu.tjufe.zql.domain.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author: littleseventhirty
 * @description: 文章映射层
 * @date: 2025/12/12-13:59
 **/
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
    @Select("SELECT * FROM T_ARTICLE WHERE STATUS=#{status} AND IS_DELETED=0 LIMIT #{limit}")
    List<Article> selectRandomArticle(@Param("status") Integer status, @Param("limit") Integer limit);
}
