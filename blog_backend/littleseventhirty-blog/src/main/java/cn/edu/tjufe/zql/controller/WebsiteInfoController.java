package cn.edu.tjufe.zql.controller;

import cn.edu.tjufe.zql.annotation.AccessIntercepter;
import cn.edu.tjufe.zql.annotation.LogAnnotation;
import cn.edu.tjufe.zql.constants.LogConst;
import cn.edu.tjufe.zql.domain.dto.StationmasterInfoDTO;
import cn.edu.tjufe.zql.domain.dto.WebsiteInfoDTO;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
import cn.edu.tjufe.zql.domain.vo.WebsiteInfoVO;
import cn.edu.tjufe.zql.enums.UploadEnum;
import cn.edu.tjufe.zql.service.IWebsiteInfoService;
import cn.edu.tjufe.zql.utils.ResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: littleseventhirty
 * @description: 站点信息控制类
 * @date: 2026/1/9-10:05
 **/
@Tag(name = "站点基本信息")
@RequestMapping("/websiteInfo")
@RestController
public class WebsiteInfoController {

    @Resource(name = "websiteInfoService")
    private IWebsiteInfoService websiteInfoService;

    /**
     * 查询网站信息
     *
     * @return
     */
    @AccessIntercepter(seconds = 60, maxCount = 30)
    @Operation(description = "查询网站信息-前端")
    @GetMapping("/front")
    public ResponseResult<WebsiteInfoVO> getFrontArticles() {
        return ResponseResult.success(websiteInfoService.getWebsiteInfo());
    }

    /**
     * 查询网站信息
     * @return 网站信息
     */
    @PreAuthorize("hasAnyAuthority('blog:get:websiteInfo')")
    @Operation(summary = "查看网站信息-后端")
    @AccessIntercepter(seconds = 60, maxCount = 30)
    @GetMapping
    public ResponseResult<WebsiteInfoVO> selectWebsiteInfo() {
        return ResponseResult.success(websiteInfoService.getWebsiteInfo());
    }

    /**
     * 修改站长信息
     * @param stationmasterInfoDTO 站长信息
     * @return 是否成功
     */
    @PreAuthorize("hasAnyAuthority('blog:update:websiteInfo')")
    @Operation(summary = "修改或创建站长信息")
    @Parameter(name = "stationmasterInfoDTO", description = "站长信息")
    @PostMapping("/stationmaster")
    public ResponseResult<Void> updateStationmasterInfo(@Valid @RequestBody StationmasterInfoDTO stationmasterInfoDTO) {
        return websiteInfoService.updateStationmasterInfo(stationmasterInfoDTO);
    }

    /**
     * 修改或创建网站信息
     * @param websiteInfoDTO 网站信息
     * @return 是否成功
     */
    @PreAuthorize("hasAnyAuthority('blog:update:websiteInfo')")
    @Operation(summary = "修改或创建网站信息")
    @Parameter(name = "websiteInfoDTO", description = "网站信息")
    @PostMapping("/webInfo")
    public ResponseResult<Void> updateWebsiteInfo(@Valid @RequestBody WebsiteInfoDTO websiteInfoDTO) {
        return websiteInfoService.updateWebsiteInfo(websiteInfoDTO);
    }

    /**
     * 上传头像
     * @param avatar 头像
     * @return 访问url
     */
    @PreAuthorize("hasAnyAuthority('blog:update:websiteInfo')")
    @Operation(summary = "上传站长头像")
    @Parameter(name = "avatar", description = "头像")
    @AccessIntercepter(seconds = 60, maxCount = 5)
    @LogAnnotation(module="信息管理",operation= LogConst.UPLOAD_IMAGE)
    @PostMapping("/upload/avatar")
    public ResponseResult<String> upload(@RequestParam("avatar") MultipartFile avatar) {
        return websiteInfoService.uploadImageInsertOrUpdate(UploadEnum.WEBSITE_INFO_AVATAR, avatar,0);
    }

    /**
     * 资料卡背景上传
     * @param background 背景图
     * @return 访问url
     */
    @PreAuthorize("hasAnyAuthority('blog:update:websiteInfo')")
    @Operation(summary = "上传站长资料卡背景")
    @Parameter(name = "background", description = "资料卡片背景")
    @AccessIntercepter(seconds = 60, maxCount = 5)
    @LogAnnotation(module="信息管理",operation= LogConst.UPLOAD_IMAGE)
    @PostMapping("/upload/background")
    public ResponseResult<String> uploadBackground(@RequestParam("background") MultipartFile background) {
        return websiteInfoService.uploadImageInsertOrUpdate(UploadEnum.WEBSITE_INFO_BACKGROUND, background, 1);
    }

}
