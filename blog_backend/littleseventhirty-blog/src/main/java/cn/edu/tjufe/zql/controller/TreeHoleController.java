package cn.edu.tjufe.zql.controller;


import cn.edu.tjufe.zql.service.ITreeHoleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @authro 钟奇林
 * @description 树洞控制层
 * @date 2026/2/19
 * @github https://github.com/little-seven-thirty
 */
@RequestMapping("/treeHole")
@Tag(name="树洞相关接口")
@RestController
@Validated
public class TreeHoleController {
    @Resource
    private ITreeHoleService treeHoleService;
}
