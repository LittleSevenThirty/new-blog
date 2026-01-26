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
 * @description: 目前文章表就这些了，后续更新数据库表再同步更新实体类
 * @date: 2025/12/12-11:51
 **/
@TableName("T_Article")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Article implements ViewObjectConvertible {
    // 数据库表标识
    private Long id;

    // 文章标识
    private Long articleId;

    // 作者ID
    private Long userId;

    // 分类ID
    private Long categoryId;

    // 文章标题
    private String articleTitle;

    // 文章内容
    private String articleContent;

    // 文章被访问次数
    private Long visitedCount;

    // 文章类型(1原创，2翻译，3转载）
    private Short articleType;

    // 文章是否置顶
    private Integer isTop;

    // 文章状态(1公开 2私密 3草稿）
    private Integer status;

    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    // 是否删除（0未删除 1已删除）
    private Short isDeleted;
}
