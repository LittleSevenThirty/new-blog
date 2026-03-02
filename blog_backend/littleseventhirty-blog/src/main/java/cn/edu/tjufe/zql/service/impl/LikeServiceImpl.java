package cn.edu.tjufe.zql.service.impl;


import cn.edu.tjufe.zql.constants.RedisConst;
import cn.edu.tjufe.zql.domain.entity.Like;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
import cn.edu.tjufe.zql.enums.LikeEnum;
import cn.edu.tjufe.zql.mapper.LikeMapper;
import cn.edu.tjufe.zql.service.ILikeService;
import cn.edu.tjufe.zql.utils.RedisCache;
import cn.edu.tjufe.zql.utils.SecurityUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/2/27
 * @github https://github.com/little-seven-thirty
 */
@Slf4j
@Service(value = "likeService")
public class LikeServiceImpl extends ServiceImpl<LikeMapper, Like> implements ILikeService {
    @Resource
    private LikeMapper likeMapper;
    @Resource
    private RedisCache redisCache;

    @Override
    public ResponseResult<Void> userLike(Integer type, Long typeId) {
        // 查询是否已经点赞
        Like like = likeMapper.selectOne(new LambdaQueryWrapper<Like>()
                .eq(Like::getUserId, SecurityUtils.getUserId())
                .eq(Like::getType, type)
                .eq(Like::getTypeId, typeId));
        if (like != null) return ResponseResult.failure();
        Like saveLike = Like.builder()
                .userId(SecurityUtils.getUserId())
                .type(type)
                .typeId(typeId).build();
        if (Objects.equals(type, LikeEnum.LIKE_TYPE_ARTICLE.getType()))
            redisCache.incrementCacheMapValue(RedisConst.ARTICLE_LIKE_COUNT, typeId.toString(), 1);
        if (this.save(saveLike)) return ResponseResult.success();
        return ResponseResult.failure();
    }

    @Override
    public ResponseResult<Void> cancelLike(Integer type, Long typeId) {
        // 查询是否已经点赞
        Like isLike = likeMapper.selectOne(new LambdaQueryWrapper<Like>()
                .eq(Like::getUserId, SecurityUtils.getUserId())
                .eq(Like::getType, type)
                .eq(Like::getTypeId, typeId));
        if (Objects.isNull(isLike)) return ResponseResult.failure();
        boolean like = this.remove(new LambdaQueryWrapper<Like>()
                .eq(Like::getUserId, SecurityUtils.getUserId())
                .eq(Like::getType, type)
                .eq(Like::getTypeId, typeId));
        if (Objects.equals(type, LikeEnum.LIKE_TYPE_ARTICLE.getType()))
            redisCache.incrementCacheMapValue(RedisConst.ARTICLE_LIKE_COUNT, typeId.toString(), -1);
        if (like) return ResponseResult.success();
        return ResponseResult.failure();
    }

    @Override
    public ResponseResult<List<Like>> isLike(Integer type, Integer typeId) {
        if (SecurityUtils.isLogin()) {
            LambdaQueryWrapper<Like> wrapper = new LambdaQueryWrapper<>();
            if (Objects.equals(type, LikeEnum.LIKE_TYPE_ARTICLE.getType())) {
                List<Like> like = likeMapper.selectList(wrapper
                        .eq(Like::getUserId, SecurityUtils.getUserId())
                        .eq(Like::getType, type)
                        .eq(Like::getTypeId, typeId));
                if (!like.isEmpty()) return ResponseResult.success(like);
                else ResponseResult.failure(null);
            }
            if (Objects.equals(type, LikeEnum.LIKE_TYPE_COMMENT.getType()) || Objects.equals(type, LikeEnum.LIKE_TYPE_LEAVE_WORD.getType())) {
                if (Objects.equals(type, LikeEnum.LIKE_TYPE_LEAVE_WORD.getType())) wrapper.eq(Like::getTypeId, typeId);
                List<Like> like = likeMapper.selectList(wrapper
                        .eq(Like::getUserId, SecurityUtils.getUserId())
                        .eq(Like::getType, type));
                if (!like.isEmpty()) return ResponseResult.success(like);
                else ResponseResult.failure(null);
            }

        }
        return ResponseResult.failure(null);
    }

    @Override
    public Long getLikeCount(Integer type, Long commentId) {
        return likeMapper.selectCount(new LambdaQueryWrapper<Like>()
                .eq(Like::getType, type)
                .eq(Like::getTypeId, commentId));
    }
}
