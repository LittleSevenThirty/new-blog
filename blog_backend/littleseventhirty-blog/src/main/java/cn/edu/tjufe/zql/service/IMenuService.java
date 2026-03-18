package cn.edu.tjufe.zql.service;


import cn.edu.tjufe.zql.domain.dto.MenuDTO;
import cn.edu.tjufe.zql.domain.entity.Menu;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
import cn.edu.tjufe.zql.domain.vo.MenuByIdVO;
import cn.edu.tjufe.zql.domain.vo.MenuVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/3/17
 * @github https://github.com/little-seven-thirty
 */
public interface IMenuService extends IService<Menu> {
    /**
     * 获取菜单列表,0:系统菜单，1:菜单管理
     *
     * @return 菜单列表
     */
    ResponseResult<List<MenuVO>> getMenuList(Integer typeId, String username, Integer status);

    /**
     * 添加菜单
     *
     * @param menuDTO 菜单信息
     * @return 添加菜单的结果
     */
    ResponseResult<Void> addMenu(MenuDTO menuDTO);

    /**
     * 修改菜单，id回滚
     *
     * @param id 菜单id
     * @return 数据
     */
    ResponseResult<MenuByIdVO> selectUpdateMenu(Long id);

    /**
     * 修改菜单
     *
     * @param menuDTO 菜单信息
     * @return 是否成功
     */
    ResponseResult<Void> updateMenu(MenuDTO menuDTO);

    /**
     * 根据id删除菜单
     *
     * @param id 对应的id
     * @return 是否成功
     */
    ResponseResult<String> deleteMenu(Long id);

}
