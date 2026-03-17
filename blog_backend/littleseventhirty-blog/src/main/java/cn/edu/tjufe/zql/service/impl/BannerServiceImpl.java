package cn.edu.tjufe.zql.service.impl;

import cn.edu.tjufe.zql.constants.RespConst;
import cn.edu.tjufe.zql.constants.SQLConst;
import cn.edu.tjufe.zql.domain.entity.Banner;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
import cn.edu.tjufe.zql.enums.UploadEnum;
import cn.edu.tjufe.zql.mapper.BannerMapper;
import cn.edu.tjufe.zql.service.IBannerService;
import cn.edu.tjufe.zql.utils.FileUploadUtils;
import cn.edu.tjufe.zql.utils.SecurityUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author: littleseventhirty
 * @description:
 * @date: 2026/1/22-11:49
 **/
@Slf4j
@Service(value = "bannerService")
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements IBannerService {
    @Resource
    private BannerMapper bannerMapper;

    @Resource
    private FileUploadUtils fileUploadUtils;

    @Override
    public ResponseResult<String> removeBannerById(Long id) {
        Banner banner = bannerMapper.selectById(id);
        if (this.removeById(id)) {
            // 查看存储桶中文件是否存在
            if (fileUploadUtils.isFileExist(UploadEnum.UI_BANNERS.getDir(), fileUploadUtils.getFileName(banner.getPath()))) {
                fileUploadUtils.deleteFile(UploadEnum.UI_BANNERS.getDir(), fileUploadUtils.getFileName(banner.getPath()));
            }
        } else return ResponseResult.failure("删除失败");
        return ResponseResult.success("删除成功");
    }

    @Override
    public List<Banner> backGetBanners() {
        List<Banner> banners = bannerMapper.selectList(new LambdaQueryWrapper<Banner>().orderByAsc(Banner::getOrder));
        if (!banners.isEmpty()) {
            return banners;
        }
        return List.of();
    }

    @Override
    public ResponseResult<Banner> uploadBannerImage(MultipartFile bannerImage) {
        try {
            String bannerUrl;
            try {
                // 是否到达Banner数量上限，达上限上传失败
                if (bannerMapper.selectCount(null) >= SQLConst.BANNER_MAX_COUNT) {
                    return ResponseResult.failure(RespConst.BANNER_MAX_COUNT_MSG);
                }
                bannerUrl = fileUploadUtils.upload(UploadEnum.UI_BANNERS, bannerImage);
                Banner banner = Banner.builder().size(bannerImage.getSize())
                        .type(bannerImage.getContentType())
                        .userId(SecurityUtils.getUserId())
                        .order((bannerMapper.selectCount(null) + 1))
                        .path(bannerUrl).build();
                bannerMapper.insert(banner);
                return ResponseResult.success(banner);
            } catch (FileUploadException e) {
                return ResponseResult.failure(e.getMessage());
            }
        } catch (Exception e) {
            log.error(UploadEnum.UI_BANNERS.getDescription() + "上传失败", e);
            return ResponseResult.failure();
        }
    }

    @Override
    public List<String> getSlideshow() {
        List<Banner> banners = bannerMapper.selectList(new LambdaQueryWrapper<Banner>().orderByAsc(Banner::getOrder));
        if (!banners.isEmpty()) {
            return banners.stream().map(Banner::getPath).toList();
        }
        return List.of();
    }

    @Override
    public ResponseResult<String> updateSortOrder(List<Banner> banners) {
        // 删除全部
        bannerMapper.delete(Wrappers.emptyWrapper());
        //  重新排序
        for (int i = 0; i < banners.size(); i++) {
            banners.get(i).setOrder(((long) i + 1));
            bannerMapper.insert(banners.get(i));
        }
        return ResponseResult.success();
    }
}
