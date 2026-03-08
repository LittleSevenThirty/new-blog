package cn.edu.tjufe.zql.domain.dto;


import cn.edu.tjufe.zql.domain.ViewObjectConvertible;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/3/8
 * @github https://github.com/little-seven-thirty
 */
@Accessors(chain = true)
@Data
public class CategoryDTO implements ViewObjectConvertible {
    //分类id
    private Long categoryId;
    //分类名
    @NotBlank(message = "分类名称不能为空")
    @Length(max = 20, message = "分类名称长度不能超过20")
    private String categoryName;
}
