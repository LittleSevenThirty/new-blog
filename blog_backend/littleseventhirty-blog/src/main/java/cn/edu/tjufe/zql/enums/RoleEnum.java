package cn.edu.tjufe.zql.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/3/6
 * @github https://github.com/little-seven-thirty
 */
@Getter
@AllArgsConstructor
public enum RoleEnum {

    Role_STATUS_ARTICLE(0, "状态：正常"),
    ROLE_STATUS_ARTICLE(1, "状态：停用");

    // 类型
    private final Integer status;
    // 描述
    private final String desc;
}
