package cn.edu.tjufe.zql.service;

import cn.edu.tjufe.zql.domain.entity.Article;
import cn.edu.tjufe.zql.domain.vo.*;
import com.baomidou.mybatisplus.extension.service.IService;

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
     * 所有文章列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageVO<List<ArticleVO>> allArticleList(Integer pageNum, Integer pageSize);
}
