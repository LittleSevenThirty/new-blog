package cn.edu.tjufe.zql.enums;

import cn.edu.tjufe.zql.constants.ImageConst;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 *
 * @authro 钟奇林
 * @description 文件上传类型枚举
 * @date 2026/3/7
 * @github https://github.com/little-seven-thirty
 */
@AllArgsConstructor
@Getter
public enum UploadEnum {
    // 站长头像
    WEBSITE_INFO_AVATAR("websiteInfo/avatar/", "站长头像", List.of(ImageConst.JPG, ImageConst.JPEG, ImageConst.PNG, ImageConst.WEBP), 0.8),
    // 站长背景
    WEBSITE_INFO_BACKGROUND("websiteInfo/background/", "站长背景", List.of(ImageConst.JPG, ImageConst.JPEG, ImageConst.PNG, ImageConst.WEBP), 0.8),
    // 文章封面
    ARTICLE_COVER("article/articleCover/", "文章封面", List.of(ImageConst.JPG, ImageConst.JPEG, ImageConst.PNG, ImageConst.WEBP), 0.8),
    // 文章图片
    ARTICLE_IMAGE("article/articleImage/", "文章图片", List.of(ImageConst.JPG, ImageConst.JPEG, ImageConst.PNG, ImageConst.GIF, ImageConst.WEBP), 3.0),
    // 用户头像
    USER_AVATAR("user/avatar/", "用户头像", List.of(ImageConst.JPG, ImageConst.JPEG, ImageConst.PNG, ImageConst.WEBP), 0.8),
    // 前台首页Banners轮播图片
    UI_BANNERS("banners/", "前台首页Banners轮播图片", List.of(ImageConst.JPG, ImageConst.JPEG, ImageConst.PNG, ImageConst.WEBP), 0.8),
    // 相册模块图片
    PHOTO_ALBUM("photoAlbum/", "相册模块图片", List.of(ImageConst.JPG, ImageConst.JPEG, ImageConst.PNG, ImageConst.WEBP, ImageConst.GIF), 4.0);


    // 上传目录
    private final String dir;

    // 描述
    private final String description;

    // 支持的格式
    private final List<String> format;

    // 文件最大大小 单位：MB
    private final Double limitSize;
}
