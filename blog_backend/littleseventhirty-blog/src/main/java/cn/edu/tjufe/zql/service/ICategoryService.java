package cn.edu.tjufe.zql.service;

import cn.edu.tjufe.zql.domain.entity.Category;
import cn.edu.tjufe.zql.domain.vo.CategoryVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author: littleseventhirty
 * @description:
 * @date: 2025/12/15-16:43
 **/
public interface ICategoryService extends IService<Category> {
    /**
     *查询所有分类
     * @return
     */
    public List<CategoryVO> listAllCategory();
}
