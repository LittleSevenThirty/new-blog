package cn.edu.tjufe.zql.domain.dto;


import cn.edu.tjufe.zql.domain.ViewObjectConvertible;
import lombok.Data;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/3/7
 * @github https://github.com/little-seven-thirty
 */
@Data
public class SearchArticleDTO implements ViewObjectConvertible {
    // 分类id
    private Long categoryId;
    //文章标题
    private String articleTitle;
    //文章状态 (1公开 2私密 3草稿)
    private Integer status;
    //是否置顶 (0否 1是）
    private Integer isTop;
}
