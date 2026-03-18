package cn.edu.tjufe.zql.controller;

import cn.edu.tjufe.zql.annotation.AccessLimit;
import cn.edu.tjufe.zql.annotation.LogAnnotation;
import cn.edu.tjufe.zql.constants.LogConst;
import cn.edu.tjufe.zql.domain.dto.CategoryDTO;
import cn.edu.tjufe.zql.domain.dto.SearchCategoryDTO;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
import cn.edu.tjufe.zql.domain.vo.CategoryVO;
import cn.edu.tjufe.zql.service.ICategoryService;
import cn.edu.tjufe.zql.utils.ResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    @Resource(name="categoryService")
    private ICategoryService categoryService;

    @Operation(summary = "新增分类-文章列表")
    @PreAuthorize("hasAnyAuthority('blog:category:add')")
    @LogAnnotation(module="新增分类",operation= LogConst.INSERT)
    @AccessLimit(seconds = 60, maxCount = 30)
    @PutMapping()
    public ResponseResult<Void> addCategory(@RequestBody @Valid CategoryDTO categoryDTO) {
        return categoryService.addCategory(categoryDTO);
    }

    @Operation(summary = "获取所有分类")
    @AccessLimit(seconds =60,maxCount=60)
    @GetMapping("/list")
    public ResponseResult<List<CategoryVO>> allCategoryList(){
        return ResponseWrapper.handler(()->categoryService.listAllCategory());
    }

    @Operation(summary = "获取分类列表")
    @PreAuthorize("hasAnyAuthority('blog:category:list')")
    @LogAnnotation(module="分类管理",operation= LogConst.GET)
    @AccessLimit(seconds = 60, maxCount = 30)
    @GetMapping("/back/list")
    public ResponseResult<List<CategoryVO>> listArticleCategory() {
        return ResponseWrapper.handler((categoryService::listAllCategory));
    }

    @Operation(summary = "搜索分类列表")
    @PreAuthorize("hasAnyAuthority('blog:category:search')")
    @LogAnnotation(module="分类管理",operation= LogConst.SEARCH)
    @AccessLimit(seconds = 60, maxCount = 30)
    @PostMapping("/back/search")
    public ResponseResult<List<CategoryVO>> searchCategory(@RequestBody SearchCategoryDTO searchCategoryDTO) {
        return ResponseWrapper.handler(() -> categoryService.searchCategory(searchCategoryDTO));
    }

    @Operation(summary = "根据id查询分类")
    @PreAuthorize("hasAnyAuthority('blog:category:search')")
    @LogAnnotation(module="分类管理",operation= LogConst.GET)
    @AccessLimit(seconds = 60, maxCount = 30)
    @GetMapping("/back/get/{id}")
    public ResponseResult<CategoryVO> getCategoryById(@PathVariable(value = "id") Long id) {
        return ResponseWrapper.handler(() -> categoryService.getCategoryById(id));
    }

    @Operation(summary = "修改分类")
    @PreAuthorize("hasAnyAuthority('blog:category:update')")
    @LogAnnotation(module="分类管理",operation= LogConst.UPDATE)
    @AccessLimit(seconds = 60, maxCount = 30)
    @PostMapping("/back/update")
    public ResponseResult<Void> updateCategory(@RequestBody @Valid CategoryDTO categoryDTO) {
        return categoryService.addOrUpdateCategory(categoryDTO);
    }

    @Operation(summary = "删除分类")
    @PreAuthorize("hasAnyAuthority('blog:category:delete')")
    @LogAnnotation(module="分类管理",operation= LogConst.DELETE)
    @AccessLimit(seconds = 60, maxCount = 30)
    @DeleteMapping("/back/delete")
    public ResponseResult<Void> deleteCategory(@RequestBody List<Long> ids) {
        return categoryService.deleteCategoryByIds(ids);
    }
}
