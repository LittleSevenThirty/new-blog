package cn.edu.tjufe.zql.controller;

import cn.edu.tjufe.zql.annotation.AccessIntercepter;
import cn.edu.tjufe.zql.annotation.LogAnnotation;
import cn.edu.tjufe.zql.constants.LogConst;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
import cn.edu.tjufe.zql.service.impl.BannerServiceImpl;
import cn.edu.tjufe.zql.utils.ResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: littleseventhirty
 * @description:
 * @date: 2026/1/22-12:53
 **/
@RestController
@RequestMapping("/banner")
public class BannerController {
    @Resource
    private BannerServiceImpl bannerService;

    /**
     * 获取轮播图路径
     *
     * @return 轮播图路径列表
     */
    @Operation(summary = "前端获取轮播图url")
    @AccessIntercepter(seconds = 60, maxCount = 25)
    @LogAnnotation(module = "信息管理", operation = LogConst.GET)
    @GetMapping("/slideshow")
    public ResponseResult<List<String>> getSlideshowImages() {
        return ResponseWrapper.handler(() -> bannerService.getSlideshow());
    }
}
