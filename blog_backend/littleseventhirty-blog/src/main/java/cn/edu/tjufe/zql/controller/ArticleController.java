package cn.edu.tjufe.zql.controller;

import cn.edu.tjufe.zql.annotation.AccessIntercepter;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
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

@Validated
@RestController
@Tag(name = "文章相关接口")
@RequestMapping("article/")
public class ArticleController {

    @Resource(name = "articleService")
    private IArticleService articleService;

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
    @GetMapping("search/by/content")
    public ResponseResult searchArticleByContent(
            @NotEmpty
            @Length(min = 1, max = 15, message = "搜索内容长度应不超过15字符")
            @RequestParam("content")
            String content) {
        return ResponseWrapper.handler(() -> articleService.searchArticleByContent(content));
    }
}
