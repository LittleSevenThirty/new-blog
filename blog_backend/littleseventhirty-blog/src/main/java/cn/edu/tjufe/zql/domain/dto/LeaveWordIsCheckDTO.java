package cn.edu.tjufe.zql.domain.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/3/8
 * @github https://github.com/little-seven-thirty
 */
@Data
public class LeaveWordIsCheckDTO {
    // 留言id
    @NotNull(message = "留言id不能为空")
    private Long id;
    // 是否通过
    @NotNull(message = "是否通过不能为空")
    private Integer isCheck;
}