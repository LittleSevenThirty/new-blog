package cn.edu.tjufe.zql.service.impl;

import cn.edu.tjufe.zql.domain.entity.WebsiteInfo;
import cn.edu.tjufe.zql.mapper.WebsiteInfoMapper;
import cn.edu.tjufe.zql.service.IWebsiteInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author: littleseventhirty
 * @description:
 * @date: 2026/1/8-17:25
 **/
@Slf4j
@Service
public class WebsiteInfoService extends ServiceImpl<WebsiteInfoMapper, WebsiteInfo> implements IWebsiteInfoService  {
}
