package cn.edu.tjufe.zql.controller;

import cn.edu.tjufe.zql.annotation.AccessIntercepter;
import cn.edu.tjufe.zql.annotation.LogAnnotation;
import cn.edu.tjufe.zql.constants.LogConst;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
import cn.edu.tjufe.zql.domain.vo.*;
import cn.edu.tjufe.zql.service.IArticleService;
import cn.edu.tjufe.zql.utils.ResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@Tag(name = "文章相关接口")
@RequestMapping("/article")
public class ArticleController {

    @Resource(name = "articleService")
    private IArticleService articleService;

    /**
     * 初始化标题搜索文章数据
     *
     * @return
     */
    @Operation(summary = "初始化标题搜索文章数据")
    @AccessIntercepter(second = 60, maxCount = 5)
    @GetMapping("/search/init/title")
    public ResponseResult<List<InitSearchTitleVO>> initSearchByTitle() {
        return ResponseWrapper.handler(() -> articleService.initSearchByTitle());
    }

    /**
     * 根据内容片段搜索文章
     *
     * @param content 搜索文章内容片段
     * @return
     */
    @Operation(summary = "根据内容片段搜索文章")
    @Parameters({
            @Parameter(name = "content", description = "搜索文章内容片段", required = true)
    })
    @AccessIntercepter(second = 60, maxCount = 5)
    @GetMapping("/search/by/content")
    public ResponseResult<List<SearchArticleByContentVO>> searchArticleByContent(
            @NotEmpty
            @Length(min = 1, max = 15, message = "搜索内容长度应不超过15字符")
            @RequestParam("content")
            String content) {
        return ResponseWrapper.handler(() -> articleService.searchArticleByContent(content));
    }

    /**
     * 热门文章推荐接口
     *
     * @return
     */
    @Operation(summary = "热门内容推荐接口")
    @AccessIntercepter(second = 60, maxCount = 60)
    @GetMapping("/hot")
    public ResponseResult<List<HotArticleVO>> hotArticles() {
        return ResponseWrapper.handler(() -> articleService.getHotArticles());
    }

    /**
     * 随机文章推荐接口
     *
     * @return
     */
    @GetMapping("/random")
    @AccessIntercepter(second = 60, maxCount = 5)
    @Operation(description = "刷一刷获取随机文章")
    public ResponseResult<List<RandomArticleVO>> randomArticles() {
        return ResponseWrapper.handler(() -> articleService.getRandomArticles());
    }

    /**
     * 置顶文章接口
     *
     * @return
     */
    @LogAnnotation(module = "文章管理", operation = LogConst.GET)
    @AccessIntercepter(second = 60, maxCount = 5)
    @Operation(summary = "推荐文章接口")
    @GetMapping("/recommend")
    public ResponseResult<List<RecommendArticleVO>> recommend() {
        return ResponseWrapper.handler(() -> articleService.getRecommendArticles());
    }

    /**
     * 所有文章列表接口
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Operation(summary = "获取所有文章列表")
    @AccessIntercepter(second = 60, maxCount = 10)
    @LogAnnotation(module = "文章管理", operation = LogConst.GET)
    @Parameters({
            @Parameter(name = "pageNum", description = "页码", required = true),
            @Parameter(name = "pageSize", description = "每页数量", required = true)
    })
    @GetMapping("/list")
    public ResponseResult<PageVO<List<ArticleVO>>> list(@NotNull Integer pageNum, @NotNull Integer pageSize) {
        return ResponseWrapper.handler(() -> articleService.allArticleList(pageNum, pageSize));
    }

    @Operation(summary = "获取相关文章接口")
    @Parameters({
            @Parameter(name="categoryId",description = "分类id",required = true),
            @Parameter(name="articleId",description = "文章id",required=true)
    })
    @AccessIntercepter(second = 60, maxCount = 60)
    @GetMapping("/related/{categoryId}/{articleId}")
    public ResponseResult<List<RelatedArticleVO>> related(
            @PathVariable @NotNull Integer categoryId,
            @PathVariable @NotNull Integer articleId
    ){
        return ResponseWrapper.handler(()->articleService.getRelatedArticles(categoryId,articleId));
    }

    @Operation(summary="获取分类下的文章")
    @Parameters({
        @Parameter(name="typeId",description = "类型id",required=true),
        @Parameter(name="type",description = "类型",required=true)
    })
    @AccessIntercepter(second = 60, maxCount = 60)
    @GetMapping("/type/list/{typeId}")
    public ResponseResult<List<CategoryArticleVO>> getCategoryArticle(
            @NotNull @RequestParam("type") Integer type,
            @NotNull @PathVariable("typeId") Integer typeId
            ){
        return ResponseWrapper.handler(()->{return articleService.getCategoryArticleList(type,typeId);});
    }

    @Operation(summary="获取时间轴数据")
    @AccessIntercepter(second=60,maxCount = 15)
    @GetMapping("/timeline")
    public ResponseResult<List<TimelineVO>> timeline(){
        return ResponseWrapper.handler(()->articleService.listTimeline());
    }
}
