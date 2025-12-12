package cn.edu.tjufe.zql.controller;

import cn.edu.tjufe.zql.service.IArticleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@Tag(name="文章相关接口")
@RequestMapping("article/")
public class articleController {

    @Resource(name="articleService")
    private IArticleService articleService;

    @GetMapping("search/by/content")
    public void searchArticleByContent(String content){
        String result=articleService.searchArticleByContent(content);
    }
}
