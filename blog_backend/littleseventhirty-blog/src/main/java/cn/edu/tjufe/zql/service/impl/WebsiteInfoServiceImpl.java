package cn.edu.tjufe.zql.service.impl;

import cn.edu.tjufe.zql.constants.SQLConst;
import cn.edu.tjufe.zql.constants.WebsiteInfoConst;
import cn.edu.tjufe.zql.domain.entity.Article;
import cn.edu.tjufe.zql.domain.entity.WebsiteInfo;
import cn.edu.tjufe.zql.domain.vo.WebsiteInfoVO;
import cn.edu.tjufe.zql.mapper.ArticleMapper;
import cn.edu.tjufe.zql.mapper.CategoryMapper;
import cn.edu.tjufe.zql.mapper.CommentMapper;
import cn.edu.tjufe.zql.mapper.WebsiteInfoMapper;
import cn.edu.tjufe.zql.service.IWebsiteInfoService;
import cn.edu.tjufe.zql.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: littleseventhirty
 * @description:
 * @date: 2026/1/8-17:25
 **/
@Slf4j
@Service(value = "websiteInfoService")
public class WebsiteInfoServiceImpl extends ServiceImpl<WebsiteInfoMapper, WebsiteInfo> implements IWebsiteInfoService {
    @Resource
    private WebsiteInfoMapper websiteInfoMapper;    // 网站信息映射类

    @Resource
    private ArticleMapper articleMapper;    // 文章映射类
    @Resource
    private CategoryMapper categoryMapper;
    @Resource
    private CommentMapper commentMapper;

    @Override
    public WebsiteInfoVO getWebsiteInfo() {
        WebsiteInfo websiteInfoEntity = websiteInfoMapper.selectById(WebsiteInfoConst.WEBSITE_INFO_ID);
        if (websiteInfoEntity == null) {
            // 如果没有网站信息记录，返回一个默认的WebsiteInfoVO
            return new WebsiteInfoVO();
        }
        
        WebsiteInfoVO websiteInfo = websiteInfoEntity.asViewObject(WebsiteInfoVO.class);
        Long articleCount = articleMapper.selectCount(null);
        if (articleCount > 0) {
            LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
            // 挑选最近更新文章的更新时间作为最近更新时间
            wrapper.select(Article::getUpdateTime).orderByDesc(Article::getUpdateTime).last(SQLConst.LIMIT_ONE_SQL);
            Article latestArticle = articleMapper.selectOne(wrapper);
            if (latestArticle != null) {
                websiteInfo.setLastUpdateTime(latestArticle.getUpdateTime());
            }
            websiteInfo.setArticleCount(articleCount);  // 设置文章数目
            // 获取所有文章内容
            List<String> articleContentList = articleMapper.selectList(null).stream().map(Article::getArticleContent).toList();
            // 合并字符串，并去除特殊格式符号，计算字数
            String llString = String.join("", articleContentList);
            websiteInfo.setWordCount((long) StringUtils.stripMarkdown(llString).trim().length());
            // 条件构造器清空，重新构造，
            wrapper.clear();
            wrapper.select(Article::getVisitedCount);
            // 获取访问数量
            websiteInfo.setVisitedCount(articleMapper.selectObjs(wrapper).stream().mapToLong(visitedCount -> (long) visitedCount).sum());
        }
        // 获取分类数量
        websiteInfo.setCategoryCount(categoryMapper.selectCount(null));
        // 获取评论数量
        websiteInfo.setCommentCount(commentMapper.selectCount(null));
        return websiteInfo;
    }
}
