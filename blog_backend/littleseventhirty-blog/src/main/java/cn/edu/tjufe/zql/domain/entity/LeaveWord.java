package cn.edu.tjufe.zql.domain.entity;


import cn.edu.tjufe.zql.domain.ViewObjectConvertible;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 *
 * @authro 钟奇林
 * @description t_leave_word实体
 * @date 2026/2/24
 * @github https://github.com/little-seven-thirty
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_leave_word")
public class LeaveWord implements ViewObjectConvertible {
    @Schema(description ="留言id" )
    private Long leaveWordId;
    @Schema(description = "用户id")
    private Long userId;
    @Schema(description ="留言内容" )
    private String content;
    @Schema(description = "是否通过,0否，1是")
    private Integer isCheck;
    @Schema(description = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @Schema(description = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    @Schema(description = "是否删除")
    private Integer isDeleted;
}
