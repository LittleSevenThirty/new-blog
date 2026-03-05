package cn.edu.tjufe.zql.domain.entity;

import cn.edu.tjufe.zql.domain.ViewObjectConvertible;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author: littleseventhirty
 * @description: 站点信息实体类
 * @date: 2026/1/8-17:10
 **/
@TableName("sys_website_info")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class WebsiteInfo implements ViewObjectConvertible {
    private Long id;
    // 站长头像地址
    private String webMasterAvatar;
    // 站长名称
    private String webMasterName;
    // 站长个人简介
    private String webMasterCopy;
    // 站长个人主页背景图片
    private String webMasterProfileBackground;
    // 站长的 Gitee 代码仓库链接
    private String giteeLink;
    // 站长的 GitHub 代码仓库链接
    private String githubLink;
    // 站点的名称
    private String websiteName;
    // 站点头部的通知文案
    private String headerNotification;
    // 站点侧边通知
    private String sidebarAnnouncement;
    // 站点的备案信息
    private String recordInfo;
    // 站点开始时间
    private Date startTime;
    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    // 最近更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    // 是否删除(0：未删除，1：已删除）
    private Integer isDeleted;
}
