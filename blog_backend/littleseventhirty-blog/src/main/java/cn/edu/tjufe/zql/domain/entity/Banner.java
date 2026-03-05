package cn.edu.tjufe.zql.domain.entity;

import cn.edu.tjufe.zql.domain.ViewObjectConvertible;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author: littleseventhirty
 * @description: 和WebsiteInfo一样是前端的信息实体类，图片广告
 * @date: 2026/1/22-11:10
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_banner")
public class Banner implements ViewObjectConvertible {
    // 主键id
    @TableId("banner_id")
    private Long banner_id;
    // 图片路径
    private String path;
    // 图片大小(字节)
    private Long size;
    // 图片类型
    private String type;
    // 上传人id
    private Long userId;
    // 图片顺序
    @TableField("`order`")
    private Long order;
    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
}
