package cn.edu.tjufe.zql.service.impl;

import cn.edu.tjufe.zql.constants.SQLConst;
import cn.edu.tjufe.zql.domain.entity.Article;
import cn.edu.tjufe.zql.domain.entity.Category;
import cn.edu.tjufe.zql.domain.vo.SearchArticleByContentVO;
import cn.edu.tjufe.zql.mapper.ArticleMapper;
import cn.edu.tjufe.zql.mapper.CategoryMapper;
import cn.edu.tjufe.zql.service.IArticleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author: littleseventhirty
 * @description:
 * @date: 2025/12/12-11:44
 **/
@Slf4j
@Service(value="articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {
    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public List<SearchArticleByContentVO> searchArticleByContent(String content) {
        // 从数据库中查询相关对应文章
        List<Article> articles=articleMapper.selectList(
                new LambdaQueryWrapper<Article>()
                        .like(Article::getArticleContent,content)
                        .eq(Article::getStatus, SQLConst.PUBLIC_APTICLE));
        // 再从数据库中查询相关分类
        Map<String,String> categoryMap=categoryMapper.selectList(null).stream().collect(Collectors.toMap(Category::getCategoryId,Category::getCategoryName));
        // 将对应数据存放到对应实体中
        if(!articles.isEmpty()){
            List<SearchArticleByContentVO> searchArticleByContentVOList=articles.stream().map(article->{
                return article.asViewObject(SearchArticleByContentVO.class,vo->{
                    vo.setCategoryName(categoryMap.get(article.getCategoryId()));
                });
            }).toList();
            int index=-1;
            for(SearchArticleByContentVO vo:searchArticleByContentVOList){
                String articleContent=vo.getArticleContent();
                index=articleContent.toLowerCase().indexOf(content.toLowerCase());
                if(index!=-1){
                    int start=Math.min(0,index-10);
                    int end=Math.min(articleContent.length(),index+content.length()+10);
                    String snippet=articleContent.substring(start,end);
                    String hilighted=snippet.replaceFirst(
                            "(?i)"+ Pattern.quote(content),
                            "<mark>"+content+"</mark>"
                    );
                    vo.setArticleContent(stripMarkdown(hilighted));
                }
            }
            return searchArticleByContentVOList;
        }
        return List.of();
    }

    /**
     * 去除字符串中markdown格式
     * @param markdown
     * @return 纯文本字符串
     */
    public static String stripMarkdown(String markdown) {
        if (markdown == null || markdown.isEmpty()) {
            return markdown;
        }
        return markdown.replaceAll("(?m)^#+\\s+", "")   // 去除标题
                .replaceAll("(?s)```.*?```", "")    // 去除代码块（多行）
                .replaceAll("`[^`]*`", "")  // 去掉行内代码
                .replaceAll("!?\\[(.*?)\\]\\(.*?\\)", "$1") // 去掉链接和图片
                .replaceAll("(\\*\\*|__)(.*?)\\1", "$2")    //去除粗体和斜体
                .replaceAll("(\\*|_)(.*?)\\1", "$2")
                .replaceAll("(?m)^[\\s]*[-*+]\\s+", "") // 去除无序列表标识
                .replaceAll("(?m)^[\\s]*\\d+\\.\\s+", "")    // 去除有序列表标识
                .replaceAll("(?m)^\\s*>\\s*", "")    // 去除引用
                .replaceAll("(?m)^[-*_]{3,}\\s*$", "");   // 去除水平线
                // 去除HTML标签（如果需要）replaceAll("<[^>]*>", "");
    }
}
