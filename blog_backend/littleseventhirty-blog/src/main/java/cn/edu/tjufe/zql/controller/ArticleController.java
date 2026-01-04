package cn.edu.tjufe.zql.controller;

import cn.edu.tjufe.zql.constants.SQLConst;
import cn.edu.tjufe.zql.domain.entity.Article;
import cn.edu.tjufe.zql.domain.entity.Category;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
import cn.edu.tjufe.zql.domain.vo.SearchArticleByContentVO;
import cn.edu.tjufe.zql.service.IArticleService;
import cn.edu.tjufe.zql.utils.ResponseWrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Validated
@RestController
@Tag(name="文章相关接口")
@RequestMapping("article/")
public class ArticleController {

    @Resource(name="articleService")
    private IArticleService articleService;

    /**
     * 根据内容片段搜索文章
     * @param content 搜索文章内容片段
     * @return
     */
    @Operation(summary="根据内容片段搜索文章")
    @Parameters({
            @Parameter(name="content",description = "搜索文章内容片段",required = true)
    })
    @GetMapping("search/by/content")
    public ResponseResult searchArticleByContent(
            @NotEmpty
            @Length()
            @RequestParam("content")
            String content){
        return ResponseWrapper.handler(()->articleService.searchArticleByContent(content));
    }
}
