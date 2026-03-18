package cn.edu.tjufe.zql.domain.email;


import cn.edu.tjufe.zql.domain.ViewObjectConvertible;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/3/14
 * @github https://github.com/little-seven-thirty
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CommentEmail implements Serializable, ViewObjectConvertible {
    // id
    private Long commentId;
    // 类型 1:文章评论 2:留言
    private Integer type;
    // 类型ID
    private Long typeId;
    // 评论标题
    private String title;
    // 文章地址
    private String url;
    // 评论人头像
    private String avatar;
    // 评论人昵称
    private String nickname;
    // 评论内容
    private String content;
    // 评论时间
    private String time;
}
