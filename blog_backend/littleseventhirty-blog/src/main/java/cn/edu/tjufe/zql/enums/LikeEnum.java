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
public enum LikeEnum {
    LIKE_TYPE_ARTICLE(1,"点赞：文章"),
    LIKE_TYPE_COMMENT(2,"点赞：评论"),
    LIKE_TYPE_LEAVE_WORD(3,"点赞：留言")
    ;

    private final Integer type;
    private final String desc;
}
