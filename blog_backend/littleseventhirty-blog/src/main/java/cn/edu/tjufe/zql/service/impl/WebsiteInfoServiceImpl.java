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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: littleseventhirty
 * @description:
 * @date: 2026/1/8-17:25
 **/
@Slf4j
@Service(value = "websiteInfoService")
public class WebsiteInfoServiceImpl extends ServiceImpl<WebsiteInfoMapper, WebsiteInfo> implements IWebsiteInfoService {
    @Resource
    private WebsiteInfoMapper websiteInfoMapper; // 网站信息映射类

    @Resource
    private ArticleMapper articleMapper; // 文章映射类
    @Resource
    private CategoryMapper categoryMapper;
    @Resource
    private CommentMapper commentMapper;

    @Override
    public WebsiteInfoVO getWebsiteInfo() {
        WebsiteInfoVO websiteInfoVO = this.getById(WebsiteInfoConst.WEBSITE_INFO_ID).asViewObject(WebsiteInfoVO.class);
        // 运行时长
        if (StringUtils.isNotNull(websiteInfoVO)) {
            if (articleMapper.selectCount(null) <= 0)
                return websiteInfoVO;
            LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
            wrapper.select(Article::getUpdateTime).orderByDesc(Article::getUpdateTime).last(SQLConst.LIMIT_ONE_SQL);
            websiteInfoVO.setLastUpdateTime(articleMapper.selectOne(wrapper).getUpdateTime());
            websiteInfoVO.setArticleCount(articleMapper.selectCount(null));
            List<String> listArticleContent = articleMapper.selectList(null).stream().map(Article::getArticleContent)
                    .toList();
            // 合成一个string
            String mergedString = String.join("", listArticleContent);
            websiteInfoVO.setWordCount((long) extractTextFromMarkdown(mergedString).length());
            wrapper.clear();
            wrapper.select(Article::getVisitedCount);
            websiteInfoVO.setVisitedCount(articleMapper.selectObjs(wrapper).stream().mapToLong(visitCount -> {
                if (visitCount instanceof java.math.BigInteger) {
                    return ((java.math.BigInteger) visitCount).longValue();
                } else if (visitCount instanceof Long) {
                    return (Long) visitCount;
                } else if (visitCount instanceof Integer) {
                    return ((Integer) visitCount).longValue();
                } else {
                    return 0;
                }
            }).sum());
            websiteInfoVO.setCategoryCount(categoryMapper.selectCount(null));
            websiteInfoVO.setCommentCount(commentMapper.selectCount(null));
            return websiteInfoVO;
        }
        return null;
    }

    /**
     * 从Markdown文档中提取文字内容
     *
     * @param markdownContent Markdown文档内容
     * @return 文字内容
     */
    private static String extractTextFromMarkdown(String markdownContent) {
        // 使用正则表达式匹配Markdown文档中的文字内容，并去掉空格
        Pattern pattern = Pattern.compile("[^#>\\*\\[\\]`\\s]+");
        Matcher matcher = pattern.matcher(markdownContent);

        StringBuilder result = new StringBuilder();
        while (matcher.find()) {
            result.append(matcher.group()).append("\n");
        }

        return result.toString().trim();
    }
}
