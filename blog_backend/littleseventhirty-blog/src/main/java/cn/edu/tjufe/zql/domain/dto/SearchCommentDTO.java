package cn.edu.tjufe.zql.domain.dto;


import lombok.Data;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/3/8
 * @github https://github.com/little-seven-thirty
 */
@Data
public class SearchCommentDTO {
    //评论用户的名称
    private String commentUserName;
    //评论的内容
    private String commentContent;
    //评论类型 (1文章 2留言板)
    private Integer type;
    //是否通过 (0否 1是)
    private Integer isCheck;
}
