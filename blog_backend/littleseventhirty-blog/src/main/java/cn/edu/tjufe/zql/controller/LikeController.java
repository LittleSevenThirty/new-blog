package cn.edu.tjufe.zql.controller;

import cn.edu.tjufe.zql.annotation.AccessLimit;
import cn.edu.tjufe.zql.annotation.CheckBlacklist;
import cn.edu.tjufe.zql.domain.entity.Like;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
import cn.edu.tjufe.zql.service.ILikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/like")
@Tag(name = "点赞管理")
public class LikeController {

    @Resource(name="likeService")
    private ILikeService likeService;

    @CheckBlacklist
    @Operation(summary = "点赞")
    @Parameters({
            @Parameter(name = "type", description = "点赞类型", required = true),
            @Parameter(name = "typeId", description = "点赞id", required = true)
    })
    @AccessLimit(seconds = 60, maxCount = 10)
    @PostMapping("/auth/like")
    public ResponseResult<Void> like(
            @RequestParam("type") @Valid @NotNull Integer type,
            @RequestParam("typeId") @Valid @NotNull Long typeId
    ) {
        return likeService.userLike(type, typeId);
    }

    @CheckBlacklist
    @Operation(summary = "取消点赞")
    @Parameters({
            @Parameter(name = "type", description = "点赞类型", required = true),
            @Parameter(name = "typeId", description = "点赞id", required = true)
    })
    @AccessLimit(seconds = 60, maxCount = 10)
    @DeleteMapping("/auth/like")
    public ResponseResult<Void> cancelLike(
            @RequestParam("type") @Valid @NotNull Integer type,
            @RequestParam("typeId") @Valid @NotNull Long typeId
    ) {
        return likeService.cancelLike(type, typeId);
    }

    @Operation(summary = "是否已经点赞")
    @Parameters({
            @Parameter(name = "type", description = "点赞类型", required = true),
            @Parameter(name = "typeId", description = "点赞id", required = true)
    })
    @AccessLimit(seconds = 60, maxCount = 60)
    @GetMapping("whether/like")
    public ResponseResult<List<Like>> isLike(
            @Valid @NotNull @RequestParam("type") Integer type,
            @RequestParam(value = "typeId", required = false) Integer typeId
    ) {
        return likeService.isLike(type, typeId);
    }
}
