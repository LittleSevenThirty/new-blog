package cn.edu.tjufe.zql.service.impl;

import cn.edu.tjufe.zql.domain.entity.Article;
import cn.edu.tjufe.zql.domain.vo.SearchArticleByContentVO;
import cn.edu.tjufe.zql.mapper.ArticleMapper;
import cn.edu.tjufe.zql.service.IArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: littleseventhirty
 * @description:
 * @date: 2025/12/12-11:44
 **/
@Slf4j
@Service(value="articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {
    @Override
    public List<SearchArticleByContentVO> searchArticleByContent(String content) {
        return List.of();
    }
}
