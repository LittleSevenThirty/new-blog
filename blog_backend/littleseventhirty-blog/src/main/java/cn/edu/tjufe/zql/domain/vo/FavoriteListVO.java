package cn.edu.tjufe.zql.domain.vo;


import lombok.Data;

import java.util.Date;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/3/8
 * @github https://github.com/little-seven-thirty
 */
@Data
public class FavoriteListVO {
    //收藏id
    private Long favoriteId;
    //收藏的用户姓名
    private String userName;
    //收藏类型(1,文章 2,留言板)
    private Integer type;
    // 收藏内容
    private String content;
    // 是否有效 (0否 1是)
    private Integer isCheck;
    //收藏时间
    private Date createTime;
}
