package cn.edu.tjufe.zql.domain.entity;

import cn.edu.tjufe.zql.domain.ViewObjectConvertible;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author: littleseventhirty
 * @description: 分类实体
 * @date: 2025/12/15-16:15
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_category")
public class Category implements ViewObjectConvertible {
    // 数据库标识
    private Long id;
    // 分类ID
    private Long categoryId;
    // 分类名称
    private String categoryName;
    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    // 是否删除（0：未删除，1：已删除）
    private Integer isDeleted;
}
