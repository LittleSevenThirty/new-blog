package cn.edu.tjufe.zql.domain.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/3/11
 * @github https://github.com/little-seven-thirty
 */
@Data
public class RoleDeleteDTO {
    @NotNull
    private List<Long> Ids;
}
