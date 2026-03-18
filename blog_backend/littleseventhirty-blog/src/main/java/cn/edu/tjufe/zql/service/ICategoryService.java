package cn.edu.tjufe.zql.service;

import cn.edu.tjufe.zql.domain.dto.CategoryDTO;
import cn.edu.tjufe.zql.domain.dto.SearchCategoryDTO;
import cn.edu.tjufe.zql.domain.entity.Category;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
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
     * 添加分类
     * @param categoryDTO 分类
     * @return 是否成功
     */
    ResponseResult<Void> addCategory(CategoryDTO categoryDTO);

    /**
     *查询所有分类
     * @return
     */
    public List<CategoryVO> listAllCategory();

    /**
     * 搜索分类
     * @param searchCategoryDTO 搜索标签DTO
     * @return 分类列表
     */
    List<CategoryVO> searchCategory(SearchCategoryDTO searchCategoryDTO);

    /**
     * 根据id查询
     * @param id id
     * @return 标签
     */
    CategoryVO getCategoryById(Long id);

    /**
     * 新增或修改标签
     * @param categoryDTO 标签DTO
     * @return 是否成功
     */
    ResponseResult<Void> addOrUpdateCategory(CategoryDTO categoryDTO);

    /**
     * 根据id删除
     * @param ids id
     * @return 是否成功
     */
    ResponseResult<Void> deleteCategoryByIds(List<Long> ids);
}
