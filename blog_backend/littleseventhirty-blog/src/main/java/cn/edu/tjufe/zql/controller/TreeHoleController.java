package cn.edu.tjufe.zql.controller;


import cn.edu.tjufe.zql.annotation.AccessIntercepter;
import cn.edu.tjufe.zql.annotation.CheckBlacklist;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
import cn.edu.tjufe.zql.domain.vo.TreeHoleVO;
import cn.edu.tjufe.zql.service.ITreeHoleService;
import cn.edu.tjufe.zql.utils.ResponseWrapper;
import com.alibaba.fastjson2.JSON;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @CheckBlacklist
    @Operation(summary = "添加树洞")
    @AccessIntercepter(seconds =60,maxCount=60)
    @PostMapping("/auth/addTreeHole")
    public ResponseResult<Void> addTreeHole(@Valid @NotNull @RequestBody String content){
        return treeHoleService.addTreeHole(JSON.parseObject(content).getString("content"));
    }

    @Operation(summary="查看树洞")
    @AccessIntercepter(seconds =60,maxCount=60)
    @GetMapping("/getTreeHoleList")
    public ResponseResult<List<TreeHoleVO>> getTreeHole(){
        return ResponseWrapper.handler(()->treeHoleService.getTreeHole());
    }
}
