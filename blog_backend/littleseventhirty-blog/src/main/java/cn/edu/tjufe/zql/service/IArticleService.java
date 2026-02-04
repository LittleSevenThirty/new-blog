package cn.edu.tjufe.zql.service;

import cn.edu.tjufe.zql.domain.entity.Article;
import cn.edu.tjufe.zql.domain.vo.*;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * @author: littleseventhirty
 * @description:
 * @date: 2025/12/11-17:22
 **/
public interface IArticleService extends IService<Article> {

    /**
     * 初始化标题搜索接口
     *
     * @return
     */
    List<InitSearchTitleVO> initSearchByTitle();

    /**
     * 根据搜索内容返回对应结果
     *
     * @param content 被搜索目标
     * @return
     */
    List<SearchArticleByContentVO> searchArticleByContent(String content);

    /**
     * 获得前5热门文章
     *
     * @return 前五热门文章
     */
    List<HotArticleVO> getHotArticles();

    /**
     * 刷一刷，获得随机文章
     *
     * @return
     */
    List<RandomArticleVO> getRandomArticles();

    /**
     * 获取推荐文章
     *
     * @return
     */
    List<RecommendArticleVO> getRecommendArticles();

    /**
     * 获取所有文章
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageVO<List<ArticleVO>> allArticleList(Integer pageNum, Integer pageSize);

    /**
     * 获取相关文章服务接口
     * @param categoryId
     * @param articleId
     * @return
     */
    List<RelatedArticleVO> getRelatedArticles(@NotNull Integer categoryId, @NotNull Integer articleId);
}
