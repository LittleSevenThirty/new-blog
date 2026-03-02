package cn.edu.tjufe.zql.service;

import cn.edu.tjufe.zql.domain.entity.Like;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface ILikeService extends IService<Like> {
    /**
     * 点赞文章
     *
     * @param type   点赞类型
     * @param typeId 点赞id
     * @return 点赞结果
     */
    ResponseResult<Void> userLike(Integer type, Long typeId);

    /**
     * 点赞文章
     *
     * @param type   点赞类型
     * @param typeId 点赞id
     * @return 点赞结果
     */
    ResponseResult<Void> cancelLike(Integer type, Long typeId);

    /**
     * 是否点赞
     *
     * @param type   点赞类型
     * @param typeId 点赞id
     * @return 是否点赞
     */
    ResponseResult<List<Like>> isLike(Integer type, Integer typeId);

    /**
     * 获取点赞数
     * @param type
     * @param commentId
     * @return
     */
    Long getLikeCount(Integer type, Long commentId);
}
