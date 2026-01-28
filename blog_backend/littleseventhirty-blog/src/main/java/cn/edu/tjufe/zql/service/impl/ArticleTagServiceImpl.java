package cn.edu.tjufe.zql.service.impl;

import cn.edu.tjufe.zql.domain.entity.ArticleTag;
import cn.edu.tjufe.zql.mapper.ArticleTagMapper;
import cn.edu.tjufe.zql.service.IArticleTagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author: littleseventhirty
 * @description:
 * @date: 2026/1/28-15:23
 **/
@Slf4j
@Service("articleTagService")
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements IArticleTagService {
}
