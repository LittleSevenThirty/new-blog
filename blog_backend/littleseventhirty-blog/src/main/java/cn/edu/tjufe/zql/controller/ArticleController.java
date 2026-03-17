package cn.edu.tjufe.zql.controller;

import cn.edu.tjufe.zql.annotation.AccessLimit;
import cn.edu.tjufe.zql.annotation.LogAnnotation;
import cn.edu.tjufe.zql.constants.LogConst;
import cn.edu.tjufe.zql.domain.dto.ArticleDTO;
import cn.edu.tjufe.zql.domain.dto.SearchArticleDTO;
import cn.edu.tjufe.zql.domain.vo.ArticleDetailVO;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
import cn.edu.tjufe.zql.domain.vo.*;
import cn.edu.tjufe.zql.service.IArticleService;
import cn.edu.tjufe.zql.utils.ResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @AccessLimit(seconds = 60, maxCount = 5)
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
    @AccessLimit(seconds = 60, maxCount = 5)
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
    @AccessLimit(seconds = 60, maxCount = 60)
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
    @AccessLimit(seconds = 60, maxCount = 5)
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
    @AccessLimit(seconds = 60, maxCount = 5)
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
    @AccessLimit(seconds = 60, maxCount = 10)
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
    @AccessLimit(seconds = 60, maxCount = 60)
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
    @AccessLimit(seconds = 60, maxCount = 60)
    @GetMapping("/type/list/{typeId}")
    public ResponseResult<List<CategoryArticleVO>> getCategoryArticle(
            @NotNull @RequestParam("type") Integer type,
            @NotNull @PathVariable("typeId") Integer typeId
            ){
        return ResponseWrapper.handler(()->{return articleService.getCategoryArticleList(type,typeId);});
    }

    @Operation(summary="获取时间轴数据")
    @AccessLimit(seconds =60,maxCount = 15)
    @GetMapping("/timeline")
    public ResponseResult<List<TimelineVO>> timeline(){
        return ResponseWrapper.handler(()->articleService.listTimeline());
    }

    @Operation(summary = "文章访问量+1")
    @Parameter(name = "id", description = "文章id", required = true)
    @AccessLimit(seconds = 60, maxCount = 60)
    @GetMapping("/visit/{id}")
    public ResponseResult<Void> visit(@PathVariable("id") @NotNull Long id) {
        articleService.addVisitCount(id);
        return ResponseWrapper.handler(() -> null);
    }

    @PreAuthorize("hasAnyAuthority('blog:publish:article')")
    @Operation(summary = "上传文章封面")
    @Parameter(name = "articleCover", description = "文章封面")
    @LogAnnotation(module = "文章管理", operation = LogConst.UPLOAD_IMAGE)
    @AccessLimit(seconds = 60, maxCount = 5)
    @PostMapping("/upload/articleCover")
    public ResponseResult<String> uploadArticleCover(@RequestParam("articleCover") MultipartFile articleCover) {
        ResponseResult<String> result=articleService.uploadArticleCover(articleCover);
        return result;
    }

    @PreAuthorize("hasAnyAuthority('blog:publish:article')")
    @Operation(summary = "发布文章")
    @Parameter(name = "articleDTO", description = "文章信息")
    @LogAnnotation(module = "文章管理", operation = LogConst.INSERT)
    @AccessLimit(seconds = 60, maxCount = 30)
    @PostMapping("/publish")
    public ResponseResult<Void> publish(@RequestBody @Valid ArticleDTO articleDTO) {
        return articleService.publish(articleDTO);
    }

    @PreAuthorize("hasAnyAuthority('blog:publish:article')")
    @Operation(summary = "删除文章封面")
    @Parameter(name = "articleCover", description = "文章封面")
    @LogAnnotation(module = "发布错误", operation = LogConst.DELETE)
    @AccessLimit(seconds = 60, maxCount = 30)
    @GetMapping("/delete/articleCover")
    public ResponseResult<Void> deleteArticleCover(@RequestParam("articleCoverUrl") String articleCoverUrl) {
        return articleService.deleteArticleCover(articleCoverUrl);
    }

    @Operation(summary = "获取文章详情")
    @Parameter(name = "id", description = "文章id", required = true)
    @AccessLimit(seconds = 60, maxCount = 60)
    @GetMapping("/detail/{id}")
    public ResponseResult<ArticleDetailVO> detail(@PathVariable("id") @NotNull Integer id) {
        return ResponseWrapper.handler(() -> articleService.getArticleDetail(id));
    }

    @PreAuthorize("hasAnyAuthority('blog:publish:article')")
    @Operation(summary = "上传文章图片")
    @Parameters({
            @Parameter(name = "articleImage", description = "文章图片"),
            @Parameter(name = "articleId", description = "文章id", required = true)
    })
    @LogAnnotation(module = "文章管理", operation = LogConst.UPLOAD_IMAGE)
    @AccessLimit(seconds = 60, maxCount = 30)
    @PostMapping("/upload/articleImage")
    public ResponseResult<String> uploadArticleImage(
            @RequestParam("articleImage") MultipartFile articleImage
    ) {
        return articleService.uploadArticleImage(articleImage);
    }

    @PreAuthorize("hasAnyAuthority('blog:article:list')")
    @Operation(summary = "获取所有的文章列表")
    @LogAnnotation(module = "文章管理", operation = LogConst.GET)
    @AccessLimit(seconds = 60, maxCount = 30)
    @GetMapping("/back/list")
    public ResponseResult<List<ArticleListVO>> listArticle() {
        return ResponseWrapper.handler(() -> articleService.listArticle());
    }

    @PreAuthorize("hasAnyAuthority('blog:article:search')")
    @Operation(summary = "搜索文章列表")
    @Parameters({
            @Parameter(name = "searchArticleDTO", description = "搜索文章信息", required = true)
    })
    @LogAnnotation(module = "文章管理", operation = LogConst.SEARCH)
    @AccessLimit(seconds = 60, maxCount = 30)
    @PostMapping("/back/search")
    public ResponseResult<List<ArticleListVO>> searchArticle(@RequestBody SearchArticleDTO searchArticleDTO) {
        return ResponseWrapper.handler(() -> articleService.searchArticle(searchArticleDTO));
    }

    @PreAuthorize("hasAnyAuthority('blog:article:update')")
    @Operation(summary = "修改文章状态")
    @Parameters({
            @Parameter(name = "id", description = "文章id", required = true),
            @Parameter(name = "status", description = "状态", required = true)
    })
    @LogAnnotation(module = "文章管理", operation = LogConst.UPDATE)
    @AccessLimit(seconds = 60, maxCount = 30)
    @PostMapping("/back/update/status")
    public ResponseResult<Void> updateArticleStatus(
            @RequestParam("id") @NotNull Long id,
            @RequestParam("status") @NotNull Integer status
    ) {
        return articleService.updateStatus(id, status);
    }

    @PreAuthorize("hasAnyAuthority('blog:article:update')")
    @Operation(summary = "修改文章是否置顶")
    @Parameters({
            @Parameter(name = "id", description = "文章id", required = true),
            @Parameter(name = "isTop", description = "是否置顶", required = true)
    })
    @LogAnnotation(module = "文章管理", operation = LogConst.UPDATE)
    @AccessLimit(seconds = 60, maxCount = 30)
    @PostMapping("/back/update/isTop")
    public ResponseResult<Void> updateArticleIsTop(
            @RequestParam("id") @NotNull Long id,
            @RequestParam("isTop") @NotNull Integer isTop
    ) {
        return articleService.updateIsTop(id, isTop);
    }

    @PreAuthorize("hasAnyAuthority('blog:article:echo')")
    @Operation(summary = "回显文章数据")
    @Parameters({
            @Parameter(name = "id", description = "文章id", required = true)
    })
    @LogAnnotation(module = "文章管理", operation = LogConst.GET)
    @AccessLimit(seconds = 60, maxCount = 30)
    @GetMapping("/back/echo/{id}")
    public ResponseResult<ArticleDTO> getArticleEcho(@PathVariable("id") Long id) {
        return ResponseWrapper.handler(() -> articleService.getArticleDTO(id));
    }

    @PreAuthorize("hasAnyAuthority('blog:article:delete')")
    @Operation(summary = "删除文章")
    @Parameters({
            @Parameter(name = "id", description = "文章id", required = true)
    })
    @LogAnnotation(module = "文章管理", operation = LogConst.DELETE)
    @AccessLimit(seconds = 60, maxCount = 30)
    @DeleteMapping("/back/delete")
    public ResponseResult<Void> deleteArticle(@RequestBody List<Long> ids) {
        return articleService.deleteArticle(ids);
    }
}
