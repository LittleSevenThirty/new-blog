package cn.edu.tjufe.zql.controller;

import cn.edu.tjufe.zql.constants.SQLConst;
import cn.edu.tjufe.zql.domain.entity.Article;
import cn.edu.tjufe.zql.domain.entity.Category;
import cn.edu.tjufe.zql.service.IArticleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @GetMapping("search/by/content")
    public void searchArticleByContent(String content){
        articleService.searchArticleByContent(content);
    }
}
