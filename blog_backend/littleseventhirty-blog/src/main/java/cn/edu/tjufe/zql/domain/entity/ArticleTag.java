package cn.edu.tjufe.zql.domain.entity;

import cn.edu.tjufe.zql.domain.ViewObjectConvertible;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author: littleseventhirty
 * @description:
 * @date: 2026/1/28-15:01
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("t_article_tag")
public class ArticleTag implements ViewObjectConvertible {
    // 关系表id
    @TableId("article_tag_id")
    private Long articleTagId;
    // 文章id
    private Long articleId;
    // 标签id
    private Long tagId;
    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    //是否删除（0：未删除，1：已删除）
    private Integer isDeleted;
}

