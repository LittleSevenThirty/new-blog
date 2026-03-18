package cn.edu.tjufe.zql.service.impl;


import cn.edu.tjufe.zql.domain.entity.RoleMenu;
import cn.edu.tjufe.zql.mapper.RoleMenuMapper;
import cn.edu.tjufe.zql.service.IRoleMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/3/11
 * @github https://github.com/little-seven-thirty
 */
@Service("roleMenuService")
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements IRoleMenuService {
}
