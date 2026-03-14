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
public class SearchTreeHoleDTO {
    // 树洞用户
    private String userName;
    //是否通过 (0否 1是)
    private Integer isCheck;
    // 开始时间
    private String startTime;
    // 结束时间
    private String endTime;
}
