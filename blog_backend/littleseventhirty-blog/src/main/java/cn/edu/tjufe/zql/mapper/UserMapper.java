package cn.edu.tjufe.zql.mapper;

import cn.edu.tjufe.zql.domain.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: littleseventhirty
 * @description: User对应Mapper接口
 * @date: 2026/1/13-12:57
 **/
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
