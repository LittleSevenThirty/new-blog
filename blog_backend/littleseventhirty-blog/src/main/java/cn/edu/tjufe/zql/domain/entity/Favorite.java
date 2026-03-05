package cn.edu.tjufe.zql.domain.entity;


import cn.edu.tjufe.zql.domain.ViewObjectConvertible;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.annotation.security.DenyAll;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/2/24
 * @github https://github.com/little-seven-thirty
 */
@TableName("t_favorite")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Favorite implements ViewObjectConvertible {
    //收藏id
    @TableId("favorite_id")
    private Long favoriteId;
    //收藏的用户id
    private Long userId;
    //收藏类型(1,文章 2,留言板)
    private Integer type;
    //类型id
    private Long typeId;
    // 是否有效 (0否 1是)
    private Integer isCheck;
    //收藏时间
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
}
