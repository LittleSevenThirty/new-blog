package cn.edu.tjufe.zql.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@Tag(name="文章相关接口")
@RequestMapping("article/")
public class articleController {

    public void searchArticleByContent(String content){

    }
}
