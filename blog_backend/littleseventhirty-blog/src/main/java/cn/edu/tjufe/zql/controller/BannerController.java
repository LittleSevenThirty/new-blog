package cn.edu.tjufe.zql.controller;

import cn.edu.tjufe.zql.annotation.AccessLimit;
import cn.edu.tjufe.zql.annotation.LogAnnotation;
import cn.edu.tjufe.zql.constants.LogConst;
import cn.edu.tjufe.zql.domain.entity.Banner;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
import cn.edu.tjufe.zql.service.IBannerService;
import cn.edu.tjufe.zql.utils.ResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author: littleseventhirty
 * @description:
 * @date: 2026/1/22-12:53
 **/
@RestController
@RequestMapping("/banner")
public class BannerController {
    @Resource(name="bannerService")
    private IBannerService bannerService;

    /**
     * 更新顺序
     */
    @PreAuthorize("hasAnyAuthority('blog:banner:update')")
    @Operation(summary = "更新前台首页Banner图片顺序")
    @LogAnnotation(module = "信息管理", operation = LogConst.UPDATE)
    @Parameter(name = "SortOrders", description = "顺序", required = true)
    @AccessLimit(seconds = 60, maxCount = 30)
    @PutMapping("/update/sort/order")
    public ResponseResult<String> updateSortOrder(@RequestBody List<Banner> Banners) {
        return bannerService.updateSortOrder(Banners);
    }

    /**
     * 删除首页banner
     */
    @PreAuthorize("hasAnyAuthority('blog:banner:delete')")
    @Operation(summary = "删除前台首页Banner图片")
    @LogAnnotation(module = "信息管理", operation = LogConst.DELETE)
    @Parameter(name = "id", description = "Banner ID", required = true)
    @AccessLimit(seconds = 60, maxCount = 30)
    @DeleteMapping("/{id}")
    public ResponseResult<String> delete(@PathVariable("id") Long id) {
        return bannerService.removeBannerById(id);
    }


    /**
     * 添加轮播图
     */
    @PreAuthorize("hasAnyAuthority('blog:banner:add')")
    @Operation(summary = "添加前台首页Banner图片")
    @LogAnnotation(module = "信息管理", operation = LogConst.INSERT)
    @Parameter(name = "bannerImage", description = "Banner图片", required = true)
    @AccessLimit(seconds = 60, maxCount = 30)
    @PostMapping("/upload/banner")
    public ResponseResult<Banner> uploadArticleImage(
            @RequestParam("bannerImage") MultipartFile bannerImage
    ) {
        return bannerService.uploadBannerImage(bannerImage);
    }


    /**
     * 获取轮播图路径
     *
     * @return 轮播图路径列表
     */
    @Operation(summary = "前端获取轮播图url")
    @AccessLimit(seconds = 60, maxCount = 25)
    @LogAnnotation(module = "信息管理", operation = LogConst.GET)
    @GetMapping("/slideshow")
    public ResponseResult<List<String>> getSlideshowImages() {
        return ResponseWrapper.handler(() -> bannerService.getSlideshow());
    }

    /**
     * 查询首页banner列表
     */
    @PreAuthorize("hasAnyAuthority('blog:banner:list')")
    @Operation(summary = "后台获取所有前台首页Banner图片")
    @LogAnnotation(module = "信息管理", operation = LogConst.GET)
    @AccessLimit(seconds = 60, maxCount = 60)
    @GetMapping("/back/slideshow")
    public ResponseResult<List<Banner>> backGetBanners() {
        return ResponseWrapper.handler(() -> bannerService.backGetBanners());
    }
}
