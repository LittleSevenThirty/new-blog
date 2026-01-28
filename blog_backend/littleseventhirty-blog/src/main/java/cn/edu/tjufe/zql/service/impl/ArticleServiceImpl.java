package cn.edu.tjufe.zql.service.impl;

import cn.edu.tjufe.zql.constants.RedisConst;
import cn.edu.tjufe.zql.constants.SQLConst;
import cn.edu.tjufe.zql.domain.entity.Article;
import cn.edu.tjufe.zql.domain.entity.ArticleTag;
import cn.edu.tjufe.zql.domain.entity.Category;
import cn.edu.tjufe.zql.domain.entity.Tag;
import cn.edu.tjufe.zql.domain.vo.*;
import cn.edu.tjufe.zql.mapper.ArticleMapper;
import cn.edu.tjufe.zql.mapper.ArticleTagMapper;
import cn.edu.tjufe.zql.mapper.CategoryMapper;
import cn.edu.tjufe.zql.mapper.TagMapper;
import cn.edu.tjufe.zql.service.IArticleService;
import cn.edu.tjufe.zql.utils.RedisCache;
import cn.edu.tjufe.zql.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author: littleseventhirty
 * @description:
 * @date: 2025/12/12-11:44
 **/
@Slf4j
@Service(value = "articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {
    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private ArticleTagMapper articleTagMapper;

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private RedisCache redisCache;

    @Resource
    private TagMapper tagMapper;

    @Override
    public List<InitSearchTitleVO> initSearchByTitle() {
        List<Article> articles = articleMapper.selectList(new LambdaQueryWrapper<Article>().eq(Article::getStatus, SQLConst.PUBLIC_APTICLE));
        Map<Long, String> categoryMap = categoryMapper.selectList(null).stream().collect(Collectors.toMap(Category::getCategoryId, Category::getCategoryName));
        if (!articles.isEmpty()) {
            return articles.stream().map(article -> article.asViewObject(InitSearchTitleVO.class, item -> {
                item.setCategoryName(categoryMap.get(article.getCategoryId()));
            })).toList();
        }
        return List.of();
    }

    @Override
    public List<SearchArticleByContentVO> searchArticleByContent(String content) {
        // 从数据库中查询相关对应文章
        List<Article> articles = articleMapper.selectList(
                new LambdaQueryWrapper<Article>()
                        .like(Article::getArticleContent, content)
                        .eq(Article::getStatus, SQLConst.PUBLIC_APTICLE));
        // 再从数据库中查询相关分类
        Map<Long, String> categoryMap = categoryMapper.selectList(null).stream().collect(Collectors.toMap(Category::getCategoryId, Category::getCategoryName));
        // 将对应数据存放到对应实体中
        if (!articles.isEmpty()) {
            List<SearchArticleByContentVO> searchArticleByContentVOList = articles.stream().map(article -> {
                return article.asViewObject(SearchArticleByContentVO.class, vo -> {
                    vo.setCategoryName(categoryMap.get(article.getCategoryId()));
                });
            }).toList();
            int index = -1;
            for (SearchArticleByContentVO vo : searchArticleByContentVOList) {
                String articleContent = vo.getArticleContent();
                index = articleContent.toLowerCase().indexOf(content.toLowerCase());
                if (index != -1) {
                    int start = Math.min(0, index - 10);
                    int end = Math.min(articleContent.length(), index + content.length() + 10);
                    String snippet = articleContent.substring(start, end);
                    String hilighted = snippet.replaceFirst(
                            "(?i)" + Pattern.quote(content),
                            "<mark>" + content + "</mark>"
                    );
                    vo.setArticleContent(StringUtils.stripMarkdown(hilighted));
                }
            }
            return searchArticleByContentVOList;
        }
        return List.of();
    }

    @Override
    public List<HotArticleVO> getHotArticles() {
        List<Article> hotArticles = articleMapper.selectList(new LambdaQueryWrapper<Article>().eq(Article::getStatus, SQLConst.PUBLIC_APTICLE).orderByDesc(Article::getVisitedCount).last("LIMIT 5"));
        if (!hotArticles.isEmpty()) {
            return hotArticles.stream().map(article -> {
                return article.asViewObject(HotArticleVO.class);
            }).toList();
        }
        return List.of();
    }

    @Override
    public List<RandomArticleVO> getRandomArticles() {
        List<Article> randomArticles = articleMapper.selectRandomArticle(SQLConst.PUBLIC_APTICLE, SQLConst.RANDOM_ARTICLE_COUNT);
        if (!randomArticles.isEmpty()) {
            return randomArticles.stream().map(article -> article.asViewObject(RandomArticleVO.class)).toList();
        }
        return List.of();
    }

    @Override
    public List<RecommendArticleVO> getRecommendArticles() {
        List<Article> articles = articleMapper.selectList(new LambdaQueryWrapper<Article>().eq(Article::getIsTop, SQLConst.RECOMMEND_ARTICLE).eq(Article::getStatus, SQLConst.PUBLIC_APTICLE));
        if (!articles.isEmpty()) {
            return articles.stream().map(article -> article.asViewObject(RecommendArticleVO.class)).toList();
        }
        return List.of();
    }

    @Override
    public PageVO<List<ArticleVO>> allArticleList(Integer pageNum, Integer pageSize) {
        // 文章
        boolean hasKey = redisCache.isHasKey(RedisConst.ARTICLE_COMMENT_COUNT) && redisCache.isHasKey(RedisConst.ARTICLE_FAVORITE_COUNT) && redisCache.isHasKey(RedisConst.ARTICLE_LIKE_COUNT);
        IPage<Article> page = new Page<>(pageNum, pageSize);
        this.page(page, new LambdaQueryWrapper<Article>().eq(Article::getStatus, SQLConst.PUBLIC_APTICLE).orderByDesc(Article::getCreateTime));
        List<Article> list = page.getRecords();
        // 分类映射
        Map<Long, String> categoryMap = categoryMapper.selectList(new LambdaQueryWrapper<Category>().in(Category::getCategoryId, list.stream().map(Article::getCategoryId).toList()))
                .stream().collect(Collectors.toMap(Category::getCategoryId, Category::getCategoryName));
        // 关联Article和Tag表
        List<ArticleTag> articleTags = articleTagMapper.selectList(new LambdaQueryWrapper<ArticleTag>().in(ArticleTag::getArticleId, list.stream().map(Article::getArticleId).toList()));
        // 标签映射
        Map<Long, String> tagMap = tagMapper.selectList(new LambdaQueryWrapper<Tag>().in(Tag::getTagId, articleTags.stream().map(ArticleTag::getArticleId).toList()))
                .stream().collect(Collectors.toMap(Tag::getTagId, Tag::getTagName));
        List<ArticleVO> articleVOS=list.stream().map(article->{
            ArticleVO articleVO=article.asViewObject(ArticleVO.class);
            articleVO.setCategoryName(categoryMap.get(article.getCategoryId()));
            articleVO.setTags(articleTags
                    .stream()
                    .filter(at-> Objects.equals(at.getArticleId(),article.getArticleId()))  // 过滤后的集合
                    .map(at->tagMap.get(at.getTagId()))
                    .toList());
            return articleVO;
        }).toList();
        if(hasKey){
            articleVOS=articleVOS.stream().peek(at->{

            }).toList();
        }

        return new PageVO<List<ArticleVO>>(articleVOS,page.getTotal());
    }
}
