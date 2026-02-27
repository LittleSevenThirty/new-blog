package cn.edu.tjufe.zql.service;

import cn.edu.tjufe.zql.domain.entity.Favorite;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IFavoriteService extends IService<Favorite> {
    /**
     * 收藏文章
     *
     * @param type   收藏类型
     * @param typeId 收藏id
     * @return 收藏结果
     */
    ResponseResult<Void> userFavorite(Integer type, Long typeId);

    /**
     * 取消收藏文章
     *
     * @param type   收藏类型
     * @param typeId 收藏id
     * @return 取消收藏结果
     */
    ResponseResult<Void> cancelFavorite(Integer type, Long typeId);

    /**
     * 是否已经收藏
     *
     * @param type   收藏类型
     * @param typeId 收藏id
     * @return 是否已经收藏
     */
    Boolean isFavorite(Integer type, Integer typeId);
}
