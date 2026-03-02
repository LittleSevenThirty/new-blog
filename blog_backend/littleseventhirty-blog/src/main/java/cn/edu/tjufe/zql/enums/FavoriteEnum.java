package cn.edu.tjufe.zql.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/2/28
 * @github https://github.com/little-seven-thirty
 */
@Getter
@AllArgsConstructor
public enum FavoriteEnum {

    FAVORITE_TYPE_ARTICLE(1, "收藏：文章"),
    FAVORITE_TYPE_LEAVE_WORD(2, "收藏：留言");
    // 类型
    private final Integer type;
    // 描述
    private final String desc;
}
