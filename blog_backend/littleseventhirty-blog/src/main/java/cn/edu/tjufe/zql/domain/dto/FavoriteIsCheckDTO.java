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
public class FavoriteIsCheckDTO {
    // 收藏id
    @NotNull(message = "收藏id不能为空")
    private Long favoriteId;
    // 是否通过
    @NotNull(message = "是否有效不能为空")
    private Integer isCheck;
}
