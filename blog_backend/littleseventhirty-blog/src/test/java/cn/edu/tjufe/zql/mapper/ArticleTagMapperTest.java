package cn.edu.tjufe.zql.mapper;

import cn.edu.tjufe.zql.domain.entity.ArticleTag;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ArticleTagMapperTest {
    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Test
    public void testSelectById() {
        // 文章关系
        List<ArticleTag> articleTags = articleTagMapper.selectList(new LambdaQueryWrapper<ArticleTag>().eq(ArticleTag::getArticleId, 53));
        System.out.println(articleTags);
    }
}