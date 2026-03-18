package cn.edu.tjufe.zql.domain.entity;


import cn.edu.tjufe.zql.domain.ViewObjectConvertible;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/3/5
 * @github https://github.com/little-seven-thirty
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("sys_user_role")
public class UserRole implements ViewObjectConvertible {
    //主键
    @TableId("user_role_id")
    private Long userRoleId;
    //用户id
    private Long userId;
    //角色id
    private Long roleId;
}
