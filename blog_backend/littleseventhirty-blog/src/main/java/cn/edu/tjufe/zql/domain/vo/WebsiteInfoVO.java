package cn.edu.tjufe.zql.domain.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author: littleseventhirty
 * @description: 站点信息视图类
 * @date: 2026/1/9-10:16
 **/
@Data
public class WebsiteInfoVO {
    //站长头像
    private String webMasterAvatar;
    //站长名称
    private String webMasterName;
    //站长文案
    private String webMasterCopy;
    //站长资料卡背景图
    private String webMasterProfileBackground;
    //gitee链接
    private String giteeLink;
    //github链接
    private String githubLink;
    //网站名称
    private String websiteName;
    //头部通知
    private String headerNotification;
    //侧面公告
    private String sidebarAnnouncement;
    //备案信息
    private String recordInfo;
    //开始运行时间
    private Date startTime;
    // 文章的最后更新
    private Date lastUpdateTime;
    // 文章数目
    private Long articleCount;
    // 分类数
    private Long categoryCount;
    // 评论数
    private Long commentCount;
    // 全站字数
    private Long wordCount;
    // 访问次数
    private Long visitedCount;
}
