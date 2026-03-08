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
public class LeaveWordListVO {
    //id
    private Long leaveWordId;
    // 留言用户名称
    private String userName;
    //留言内容
    private String content;
    //是否通过 (0否 1是)
    private Integer isCheck;
    //留言时间
    private Date createTime;
    //更新时间
    private Date updateTime;
}
