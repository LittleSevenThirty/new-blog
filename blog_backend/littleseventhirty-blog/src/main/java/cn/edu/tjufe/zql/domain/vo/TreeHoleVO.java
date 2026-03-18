package cn.edu.tjufe.zql.domain.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 *
 * @authro 钟奇林
 * @description 用户树洞留言VO
 * @date 2026/2/22
 * @github https://github.com/little-seven-thirty
 */
@Data
public class TreeHoleVO {
    @Schema(description = "用户昵称")
    private String nickName;
    @Schema(description = "用户头像")
    private String avater;
    @Schema(description = "用户留言")
    private String content;
}
