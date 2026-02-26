package cn.edu.tjufe.zql.controller;

import cn.edu.tjufe.zql.domain.response.ResponseResult;
import cn.edu.tjufe.zql.service.IFavoriteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/favorite")
@Tag(name = "收藏管理")
public class FavoriteController {

    @Resource
    private IFavoriteService favoriteService;

    @PostMapping("/auth/favorite")
    @Operation(summary = "收藏")
    public ResponseResult<Void> favorite(@RequestParam Integer type, @RequestParam Long typeId) {
        return favoriteService.favorite(type, typeId);
    }

    @DeleteMapping("/auth/favorite")
    @Operation(summary = "取消收藏")
    public ResponseResult<Void> cancelFavorite(@RequestParam Integer type, @RequestParam Long typeId) {
        return favoriteService.cancelFavorite(type, typeId);
    }
}
