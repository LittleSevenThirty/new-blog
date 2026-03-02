package cn.edu.tjufe.zql.service.impl;

import cn.edu.tjufe.zql.constants.RedisConst;
import cn.edu.tjufe.zql.constants.SQLConst;
import cn.edu.tjufe.zql.domain.entity.*;
import cn.edu.tjufe.zql.domain.vo.*;
import cn.edu.tjufe.zql.enums.CommentEnum;
import cn.edu.tjufe.zql.enums.CountTypeEnum;
import cn.edu.tjufe.zql.enums.FavoriteEnum;
import cn.edu.tjufe.zql.enums.LikeEnum;
import cn.edu.tjufe.zql.mapper.ArticleMapper;
import cn.edu.tjufe.zql.mapper.ArticleTagMapper;
import cn.edu.tjufe.zql.mapper.CategoryMapper;
import cn.edu.tjufe.zql.mapper.TagMapper;
import cn.edu.tjufe.zql.service.IArticleService;
import cn.edu.tjufe.zql.service.ICommentService;
import cn.edu.tjufe.zql.service.IFavoriteService;
import cn.edu.tjufe.zql.service.ILikeService;
import cn.edu.tjufe.zql.utils.IpUtils;
import cn.edu.tjufe.zql.utils.RedisCache;
import cn.edu.tjufe.zql.utils.SecurityUtils;
import cn.edu.tjufe.zql.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
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

    @Resource
    private ILikeService likeService;

    @Resource
    private IFavoriteService favoriteService;

    @Resource
    private ICommentService commentService;

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
    public void addVisitCount(Long id) {
        // 访问量去重
        HttpServletRequest request= SecurityUtils.getCurrentHttpRequest();
        // key + id + ip + time(秒)
        String KEY = RedisConst.ARTICLE_VISIT_COUNT_LIMIT + id + ":" + IpUtils.getIpAddr(request);
        if(redisCache.getCacheObject(KEY) == null){
            // 设置间隔时间
            redisCache.setCacheObject(KEY, 1, RedisConst.ARTICLE_VISIT_COUNT_INTERVAL, TimeUnit.SECONDS);

            if (redisCache.isHasKey(RedisConst.ARTICLE_VISIT_COUNT + id))
                redisCache.increment(RedisConst.ARTICLE_VISIT_COUNT + id, 1L);
            else redisCache.setCacheObject(RedisConst.ARTICLE_VISIT_COUNT + id, 0);
        }
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
        List<ArticleVO> articleVOS = list.stream().map(article -> {
            ArticleVO articleVO = article.asViewObject(ArticleVO.class);
            articleVO.setCategoryName(categoryMap.get(article.getCategoryId()));
            articleVO.setTags(articleTags
                    .stream()
                    .filter(at -> Objects.equals(at.getArticleId(), article.getArticleId()))  // 过滤后的集合
                    .map(at -> tagMap.get(at.getTagId()))
                    .toList());
            return articleVO;
        }).toList();
        if (hasKey) {
            articleVOS = articleVOS.stream().peek(articleVO -> {
                setRedisCache(articleVO, RedisConst.ARTICLE_COMMENT_COUNT, CountTypeEnum.COMMENT);
                setRedisCache(articleVO, RedisConst.ARTICLE_FAVORITE_COUNT, CountTypeEnum.FAVORITE);
                setRedisCache(articleVO, RedisConst.ARTICLE_LIKE_COUNT, CountTypeEnum.LIKE);
            }).toList();
        }

        return new PageVO<List<ArticleVO>>(articleVOS, page.getTotal());
    }

    @Override
    public List<RelatedArticleVO> getRelatedArticles(Integer categoryId, Integer articleId) {
        // 传回来的文章id不一定就是数据库里的文章id
        List<Article> articles=articleMapper.selectList(new LambdaQueryWrapper<Article>()
                .eq(Article::getStatus, SQLConst.PUBLIC_APTICLE)
                .and(wrapper->wrapper.eq(Article::getCategoryId, categoryId))
                .ne(Article::getArticleId, articleId)
        );
        List<Article> limitArticles=articles.stream().limit(SQLConst.RELATED_ARTICLE_COUNT).toList();
        return limitArticles.stream().map(article->article.asViewObject(RelatedArticleVO.class)).toList();
    }

    @Override
    public List<TimelineVO> listTimeline() {
        List<Article> articles=this.query().list();
        if(null!=articles){
            return articles.stream().map(article->article.asViewObject(TimelineVO.class)).toList();
        }
        return List.of();
    }

    @Override
    public List<CategoryArticleVO> getCategoryArticleList(Integer type, Integer typeId) {
        List<Article> articles;
        if(type==1){
            articles=articleMapper.selectList(new LambdaQueryWrapper<Article>().eq(Article::getCategoryId,typeId));
        }else if(type==2){
            // 获取所有有typeid关键词的tag文章列表，将各自的文章id汇总成列表
            List<Long> articleIds=articleTagMapper.selectList(new LambdaQueryWrapper<ArticleTag>().eq(ArticleTag::getTagId,typeId)).stream().map(ArticleTag::getArticleId).toList();
            // 获取所有文章
            if(!articleIds.isEmpty()){
                articles=articleMapper.selectByIds(articleIds);
            }
            else articles=List.of();
        }else articles=List.of();
        if(Objects.isNull(articles)||articles.isEmpty())return List.of();
        // 获取文章列表包含的所有tag部分信息集合体
        List<ArticleTag> articleTags=articleTagMapper.selectByIds(articles.stream().map(Article::getArticleId).toList());
        // 获取对应tag实体
        List<Tag> tags=tagMapper.selectByIds(articleTags.stream().map(ArticleTag::getArticleId).toList());

        return articles.stream().map(article->article.asViewObject(CategoryArticleVO.class,item->{
            item.setCategoryId(articles.stream().filter(art -> Objects.equals(art.getId(), article.getId())).findFirst().orElseThrow().getCategoryId());
            item.setTags(tags.stream().filter(tag -> articleTags.stream().anyMatch(articleTag -> Objects.equals(articleTag.getArticleId(), article.getArticleId()) && Objects.equals(articleTag.getTagId(), tag.getTagId()))).map(tag -> tag.asViewObject(TagVO.class)).toList());
        })).toList();
    }

    @Override
    public ArticleDetailVO getArticleDetail(Integer id) {
        Article article = articleMapper.selectOne(new LambdaQueryWrapper<Article>().eq(Article::getStatus, SQLConst.PUBLIC_ARTICLE).and(i -> i.eq(Article::getId, id)));
        if (StringUtils.isNull(article)) return null;
        // 文章分类
        Category category = categoryMapper.selectById(article.getCategoryId());
        // 文章关系
        List<ArticleTag> articleTags = articleTagMapper.selectList(new LambdaQueryWrapper<ArticleTag>().eq(ArticleTag::getArticleId, article.getId()));
        // 标签
        List<Tag> tags = tagMapper.selectBatchIds(articleTags.stream().map(ArticleTag::getTagId).toList());
        // 当前文章的上一篇文章与下一篇文章,大于当前文章的最小文章与小于当前文章的最大文章
        LambdaQueryWrapper<Article> preAndNextWrapper = new LambdaQueryWrapper<>();
        preAndNextWrapper.lt(Article::getId, id);
        Article preArticle = articleMapper.selectOne(preAndNextWrapper.orderByDesc(Article::getId).last(SQLConst.LIMIT_ONE_SQL));
        preAndNextWrapper.clear();
        preAndNextWrapper.gt(Article::getId, id);
        Article nextArticle = articleMapper.selectOne(preAndNextWrapper.orderByAsc(Article::getId).last(SQLConst.LIMIT_ONE_SQL));

        return article.asViewObject(ArticleDetailVO.class, vo -> {
            vo.setCategoryName(category.getCategoryName());
            vo.setCategoryId(category.getId());
            vo.setTags(tags.stream().map(tag -> tag.asViewObject(TagVO.class)).toList());
            vo.setCommentCount(commentService.count(new LambdaQueryWrapper<Comment>().eq(Comment::getTypeId, article.getId()).eq(Comment::getType, CommentEnum.COMMENT_TYPE_ARTICLE.getType())));
            vo.setLikeCount(likeService.count(new LambdaQueryWrapper<Like>().eq(Like::getTypeId, article.getId()).eq(Like::getType, LikeEnum.LIKE_TYPE_ARTICLE.getType())));
            vo.setFavoriteCount(favoriteService.count(new LambdaQueryWrapper<Favorite>().eq(Favorite::getTypeId, article.getId()).eq(Favorite::getType, FavoriteEnum.FAVORITE_TYPE_ARTICLE.getType())));
            vo.setPreArticleId(preArticle == null ? 0 : preArticle.getId());
            vo.setPreArticleTitle(preArticle == null ? "" : preArticle.getArticleTitle());
            vo.setNextArticleId(nextArticle == null ? 0 : nextArticle.getId());
            vo.setNextArticleTitle(nextArticle == null ? "" : nextArticle.getArticleTitle());
        });
    }

    private <T> void setRedisCache(ArticleVO articleVO, String redisKey, CountTypeEnum countType) {
        String articleId = articleVO.getArticleId().toString();
        Object countObj = redisCache.getCacheMap(redisKey).get(articleId);
        long count = 0l;
        if (countObj != null) {
            count = Long.parseLong(countObj.toString());
        } else {
            redisCache.setCacheMap(redisKey, Map.of(articleId, 0));
        }

        if (countType.equals(CountTypeEnum.COMMENT)) {
            articleVO.setCommmentCount(count);
        } else if (countType.equals(CountTypeEnum.FAVORITE)) {
            articleVO.setFavoriteCount(count);
        } else if (countType.equals(CountTypeEnum.LIKE)) {
            articleVO.setLikeCount(count);
        }
    }
}
