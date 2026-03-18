package cn.edu.tjufe.zql.domain.dto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/3/11
 * @github https://github.com/little-seven-thirty
 */
@Data
public class UpdateRoleStatusDTO {
    @NotNull
    private Long roleId;
    @Min(value = 0)
    @Max(value = 1)
    private Integer status;
}
