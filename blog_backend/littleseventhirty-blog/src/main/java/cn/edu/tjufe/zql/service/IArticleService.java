package cn.edu.tjufe.zql.service;

import cn.edu.tjufe.zql.domain.dto.ArticleDTO;
import cn.edu.tjufe.zql.domain.dto.SearchArticleDTO;
import cn.edu.tjufe.zql.domain.entity.Article;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
import cn.edu.tjufe.zql.domain.vo.ArticleDetailVO;
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
     * 增加文章访问量
     * @param id 文章id
     */
    void addVisitCount(Long id);

    /**
     * 根据搜索内容返回对应结果
     *
     * @param content 被搜索目标
     * @return
     */
    List<SearchArticleByContentVO> searchArticleByContent(String content);

    /**
     * 搜索文章列表
     * @param searchArticleDTO 搜索条件
     * @return 结果
     */
    List<ArticleListVO> searchArticle(SearchArticleDTO searchArticleDTO);

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

    /**
     * 获取时间轴数据
     * @return
     */
    List<TimelineVO> listTimeline();

    /**
     * 获取分类文章列表
     * @param typeId
     * @param type type==1从分类中找，2从tag标签中找
     * @return
     */
    List<CategoryArticleVO> getCategoryArticleList(@NotNull Integer type, @NotNull Integer typeId);

    /**
     * 获取文章详情
     * @param id 文章id
     * @return 文章相关数据
     */
    ArticleDetailVO getArticleDetail(Integer id);

    /**
     * 后台文章列表
     * @return 文章列表
     */
    List<ArticleListVO> listArticle();

    /**
     * 修改文章状态
     * @param id 文章id
     * @param status 状态
     * @return 是否成功
     */
    ResponseResult<Void> updateStatus(Long id, Integer status);

    /**
     * 修改文章是否顶置
     * @param id 文章id
     * @param isTop 是否顶置
     * @return 是否成功
     */
    ResponseResult<Void> updateIsTop(Long id, Integer isTop);

    /**
     * 回显文章数据
     * @param id 文章id
     * @return 数据
     */
    ArticleDTO getArticleDTO(Long id);

    /**
     * 删除文章
     * @param id 文章id
     * @return 是否成功
     */
    ResponseResult<Void> deleteArticle(List<Long> id);
}
