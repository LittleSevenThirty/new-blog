package cn.edu.tjufe.zql.controller;


import cn.edu.tjufe.zql.annotation.AccessLimit;
import cn.edu.tjufe.zql.annotation.CheckBlacklist;
import cn.edu.tjufe.zql.annotation.LogAnnotation;
import cn.edu.tjufe.zql.constants.LogConst;
import cn.edu.tjufe.zql.domain.dto.SearchTreeHoleDTO;
import cn.edu.tjufe.zql.domain.dto.TreeHoleIsCheckDTO;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
import cn.edu.tjufe.zql.domain.vo.TreeHoleListVO;
import cn.edu.tjufe.zql.domain.vo.TreeHoleVO;
import cn.edu.tjufe.zql.service.ITreeHoleService;
import cn.edu.tjufe.zql.utils.ResponseWrapper;
import com.alibaba.fastjson2.JSON;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @Resource(name="treeHoleService")
    private ITreeHoleService treeHoleService;

    @CheckBlacklist
    @Operation(summary = "添加树洞")
    @AccessLimit(seconds =60,maxCount=60)
    @PostMapping("/auth/addTreeHole")
    public ResponseResult<Void> addTreeHole(@Valid @NotNull @RequestBody String content){
        return treeHoleService.addTreeHole(JSON.parseObject(content).getString("content"));
    }

    @Operation(summary="查看树洞")
    @AccessLimit(seconds =60,maxCount=60)
    @GetMapping("/getTreeHoleList")
    public ResponseResult<List<TreeHoleVO>> getTreeHole(){
        return ResponseWrapper.handler(()->treeHoleService.getTreeHole());
    }

    @PreAuthorize("hasAnyAuthority('blog:treeHole:list')")
    @Operation(summary = "后台树洞列表")
    @AccessLimit(seconds = 60, maxCount = 30)
    @LogAnnotation(module="树洞管理",operation= LogConst.GET)
    @GetMapping("/back/list")
    public ResponseResult<List<TreeHoleListVO>> backList() {
        return ResponseWrapper.handler(() -> treeHoleService.getBackTreeHoleList(null));
    }

    @PreAuthorize("hasAnyAuthority('blog:treeHole:search')")
    @Operation(summary = "搜索后台树洞列表")
    @AccessLimit(seconds = 60, maxCount = 30)
    @LogAnnotation(module="树洞管理",operation= LogConst.SEARCH)
    @PostMapping("/back/search")
    public ResponseResult<List<TreeHoleListVO>> backList(@RequestBody SearchTreeHoleDTO searchDTO) {
        return ResponseWrapper.handler(() -> treeHoleService.getBackTreeHoleList(searchDTO));
    }

    @PreAuthorize("hasAnyAuthority('blog:treeHole:isCheck')")
    @Operation(summary = "修改树洞是否通过")
    @AccessLimit(seconds = 60, maxCount = 30)
    @LogAnnotation(module="树洞管理",operation= LogConst.UPDATE)
    @PostMapping("/back/isCheck")
    public ResponseResult<Void> isCheck(@RequestBody @Valid TreeHoleIsCheckDTO treeHoleIsCheckDTO) {
        return treeHoleService.isCheckTreeHole(treeHoleIsCheckDTO);
    }

    @PreAuthorize("hasAnyAuthority('blog:treeHole:delete')")
    @Operation(summary = "删除树洞")
    @AccessLimit(seconds = 60, maxCount = 30)
    @LogAnnotation(module="树洞管理",operation= LogConst.DELETE)
    @DeleteMapping("/back/delete")
    public ResponseResult<Void> delete(@RequestBody List<Long> ids) {
        return treeHoleService.deleteTreeHole(ids);
    }
}
