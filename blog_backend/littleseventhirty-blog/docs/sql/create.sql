-- Table structure for t_article
-- Created time: 2026/1/4 ; Status: unfinished
DROP TABLE IF EXISTS `t_article`
CREATE TABLE `t_article`
(
    `id`              BIGINT unsigned NOT NULL auto_increment comment '表id',
    `article_id`      BIGINT unsigned NOT NULL auto_increment comment '文章id',
    `user_id`         BIGINT                                                       NOT NULL comment '作者id',
    `category_id`     BIGINT                                                       NOT NULL commnet '分类id',
    `article_titile`  varchar(50) character set UTF8MB4 COLLATE UTF8MB4_0900_AI_CI NOT NULL comment '文章标题',
    `article_content` LONGTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL comment '文章内容',
    `artile_type`     TINYINT                                                      NOT NULL comment '类型（1原创 2转载 3翻译）',
    `status`          TINYINT                                                      NOT NULL comment '文章状态（1公开 2私密 3草稿）',
    `create_time`     DATETIME                                                     NOT NULL comment '文章创建时间',
    `update_time`     DATETIME                                                     NOT NULL comment '文章更新时间',
    `is_deleted`      TINYINT                                                      NOT NULL DEFAULT 0 comment '是否删除（0未删除，1已删除）',
    PRIMARY KEY ('artilce_id') USING btree
)ENGINE = InnoDB AUTO_INCREMENT=53 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;