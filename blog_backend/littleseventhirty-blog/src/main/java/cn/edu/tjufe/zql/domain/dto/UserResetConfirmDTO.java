package cn.edu.tjufe.zql.domain.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/3/6
 * @github https://github.com/little-seven-thirty
 */
@Data
public class UserResetConfirmDTO {
    //验证码
    @Schema(description = "验证码")
    @Length(max = 6, min = 6)
    private String code;
    // 邮箱
    @Schema(description = "邮箱")
    @Email
    @Length(min = 4)
    private String email;
}
