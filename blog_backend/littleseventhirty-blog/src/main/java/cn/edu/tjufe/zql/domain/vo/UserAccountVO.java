package cn.edu.tjufe.zql.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author: littleseventhirty
 * @description: 前端用户信息VO
 * @date: 2026/1/13-11:12
 **/
@Data
@Schema(name = "UserAccountVO", description = "前端用户信息VO")
public class UserAccountVO {
    @Schema(name = "昵称")
    private String nickname;
    @Schema(name = "用户名")
    private String username;
    @Schema(name = "头像")
    private String avater;
    @Schema(name = "介绍")
    private String introduce;
    @Schema(name = "注册类型")
    private Integer registerType;
    @Schema(name = "邮件地址")
    private String email;
    @Schema(name = "用户角色列表")
    private List<String> roles;
    @Schema(name = "性别")
    private Integer gender;
    @Schema(name = "用户权限列表")
    private List<String> permissions;
    @Schema(name = "最后登陆时间")
    private Date loginTime;
    @Schema(name = "创建时间")
    private Date createTime;
}
