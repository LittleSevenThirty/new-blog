package cn.edu.tjufe.zql.domain.email;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/3/14
 * @github https://github.com/little-seven-thirty
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeaveWordEmail {
    // 留言头像
    private String avatar;
    // 留言用户的昵称
    private String nickname;
    // 留言内容
    private String content;
    // 留言时间
    private String time;
    // 留言的地址
    private String url;
}
