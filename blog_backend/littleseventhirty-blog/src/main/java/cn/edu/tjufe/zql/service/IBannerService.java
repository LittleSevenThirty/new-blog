package cn.edu.tjufe.zql.service;

import cn.edu.tjufe.zql.domain.entity.Banner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author: littleseventhirty
 * @description:
 * @date: 2026/1/22-11:48
 **/
public interface IBannerService extends IService<Banner> {
    /**
     * 获取轮播图
     *
     * @return
     */
    List<String> getSlideshow();
}
