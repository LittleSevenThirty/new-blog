package cn.edu.tjufe.zql.controller;


import cn.edu.tjufe.zql.annotation.AccessLimit;
import cn.edu.tjufe.zql.annotation.LogAnnotation;
import cn.edu.tjufe.zql.constants.LogConst;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
import cn.edu.tjufe.zql.service.IPublicService;
import cn.edu.tjufe.zql.utils.ResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/3/5
 * @github https://github.com/little-seven-thirty
 */
@RestController
@Tag(name = "公共接口")
@RequestMapping("/public")
@Validated
public class PublicController {

    @Resource(name="publicService")
    private IPublicService publicService;

    /**
     * 邮件发送
     */
    @Operation(summary = "邮件发送")
    @Parameters({
            @Parameter(name = "email", description = "邮箱", required = true),
            @Parameter(name = "type", description = "邮箱类型", required = true)
    })
    @AccessLimit(seconds = 60, maxCount = 1)
    @LogAnnotation(module="邮件发送",operation= LogConst.EMAIL_SEND)
    @GetMapping("/ask-code")
    public ResponseResult<String> askVerifyCode(
            @RequestParam @Email String email,
            @RequestParam @Pattern(regexp = "(register|reset|resetEmail)",message = "邮箱类型错误" ) String type
    ) {
        return ResponseWrapper.handler(() -> publicService.registerEmailVerifyCode(type, email));
    }

}
