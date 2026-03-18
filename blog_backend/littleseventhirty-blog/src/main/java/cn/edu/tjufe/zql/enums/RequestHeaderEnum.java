package cn.edu.tjufe.zql.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/3/5
 * @github https://github.com/little-seven-thirty
 */
@Getter
@AllArgsConstructor
public enum RequestHeaderEnum {

    /**
     * Github获取个人信息Accept请求头
     */
    GITHUB_USER_INFO("Accept", "application/vnd.github.v3+json");


    /**
     * 请求头
     */
    public final String header;

    /**
     * 内容
     */
    public final String content;
}
