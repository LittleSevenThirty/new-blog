package cn.edu.tjufe.zql.service.impl;

import cn.edu.tjufe.zql.domain.entity.Banner;
import cn.edu.tjufe.zql.mapper.BannerMapper;
import cn.edu.tjufe.zql.service.IBannerService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    @Override
    public List<String> getSlideshow() {
        List<Banner> banners = bannerMapper.selectList(new LambdaQueryWrapper<Banner>().orderByAsc(Banner::getOrder));
        if (!banners.isEmpty()) {
            return banners.stream().map(Banner::getPath).toList();
        }
        return List.of();
    }
}
