package cn.edu.tjufe.zql.service.impl;


import cn.edu.tjufe.zql.constants.RedisConst;
import cn.edu.tjufe.zql.domain.dto.FavoriteIsCheckDTO;
import cn.edu.tjufe.zql.domain.dto.SearchFavoriteDTO;
import cn.edu.tjufe.zql.domain.entity.Favorite;
import cn.edu.tjufe.zql.domain.entity.User;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
import cn.edu.tjufe.zql.domain.vo.FavoriteListVO;
import cn.edu.tjufe.zql.mapper.ArticleMapper;
import cn.edu.tjufe.zql.mapper.FavoriteMapper;
import cn.edu.tjufe.zql.mapper.LeaveWordMapper;
import cn.edu.tjufe.zql.mapper.UserMapper;
import cn.edu.tjufe.zql.service.IFavoriteService;
import cn.edu.tjufe.zql.utils.RedisCache;
import cn.edu.tjufe.zql.utils.SecurityUtils;
import cn.edu.tjufe.zql.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/2/27
 * @github https://github.com/little-seven-thirty
 */
@Service("favoriteService")
public class FavoriteServiceImpl extends ServiceImpl<FavoriteMapper, Favorite> implements IFavoriteService {
    @Resource
    private FavoriteMapper favoriteMapper;

    @Resource
    private RedisCache redisCache;

    @Resource
    private UserMapper userMapper;

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private LeaveWordMapper leaveWordMapper;

    @Override
    public ResponseResult<Void> userFavorite(Integer type, Long typeId) {
        // 查询是否已经收藏
        Favorite favorite = favoriteMapper.selectOne(new LambdaQueryWrapper<Favorite>()
                .eq(Favorite::getUserId, SecurityUtils.getUserId())
                .eq(Favorite::getType, type)
                .eq(Favorite::getTypeId, typeId));
        if (StringUtils.isNotNull(favorite)) return ResponseResult.failure();
        Favorite Savefavorite = Favorite.builder()
                .favoriteId(null)
                .userId(SecurityUtils.getUserId())
                .type(type)
                .typeId(typeId).build();
        redisCache.incrementCacheMapValue(RedisConst.ARTICLE_FAVORITE_COUNT, typeId.toString(), 1);
        if (this.save(Savefavorite)) return ResponseResult.success();
        return ResponseResult.failure();
    }

    @Override
    public ResponseResult<Void> cancelFavorite(Integer type, Long typeId) {
        // 查询是否已经收藏
        Favorite isFavorite = favoriteMapper.selectOne(new LambdaQueryWrapper<Favorite>()
                .eq(Favorite::getUserId, SecurityUtils.getUserId())
                .eq(Favorite::getType, type)
                .eq(Favorite::getTypeId, typeId));
        if (Objects.isNull(isFavorite)) return ResponseResult.failure();
        boolean cancelFavorite = this.remove(new LambdaQueryWrapper<Favorite>()
                .eq(Favorite::getUserId, SecurityUtils.getUserId())
                .eq(Favorite::getType, type)
                .eq(Favorite::getTypeId, typeId));
        redisCache.incrementCacheMapValue(RedisConst.ARTICLE_FAVORITE_COUNT, typeId.toString(), -1);
        if (cancelFavorite) return ResponseResult.success();
        return ResponseResult.failure();
    }

    @Override
    public Boolean isFavorite(Integer type, Integer typeId) {
        if (SecurityUtils.isLogin()) {
            // 是否收藏
            Favorite favorite = favoriteMapper.selectOne(new LambdaQueryWrapper<Favorite>()
                    .eq(Favorite::getUserId, SecurityUtils.getUserId())
                    .eq(Favorite::getType, type)
                    .eq(Favorite::getTypeId, typeId));
            return favorite != null;
        }
        return false;
    }

    @Override
    public List<FavoriteListVO> getBackFavoriteList(SearchFavoriteDTO searchDTO) {
        LambdaQueryWrapper<Favorite> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotNull(searchDTO)) {
            // 搜索
            List<User> users = userMapper.selectList(new LambdaQueryWrapper<User>().like(User::getUsername, searchDTO.getUserName()));
            if (!users.isEmpty())
                wrapper.in(StringUtils.isNotEmpty(searchDTO.getUserName()), Favorite::getUserId, users.stream().map(User::getUserId).collect(Collectors.toList()));
            else
                wrapper.eq(StringUtils.isNotNull(searchDTO.getUserName()), Favorite::getUserId, null);

            wrapper.eq(StringUtils.isNotNull(searchDTO.getIsCheck()), Favorite::getIsCheck, searchDTO.getIsCheck())
                    .eq(StringUtils.isNotNull(searchDTO.getType()), Favorite::getType, searchDTO.getType());
            if (StringUtils.isNotNull(searchDTO.getStartTime()) && StringUtils.isNotNull(searchDTO.getEndTime()))
                wrapper.between(Favorite::getCreateTime, searchDTO.getStartTime(), searchDTO.getEndTime());
        }
        List<Favorite> favorites = favoriteMapper.selectList(wrapper);
        if (!favorites.isEmpty()) {
            return favorites.stream().map(favorite -> favorite.asViewObject(FavoriteListVO.class,
                    v -> {
                        v.setUserName(userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUserId, favorite.getUserId())).getUsername());
                        switch (favorite.getType()) {
                            case 1 -> v.setContent(articleMapper.selectById(favorite.getTypeId()).getArticleContent());
                            case 2 -> v.setContent(leaveWordMapper.selectById(favorite.getTypeId()).getContent());
                        }
                    })).toList();
        }
        return null;
    }

    @Override
    public ResponseResult<Void> isCheckFavorite(FavoriteIsCheckDTO isCheckDTO) {
        if (favoriteMapper.updateById(Favorite.builder().favoriteId(isCheckDTO.getFavoriteId()).isCheck(isCheckDTO.getIsCheck()).build()) > 0)
            return ResponseResult.success();
        return ResponseResult.failure();
    }

    @Override
    public ResponseResult<Void> deleteFavorite(List<Long> ids) {
        if (favoriteMapper.deleteBatchIds(ids) > 0) return ResponseResult.success();
        return ResponseResult.failure();
    }
}
