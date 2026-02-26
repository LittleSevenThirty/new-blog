package cn.edu.tjufe.zql.controller;

import cn.edu.tjufe.zql.domain.response.ResponseResult;
import cn.edu.tjufe.zql.service.ILikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/like")
@Tag(name = "点赞管理")
public class LikeController {

    @Resource
    private ILikeService likeService;

    @PostMapping("/auth/like")
    @Operation(summary = "点赞")
    public ResponseResult<Void> like(@RequestParam Integer type, @RequestParam Long typeId) {
        return likeService.like(type, typeId);
    }

    @DeleteMapping("/auth/like")
    @Operation(summary = "取消点赞")
    public ResponseResult<Void> cancelLike(@RequestParam Integer type, @RequestParam Long typeId) {
        return likeService.cancelLike(type, typeId);
    }
}
