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
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("t_like")
public class Like implements ViewObjectConvertible {
    // 点赞id
    @TableId("like_id")
    private Long likeId;
    // 点赞用户id
    private Long userId;
    // 点赞类型（1文章，2品论）
    private Integer type;
    // 对应类型id
    private Long typeId;
    // 创建时间
    @TableField(fill= FieldFill.INSERT)
    private Date createTime;
    @TableField(fill=FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
