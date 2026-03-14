package cn.edu.tjufe.zql.domain.dto;


import cn.edu.tjufe.zql.domain.ViewObjectConvertible;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/3/9
 * @github https://github.com/little-seven-thirty
 */
@Data
public class StationmasterInfoDTO implements ViewObjectConvertible {
    //站长名称
    @Length(max = 30, message = "站长名称字数不能超过30")
    private String webmasterName;
    //站长文案
    @Length(max = 100, message = "站长文案字数不能超过100")
    private String webmasterCopy;
    //gitee链接
    @Length(max = 100, message = "gitee链接字数不能超过100")
    private String giteeLink;
    //github链接
    @Length(max = 100, message = "github链接字数不能超过100")
    private String githubLink;
}
