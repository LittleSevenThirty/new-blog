package cn.edu.tjufe.zql.domain.entity;


import cn.edu.tjufe.zql.domain.ViewObjectConvertible;
import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/2/19
 * @github https://github.com/little-seven-thirty
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@TableName("t_tree_hole")
public class TreeHole implements ViewObjectConvertible {
    // 树洞ID
    @TableId(type= IdType.AUTO)
    private Long treeHoleId;
    // 用户ID
    private Long userId;
    // 内容
    private String content;
    // 是否通过
    private Integer isCheck;
    // 创建时间
    @TableField(fill= FieldFill.INSERT)
    private Date createTime;
    // 更新时间
    @TableField(fill= FieldFill.INSERT_UPDATE)
    private Date updateTime;
    // 是否删除(0未删除，1删除）
    private Integer isDelete;
}
