package cn.edu.tjufe.zql.domain.dto;


import cn.edu.tjufe.zql.domain.ViewObjectConvertible;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/3/8
 * @github https://github.com/little-seven-thirty
 */
@Accessors(chain = true)
@Data
public class TagDTO implements ViewObjectConvertible {
    //标签id
    private Long tagId;
    //标签名称
    @NotBlank(message = "标签名称不能为空")
    @Length(max = 20, message = "标签名称长度不能超过20")
    private String tagName;
}
