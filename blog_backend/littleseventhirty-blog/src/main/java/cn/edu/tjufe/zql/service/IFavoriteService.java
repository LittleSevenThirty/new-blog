package cn.edu.tjufe.zql.service;

import cn.edu.tjufe.zql.domain.entity.Favorite;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IFavoriteService extends IService<Favorite> {
    ResponseResult<Void> favorite(Integer type, Long typeId);
    ResponseResult<Void> cancelFavorite(Integer type, Long typeId);
}
