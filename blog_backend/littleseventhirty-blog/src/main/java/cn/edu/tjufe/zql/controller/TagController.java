package cn.edu.tjufe.zql.controller;


import cn.edu.tjufe.zql.annotation.AccessIntercepter;
import cn.edu.tjufe.zql.annotation.LogAnnotation;
import cn.edu.tjufe.zql.constants.LogConst;
import cn.edu.tjufe.zql.domain.dto.SearchTagDTO;
import cn.edu.tjufe.zql.domain.dto.TagDTO;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
import cn.edu.tjufe.zql.domain.vo.TagVO;
import cn.edu.tjufe.zql.service.ITagService;
import cn.edu.tjufe.zql.service.impl.TagServiceImpl;
import cn.edu.tjufe.zql.utils.ResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @authro 钟奇林
 * @description Tag相关接口的Controller层
 * @date 2026/2/10
 * @github https://github.com/little-seven-thirty
 */
@RequestMapping("/tag")
@Tag(name="标签相关接口")
@RestController
@Validated  // 开启方法或类级别的参数校验
public class TagController {
    @Resource(name="tagService")
    private ITagService tagService;

    @Operation(summary = "新增标签-文章列表")
    @PreAuthorize("hasAnyAuthority('blog:tag:add')")
    @LogAnnotation(module="标签管理",operation= LogConst.INSERT)
    @AccessIntercepter(seconds = 60, maxCount = 30)
    @PutMapping()
    public ResponseResult<Void> addTag(@RequestBody @Valid TagDTO tagDTO) {
        return tagService.addTag(tagDTO);
    }


    @Operation(summary = "获取所有标签列表")
    @AccessIntercepter(seconds = 60,maxCount = 60)
    @GetMapping("/list")
    public ResponseResult<List<TagVO>> tagList(){
        return ResponseWrapper.handler(()->tagService.listAllTag());
    }

    @Operation(summary = "获取标签列表")
    @PreAuthorize("hasAnyAuthority('blog:tag:list')")
    @LogAnnotation(module="标签管理",operation= LogConst.GET)
    @AccessIntercepter(seconds = 60, maxCount = 30)
    @GetMapping("/back/list")
    public ResponseResult<List<TagVO>> listArticleTag() {
        return ResponseWrapper.handler(() -> tagService.listAllTag());
    }

    @Operation(summary = "搜索标签列表")
    @PreAuthorize("hasAnyAuthority('blog:tag:search')")
    @LogAnnotation(module="标签管理",operation= LogConst.SEARCH)
    @AccessIntercepter(seconds = 60, maxCount = 30)
    @PostMapping("/back/search")
    public ResponseResult<List<TagVO>> searchTag(@RequestBody SearchTagDTO searchTagDTO) {
        return ResponseWrapper.handler(() -> tagService.searchTag(searchTagDTO));
    }

    @Operation(summary = "根据id查询标签")
    @PreAuthorize("hasAnyAuthority('blog:tag:search')")
    @LogAnnotation(module="标签管理",operation= LogConst.GET)
    @AccessIntercepter(seconds = 60, maxCount = 30)
    @GetMapping("/back/get/{id}")
    public ResponseResult<TagVO> getTagById(@PathVariable(value = "id") Long id) {
        return ResponseWrapper.handler(() -> tagService.getTagById(id));
    }

    @Operation(summary = "新增标签-标签列表")
    @PreAuthorize("hasAnyAuthority('blog:tag:add')")
    @LogAnnotation(module="标签管理",operation= LogConst.INSERT)
    @AccessIntercepter(seconds = 60, maxCount = 30)
    @PutMapping("/back/add")
    public ResponseResult<Void> addOrUpdateTag(@RequestBody @Valid TagDTO tagDTO) {
        return tagService.addOrUpdateTag(tagDTO.setTagId(null));
    }

    @Operation(summary = "修改标签")
    @PreAuthorize("hasAnyAuthority('blog:tag:update')")
    @LogAnnotation(module="标签管理",operation= LogConst.UPDATE)
    @AccessIntercepter(seconds = 60, maxCount = 30)
    @PostMapping("/back/update")
    public ResponseResult<Void> updateTag(@RequestBody @Valid TagDTO tagDTO) {
        return tagService.addOrUpdateTag(tagDTO);
    }

    @Operation(summary = "删除标签")
    @PreAuthorize("hasAnyAuthority('blog:tag:delete')")
    @LogAnnotation(module="标签管理",operation= LogConst.DELETE)
    @AccessIntercepter(seconds = 60, maxCount = 30)
    @DeleteMapping("/back/delete")
    public ResponseResult<Void> deleteTag(@RequestBody List<Long> ids) {
        return tagService.deleteTagByIds(ids);
    }
}
