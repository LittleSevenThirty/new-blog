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


-- --------------------------------
--    new
-- --------------------------------

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'ADMIN', 'ADMIN', 0, '$2a$10$VyFtQ3T943p3NY5R0IxzIONjdqABmuCSGiHe5uV8d1ujLGYuS2KZe', 'http://cdn.kuailemao.lielfw.cn/articleCover/Sara11676693014447852.png', '该用户比较懒还未添加简介', 'test@qq.com', '127.0.0.1', 0, '内网IP', '127.0.0.1', '内网IP', 0, '2023-12-21 19:29:37', 0, '2023-10-13 15:16:01', '2023-12-21 19:29:37', 0);
INSERT INTO `sys_user` VALUES (2, '快乐猫ce', 'hh', 0, '$2a$10$Apqq5cDdBQSKRO1v99qb4.rwSHC/5rFY1AAPw3BGgigGOpS9mnUga', 'http://cdn.kuailemao.lielfw.cn/userAvatar/21676863574334604.png', '这个人很懒，什么都没有留下', 'test@qq.com', '127.0.0.1', 0, '内网IP', '127.0.0.1', '内网IP', 0, '2023-12-15 10:18:28', 1, '2023-12-15 10:18:05', '2024-01-16 12:32:20', 1);
INSERT INTO `sys_user` VALUES (3, '快乐猫用户', 'qq', 0, '$2a$10$8QUAhUAD4zoLHHqcN644/.8XzN5TBJmrYIKJx.tgvttmXjh9VJi2K', 'http://cdn.kuailemao.lielfw.cn/userAvatar/21676863574334604.png', '这个人很懒，什么都没有留下', 'test@qq.com', '127.0.0.1', 0, '内网IP', '127.0.0.1', '内网IP', 0, '2023-12-19 10:17:15', 0, '2023-12-19 10:17:07', '2024-02-28 10:37:26', 1);
INSERT INTO `sys_user` VALUES (5, '快乐猫用户', 'qq', 0, '$2a$10$8QUAhUAD4zoLHHqcN644/.8XzN5TBJmrYIKJx.tgvttmXjh9VJi2K', 'http://cdn.kuailemao.lielfw.cn/userAvatar/21676863574334604.png', '这个人很懒，什么都没有留下', 'test@qq.com', '127.0.0.1', 0, '内网IP', '127.0.0.1', '内网IP', 0, '2023-12-19 10:17:15', 0, '2023-12-19 10:17:07', '2024-02-28 10:37:26', 1);
INSERT INTO `sys_user` VALUES (6, '快乐猫用户', 'qq', 0, '$2a$10$8QUAhUAD4zoLHHqcN644/.8XzN5TBJmrYIKJx.tgvttmXjh9VJi2K', 'http://cdn.kuailemao.lielfw.cn/userAvatar/21676863574334604.png', '这个人很懒，什么都没有留下', 'test@qq.com', '127.0.0.1', 0, '内网IP', '127.0.0.1', '内网IP', 0, '2023-12-19 10:17:15', 0, '2023-12-19 10:17:07', '2024-02-28 10:37:26', 1);
INSERT INTO `sys_user` VALUES (10, 'ruyu', 'ruyu', 0, '$2a$10$g4u8DiBotv.H8kvF35CgM.i1l3v0/JbBya3PvGiAghJOfOcj9RfIS', 'http://cdn.kuailemao.lielfw.cn/userAvatar/21676863574334604.png', '这个人很懒，什么都没有留下', 'test@163.com', '127.0.0.1', 0, '内网IP', '127.0.0.1', '内网IP', 0, '2023-12-11 17:21:58', 0, '2023-10-17 11:29:44', '2024-02-28 10:37:26', 1);
INSERT INTO `sys_user` VALUES (15, 'test', 'test', 0, '$2a$10$03DwMR0YxjV7KJ.D9YSPwO0qUwIT8lF1mw89SsKrUVIPVK5M2818y', 'http://cdn.kuailemao.lielfw.cn/userAvatar/21676863574334604.png', '这个人很懒，什么都没有留下', 'test@qq.com', '127.0.0.1', 0, '内网IP', '127.0.0.1', '内网IP', 0, '2023-11-30 15:50:58', 0, '2023-10-18 21:26:31', '2024-02-28 10:37:26', 1);
INSERT INTO `sys_user` VALUES (11937114, 'kuailemao', 'kuailemao', 0, '$2a$10$6gAaRvqfhNxjc5fr6e4sauX63SclNP193gnpzeLnU/nATSRS0CG4C', 'https://foruda.gitee.com/avatar/1667975309022664009/11937114_kuailemao_1667975308.png', NULL, NULL, '127.0.0.1', 1, '内网IP', '127.0.0.1', '内网IP', 1, '2023-12-22 15:14:53', 0, '2023-12-22 15:14:53', '2024-02-28 11:13:00', 0);
INSERT INTO `sys_user` VALUES (88065987, 'aaa', 'kuailemao', 0, '$2a$10$q7d2HRUMtxNKSIgGha3tg.syuaEdrU41AL1puQ26GgIVdzQurEMSy', 'https://avatars.githubusercontent.com/u/88065987?v=4', NULL, NULL, '127.0.0.1', 2, '内网IP', '127.0.0.1', '内网IP', 2, '2023-12-24 19:58:20', 0, '2023-12-24 19:58:20', '2024-02-28 11:13:20', 0);
INSERT INTO `sys_user` VALUES (88065988, '快乐猫用户', 'mao', 0, '$2a$10$mvpsj1LCUe8Vl9FPzAxzKOhuin7sJ6lQZD8r8Mo09kltVkZgKVQuG', 'http://cdn.kuailemao.lielfw.cn/userAvatar/21676863574334604.png', '这个人很懒，什么都没有留下', 'test@qq.com', '127.0.0.1', 0, '内网IP', NULL, NULL, NULL, '2024-02-28 10:54:00', 0, '2024-02-28 10:54:00', '2024-02-28 10:54:00', 0);

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1, '1');

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES (162, 1, 7);
INSERT INTO `sys_role_permission` VALUES (163, 1, 8);
INSERT INTO `sys_role_permission` VALUES (164, 1, 9);
INSERT INTO `sys_role_permission` VALUES (165, 1, 10);
INSERT INTO `sys_role_permission` VALUES (166, 1, 11);
INSERT INTO `sys_role_permission` VALUES (167, 1, 12);
INSERT INTO `sys_role_permission` VALUES (168, 1, 13);
INSERT INTO `sys_role_permission` VALUES (170, 1, 15);
INSERT INTO `sys_role_permission` VALUES (171, 1, 16);
INSERT INTO `sys_role_permission` VALUES (172, 1, 17);
INSERT INTO `sys_role_permission` VALUES (173, 1, 18);
INSERT INTO `sys_role_permission` VALUES (174, 1, 19);
INSERT INTO `sys_role_permission` VALUES (175, 1, 20);
INSERT INTO `sys_role_permission` VALUES (176, 1, 21);
INSERT INTO `sys_role_permission` VALUES (177, 1, 22);
INSERT INTO `sys_role_permission` VALUES (178, 1, 23);
INSERT INTO `sys_role_permission` VALUES (179, 1, 24);
INSERT INTO `sys_role_permission` VALUES (180, 1, 27);
INSERT INTO `sys_role_permission` VALUES (181, 1, 28);
INSERT INTO `sys_role_permission` VALUES (182, 1, 30);
INSERT INTO `sys_role_permission` VALUES (183, 1, 31);
INSERT INTO `sys_role_permission` VALUES (184, 1, 33);
INSERT INTO `sys_role_permission` VALUES (185, 1, 34);
INSERT INTO `sys_role_permission` VALUES (186, 1, 35);
INSERT INTO `sys_role_permission` VALUES (192, 1, 5);
INSERT INTO `sys_role_permission` VALUES (193, 1, 6);
INSERT INTO `sys_role_permission` VALUES (198, 1, 36);
INSERT INTO `sys_role_permission` VALUES (199, 1, 37);
INSERT INTO `sys_role_permission` VALUES (200, 1, 38);
INSERT INTO `sys_role_permission` VALUES (201, 1, 14);
INSERT INTO `sys_role_permission` VALUES (203, 2, 7);
INSERT INTO `sys_role_permission` VALUES (211, 1, 87);
INSERT INTO `sys_role_permission` VALUES (212, 1, 45);
INSERT INTO `sys_role_permission` VALUES (213, 1, 91);
INSERT INTO `sys_role_permission` VALUES (214, 1, 92);
INSERT INTO `sys_role_permission` VALUES (215, 1, 93);
INSERT INTO `sys_role_permission` VALUES (216, 1, 94);
INSERT INTO `sys_role_permission` VALUES (217, 1, 97);
INSERT INTO `sys_role_permission` VALUES (218, 1, 98);
INSERT INTO `sys_role_permission` VALUES (219, 1, 99);
INSERT INTO `sys_role_permission` VALUES (220, 1, 100);
INSERT INTO `sys_role_permission` VALUES (223, 1, 101);
INSERT INTO `sys_role_permission` VALUES (224, 1, 103);
INSERT INTO `sys_role_permission` VALUES (225, 1, 102);
INSERT INTO `sys_role_permission` VALUES (226, 1, 104);
INSERT INTO `sys_role_permission` VALUES (227, 1, 105);
INSERT INTO `sys_role_permission` VALUES (228, 1, 106);
INSERT INTO `sys_role_permission` VALUES (229, 1, 107);
INSERT INTO `sys_role_permission` VALUES (230, 1, 108);
INSERT INTO `sys_role_permission` VALUES (231, 1, 109);
INSERT INTO `sys_role_permission` VALUES (232, 1, 110);
INSERT INTO `sys_role_permission` VALUES (233, 1, 111);
INSERT INTO `sys_role_permission` VALUES (234, 1, 112);
INSERT INTO `sys_role_permission` VALUES (235, 1, 113);
INSERT INTO `sys_role_permission` VALUES (236, 1, 114);
INSERT INTO `sys_role_permission` VALUES (237, 1, 115);
INSERT INTO `sys_role_permission` VALUES (238, 1, 116);
INSERT INTO `sys_role_permission` VALUES (239, 1, 117);
INSERT INTO `sys_role_permission` VALUES (240, 1, 118);
INSERT INTO `sys_role_permission` VALUES (241, 1, 119);
INSERT INTO `sys_role_permission` VALUES (242, 1, 120);
INSERT INTO `sys_role_permission` VALUES (243, 1, 121);
INSERT INTO `sys_role_permission` VALUES (244, 1, 122);
INSERT INTO `sys_role_permission` VALUES (245, 1, 123);
INSERT INTO `sys_role_permission` VALUES (246, 1, 124);
INSERT INTO `sys_role_permission` VALUES (247, 1, 125);
INSERT INTO `sys_role_permission` VALUES (248, 1, 126);
INSERT INTO `sys_role_permission` VALUES (249, 1, 127);
INSERT INTO `sys_role_permission` VALUES (250, 1, 128);
INSERT INTO `sys_role_permission` VALUES (251, 1, 129);
INSERT INTO `sys_role_permission` VALUES (252, 1, 130);
INSERT INTO `sys_role_permission` VALUES (253, 1, 131);
INSERT INTO `sys_role_permission` VALUES (254, 1, 132);
INSERT INTO `sys_role_permission` VALUES (255, 1, 133);
INSERT INTO `sys_role_permission` VALUES (256, 1, 134);
INSERT INTO `sys_role_permission` VALUES (257, 1, 136);
INSERT INTO `sys_role_permission` VALUES (258, 1, 137);
INSERT INTO `sys_role_permission` VALUES (259, 1, 138);
INSERT INTO `sys_role_permission` VALUES (260, 1, 139);
INSERT INTO `sys_role_permission` VALUES (261, 1, 140);
INSERT INTO `sys_role_permission` VALUES (262, 1, 141);
INSERT INTO `sys_role_permission` VALUES (263, 1, 142);
INSERT INTO `sys_role_permission` VALUES (264, 1, 143);
INSERT INTO `sys_role_permission` VALUES (265, 1, 144);
INSERT INTO `sys_role_permission` VALUES (266, 1, 145);
INSERT INTO `sys_role_permission` VALUES (268, 1, 146);
INSERT INTO `sys_role_permission` VALUES (269, 1, 147);

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES (5, '获取菜单', 'system:menu:list', 2, '2023-12-06 08:41:49', '2023-12-14 17:21:57', 0);
INSERT INTO `sys_permission` VALUES (6, '查询菜单', 'system:menu:select', 2, '2023-12-05 08:41:54', '2023-12-07 12:02:31', 0);
INSERT INTO `sys_permission` VALUES (7, '修改菜单', 'system:menu:update', 2, '2023-12-04 08:41:54', '2023-12-12 20:36:49', 0);
INSERT INTO `sys_permission` VALUES (8, '删除菜单', 'system:menu:delete', 2, '2023-12-04 08:41:54', '2023-12-11 21:03:10', 0);
INSERT INTO `sys_permission` VALUES (9, '添加菜单', 'system:menu:add', 2, '2023-12-02 08:41:54', '2023-12-04 08:41:54', 0);
INSERT INTO `sys_permission` VALUES (10, '修改菜单角色列表', 'system:menu:role:list', 2, '2023-12-02 08:41:54', '2023-12-02 08:41:54', 0);
INSERT INTO `sys_permission` VALUES (11, '获取所有角色', 'system:role:list', 23, '2023-12-02 08:41:54', '2023-12-02 08:41:54', 0);
INSERT INTO `sys_permission` VALUES (12, '更新角色状态', 'system:role:status:update', 23, '2023-12-02 08:41:54', '2023-12-02 08:41:54', 0);
INSERT INTO `sys_permission` VALUES (13, '获取对应角色信息', 'system:role:get', 23, '2023-12-02 08:41:54', '2023-12-02 08:41:54', 0);
INSERT INTO `sys_permission` VALUES (14, '修改角色信息', 'system:role:update', 23, '2023-12-02 08:41:54', '2023-12-02 08:41:54', 0);
INSERT INTO `sys_permission` VALUES (15, '根据id删除角色', 'system:role:delete', 23, '2023-12-02 08:41:54', '2023-12-02 08:41:54', 0);
INSERT INTO `sys_permission` VALUES (16, '根据条件搜索角色', 'system:role:search', 23, '2023-12-02 08:41:54', '2023-12-02 08:41:54', 0);
INSERT INTO `sys_permission` VALUES (17, '查询拥有角色的用户列表', 'system:user:role:list', 23, '2023-12-02 08:41:54', '2023-12-02 08:41:54', 0);
INSERT INTO `sys_permission` VALUES (18, '查询未拥有角色的用户列表', 'system:not:role:user:list', 23, '2023-12-02 08:41:54', '2023-12-06 10:43:49', 0);
INSERT INTO `sys_permission` VALUES (19, '添加用户角色关系', 'system:user:role:add', 23, '2023-12-05 08:41:54', '2023-12-11 21:02:51', 0);
INSERT INTO `sys_permission` VALUES (20, '删除用户角色关系', 'system:user:role:delete', 23, '2023-12-02 08:41:54', '2023-12-02 08:41:54', 0);
INSERT INTO `sys_permission` VALUES (21, '所有权限', 'system:permission:list', 24, '2023-12-06 10:34:21', '2023-12-06 10:34:24', 0);
INSERT INTO `sys_permission` VALUES (22, '查询所有权限所在菜单', 'system:permission:menu:list', 24, '2023-12-06 14:26:28', '2023-12-06 14:26:31', 0);
INSERT INTO `sys_permission` VALUES (23, '搜索权限', 'system:permission:search', 24, '2023-12-06 15:18:29', '2023-12-06 15:18:33', 0);
INSERT INTO `sys_permission` VALUES (24, '添加权限', 'system:permission:add', 24, '2023-12-06 19:12:47', '2023-12-06 19:12:50', 0);
INSERT INTO `sys_permission` VALUES (25, '测试测试', 'sdss', 3, '2023-12-06 20:26:46', '2023-12-06 20:26:46', 1);
INSERT INTO `sys_permission` VALUES (26, 'sdsd', 'sdd', 26, '2023-12-06 20:29:24', '2023-12-06 20:29:24', 1);
INSERT INTO `sys_permission` VALUES (27, '获取要修改的权限信息', 'system:permission:get', 24, '2023-12-06 20:48:33', '2023-12-06 20:48:33', 0);
INSERT INTO `sys_permission` VALUES (28, '修改权限字符信息', 'system:permission:update', 24, '2023-12-07 12:01:34', '2023-12-07 12:01:36', 0);
INSERT INTO `sys_permission` VALUES (29, 'sss', '测试', 21, '2023-12-07 12:06:26', '2023-12-07 12:06:26', 1);
INSERT INTO `sys_permission` VALUES (30, '删除权限', 'system:permission:delete', 24, '2023-12-07 12:14:14', '2023-12-07 12:14:14', 0);
INSERT INTO `sys_permission` VALUES (31, '查询权限的角色列表', 'system:permission:role:list', 65, '2023-12-07 15:02:03', '2023-12-07 15:02:03', 0);
INSERT INTO `sys_permission` VALUES (32, '2121', '测试', 26, '2023-12-07 17:17:13', '2023-12-07 17:17:13', 1);
INSERT INTO `sys_permission` VALUES (33, '查询没有该权限的角色列表', 'system:permission:role:not:list', 65, '2023-12-07 17:41:38', '2023-12-07 17:41:38', 0);
INSERT INTO `sys_permission` VALUES (34, '单个/批量添加角色权限关系', 'system:permission:role:add', 65, '2023-12-07 20:53:14', '2023-12-08 10:51:10', 0);
INSERT INTO `sys_permission` VALUES (35, '删除角色权限关系', 'system:permission:role:delete', 65, '2023-12-07 21:00:55', '2023-12-07 21:00:55', 0);
INSERT INTO `sys_permission` VALUES (36, '显示所有登录日志', 'system:log:login:list', 27, '2023-12-11 16:20:00', '2023-12-14 17:48:07', 0);
INSERT INTO `sys_permission` VALUES (37, '登录日志搜索', 'system:log:login:search', 27, '2023-12-11 19:51:27', '2023-12-11 19:51:27', 0);
INSERT INTO `sys_permission` VALUES (38, '删除/清空登录日志', 'system:log:login:delete', 27, '2023-12-11 20:19:08', '2023-12-11 20:19:08', 0);
INSERT INTO `sys_permission` VALUES (45, '显示所有操作日志', 'system:log:list', 26, '2023-12-13 16:13:41', '2023-12-13 16:13:41', 0);
INSERT INTO `sys_permission` VALUES (46, '显示所有操作日志', 'system:log:list', 26, '2023-12-13 16:13:41', '2023-12-13 16:13:41', 1);
INSERT INTO `sys_permission` VALUES (87, '添加角色信息', 'system:role:add', 23, '2023-12-13 17:23:42', '2023-12-13 17:23:42', 0);
INSERT INTO `sys_permission` VALUES (88, '添加角色信息', 'system:role:add', 23, '2023-12-13 17:23:42', '2023-12-13 17:23:42', 1);
INSERT INTO `sys_permission` VALUES (91, '搜索操作日志', 'system:log:search', 26, '2023-12-13 20:43:04', '2023-12-13 20:43:04', 0);
INSERT INTO `sys_permission` VALUES (92, '删除/清空操作日志', 'system:log:delete', 26, '2023-12-14 08:45:38', '2023-12-14 08:45:38', 0);
INSERT INTO `sys_permission` VALUES (93, 'id查询操作日志', 'system:log:select:id', 26, '2023-12-14 09:00:53', '2023-12-14 09:00:53', 0);
INSERT INTO `sys_permission` VALUES (94, '获取服务监控数据', 'monitor:server:list', 44, '2023-12-14 15:21:21', '2023-12-14 15:21:21', 0);
INSERT INTO `sys_permission` VALUES (95, 'ssss', 'system:menu:list	', 26, '2023-12-14 17:19:50', '2023-12-14 17:19:50', 1);
INSERT INTO `sys_permission` VALUES (96, 'dd', 'system:menu:lists', 21, '2023-12-14 17:21:46', '2023-12-14 17:21:46', 1);
INSERT INTO `sys_permission` VALUES (97, '获取用户列表', 'system:user:list', 3, '2023-12-18 12:07:00', '2023-12-18 12:07:00', 0);
INSERT INTO `sys_permission` VALUES (98, '搜索用户列表', 'system:user:search', 3, '2023-12-18 14:15:46', '2023-12-18 14:15:46', 0);
INSERT INTO `sys_permission` VALUES (99, '更新用户状态', 'system:user:status:update', 3, '2023-12-18 15:11:34', '2023-12-18 15:11:34', 0);
INSERT INTO `sys_permission` VALUES (100, '获取用户详细信息', 'system:user:details', 3, '2023-12-18 16:40:52', '2023-12-18 16:40:52', 0);
INSERT INTO `sys_permission` VALUES (101, '删除用户&用户角色关系', 'system:user:delete', 3, '2023-12-19 10:11:46', '2023-12-19 10:12:15', 0);
INSERT INTO `sys_permission` VALUES (102, '查询没有该用户的角色列表', 'system:user:role:not:list', 23, '2023-12-19 11:10:11', '2023-12-19 11:10:11', 0);
INSERT INTO `sys_permission` VALUES (103, '查询拥有用户的角色列表', 'system:role:user:list', 23, '2023-12-19 11:17:55', '2023-12-19 11:17:55', 0);
INSERT INTO `sys_permission` VALUES (104, '搜索管理菜单列表', 'system:search:menu:list', 2, '2023-12-25 11:48:02', '2023-12-25 11:48:02', 0);
INSERT INTO `sys_permission` VALUES (105, '添加或修改站长信息', 'blog:update:websiteInfo', 29, '2023-12-27 16:19:23', '2024-01-04 10:49:12', 0);
INSERT INTO `sys_permission` VALUES (106, '查看网站信息-后台', 'blog:get:websiteInfo', 29, '2023-12-29 08:52:51', '2024-01-22 22:18:56', 0);
INSERT INTO `sys_permission` VALUES (107, '新增标签', 'blog:tag:add', 33, '2024-01-04 10:55:39', '2024-01-04 10:55:39', 0);
INSERT INTO `sys_permission` VALUES (108, '新增分类', 'blog:category:add', 34, '2024-01-04 11:17:12', '2024-01-04 11:17:12', 0);
INSERT INTO `sys_permission` VALUES (109, '发布文章相关', 'blog:publish:article', 31, '2024-01-05 15:01:54', '2024-01-05 15:01:54', 0);
INSERT INTO `sys_permission` VALUES (110, '获取文章列表', 'blog:article:list', 32, '2024-01-07 11:28:14', '2024-01-07 11:28:14', 0);
INSERT INTO `sys_permission` VALUES (111, '搜索文章', 'blog:article:search', 32, '2024-01-07 19:30:10', '2024-01-07 19:30:10', 0);
INSERT INTO `sys_permission` VALUES (112, '修改文章', 'blog:article:update', 32, '2024-01-07 19:56:37', '2024-01-07 19:56:37', 0);
INSERT INTO `sys_permission` VALUES (113, '回显文章数据', 'blog:article:echo', 31, '2024-01-08 09:24:01', '2024-01-08 09:24:01', 0);
INSERT INTO `sys_permission` VALUES (114, '删除文章', 'blog:article:delete', 30, '2024-01-08 11:29:23', '2024-01-08 11:29:23', 0);
INSERT INTO `sys_permission` VALUES (115, '后台留言列表', 'blog:leaveword:list', 36, '2024-01-12 21:14:18', '2024-01-12 21:14:18', 0);
INSERT INTO `sys_permission` VALUES (116, '搜索后台留言列表', 'blog:leaveword:search', 36, '2024-01-16 08:50:46', '2024-01-16 08:50:46', 0);
INSERT INTO `sys_permission` VALUES (117, '修改留言是否通过', 'blog:leaveword:isCheck', 36, '2024-01-16 10:02:20', '2024-01-16 10:02:20', 0);
INSERT INTO `sys_permission` VALUES (118, '删除留言', 'blog:leaveword:delete', 36, '2024-01-16 11:11:59', '2024-01-16 11:11:59', 0);
INSERT INTO `sys_permission` VALUES (119, '获取标签列表', 'blog:tag:list', 33, '2024-01-18 14:30:10', '2024-01-18 14:30:10', 0);
INSERT INTO `sys_permission` VALUES (120, '搜索标签列表', 'blog:tag:search', 33, '2024-01-18 14:47:10', '2024-01-18 14:47:10', 0);
INSERT INTO `sys_permission` VALUES (121, '修改标签', 'blog:tag:update', 33, '2024-01-18 15:56:45', '2024-01-18 15:56:45', 0);
INSERT INTO `sys_permission` VALUES (122, '删除标签', 'blog:tag:delete', 33, '2024-01-18 16:16:41', '2024-01-18 16:16:41', 0);
INSERT INTO `sys_permission` VALUES (123, '获取分类列表', 'blog:category:list', 34, '2024-01-18 22:42:29', '2024-01-18 22:43:28', 0);
INSERT INTO `sys_permission` VALUES (124, '搜索分类列表', 'blog:category:search', 34, '2024-01-18 22:43:06', '2024-01-18 22:43:06', 0);
INSERT INTO `sys_permission` VALUES (125, '修改分类', 'blog:category:update', 34, '2024-01-18 22:44:21', '2024-01-18 22:44:21', 0);
INSERT INTO `sys_permission` VALUES (126, '删除分类', 'blog:category:delete', 34, '2024-01-18 22:44:51', '2024-01-18 22:44:51', 0);
INSERT INTO `sys_permission` VALUES (127, '后台树洞列表', 'blog:treeHole:list', 37, '2024-01-19 21:25:39', '2024-01-19 21:25:39', 0);
INSERT INTO `sys_permission` VALUES (128, '搜索后台树洞列表', 'blog:treeHole:search', 37, '2024-01-19 21:26:03', '2024-01-19 21:26:03', 0);
INSERT INTO `sys_permission` VALUES (129, '修改树洞是否通过', 'blog:treeHole:isCheck', 37, '2024-01-19 21:26:28', '2024-01-19 21:26:28', 0);
INSERT INTO `sys_permission` VALUES (130, '删除树洞', 'blog:treeHole:delete', 37, '2024-01-19 21:27:20', '2024-01-19 21:27:20', 0);
INSERT INTO `sys_permission` VALUES (131, '搜索后台收藏列表', 'blog:favorite:search', 43, '2024-01-21 09:36:25', '2024-01-21 09:36:25', 0);
INSERT INTO `sys_permission` VALUES (132, '修改收藏是否通过', 'blog:favorite:isCheck', 43, '2024-01-21 09:36:47', '2024-01-21 09:36:47', 0);
INSERT INTO `sys_permission` VALUES (133, '删除收藏', 'blog:favorite:delete', 43, '2024-01-21 09:37:11', '2024-01-21 09:37:11', 0);
INSERT INTO `sys_permission` VALUES (134, '后台收藏列表', 'blog:favorite:list', 43, '2024-01-21 09:39:35', '2024-01-21 09:39:35', 0);
INSERT INTO `sys_permission` VALUES (135, '后台留言列表', 'blog:chatGpt:list', 38, '2024-01-21 11:05:47', '2024-01-21 11:05:47', 1);
INSERT INTO `sys_permission` VALUES (136, '后台聊天列表', 'blog:chatGpt:list', 38, '2024-01-21 11:06:39', '2024-01-21 11:06:39', 0);
INSERT INTO `sys_permission` VALUES (137, '搜索后台聊天列表', 'blog:chatGpt:search', 38, '2024-01-21 11:07:18', '2024-01-21 11:07:18', 0);
INSERT INTO `sys_permission` VALUES (138, '修改聊天是否通过', 'blog:chatGpt:isCheck', 38, '2024-01-21 11:07:53', '2024-01-21 11:07:53', 0);
INSERT INTO `sys_permission` VALUES (139, '删除聊天', 'blog:chatGpt:delete', 38, '2024-01-21 11:08:18', '2024-01-21 11:08:18', 0);
INSERT INTO `sys_permission` VALUES (140, '后台评论列表', 'blog:comment:list', 35, '2024-01-22 09:40:40', '2024-01-22 09:40:40', 0);
INSERT INTO `sys_permission` VALUES (141, '搜索后台评论列表', 'blog:comment:search', 35, '2024-01-22 11:02:58', '2024-01-22 11:02:58', 0);
INSERT INTO `sys_permission` VALUES (142, '修改评论是否通过', 'blog:comment:isCheck', 35, '2024-01-22 20:01:50', '2024-01-22 20:01:50', 0);
INSERT INTO `sys_permission` VALUES (143, '删除评论', 'blog:comment:delete', 35, '2024-01-22 20:02:20', '2024-01-22 20:02:20', 0);
INSERT INTO `sys_permission` VALUES (144, '后台友链列表', 'blog:link:list', 39, '2024-01-22 21:03:24', '2024-01-22 21:03:24', 0);
INSERT INTO `sys_permission` VALUES (145, '搜索后台友链列表', 'blog:link:search', 39, '2024-01-22 21:03:46', '2024-01-22 21:03:46', 0);
INSERT INTO `sys_permission` VALUES (146, '修改友链是否通过', 'blog:link:isCheck', 39, '2024-01-22 21:04:18', '2024-01-22 21:04:18', 0);
INSERT INTO `sys_permission` VALUES (147, '删除友链', 'blog:link:delete', 39, '2024-01-22 21:04:46', '2024-01-22 21:04:46', 0);
INSERT INTO `sys_permission` VALUES (148, '上传站长头像', 'system:update:websiteInfo', 29, '2024-01-22 22:17:43', '2024-01-22 22:17:43', 1);


-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'ADMIN', 0, 0, '最高管理者', '2023-11-17 15:19:01', '2023-12-14 16:47:07', 0);
INSERT INTO `sys_role` VALUES (2, '测试角色', 'Test', 0, 1, '测试的用户，没有任何操作权限', '2023-11-17 15:19:06', '2023-12-25 16:28:33', 0);
INSERT INTO `sys_role` VALUES (3, '普通用户', 'USER', 0, 3, '前台普通用户（前台用户默认角色）', '2023-12-03 21:12:24', '2023-12-14 17:15:52', 0);
