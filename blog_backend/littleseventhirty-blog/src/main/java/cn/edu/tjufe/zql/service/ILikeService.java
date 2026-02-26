package cn.edu.tjufe.zql.service;

import cn.edu.tjufe.zql.domain.entity.Like;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ILikeService extends IService<Like> {
    ResponseResult<Void> like(Integer type, Long typeId);
    ResponseResult<Void> cancelLike(Integer type, Long typeId);
}
