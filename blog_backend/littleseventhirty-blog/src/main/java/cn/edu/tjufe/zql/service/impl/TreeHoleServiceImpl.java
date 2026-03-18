package cn.edu.tjufe.zql.service.impl;


import cn.edu.tjufe.zql.constants.SQLConst;
import cn.edu.tjufe.zql.domain.dto.SearchTreeHoleDTO;
import cn.edu.tjufe.zql.domain.dto.TreeHoleIsCheckDTO;
import cn.edu.tjufe.zql.domain.entity.TreeHole;
import cn.edu.tjufe.zql.domain.entity.User;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
import cn.edu.tjufe.zql.domain.vo.TreeHoleListVO;
import cn.edu.tjufe.zql.domain.vo.TreeHoleVO;
import cn.edu.tjufe.zql.mapper.TreeHoleMapper;
import cn.edu.tjufe.zql.mapper.UserMapper;
import cn.edu.tjufe.zql.service.ITreeHoleService;
import cn.edu.tjufe.zql.utils.SecurityUtils;
import cn.edu.tjufe.zql.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @authro 钟奇林
 * @description 树洞服务层
 * @date 2026/2/19
 * @github https://github.com/little-seven-thirty
 */
@Service("treeHoleService")
public class TreeHoleServiceImpl extends ServiceImpl<TreeHoleMapper, TreeHole> implements ITreeHoleService {
    @Resource
    private TreeHoleMapper treeHoleMapper;

    @Resource
    private UserMapper userMapper;

    @Override
    public ResponseResult<Void> addTreeHole(String content) {
        if(this.save(TreeHole.builder().userId(SecurityUtils.getUserId()).content(content).build())){
            return ResponseResult.success();
        }
        return ResponseResult.failure();
    }

    @Override
    public List<TreeHoleVO> getTreeHole() {
        List<TreeHole> treeHoles=treeHoleMapper.selectList(new LambdaQueryWrapper<TreeHole>().eq(TreeHole::getIsCheck, SQLConst.IS_CHECK_YES));
        if(treeHoles.isEmpty())return List.of();
        // 提取所有要查询用户的用户ID
        Set<Long> userIds=treeHoles.
                stream().map(TreeHole::getUserId)
                .collect(Collectors.toSet());

        // 批量查询用户信息
        Map<Long, User> userMap=userMapper.selectList(
                new LambdaQueryWrapper<User>()
                        .select(User::getUserId,User::getNickname,User::getAvatar)
                        .in(User::getUserId,userIds)
        ).stream().collect(Collectors.toMap(User::getUserId, user -> user));

        // 组装返回值
        return treeHoles.stream().map(treeHole -> treeHole.asViewObject(TreeHoleVO.class,(treeHoleVO)->{
            User user=userMap.get(treeHole.getUserId());
            if(user!=null){
                treeHoleVO.setNickName(user.getNickname());
                treeHoleVO.setAvater(user.getAvatar());
            }
        })).collect(Collectors.toList());
    }

    @Override
    public List<TreeHoleListVO> getBackTreeHoleList(SearchTreeHoleDTO searchDTO) {
        LambdaQueryWrapper<TreeHole> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotNull(searchDTO)) {
            // 搜索 用户名相关树洞留言
            List<User> users = userMapper.selectList(new LambdaQueryWrapper<User>().like(User::getUsername, searchDTO.getUserName()));
            if (!users.isEmpty())
                wrapper.in(StringUtils.isNotEmpty(searchDTO.getUserName()), TreeHole::getUserId, users.stream().map(User::getUserId).collect(Collectors.toList()));
            else
                wrapper.eq(StringUtils.isNotNull(searchDTO.getUserName()), TreeHole::getUserId, null);

            wrapper.eq(StringUtils.isNotNull(searchDTO.getIsCheck()), TreeHole::getIsCheck, searchDTO.getIsCheck());
            if (StringUtils.isNotNull(searchDTO.getStartTime()) && StringUtils.isNotNull(searchDTO.getEndTime()))
                wrapper.between(TreeHole::getCreateTime, searchDTO.getStartTime(), searchDTO.getEndTime());
        }
        List<TreeHole> treeHoles = treeHoleMapper.selectList(wrapper);
        if (!treeHoles.isEmpty()) {
            return treeHoles.stream().map(treeHole -> treeHole.asViewObject(TreeHoleListVO.class,
                    v -> v.setUserName(userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUserId, treeHole.getUserId()))
                            .getUsername()))).toList();
        }
        return null;
    }

    @Override
    public ResponseResult<Void> isCheckTreeHole(TreeHoleIsCheckDTO isCheckDTO) {
        if (treeHoleMapper.updateById(TreeHole.builder().treeHoleId(isCheckDTO.getTreeHoleId()).isCheck(isCheckDTO.getIsCheck()).build()) > 0)
            return ResponseResult.success();

        return ResponseResult.failure();
    }

    @Override
    public ResponseResult<Void> deleteTreeHole(List<Long> ids) {
        if (treeHoleMapper.deleteBatchIds(ids) > 0) {
            return ResponseResult.success();
        }
        return ResponseResult.failure();
    }
}
