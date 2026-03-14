package cn.edu.tjufe.zql.controller;


import cn.edu.tjufe.zql.annotation.AccessIntercepter;
import cn.edu.tjufe.zql.annotation.CheckBlacklist;
import cn.edu.tjufe.zql.annotation.LogAnnotation;
import cn.edu.tjufe.zql.constants.LogConst;
import cn.edu.tjufe.zql.domain.dto.LeaveWordIsCheckDTO;
import cn.edu.tjufe.zql.domain.dto.SearchLeaveWordDTO;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
import cn.edu.tjufe.zql.domain.vo.LeaveWordListVO;
import cn.edu.tjufe.zql.domain.vo.LeaveWordVO;
import cn.edu.tjufe.zql.service.ILeaveWordService;
import cn.edu.tjufe.zql.utils.ResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
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
 * @description 留言接口控制层
 * @date 2026/2/24
 * @github https://github.com/little-seven-thirty
 */
@RestController
@RequestMapping("/leaveWord")
@Validated
@Tag(name="留言板",description="留言板相关接口")
public class LeaveWordController {
    @Resource(name="leaveWordService")
    private ILeaveWordService leaveWordService;

    @CheckBlacklist
    @Operation(summary="用户留言")
    @AccessIntercepter(seconds = 60,maxCount = 60)
    @GetMapping("/auth/userLeaveWord")
    public ResponseResult<Void> userLeaveWord(@RequestBody @NotNull String content){
        return leaveWordService.addUserLeaveWord(content);
    }

    @Operation(summary = "获取留言板列表")
    @Parameters({
            @Parameter(name = "id", description = "留言板id", in = ParameterIn.QUERY)
    })
    @GetMapping("/list")
    @AccessIntercepter(seconds = 60, maxCount = 10)
    public ResponseResult<List<LeaveWordVO>>  getAllLeaveWord(@RequestParam(value = "id",required = false) String id){
        return ResponseWrapper.handler(()->leaveWordService.getAllLeaveWordList(id));
    }

    @PreAuthorize("hasAnyAuthority('blog:leaveword:list')")
    @Operation(summary = "后台留言列表")
    @AccessIntercepter(seconds = 60, maxCount = 30)
    @LogAnnotation(module="留言管理",operation= LogConst.GET)
    @GetMapping("/back/list")
    public ResponseResult<List<LeaveWordListVO>> backList() {
        return ResponseWrapper.handler(() -> leaveWordService.getBackLeaveWordList(null));
    }

    @PreAuthorize("hasAnyAuthority('blog:leaveword:search')")
    @Operation(summary = "搜索后台留言列表")
    @AccessIntercepter(seconds = 60, maxCount = 30)
    @LogAnnotation(module="留言管理",operation= LogConst.SEARCH)
    @PostMapping("/back/search")
    public ResponseResult<List<LeaveWordListVO>> backList(@RequestBody SearchLeaveWordDTO searchDTO) {
        return ResponseWrapper.handler(() -> leaveWordService.getBackLeaveWordList(searchDTO));
    }

    @PreAuthorize("hasAnyAuthority('blog:leaveword:isCheck')")
    @Operation(summary = "修改留言是否通过")
    @AccessIntercepter(seconds = 60, maxCount = 30)
    @LogAnnotation(module="留言管理",operation= LogConst.UPDATE)
    @PostMapping("/back/isCheck")
    public ResponseResult<Void> isCheck(@RequestBody @Valid LeaveWordIsCheckDTO leaveWordIsCheckDTO) {
        return leaveWordService.isCheckLeaveWord(leaveWordIsCheckDTO);
    }

    @PreAuthorize("hasAnyAuthority('blog:leaveword:delete')")
    @Operation(summary = "删除留言")
    @AccessIntercepter(seconds = 60, maxCount = 30)
    @LogAnnotation(module="留言管理",operation= LogConst.DELETE)
    @DeleteMapping("/back/delete")
    public ResponseResult<Void> delete(@RequestBody List<Long> ids) {
        return leaveWordService.deleteLeaveWord(ids);
    }

}
