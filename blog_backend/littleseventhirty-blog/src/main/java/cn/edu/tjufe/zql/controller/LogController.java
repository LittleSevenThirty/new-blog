package cn.edu.tjufe.zql.controller;


import cn.edu.tjufe.zql.annotation.AccessIntercepter;
import cn.edu.tjufe.zql.annotation.LogAnnotation;
import cn.edu.tjufe.zql.constants.LogConst;
import cn.edu.tjufe.zql.domain.dto.LogDTO;
import cn.edu.tjufe.zql.domain.dto.LogDeleteDTO;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
import cn.edu.tjufe.zql.domain.vo.PageVO;
import cn.edu.tjufe.zql.service.ILogService;
import cn.edu.tjufe.zql.utils.ResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/3/11
 * @github https://github.com/little-seven-thirty
 */
@Tag(name="操作日志相关接口")
@RestController
@RequestMapping("/log")
@Validated
public class LogController {
    /**
     * 服务对象
     */
    @Resource(name="logService")
    private ILogService logService;

    @PreAuthorize("hasAnyAuthority('system:log:list')")
    @Operation(summary = "显示所有操作日志")
    @AccessIntercepter(seconds = 60, maxCount = 30)
    @GetMapping("/list/{current}/{pageSize}")
    public ResponseResult<PageVO> getLogList(@PathVariable("current") @NotNull Long current, @PathVariable("pageSize") @NotNull Long pageSize) {
        return ResponseWrapper.handler(() -> logService.searchLog(null, current,pageSize));
    }

    @PreAuthorize("hasAnyAuthority('system:log:search')")
    @Operation(summary = "搜索操作日志")
    @Parameter(name = "loginLogDTO", description = "搜索条件")
    @AccessIntercepter(seconds = 60, maxCount = 30)
    @PostMapping("/search")
    public ResponseResult<PageVO> getLogSearch(@RequestBody @Valid LogDTO logDTO) {
        return ResponseWrapper.handler(() -> logService.searchLog(logDTO, logDTO.getCurrent(),logDTO.getPageSize()));
    }

    @PreAuthorize("hasAnyAuthority('system:log:delete')")
    @Operation(summary = "删除/清空操作日志")
    @Parameter(name = "deleteLoginLogDTO", description = "删除的id数组")
    @AccessIntercepter(seconds = 60, maxCount = 30)
    @LogAnnotation(module = "操作日志", operation = LogConst.DELETE)
    @DeleteMapping("/delete")
    public ResponseResult<Void> deleteLog(@RequestBody @Valid LogDeleteDTO logDeleteDTO) {
        return logService.deleteLog(logDeleteDTO);
    }
}
