package cn.edu.tjufe.zql.service.impl;

import cn.edu.tjufe.zql.domain.entity.Category;
import cn.edu.tjufe.zql.mapper.CategoryMapper;
import cn.edu.tjufe.zql.service.ICategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author: littleseventhirty
 * @description:
 * @date: 2025/12/15-16:44
 **/
@Slf4j
@Service(value="categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {
}
