package cn.edu.tjufe.zql.controller;

import cn.edu.tjufe.zql.annotation.AccessIntercepter;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
import cn.edu.tjufe.zql.domain.vo.WebsiteInfoVO;
import cn.edu.tjufe.zql.service.IWebsiteInfoService;
import cn.edu.tjufe.zql.utils.ResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: littleseventhirty
 * @description: 站点信息控制类
 * @date: 2026/1/9-10:05
 **/

@Tag(name = "站点基本信息")
@RequestMapping("/websiteInfo")
@RestController
public class WebsiteInfoController {

    @Resource
    private IWebsiteInfoService websiteInfoService;

    /**
     * 查询网站信息
     *
     * @return
     */
    @AccessIntercepter(second = 60, maxCount = 30)
    @Operation(description = "查询网站信息-前端")
    @GetMapping("/front")
    public ResponseResult<WebsiteInfoVO> getFrontArticles() {
        return ResponseWrapper.handler(() -> websiteInfoService.getWebsiteInfo());
    }
}
