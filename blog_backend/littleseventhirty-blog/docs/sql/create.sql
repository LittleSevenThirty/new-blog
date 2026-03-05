-- ----------------------------
-- Table structure for sys_login_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_login_log`;
CREATE TABLE `sys_login_log`  (
                                  `login_log_id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志编号',
                                  `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名称',
                                  `ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '登录ip',
                                  `address` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '登录地址',
                                  `browser` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '浏览器',
                                  `os` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作系统',
                                  `type` tinyint(1) NOT NULL COMMENT '登录类型(0：前台，1：后台，2：非法登录)',
                                  `state` tinyint(1) NOT NULL COMMENT '登录状态(0：成功，1：失败)',
                                  `message` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '登录信息',
                                  `create_time` datetime NOT NULL COMMENT '用户创建时间',
                                  `update_time` datetime NOT NULL COMMENT '用户更新时间',
                                  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除（0：未删除，1：已删除）',
                                  PRIMARY KEY (`login_log_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2275 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;



-- Table structure for sys_user
-- create time: 2026/1/13
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `user_id`       bigint unsigned                                               NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '用户标识',
    `nickname`      varchar(50) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_0900_AI_CI  NULL     DEFAULT NULL COMMENT '用户昵称',
    `username`      varchar(50) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_0900_AI_CI  NOT NULL COMMENT '用户名',
    `gender`        tinyint(1)                                                    NOT NULL DEFAULT 2 COMMENT '性别（0男，1女，2未定义）',
    `password`      varchar(100) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_0900_AI_CI NULL     DEFAULT NULL COMMENT '密码',
    `avatar`        varchar(255) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_0900_AI_CI NOT NULL COMMENT '用户头像',
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
    `webmaster_avatar`             varchar(250) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_0900_AI_CI NULL     DEFAULT NULL COMMENT '站长头像',
    `webmaster_name`               varchar(50) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_0900_AI_CI  NULL     DEFAULT NULL COMMENT '站长名称',
    `webmaster_copy`               varchar(100) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_0900_AI_CI NULL     DEFAULT NULL COMMENT '站长文案',
    `webmaster_profile_background` varchar(255) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_0900_AI_CI NULL     DEFAULT NULL COMMENT '站长资料卡背景图',
    `gitee_link`                   varchar(100) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_0900_AI_CI NULL     DEFAULT NULL COMMENT 'gitee链接',
    `github_link`                  varchar(100) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_0900_AI_CI NULL     DEFAULT NULL COMMENT 'github链接',
    `website_name`                 varchar(50) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_0900_AI_CI  NULL     DEFAULT NULL COMMENT '网站名称',
    `header_notification`            varchar(150) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_0900_AI_CI NULL     DEFAULT NULL COMMENT '头部通知',
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
    `article_tag_id`          bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '关系表id',
    `article_id`  bigint unsigned NOT NULL COMMENT '文章id',
    `tag_id`      bigint unsigned NOT NULL COMMENT '标签id',
    `create_time` datetime                 DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    `is_deleted`  tinyint         NOT NULL DEFAULT 0 COMMENT '是否删除（0：未删除，1：已删除）',
    PRIMARY KEY (article_tag_id) USING BTREE
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
    `article_id`      BIGINT unsigned                                                NOT NULL AUTO_INCREMENT COMMENT '文章id',
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
    PRIMARY KEY (`article_id`) USING BTREE
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
    `banner_id`          bigint unsigned                                               NOT NULL AUTO_INCREMENT COMMENT '主键id，也作为广告标识',
    `path`        varchar(255) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_0900_AI_CI NOT NULL COMMENT '图片路径',
    `size`        bigint                                                        NOT NULL COMMENT '图片尺寸',
    `type`        varchar(55) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_0900_AI_CI  NOT NULL COMMENT '图片类型',
    `user_id`     bigint                                                        NOT NULL COMMENT '上传人id',
    `order`       int                                                           NOT NULL COMMENT '图片顺序',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (banner_id) USING BTREE
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
    `category_id`   bigint unsigned                                              NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT  '分类id',
    `category_name` varchar(20) CHARACTER SET UTF8MB4 COLLATE UTF8MB4_0900_AI_CI NOT NULL COMMENT '分类名',
    `create_time`   datetime                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   datetime                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `is_deleted`    tinyint(1)                                                   NOT NULL DEFAULT 0 COMMENT '是否删除（0：未删除，1：已删除'
) ENGINE = InnoDB
  AUTO_INCREMENT = 53
  CHARACTER SET = UTF8MB4
  COLLATE = UTF8MB4_0900_AI_CI
  ROW_FORMAT = DYNAMIC;

DROP TABLE IF EXISTS `t_comment`;
CREATE TABLE `t_comment`  (
                              `comment_id` bigint NOT NULL AUTO_INCREMENT COMMENT '评论id',
                              `type` tinyint(1) NOT NULL COMMENT '评论类型 (1文章 2留言板)',
                              `type_id` bigint NOT NULL COMMENT '类型id',
                              `parent_id` bigint NULL DEFAULT NULL COMMENT '父评论id',
                              `reply_id` bigint NULL DEFAULT NULL COMMENT '回复评论id',
                              `comment_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评论的内容',
                              `comment_user_id` bigint NOT NULL COMMENT '评论用户的id',
                              `reply_user_id` bigint NULL DEFAULT NULL COMMENT '回复用户的id',
                              `is_check` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否通过 (0否 1是)',
                              `create_time` datetime NOT NULL COMMENT '评论时间',
                              `update_time` datetime NOT NULL COMMENT '更新时间',
                              `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除（0：未删除，1：已删除）',
                              PRIMARY KEY (`comment_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 131 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;


-- Table structure for t_leave_word
-- create time: 2026/2/19 ; Status:unfinished
-- ----------------------------
DROP TABLE IF EXISTS `t_leave_word`;
CREATE TABLE `t_leave_word`  (
                                 `leave_word_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                                 `user_id` bigint NOT NULL COMMENT '留言用户id',
                                 `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '留言内容',
                                 `is_check` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否通过 (0否 1是)',
                                 `create_time` datetime NOT NULL COMMENT '留言时间',
                                 `update_time` datetime NOT NULL COMMENT '更新时间',
                                 `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除（0：未删除，1：已删除）',
                                 PRIMARY KEY (`leave_word_id`) USING BTREE
)ENGINE = InnoDB AUTO_INCREMENT = 57 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- Table structure for t_like
-- create time: 2026/2/24 ; Status:unfinished
-- ----------------------------
DROP TABLE IF EXISTS `t_like`;
CREATE TABLE `t_like`  (
                           `like_id` bigint NOT NULL AUTO_INCREMENT COMMENT '点赞表id',
                           `user_id` bigint NOT NULL COMMENT '点赞的用户id',
                           `type` tinyint NOT NULL COMMENT '点赞类型(1,文章,2,评论,3留言板)',
                           `type_id` bigint NOT NULL COMMENT '点赞的文章id',
                           `create_time` datetime NOT NULL COMMENT '点赞时间',
                           `update_time` datetime NOT NULL COMMENT '修改时间',
                           PRIMARY KEY (`like_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 363 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- Table structure for t_favorite
-- create time: 2026/2/26 ; Status: newly created
DROP TABLE IF EXISTS `t_favorite`;
CREATE TABLE `t_favorite` (
  `favorite_id` bigint NOT NULL AUTO_INCREMENT COMMENT '收藏id',
  `user_id` bigint NOT NULL COMMENT '收藏的用户id',
  `type` int NOT NULL COMMENT '收藏类型(1,文章 2,留言板)',
  `type_id` bigint NOT NULL COMMENT '类型id',
  `is_check` int DEFAULT 1 COMMENT '是否有效 (0否 1是)',
  `create_time` datetime NOT NULL COMMENT '收藏时间',
  PRIMARY KEY (`favorite_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_tag
-- ----------------------------
DROP TABLE IF EXISTS `t_tag`;
CREATE TABLE `t_tag`  (
                          `tag_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '标签id',
                          `tag_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '标签名称',
                          `create_time` datetime NOT NULL COMMENT '标签创建时间',
                          `update_time` datetime NOT NULL COMMENT '标签更新时间',
                          `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除（0：未删除，1：已删除）',
                          PRIMARY KEY (`tag_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;


-- Table structure for t_tree_hole
-- create time: 2026/2/19 ; Status:unfinished
-- ----------------------------
-- Table structure for t_tree_hole
-- ----------------------------
DROP TABLE IF EXISTS `t_tree_hole`;
CREATE TABLE `t_tree_hole`  (
                                `tree_hole_id` bigint NOT NULL AUTO_INCREMENT COMMENT '树洞表id',
                                `user_id` bigint NOT NULL COMMENT '用户id',
                                `content` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '内容',
                                `is_check` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否通过 (0否 1是)',
                                `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP COMMENT '修改时间',
                                `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除（0：未删除，1：已删除）',
                                PRIMARY KEY (`tree_hole_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 57 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;


SET FOREIGN_KEY_CHECKS = 1;

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
