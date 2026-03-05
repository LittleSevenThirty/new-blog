package cn.edu.tjufe.zql.domain.dto;


import cn.edu.tjufe.zql.domain.ViewObjectConvertible;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/3/5
 * @github https://github.com/little-seven-thirty
 */
@Data
public class UserUpdateDTO implements ViewObjectConvertible {
    //用户昵称
    @NotNull
    private String nickname;
    //用户性别
    @NotNull
    private Integer gender;
    //用户头像
    @NotNull
    private String avatar;
    //个人简介
    @NotNull
    private String intro;
}
