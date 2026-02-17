package cn.edu.tjufe.zql.service.impl;

import cn.edu.tjufe.zql.domain.entity.Article;
import cn.edu.tjufe.zql.domain.entity.Category;
import cn.edu.tjufe.zql.domain.vo.CategoryVO;
import cn.edu.tjufe.zql.mapper.ArticleMapper;
import cn.edu.tjufe.zql.mapper.CategoryMapper;
import cn.edu.tjufe.zql.service.ICategoryService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: littleseventhirty
 * @description:
 * @date: 2025/12/15-16:44
 **/
@Slf4j
@Service(value="categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {
    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private CategoryMapper categoryMapper;


    public List<CategoryVO> listAllCategory(){
        List<Category> categories=this.query().list();
        if(categories!=null&&categories.size()>0){
            return categories.stream().map(category-> category.asViewObject(CategoryVO.class,(item)->{
                item.setArticleCount(articleMapper.selectCount(new LambdaQueryWrapper<Article>().eq(Article::getCategoryId,category.getCategoryId())));
            })).toList();
        }
        return List.of();
    }
}
