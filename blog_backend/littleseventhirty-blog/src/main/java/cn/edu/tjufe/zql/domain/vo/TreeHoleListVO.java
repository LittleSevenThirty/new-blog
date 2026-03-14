package cn.edu.tjufe.zql.domain.vo;


import lombok.Data;

import java.util.Date;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/3/9
 * @github https://github.com/little-seven-thirty
 */
@Data
public class TreeHoleListVO {
    //树洞表id
    private Long treeHoleId;
    //用户名称
    private String userName;
    //内容
    private String content;
    // 是否通过
    private Integer isCheck;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;
}
