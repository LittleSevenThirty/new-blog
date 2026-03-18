package cn.edu.tjufe.zql;


import cn.edu.tjufe.zql.service.IArticleService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 *
 * @authro 钟奇林
 * @description 公共测试类
 * @date 2026/3/6
 * @github https://github.com/little-seven-thirty
 */
@SpringBootTest
public class PublicTest {
    @Resource
    private IArticleService articleService;

    @Test
    public void testDeleteArticle(){
        boolean result=articleService.removeByIds(List.of(2033471031640932354L));
        System.out.println(result);
    }
}
