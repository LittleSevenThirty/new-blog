-- Table structure for sys_user
-- create time: 2026/1/13
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `id`            bigint unsigned                                               NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '表标识',
    `user_id`       bigint unsigned                                               NOT NULL COMMENT '用户标识',
    `nickname`      varchar(50) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_0900_AI_CI  NULL     DEFAULT NULL COMMENT '用户昵称',
    `username`      varchar(50) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_0900_AI_CI  NOT NULL COMMENT '用户名',
    `gender`        tinyint(1)                                                    NOT NULL DEFAULT 2 COMMENT '性别（0男，1女，2未定义）',
    `password`      varchar(100) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_0900_AI_CI NULL     DEFAULT NULL COMMENT '密码',
    `avater`        varchar(255) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_0900_AI_CI NOT NULL COMMENT '用户头像',
    `introduce`     varchar(100) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_0900_AI_CI NULL     DEFAULT NULL COMMENT '用户简介',
    `email`         varchar(50) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_0900_AI_CI  NULL     DEFAULT NULL COMMENT '用户邮箱',
    `register_type` tinyint(1)                                                    NOT NULL COMMENT '注册方式（0邮箱/姓名，1gitee，2github)',
    `login_type`    tinyint(1)                                                    NULL     DEFAULT NULL COMMENT '登陆方式（0邮箱/姓名，1gitee，2github)',
    `login_time`    datetime                                                      NOT NULL COMMENT '最近登陆时间',
    `create_time`   datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_delete`     tinyint(1)                                                    NOT NULL DEFAULT 0 COMMENT '是否删除'
) ENGINE = InnoDB
  AUTO_INCREMENT = 53
  CHARACTER SET = UTF8MB4
  COLLATE = UTF8MB4_0900_AI_CI
  ROW_FORMAT = DYNAMIC;

-- Table structure for sys_website_info
-- create time: 2026/1/8
DROP TABLE IF EXISTS `sys_website_info`;
CREATE TABLE `sys_website_info`
(
    `id`                           bigint unsigned                                               NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `webmaster_avater`             varchar(250) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_0900_AI_CI NULL     DEFAULT NULL COMMENT '站长头像',
    `webmaster_name`               varchar(50) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_0900_AI_CI  NULL     DEFAULT NULL COMMENT '站长名称',
    `webmaster_copy`               varchar(100) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_0900_AI_CI NULL     DEFAULT NULL COMMENT '站长文案',
    `webmaster_profile_background` varchar(255) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_0900_AI_CI NULL     DEFAULT NULL COMMENT '站长资料卡背景图',
    `gitee_link`                   varchar(100) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_0900_AI_CI NULL     DEFAULT NULL COMMENT 'gitee链接',
    `github_link`                  varchar(100) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_0900_AI_CI NULL     DEFAULT NULL COMMENT 'github链接',
    `website_name`                 varchar(50) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_0900_AI_CI  NULL     DEFAULT NULL COMMENT '网站名称',
    `header_notifation`            varchar(150) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_0900_AI_CI NULL     DEFAULT NULL COMMENT '头部通知',
    `sidebar_announcement`         varchar(255) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_0900_AI_CI NULL     DEFAULT NULL COMMENT '侧面公告',
    `record_info`                  varchar(255) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_0900_AI_CI NULL     DEFAULT NULL COMMENT '备案信息',
    `start_time`                   datetime                                                      NULL     DEFAULT NULL COMMENT '开始运行时间',
    `create_time`                  datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`                  datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`                   tinyint(1)                                                    NOT NULL DEFAULT 0 COMMENT '是否删除'
) ENGINE = InnoDB
  AUTO_INCREMENT = 53
  CHARACTER SET = UTF8MB4
  COLLATE = UTF8MB4_0900_AI_CI
  ROW_FORMAT = DYNAMIC;

-- Table structure for t_article
-- Created time: 2026/1/4 ; Status: unfinished
DROP TABLE IF EXISTS `t_article_tag`;
CREATE TABLE `t_article_tag`
(
    `id`          bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '关系表id',
    `article_id`  bigint unsigned NOT NULL COMMENT '文章id',
    `tag_id`      bigint unsigned NOT NULL COMMENT '标签id',
    `create_time` datetime                 DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    `is_deleted`  tinyint         NOT NULL DEFAULT 0 COMMENT '是否删除（0：未删除，1：已删除）',
    PRIMARY KEY (ID) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 187
  CHARACTER SET = UTF8MB4
  COLLATE = UTF8MB4_0900_AI_CI
  ROW_FORMAT = DYNAMIC;

-- Table structure for t_article
-- Created time: 2026/1/4 ; Status: unfinished
DROP TABLE IF EXISTS `t_article`;
CREATE TABLE `t_article`
(
    `id`              BIGINT unsigned                                                NOT NULL AUTO_INCREMENT COMMENT '表id',
    `article_id`      BIGINT unsigned                                                NOT NULL COMMENT '文章id',
    `user_id`         BIGINT                                                         NOT NULL COMMENT '作者id',
    `category_id`     BIGINT                                                         NOT NULL COMMENT '分类id',
    `article_title`   varchar(50) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_0900_AI_CI   NOT NULL COMMENT '文章标题',
    `article_content` LONGTEXT CHARACTER SET UTF8MB4 COLLATE UTF8MB4_0900_AI_CI      NOT NULL COMMENT '文章内容',
    `article_type`    TINYINT                                                        NOT NULL COMMENT '类型（1原创 2转载 3翻译）',
    `status`          TINYINT                                                        NOT NULL COMMENT '文章状态（1公开 2私密 3草稿）',
    `visited_count`   BIGINT unsigned                                                NOT NULL COMMENT '文章访问数目',
    `is_top`          tinyint                                                        NOT NULL COMMENT '是否置顶（0否，1是）',
    `create_time`     DATETIME DEFAULT CURRENT_TIMESTAMP                             NOT NULL COMMENT '文章创建时间',
    `update_time`     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL COMMENT '文章更新时间',
    `article_cover`   varchar(200) CHARACTER SET UTF8MB4 collate utf8mb4_0900_AI_CI not null comment '文章缩略图',
    `is_deleted`      TINYINT(1)                                                     NOT NULL DEFAULT 0 COMMENT '是否删除（0未删除，1已删除）',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 53
  CHARACTER SET = UTF8MB4
  COLLATE = UTF8MB4_0900_AI_CI
  ROW_FORMAT = DYNAMIC;

-- Table structure for t_banner
-- create time: 2026/1/22 ; status: unfinished
DROP TABLE IF EXISTS `t_banner`;
CREATE TABLE `t_banner`
(
    `id`          bigint unsigned                                               NOT NULL AUTO_INCREMENT COMMENT '主键id，也作为广告标识',
    `path`        varchar(255) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_0900_AI_CI NOT NULL COMMENT '图片路径',
    `size`        bigint                                                        NOT NULL COMMENT '图片尺寸',
    `type`        varchar(55) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_0900_AI_CI  NOT NULL COMMENT '图片类型',
    `user_id`     bigint                                                        NOT NULL COMMENT '上传人id',
    `order`       int                                                           NOT NULL COMMENT '图片顺序',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (ID) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 43
  CHARACTER SET = UTF8MB4
  COLLATE = UTF8MB4_0900_AI_CI
  ROW_FORMAT = DYNAMIC;

-- Table structure for t_category
-- create time: 2026/1/8 ; Status: unfinished
DROP TABLE IF EXISTS `t_category`;
CREATE TABLE `t_category`
(
    `id`            bigint unsigned                                              NOT NULL AUTO_INCREMENT COMMENT 't_category标识' PRIMARY KEY,
    `category_id`   bigint unsigned                                              NOT NULL COMMENT '分类id',
    `category_name` varchar(20) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_0900_AI_CI NOT NULL COMMENT '分类名',
    `create_time`   datetime                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   datetime                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `is_deleted`    tinyint(1)                                                   NOT NULL DEFAULT 0 COMMENT '是否删除（0：未删除，1：已删除'
) ENGINE = InnoDB
  AUTO_INCREMENT = 53
  CHARACTER SET = UTF8MB4
  COLLATE = UTF8MB4_0900_AI_CI
  ROW_FORMAT = DYNAMIC;

-- 导出版本（可靠性高）
CREATE TABLE BLOG.T_ARTICLE
(
    ID              bigint unsigned AUTO_INCREMENT COMMENT '表id'
        PRIMARY KEY,
    ARTICLE_ID      bigint unsigned   NOT NULL COMMENT '文章id',
    USER_ID         bigint            NOT NULL COMMENT '作者id',
    CATEGORY_ID     bigint            NOT NULL COMMENT '分类id',
    ARTICLE_TITLE   varchar(50)       NOT NULL COMMENT '文章标题',
    ARTICLE_CONTENT longtext          NOT NULL COMMENT '文章内容',
    ARTICLE_TYPE    tinyint           NOT NULL COMMENT '类型（1原创 2转载 3翻译）',
    STATUS          tinyint           NOT NULL COMMENT '文章状态（1公开 2私密 3草稿）',
    CREATE_TIME     datetime          NOT NULL COMMENT '文章创建时间',
    UPDATE_TIME     datetime          NOT NULL COMMENT '文章更新时间',
    IS_DELETED      tinyint DEFAULT 0 NOT NULL COMMENT '是否删除（0未删除，1已删除）',
    VISITED_COUNT   bigint            NOT NULL COMMENT '访问数量'
)
    ROW_FORMAT = DYNAMIC;

