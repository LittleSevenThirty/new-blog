package cn.edu.tjufe.zql.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/2/25
 * @github https://github.com/little-seven-thirty
 */
@Getter
@AllArgsConstructor
public enum CommentEnum {
    COMMENT_TYPE_ARTICLE(1,"评论类型(1,文章)"),
    COMMENT_TYPE_LEAVE_WORD(2,"评论类型(2,留言)");

    // 评论类型
    private final Integer type;
    // 类型描述
    private final String desc;
}
