-- 插入用户数据
INSERT INTO `sys_user` (`nickname`, `username`, `gender`, `password`, `avatar`, `introduce`, `email`, `register_type`, `login_type`, `login_time`, `create_time`, `update_time`, `is_delete`)
VALUES
('管理员', 'admin', 2, '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPGga31lW', 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=professional%20avatar%20of%20a%20programmer&image_size=square', '网站管理员', 'admin@example.com', 0, 0, NOW(), NOW(), NOW(), 0),
('Vue爱好者', 'vuefan', 0, '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPGga31lW', 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=casual%20avatar%20of%20a%20young%20man&image_size=square', '热爱前端开发，特别是Vue框架', 'vuefan@example.com', 0, 0, NOW(), NOW(), NOW(), 0),
('技术博主', 'techblogger', 1, '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPGga31lW', 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=professional%20avatar%20of%20a%20female%20developer&image_size=square', '分享前端技术心得', 'techblogger@example.com', 0, 0, NOW(), NOW(), NOW(), 0);

-- 插入网站信息
INSERT INTO `sys_website_info` (`webmaster_avatar`, `webmaster_name`, `webmaster_copy`, `webmaster_profile_background`, `gitee_link`, `github_link`, `website_name`, `header_notifation`, `sidebar_announcement`, `record_info`, `start_time`, `create_time`, `update_time`, `is_deleted`)
VALUES
('https://little-seven.oss-cn-beijing.aliyuncs.com/webSiteInfo/avatar.jpg', 'littleSevenThirty', '热爱技术，分享知识', 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=modern%20website%20background&image_size=landscape_16_9', 'https://gitee.com', 'https://github.com', '小七の博客', '🎉 欢迎来到我的博客！新文章已上线～', '📢 本站已支持暗色主题，欢迎体验！', '京ICP备12345678号', '2026-01-01', NOW(), NOW(), 0);

-- 插入分类数据
INSERT INTO `t_category` (`category_name`, `create_time`, `update_time`, `is_deleted`)
VALUES
('Vue', NOW(), NOW(), 0),
('前端开发', NOW(), NOW(), 0),
('JavaScript', NOW(), NOW(), 0),
('CSS', NOW(), NOW(), 0),
('HTML', NOW(), NOW(), 0);

-- 插入标签数据
INSERT INTO `t_tag` (`tag_name`, `create_time`, `update_time`, `is_deleted`)
VALUES
('Vue 3', NOW(), NOW(), 0),
('侦听器', NOW(), NOW(), 0),
('watch', NOW(), NOW(), 0),
('watchEffect', NOW(), NOW(), 0),
('响应式', NOW(), NOW(), 0),
('前端', NOW(), NOW(), 0),
('JavaScript', NOW(), NOW(), 0),
('组件', NOW(), NOW(), 0),
('路由', NOW(), NOW(), 0),
('状态管理', NOW(), NOW(), 0);

-- 插入文章数据
INSERT INTO `t_article` (`user_id`, `category_id`, `article_title`, `article_content`, `article_type`, `status`, `visited_count`, `is_top`, `create_time`, `update_time`, `article_cover`, `is_deleted`)
VALUES
(1, 1, 'Vue 3 侦听器（Watchers）新手完全指南', '# Vue 3 侦听器（Watchers）新手完全指南\n\n> 本文基于 Vue 3 官方文档整理，旨在帮助初学者系统理解侦听器的使用方法。侦听器是 Vue 响应式系统的重要组成部分，让我们能够在数据变化时执行特定操作。\n\n## 一、什么是侦听器？\n\n在 Vue 中，**侦听器**（Watchers）是一种让我们能够在数据变化时**执行副作用操作**的机制。当你需要在数据变化时执行异步操作、复杂的计算或直接操作 DOM 时，侦听器就派上用场了。\n\n> **小贴士**：计算属性（Computed）适合处理简单的派生状态，而侦听器则适合处理更复杂的逻辑，尤其是需要执行异步操作或产生副作用的场景。', 1, 1, 100, 1, NOW(), NOW(), 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Vue%203%20watchers%20tutorial%20cover&image_size=landscape_16_9', 0),
(2, 1, 'Vue 3 组件基础与动态组件', '# Vue 3 组件基础与动态组件\n\n> 本文介绍 Vue 3 中的组件基础概念和动态组件的使用方法，帮助开发者更好地理解和应用组件化开发。\n\n## 一、组件基础\n\n组件是 Vue 应用的基本构建块，允许我们将 UI 拆分为独立、可复用的模块。\n\n### 1. 定义组件\n\n在 Vue 3 中，我们可以使用 `defineComponent` 函数或 `<script setup>` 语法来定义组件。\n\n### 2. 组件通信\n\n- **Props**：父组件向子组件传递数据\n- **Events**：子组件向父组件发送消息\n- **Slots**：父组件向子组件注入内容', 1, 1, 80, 0, NOW(), NOW(), 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Vue%203%20components%20tutorial%20cover&image_size=landscape_16_9', 0),
(3, 2, '前端开发最佳实践', '# 前端开发最佳实践\n\n> 本文总结了前端开发中的一些最佳实践，帮助开发者编写更高效、更可维护的代码。\n\n## 一、代码组织\n\n- **模块化**：将代码拆分为小而专注的模块\n- **命名规范**：使用一致的命名约定\n- **注释**：添加清晰的注释说明复杂逻辑\n\n## 二、性能优化\n\n- **减少 HTTP 请求**：合并文件、使用缓存\n- **优化资源加载**：使用懒加载、预加载\n- **减少 DOM 操作**：使用虚拟 DOM、批量更新', 1, 1, 60, 0, NOW(), NOW(), 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=frontend%20development%20best%20practices%20cover&image_size=landscape_16_9', 0);

-- 插入文章标签关联数据
INSERT INTO `t_article_tag` (`article_id`, `tag_id`, `create_time`, `is_deleted`)
VALUES
(1, 1, NOW(), 0),
(1, 2, NOW(), 0),
(1, 3, NOW(), 0),
(1, 4, NOW(), 0),
(1, 5, NOW(), 0),
(2, 1, NOW(), 0),
(2, 8, NOW(), 0),
(3, 6, NOW(), 0),
(3, 7, NOW(), 0);

-- 插入轮播图数据
INSERT INTO `t_banner` (`path`, `size`, `type`, `user_id`, `order`, `create_time`)
VALUES
('https://little-seven.oss-cn-beijing.aliyuncs.com/banner/b1.jpg', 1024000, 'jeg', 1, 1, NOW()),
('https://little-seven.oss-cn-beijing.aliyuncs.com/banner/b2.jpg', 1024000, 'image/jpeg', 1, 2, NOW()),
('https://little-seven.oss-cn-beijing.aliyuncs.com/banner/b3.webp', 1024000, 'image/jpeg', 1, 3, NOW()),
('https://little-seven.oss-cn-beijing.aliyuncs.com/banner/b4.jpg', 1024000, 'image/jpeg', 1, 3, NOW()),
('https://little-seven.oss-cn-beijing.aliyuncs.com/banner/b5.jpeg', 1024000, 'image/jpeg', 1, 3, NOW()),
('https://little-seven.oss-cn-beijing.aliyuncs.com/banner/b6.jpg', 1024000, 'image/jpeg', 1, 3, NOW());

-- 插入评论数据
INSERT INTO `t_comment` (`type`, `type_id`, `parent_id`, `reply_id`, `comment_content`, `comment_user_id`, `reply_user_id`, `is_check`, `create_time`, `update_time`, `is_deleted`)
VALUES
(1, 1, NULL, NULL, '这篇文章写得很详细，对我理解 Vue 3 侦听器很有帮助！', 2, NULL, 1, NOW(), NOW(), 0),
(1, 1, 1, NULL, '谢谢你的反馈，我会继续分享更多 Vue 相关的内容。', 1, 2, 1, NOW(), NOW(), 0),
(1, 2, NULL, NULL, '动态组件的使用场景讲解得很清楚，学到了很多。', 3, NULL, 1, NOW(), NOW(), 0);

-- 插入留言数据
INSERT INTO `t_leave_word` (`user_id`, `content`, `is_check`, `create_time`, `update_time`, `is_deleted`)
VALUES
(2, '网站内容很丰富，希望能看到更多前端开发相关的文章。', 1, NOW(), NOW(), 0),
(3, '感谢分享，这些教程对我帮助很大。', 1, NOW(), NOW(), 0);

-- 插入点赞数据
INSERT INTO `t_like` (`user_id`, `type`, `type_id`, `create_time`, `update_time`)
VALUES
(2, 1, 1, NOW(), NOW()),
(3, 1, 1, NOW(), NOW()),
(2, 1, 2, NOW(), NOW()),
(3, 2, 1, NOW(), NOW());

-- 插入收藏数据
INSERT INTO `t_favorite` (`user_id`, `type`, `type_id`, `is_check`, `create_time`)
VALUES
(2, 1, 1, 1, NOW()),
(3, 1, 2, 1, NOW());

-- 插入树洞数据
INSERT INTO `t_tree_hole` (`user_id`, `content`, `is_check`, `create_time`, `update_time`, `is_deleted`)
VALUES
(2, '今天学习了 Vue 3 的组合式 API，感觉比选项式 API 更灵活。', 1, NOW(), NOW(), 0),
(3, '前端开发真的很有趣，每天都有新的东西要学习。', 1, NOW(), NOW(), 0);

-- 插入登录日志数据
INSERT INTO `sys_login_log` (`user_name`, `ip`, `address`, `browser`, `os`, `type`, `state`, `message`, `create_time`, `update_time`, `is_deleted`)
VALUES
('admin', '127.0.0.1', '本地', 'Chrome', 'Windows 10', 1, 0, '管理员登录成功', NOW(), NOW(), 0),
('vuefan', '192.168.1.100', '局域网', 'Firefox', 'Windows 10', 0, 0, '用户登录成功', NOW(), NOW(), 0),
('techblogger', '192.168.1.101', '局域网', 'Safari', 'MacOS', 0, 0, '用户登录成功', NOW(), NOW(), 0);