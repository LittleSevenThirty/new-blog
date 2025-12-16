package cn.edu.tjufe.zql.service.impl;

import cn.edu.tjufe.zql.constants.SQLConst;
import cn.edu.tjufe.zql.domain.entity.Article;
import cn.edu.tjufe.zql.domain.entity.Category;
import cn.edu.tjufe.zql.domain.vo.SearchArticleByContentVO;
import cn.edu.tjufe.zql.mapper.ArticleMapper;
import cn.edu.tjufe.zql.mapper.CategoryMapper;
import cn.edu.tjufe.zql.service.IArticleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: littleseventhirty
 * @description:
 * @date: 2025/12/12-11:44
 **/
@Slf4j
@Service(value="articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {
    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public List<SearchArticleByContentVO> searchArticleByContent(String content) {
        // 从数据库中查询相关对应文章
        List<Article> articles=articleMapper.selectList(
                new LambdaQueryWrapper<Article>()
                        .like(Article::getArticleContent,content)
                        .eq(Article::getStatus, SQLConst.PUBLIC_APTICLE));
        // 再从数据库中查询相关分类
        Map<String,String> categoryMap=categoryMapper.selectList(null).stream().collect(Collectors.toMap(Category::getCategoryId,Category::getCategoryName));
        // 将对应数据存放到对应实体中
        if(!articles.isEmpty()){
            List<SearchArticleByContentVO> searchArticleByContentVOList=articles.stream().map(article->{
                return article.asViewObject(SearchArticleByContentVO.class,vo->{
                    vo.setCategoryName(categoryMap.get(article.getCategoryId()));
                });
            }).toList();
            int index=-1;
            for(SearchArticleByContentVO vo:searchArticleByContentVOList){

            }
        }
        return List.of();
    }
}
