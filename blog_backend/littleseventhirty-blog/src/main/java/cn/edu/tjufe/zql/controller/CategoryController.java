package cn.edu.tjufe.zql.controller;

import cn.edu.tjufe.zql.annotation.AccessIntercepter;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
import cn.edu.tjufe.zql.domain.vo.CategoryVO;
import cn.edu.tjufe.zql.service.impl.CategoryServiceImpl;
import cn.edu.tjufe.zql.utils.ResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: littleseventhirty
 * @description:
 * @date: 2025/12/15-16:51
 **/
@Controller(value="categoryController")
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Resource
    private CategoryServiceImpl categoryService;

    @Operation(summary = "获取所有分类")
    @AccessIntercepter(seconds =60,maxCount=60)
    @GetMapping("/list")
    public ResponseResult<List<CategoryVO>> allCategoryList(){
        return ResponseWrapper.handler(()->categoryService.listAllCategory());
    }
}
