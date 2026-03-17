package cn.edu.tjufe.zql.controller;

import cn.edu.tjufe.zql.annotation.AccessLimit;
import cn.edu.tjufe.zql.annotation.LogAnnotation;
import cn.edu.tjufe.zql.constants.LogConst;
import cn.edu.tjufe.zql.domain.dto.MenuDTO;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
import cn.edu.tjufe.zql.domain.vo.MenuByIdVO;
import cn.edu.tjufe.zql.domain.vo.MenuVO;
import cn.edu.tjufe.zql.domain.vo.RoleVO;
import cn.edu.tjufe.zql.service.IMenuService;
import cn.edu.tjufe.zql.service.IRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * (Menu)表控制层
 *
 * @author kuailemao
 * @since 2023-11-17 22:15:18
 */
@RestController
@Tag(name = "菜单相关接口")
@RequestMapping("menu")
public class MenuController {
    /**
     * 服务对象
     */
    @Resource
    private IMenuService menuService;

    @Resource
    private IRoleService roleService;

    @PreAuthorize("hasAnyAuthority('system:menu:list')")
    @Operation(summary = "获取管理菜单列表")
    @Parameters({
            @Parameter(name = "typeId", description = "菜单类型id", required = true),
    })
    @AccessLimit(seconds = 60, maxCount = 30)
    @GetMapping("/list/{typeId}")
    public ResponseResult<List<MenuVO>> list(@PathVariable("typeId") Integer typeId) {
        return menuService.getMenuList(typeId, null, null);
    }

    @PreAuthorize("hasAnyAuthority('system:search:menu:list')")
    @Operation(summary = "搜索管理菜单列表")
    @Parameters({
            @Parameter(name = "typeId", description = "菜单类型id", required = true),
            @Parameter(name = "username", description = "用户名", required = true),
            @Parameter(name = "status", description = "状态", required = true)
    })
    @LogAnnotation(module = "菜单管理", operation = LogConst.SEARCH)
    @AccessLimit(seconds = 60, maxCount = 30)
    @GetMapping("/search/list/{typeId}")
    public ResponseResult<List<MenuVO>> searchMenu(@PathVariable("typeId") Integer typeId, String username, Integer status) {
        return menuService.getMenuList(typeId, username, status);
    }

    @PreAuthorize("hasAnyAuthority('system:menu:role:list')")
    @Operation(summary = "获取修改菜单角色列表")
    @AccessLimit(seconds = 60, maxCount = 5)
    @GetMapping("/role/list")
    public ResponseResult<List<RoleVO>> selectAll() {
        return roleService.selectAll();
    }

    @Operation(summary = "获取路由菜单列表")
    @Parameters({
            @Parameter(name = "typeId", description = "菜单类型id", required = true),
    })
    @AccessLimit(seconds = 60, maxCount = 30)
    @GetMapping("/router/list/{typeId}")
    public ResponseResult<List<MenuVO>> routerList(@PathVariable("typeId") Integer typeId) {
        return menuService.getMenuList(typeId, null, null);
    }

    @PreAuthorize("hasAnyAuthority('system:menu:add')")
    @Operation(summary = "添加菜单")
    @Parameter(name = "menuDTO", description = "菜单信息", required = true)
    @AccessLimit(seconds = 60, maxCount = 30)
    @LogAnnotation(module = "菜单管理", operation = LogConst.INSERT)
    @PostMapping
    public ResponseResult<Void> add(@RequestBody MenuDTO menuDTO) {
        return menuService.addMenu(menuDTO);
    }

    @PreAuthorize("hasAnyAuthority('system:menu:select')")
    @Operation(summary = "根据id查询菜单信息")
    @Parameter(name = "id", description = "菜单id", required = true)
    @AccessLimit(seconds = 60, maxCount = 30)
    @GetMapping("/{id}")
    public ResponseResult<MenuByIdVO> getById(@PathVariable("id") @NotNull Long id) {
        return menuService.selectUpdateMenu(id);
    }

    @PreAuthorize("hasAnyAuthority('system:menu:update')")
    @Operation(summary = "修改菜单")
    @Parameter(name = "menuDTO", description = "菜单信息", required = true)
    @AccessLimit(seconds = 60, maxCount = 30)
    @LogAnnotation(module = "菜单管理", operation = LogConst.UPDATE)
    @PutMapping
    public ResponseResult<Void> update(@RequestBody MenuDTO menuDTO) {
        return menuService.updateMenu(menuDTO);
    }

    @PreAuthorize("hasAnyAuthority('system:menu:delete')")
    @Operation(summary = "根据id删除菜单")
    @Parameter(name = "id", description = "菜单id", required = true)
    @AccessLimit(seconds = 60, maxCount = 30)
    @LogAnnotation(module = "菜单管理", operation = LogConst.DELETE)
    @DeleteMapping("/{id}")
    public ResponseResult<String> delete(@PathVariable("id") @NotNull Long id) {
        return menuService.deleteMenu(id);
    }

}

