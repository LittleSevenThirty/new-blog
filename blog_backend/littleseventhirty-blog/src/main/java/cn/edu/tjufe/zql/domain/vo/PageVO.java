package cn.edu.tjufe.zql.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: littleseventhirty
 * @description: 页面布局视图类
 * @date: 2026/1/27-10:55
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Schema(name = "PageVO", description = "分页视图类")
public class PageVO<T> {
    /**
     * 数据
     */
    @Schema(description = "数据")
    private T page;

    /**
     * 数据总数
     */
    @Schema(description = "分页总数")
    private Long total;
}
