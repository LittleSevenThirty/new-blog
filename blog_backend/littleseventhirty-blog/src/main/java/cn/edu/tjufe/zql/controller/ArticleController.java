package cn.edu.tjufe.zql.controller;

import cn.edu.tjufe.zql.annotation.AccessIntercepter;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
import cn.edu.tjufe.zql.domain.vo.HotArticleVO;
import cn.edu.tjufe.zql.domain.vo.InitSearchTitleVO;
import cn.edu.tjufe.zql.domain.vo.RandomArticleVO;
import cn.edu.tjufe.zql.domain.vo.SearchArticleByContentVO;
import cn.edu.tjufe.zql.service.IArticleService;
import cn.edu.tjufe.zql.utils.ResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseResult<List<HotArticleVO>> getHotArticles() {
        return ResponseWrapper.handler(() -> articleService.getHotArticles());
    }

    @GetMapping("/random")
    @AccessIntercepter(second = 60, maxCount = 5)
    @Operation(description = "刷一刷获取随机文章")
    public ResponseResult<List<RandomArticleVO>> getRandomArticles() {
        return ResponseWrapper.handler(() -> articleService.getRandomArticles());
    }
}
