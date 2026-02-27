package cn.edu.tjufe.zql.controller;


import cn.edu.tjufe.zql.annotation.AccessIntercepter;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
import cn.edu.tjufe.zql.domain.vo.TagVO;
import cn.edu.tjufe.zql.service.impl.TagServiceImpl;
import cn.edu.tjufe.zql.utils.ResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @Resource
    private TagServiceImpl tagService;

    @Operation(summary = "获取所有标签列表")
    @AccessIntercepter(seconds = 60,maxCount = 60)
    @GetMapping("/list")
    public ResponseResult<List<TagVO>> tagList(){
        return ResponseWrapper.handler(()->tagService.listAllTag());
    }
}
