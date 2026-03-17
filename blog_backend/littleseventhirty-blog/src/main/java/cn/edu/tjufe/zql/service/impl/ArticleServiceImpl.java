package cn.edu.tjufe.zql.service.impl;

import cn.edu.tjufe.zql.constants.RedisConst;
import cn.edu.tjufe.zql.constants.SQLConst;
import cn.edu.tjufe.zql.domain.dto.ArticleDTO;
import cn.edu.tjufe.zql.domain.dto.SearchArticleDTO;
import cn.edu.tjufe.zql.domain.entity.*;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
import cn.edu.tjufe.zql.domain.vo.*;
import cn.edu.tjufe.zql.enums.*;
import cn.edu.tjufe.zql.exception.FileUploadException;
import cn.edu.tjufe.zql.mapper.*;
import cn.edu.tjufe.zql.service.*;
import cn.edu.tjufe.zql.utils.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
    private FileUploadUtils fileUploadUtils;

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private RedisCache redisCache;

    @Resource
    private TagMapper tagMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private ILikeService likeService;

    @Resource
    private IFavoriteService favoriteService;

    @Resource
    private ICommentService commentService;

    @Resource
    private LikeMapper likeMapper;

    @Resource
    private FavoriteMapper favoriteMapper;

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private IArticleTagService articleTagService;

    @Override
    public List<InitSearchTitleVO> initSearchByTitle() {
        List<Article> articles = articleMapper
                .selectList(new LambdaQueryWrapper<Article>().eq(Article::getStatus, SQLConst.PUBLIC_APTICLE));
        Map<Long, String> categoryMap = categoryMapper.selectList(null).stream()
                .collect(Collectors.toMap(Category::getCategoryId, Category::getCategoryName));
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
        HttpServletRequest request = SecurityUtils.getCurrentHttpRequest();
        // key + id + ip + time(秒)
        String KEY = RedisConst.ARTICLE_VISIT_COUNT_LIMIT + id + ":" + IpUtils.getIpAddr(request);
        if (redisCache.getCacheObject(KEY) == null) {
            // 设置间隔时间
            redisCache.setCacheObject(KEY, 1, RedisConst.ARTICLE_VISIT_COUNT_INTERVAL, TimeUnit.SECONDS);

            if (redisCache.isHasKey(RedisConst.ARTICLE_VISIT_COUNT + id))
                redisCache.increment(RedisConst.ARTICLE_VISIT_COUNT + id, 1L);
            else
                redisCache.setCacheObject(RedisConst.ARTICLE_VISIT_COUNT + id, 0);
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
        Map<Long, String> categoryMap = categoryMapper.selectList(null).stream()
                .collect(Collectors.toMap(Category::getCategoryId, Category::getCategoryName));
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
                            "<mark>" + content + "</mark>");
                    vo.setArticleContent(StringUtils.stripMarkdown(hilighted));
                }
            }
            return searchArticleByContentVOList;
        }
        return List.of();
    }

    @Override
    public List<HotArticleVO> getHotArticles() {
        List<Article> hotArticles = articleMapper.selectList(new LambdaQueryWrapper<Article>()
                .eq(Article::getStatus, SQLConst.PUBLIC_APTICLE).orderByDesc(Article::getVisitedCount).last("LIMIT 5"));
        if (!hotArticles.isEmpty()) {
            return hotArticles.stream().map(article -> {
                return article.asViewObject(HotArticleVO.class);
            }).toList();
        }
        return List.of();
    }

    @Override
    public List<RandomArticleVO> getRandomArticles() {
        List<Article> randomArticles = articleMapper.selectRandomArticle(SQLConst.PUBLIC_APTICLE,
                SQLConst.RANDOM_ARTICLE_COUNT);
        if (!randomArticles.isEmpty()) {
            return randomArticles.stream().map(article -> article.asViewObject(RandomArticleVO.class)).toList();
        }
        return List.of();
    }

    @Override
    public List<RecommendArticleVO> getRecommendArticles() {
        List<Article> articles = articleMapper.selectList(new LambdaQueryWrapper<Article>()
                .eq(Article::getIsTop, SQLConst.RECOMMEND_ARTICLE).eq(Article::getStatus, SQLConst.PUBLIC_APTICLE));
        if (!articles.isEmpty()) {
            return articles.stream().map(article -> article.asViewObject(RecommendArticleVO.class)).toList();
        }
        return List.of();
    }

    @Override
    public ResponseResult<String> uploadArticleImage(MultipartFile articleImage) {
        try {
            String url = fileUploadUtils.upload(UploadEnum.ARTICLE_IMAGE, articleImage);
            if (StringUtils.isNotNull(url))
                return ResponseResult.success(url);
            else
                return ResponseResult.failure("上传格式错误");
        } catch (Exception e) {
            log.error("文章图片上传失败", e);
        }
        return null;
    }

    @Override
    public List<ArticleListVO> searchArticle(SearchArticleDTO searchArticleDTO) {
        // 构造查询条件
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotNull(searchArticleDTO.getArticleTitle()), Article::getArticleTitle, searchArticleDTO.getArticleTitle())
                .eq(StringUtils.isNotNull(searchArticleDTO.getCategoryId()), Article::getCategoryId, searchArticleDTO.getCategoryId())
                .eq(StringUtils.isNotNull(searchArticleDTO.getStatus()), Article::getStatus, searchArticleDTO.getStatus())
                .eq(StringUtils.isNotNull(searchArticleDTO.getIsTop()), Article::getIsTop, searchArticleDTO.getIsTop());
        // 搜索文章转换类型
        List<ArticleListVO> articleListVOS = articleMapper.selectList(wrapper).stream().map(article -> article.asViewObject(ArticleListVO.class)).toList();
        if (!articleListVOS.isEmpty()) {
            articleListVOS.forEach(articleListVO -> {
                articleListVO.setCategoryName(categoryMapper.selectById(articleListVO.getCategoryId()).getCategoryName());
                articleListVO.setUserName(userMapper.selectById(articleListVO.getUserId()).getUsername());
                // 查询文章标签
                List<Long> tagIds = articleTagMapper.selectList(new LambdaQueryWrapper<ArticleTag>().eq(ArticleTag::getArticleId, articleListVO.getArticleId())).stream().map(ArticleTag::getTagId).toList();
                articleListVO.setTagsName(tagMapper.selectBatchIds(tagIds).stream().map(Tag::getTagName).toList());
            });
            return articleListVOS;
        }
        return null;
    }

    @Override
    public PageVO<List<ArticleVO>> allArticleList(Integer pageNum, Integer pageSize) {
        // 文章
        boolean hasKey = redisCache.isHasKey(RedisConst.ARTICLE_COMMENT_COUNT)
                && redisCache.isHasKey(RedisConst.ARTICLE_FAVORITE_COUNT)
                && redisCache.isHasKey(RedisConst.ARTICLE_LIKE_COUNT);
        IPage<Article> page = new Page<>(pageNum, pageSize);
        this.page(page, new LambdaQueryWrapper<Article>().eq(Article::getStatus, SQLConst.PUBLIC_APTICLE)
                .orderByDesc(Article::getCreateTime));
        List<Article> list = page.getRecords();
        // 分类映射
        Map<Long, String> categoryMap = categoryMapper
                .selectList(new LambdaQueryWrapper<Category>().in(Category::getCategoryId,
                        list.stream().map(Article::getCategoryId).toList()))
                .stream().collect(Collectors.toMap(Category::getCategoryId, Category::getCategoryName));
        // 关联Article和Tag表
        List<ArticleTag> articleTags = articleTagMapper.selectList(new LambdaQueryWrapper<ArticleTag>()
                .in(ArticleTag::getArticleId, list.stream().map(Article::getArticleId).toList()));
        // 标签映射
        Map<Long, String> tagMap = tagMapper
                .selectList(new LambdaQueryWrapper<Tag>().in(Tag::getTagId,
                        articleTags.stream().map(ArticleTag::getArticleId).toList()))
                .stream().collect(Collectors.toMap(Tag::getTagId, Tag::getTagName));
        List<ArticleVO> articleVOS = list.stream().map(article -> {
            ArticleVO articleVO = article.asViewObject(ArticleVO.class);
            articleVO.setCategoryName(categoryMap.get(article.getCategoryId()));
            articleVO.setTags(articleTags
                    .stream()
                    .filter(at -> Objects.equals(at.getArticleId(), article.getArticleId())) // 过滤后的集合
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
        List<Article> articles = articleMapper.selectList(new LambdaQueryWrapper<Article>()
                .eq(Article::getStatus, SQLConst.PUBLIC_APTICLE)
                .and(wrapper -> wrapper.eq(Article::getCategoryId, categoryId))
                .ne(Article::getArticleId, articleId));
        List<Article> limitArticles = articles.stream().limit(SQLConst.RELATED_ARTICLE_COUNT).toList();
        return limitArticles.stream().map(article -> article.asViewObject(RelatedArticleVO.class)).toList();
    }

    @Override
    public ResponseResult<String> uploadArticleCover(MultipartFile articleCover) {
        try {
            System.out.println(articleCover.getOriginalFilename());
            String articleCoverUrl = null;
            try {
                articleCoverUrl = fileUploadUtils.upload(UploadEnum.ARTICLE_COVER, articleCover);
            } catch (FileUploadException e) {
                return ResponseResult.failure(e.getMessage());
            }
            if (StringUtils.isNotNull(articleCoverUrl))
                return ResponseResult.success(articleCoverUrl);
            else
                return ResponseResult.failure("上传格式错误");
        } catch (Exception e) {
            log.error("文章封面上传失败", e);
            return ResponseResult.failure();
        }
    }

    // 出现异常，自动回滚，数据库回滚
    @Transactional
    @Override
    public ResponseResult<Void> publish(ArticleDTO articleDTO) {
        Article article = articleDTO.asViewObject(Article.class, v -> v.setUserId(SecurityUtils.getUserId()));
        if (this.saveOrUpdate(article)) {
            // 清除标签关系
            articleTagMapper.deleteById(article.getArticleId());
            // 新增标签关系
            List<ArticleTag> articleTags = articleDTO.getTagId().stream().map(articleTag -> ArticleTag.builder().articleId(article.getArticleId()).tagId(articleTag).build()).toList();
            articleTagService.saveBatch(articleTags);
            return ResponseResult.success();
        }
        return ResponseResult.failure();
    }

    @Value("${aliyun.oss.urlPrefix}")
    private String urlPrefix;

    @Override
    public ResponseResult<Void> deleteArticleCover(String articleCoverUrl) {
        try {
            // 提取图片名称
            String articleCoverName = articleCoverUrl.replace(urlPrefix + "/", "");
            fileUploadUtils.deleteFiles(List.of(articleCoverName));
            return ResponseResult.success();
        } catch (Exception e) {
            log.error("删除文章封面失败", e);
            return ResponseResult.failure();
        }
    }

    @Override
    public List<TimelineVO> listTimeline() {
        List<Article> articles = this.query().list();
        if (null != articles) {
            return articles.stream().map(article -> article.asViewObject(TimelineVO.class)).toList();
        }
        return List.of();
    }

    @Override
    public List<CategoryArticleVO> getCategoryArticleList(Integer type, Integer typeId) {
        List<Article> articles;
        if (type == 1) {
            articles = articleMapper.selectList(new LambdaQueryWrapper<Article>().eq(Article::getCategoryId, typeId));
        } else if (type == 2) {
            // 获取所有有typeid关键词的tag文章列表，将各自的文章id汇总成列表
            List<Long> articleIds = articleTagMapper
                    .selectList(new LambdaQueryWrapper<ArticleTag>().eq(ArticleTag::getTagId, typeId)).stream()
                    .map(ArticleTag::getArticleId).toList();
            // 获取所有文章
            if (!articleIds.isEmpty()) {
                articles = articleMapper.selectByIds(articleIds);
            } else
                articles = List.of();
        } else
            articles = List.of();
        if (Objects.isNull(articles) || articles.isEmpty())
            return List.of();
        // 获取文章列表包含的所有tag部分信息集合体
        List<ArticleTag> articleTags = articleTagMapper
                .selectByIds(articles.stream().map(Article::getArticleId).toList());
        // 获取对应tag实体
        List<Tag> tags = tagMapper.selectByIds(articleTags.stream().map(ArticleTag::getArticleId).toList());

        return articles.stream().map(article -> article.asViewObject(CategoryArticleVO.class, item -> {
            item.setCategoryId(
                    articles.stream().filter(art -> Objects.equals(art.getArticleId(), article.getArticleId()))
                            .findFirst().orElseThrow().getCategoryId());
            item.setTags(tags.stream()
                    .filter(tag -> articleTags.stream()
                            .anyMatch(articleTag -> Objects.equals(articleTag.getArticleId(), article.getArticleId())
                                    && Objects.equals(articleTag.getTagId(), tag.getTagId())))
                    .map(tag -> tag.asViewObject(TagVO.class)).toList());
        })).toList();
    }

    @Override
    public ArticleDetailVO getArticleDetail(Integer id) {
        Long articleId = id.longValue();
        Article article = articleMapper.selectById(articleId);
        if (StringUtils.isNull(article) || article.getStatus() != SQLConst.PUBLIC_ARTICLE)
            return null;

        return article.asViewObject(ArticleDetailVO.class, vo -> {
            vo.setCategoryName("未分类");
            vo.setCategoryId(0L);
            vo.setTags(List.of());
            vo.setCommentCount(0L);
            vo.setLikeCount(0L);
            vo.setFavoriteCount(0L);
            vo.setPreArticleId(0L);
            vo.setPreArticleTitle("");
            vo.setNextArticleId(0L);
            vo.setNextArticleTitle("");
        });
    }

    // 后台问文章列表
    @Override
    public List<ArticleListVO> listArticle() {
        // 按时间排序获取所有文章，转为vo
        List<ArticleListVO> articleListVOS = articleMapper.selectList(new LambdaQueryWrapper<Article>()
                .orderByDesc(Article::getCreateTime)).stream().map(article -> article.asViewObject(ArticleListVO.class)).toList();
        if (!articleListVOS.isEmpty()) {
            articleListVOS.forEach(articleListVO -> {
                articleListVO.setCategoryName(categoryMapper.selectById(articleListVO.getCategoryId()).getCategoryName());
                articleListVO.setUserName(userMapper.selectById(articleListVO.getUserId()).getUsername());
                // 查询文章标签
                List<Long> tagIds = articleTagMapper.selectList(new LambdaQueryWrapper<ArticleTag>().eq(ArticleTag::getArticleId, articleListVO.getArticleId())).stream().map(ArticleTag::getTagId).toList();
                articleListVO.setTagsName(tagMapper.selectBatchIds(tagIds).stream().map(Tag::getTagName).toList());
            });
            return articleListVOS;
        }
        return null;
    }

    @Override
    public ResponseResult<Void> updateStatus(Long id, Integer status) {
        if (this.update(new LambdaUpdateWrapper<Article>().eq(Article::getArticleId, id).set(Article::getStatus, status))) {
            return ResponseResult.success();
        }
        return ResponseResult.failure();
    }

    @Override
    public ResponseResult<Void> updateIsTop(Long id, Integer isTop) {
        if (this.update(new LambdaUpdateWrapper<Article>().eq(Article::getArticleId, id).set(Article::getIsTop, isTop))) {
            return ResponseResult.success();
        }
        return ResponseResult.failure();
    }

    @Override
    public ArticleDTO getArticleDTO(Long id) {
        ArticleDTO articleDTO = articleMapper.selectById(id).asViewObject(ArticleDTO.class);
        if (StringUtils.isNotNull(articleDTO)) {
            // 查询文章标签
            List<Long> tagIds = articleTagMapper.selectList(new LambdaQueryWrapper<ArticleTag>().eq(ArticleTag::getArticleId, articleDTO.getArticleId())).stream().map(ArticleTag::getTagId).toList();
            articleDTO.setTagId(tagMapper.selectBatchIds(tagIds).stream().map(Tag::getTagId).toList());
            return articleDTO;
        }
        return null;
    }

    @Transactional
    @Override
    public ResponseResult<Void> deleteArticle(List<Long> ids) {
        try {
            boolean deleteResult = this.removeByIds(ids);
            if (deleteResult) {
                // 删除标签关系
                articleTagMapper.delete(new LambdaQueryWrapper<ArticleTag>().in(ArticleTag::getArticleId, ids));
                // 删除点赞、收藏、评论
                likeMapper.delete(new LambdaQueryWrapper<Like>().eq(Like::getType, LikeEnum.LIKE_TYPE_ARTICLE.getType()).and(a -> a.in(Like::getTypeId, ids)));
                favoriteMapper.delete(new LambdaQueryWrapper<Favorite>().eq(Favorite::getType, FavoriteEnum.FAVORITE_TYPE_ARTICLE.getType()).and(a -> a.in(Favorite::getTypeId, ids)));
                commentMapper.delete(new LambdaQueryWrapper<Comment>().eq(Comment::getType, CommentEnum.COMMENT_TYPE_ARTICLE.getType()).and(a -> a.in(Comment::getTypeId, ids)));
                return ResponseResult.success();
            }
            return ResponseResult.failure();
        } catch (Exception e) {
            System.out.println(e);
            return ResponseResult.failure();
        }
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
