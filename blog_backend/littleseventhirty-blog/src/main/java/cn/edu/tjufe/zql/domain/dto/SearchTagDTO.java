package cn.edu.tjufe.zql.domain.dto;


import lombok.Data;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/3/9
 * @github https://github.com/little-seven-thirty
 */
@Data
public class SearchTagDTO {
    //标签名称
    private String tagName;
    // 开始时间
    private String startTime;
    // 结束时间
    private String endTime;
}
