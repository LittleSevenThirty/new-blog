package cn.edu.tjufe.zql.domain.entity;

import cn.edu.tjufe.zql.domain.ViewObjectConvertible;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author: littleseventhirty
 * @description: 标签实体类
 * @date: 2026/1/28-16:42
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_tag")
public class Tag implements ViewObjectConvertible {
    // 标签id
    private Long tagId;
    // 标签名称
    private String tagName;
    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    // 是否删除
    private Integer isDelete;
}
