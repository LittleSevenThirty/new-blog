-- Table structure for t_article
-- Created time: 2026/1/4 ; Status: unfinished
DROP TABLE IF EXISTS `t_article`;
CREATE TABLE `t_article`
(
    `id`              BIGINT unsigned NOT NULL auto_increment comment '表id',
    `article_id`      BIGINT unsigned NOT NULL comment '文章id',
    `user_id`         BIGINT                                                       NOT NULL comment '作者id',
    `category_id`     BIGINT                                                       NOT NULL comment '分类id',
    `article_title`  varchar(50) character set UTF8MB4 COLLATE UTF8MB4_0900_AI_CI NOT NULL comment '文章标题',
    `article_content` LONGTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL comment '文章内容',
    `article_type`     TINYINT                                                      NOT NULL comment '类型（1原创 2转载 3翻译）',
    `status`          TINYINT                                                      NOT NULL comment '文章状态（1公开 2私密 3草稿）',
    `visited_count`   BIGINT unsigned NOT NULL COMMENT '文章访问数目',
    `create_time`     DATETIME                                                     NOT NULL comment '文章创建时间',
    `update_time`     DATETIME                                                     NOT NULL comment '文章更新时间',
    `is_deleted`      TINYINT                                                      NOT NULL DEFAULT 0 comment '是否删除（0未删除，1已删除）',
    PRIMARY KEY (`id`) USING btree
)ENGINE = InnoDB AUTO_INCREMENT=53 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- 导出版本（可靠性高）
create table blog.t_article
(
    id              bigint unsigned auto_increment comment '表id'
        primary key,
    article_id      bigint unsigned   not null comment '文章id',
    user_id         bigint            not null comment '作者id',
    category_id     bigint            not null comment '分类id',
    article_title   varchar(50)       not null comment '文章标题',
    article_content longtext          not null comment '文章内容',
    article_type    tinyint           not null comment '类型（1原创 2转载 3翻译）',
    status          tinyint           not null comment '文章状态（1公开 2私密 3草稿）',
    create_time     datetime          not null comment '文章创建时间',
    update_time     datetime          not null comment '文章更新时间',
    is_deleted      tinyint default 0 not null comment '是否删除（0未删除，1已删除）',
    visited_count   bigint            not null comment '访问数量'
)
    row_format = DYNAMIC;

