package cn.edu.tjufe.zql.service;

import cn.edu.tjufe.zql.domain.entity.Banner;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author: littleseventhirty
 * @description:
 * @date: 2026/1/22-11:48
 **/
public interface IBannerService extends IService<Banner> {

    /**
     * 更新Banner顺序
     * @param updateBannerSortOrderDTO  更新Banner顺序
     * @return ResponseResult
     */
    ResponseResult<String> updateSortOrder(List<Banner> updateBannerSortOrderDTO);

    /**
     * 删除Banner
     *
     * @param id Banner ID
     * @return ResponseResult
     */
    ResponseResult<String> removeBannerById(Long id);

    /**
     * 后台获取所有前台首页Banner图片
     * @return banners Entity（List）
     */
    List<Banner> backGetBanners();

    /**
     * 上传Banner图片
     *
     * @param bannerImage Banner图片
     * @return ResponseResult
     */
    ResponseResult<Banner> uploadBannerImage(MultipartFile bannerImage);

    /**
     * 获取轮播图
     *
     * @return
     */
    List<String> getSlideshow();
}
