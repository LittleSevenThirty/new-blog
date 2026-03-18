package cn.edu.tjufe.zql.service;

import cn.edu.tjufe.zql.domain.dto.FavoriteIsCheckDTO;
import cn.edu.tjufe.zql.domain.dto.SearchFavoriteDTO;
import cn.edu.tjufe.zql.domain.entity.Favorite;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
import cn.edu.tjufe.zql.domain.vo.FavoriteListVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

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

    /**
     * 后台收藏列表
     * @return 结果
     */
    List<FavoriteListVO> getBackFavoriteList(SearchFavoriteDTO searchDTO);

    /**
     * 是否通过收藏
     * @param isCheckDTO 是否通过
     * @return 是否成功
     */
    ResponseResult<Void> isCheckFavorite(FavoriteIsCheckDTO isCheckDTO);

    /**
     * 删除收藏
     * @param ids id 列表
     * @return 是否成功
     */
    ResponseResult<Void> deleteFavorite(List<Long> ids);
}
