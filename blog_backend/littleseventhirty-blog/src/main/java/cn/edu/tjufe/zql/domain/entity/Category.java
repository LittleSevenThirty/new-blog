package cn.edu.tjufe.zql.domain.entity;

import cn.edu.tjufe.zql.domain.BaseData;
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
public class Category implements BaseData {
    private String id;

    private String categoryId;

    private String categoryName;

    @TableField(fill= FieldFill.INSERT)
    private Date createTime;

    @TableField(fill= FieldFill.INSERT_UPDATE)
    private Date updateTime;

    private Integer isDeleted;
}
