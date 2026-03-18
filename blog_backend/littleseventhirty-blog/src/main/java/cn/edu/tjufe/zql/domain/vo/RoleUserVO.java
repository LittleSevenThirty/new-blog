package cn.edu.tjufe.zql.domain.vo;


import lombok.Data;

import java.util.Date;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/3/12
 * @github https://github.com/little-seven-thirty
 */
@Data
public class RoleUserVO {
    //用户id
    private Long roleUserId;
    //用户昵称
    private String nickname;
    //用户名
    private String username;
    //用户邮箱
    private String email;
    //是否禁用 0 否 1 是
    private Integer isDisable;
    //用户创建时间
    private Date createTime;
}
