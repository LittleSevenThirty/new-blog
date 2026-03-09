# 博客系统数据库ER图

## 数据库表结构

### 系统表

1. **sys_login_log**：登录日志表，记录用户登录信息
2. **sys_permission**：权限表，定义系统权限
3. **sys_role**：角色表，定义用户角色
4. **sys_role_permission**：角色权限关系表，关联角色和权限
5. **sys_user**：用户表，存储用户信息
6. **sys_user_role**：用户角色关系表，关联用户和角色
7. **sys_website_info**：网站信息表，存储网站基本信息

### 业务表

1. **t_article**：文章表，存储文章内容
2. **t_article_tag**：文章标签关系表，关联文章和标签
3. **t_banner**：轮播图表，存储轮播图信息
4. **t_category**：分类表，存储文章分类
5. **t_comment**：评论表，存储用户评论
6. **t_leave_word**：留言表，存储用户留言
7. **t_like**：点赞表，记录用户点赞
8. **t_favorite**：收藏表，记录用户收藏
9. **t_tag**：标签表，存储文章标签
10. **t_tree_hole**：树洞表，存储用户发布的树洞内容

## 表关系

- **用户与角色**：多对多关系，通过sys_user_role表关联
- **角色与权限**：多对多关系，通过sys_role_permission表关联
- **文章与分类**：一对多关系，文章属于一个分类
- **文章与标签**：多对多关系，通过t_article_tag表关联
- **文章与评论**：一对多关系，文章可以有多个评论
- **用户与评论**：一对多关系，用户可以发表多个评论
- **用户与点赞**：一对多关系，用户可以点赞多个内容
- **用户与收藏**：一对多关系，用户可以收藏多个内容
- **用户与留言**：一对多关系，用户可以发表多个留言
- **用户与树洞**：一对多关系，用户可以发布多个树洞内容
- **用户与轮播图**：一对多关系，用户可以上传多个轮播图
- **登录日志与用户**：多对一关系，登录日志属于一个用户

## ER图

```plantuml
' 博客系统数据库ER图
' 基于Spring Boot和Vue 3的博客系统

' 系统表
entity "sys_login_log" as SysLoginLog {
  * login_log_id : bigint [PK]
  --
  user_name : varchar(50)
  ip : varchar(50)
  address : varchar(50)
  browser : varchar(50)
  os : varchar(50)
  type : tinyint(1)
  state : tinyint(1)
  message : longtext
  create_time : datetime
  update_time : datetime
  is_deleted : tinyint(1)
}

entity "sys_permission" as SysPermission {
  * permission_id : bigint UNSIGNED [PK]
  --
  permission_desc : varchar(50)
  permission_key : varchar(255)
  menu_id : bigint
  create_time : datetime
  update_time : datetime
  is_deleted : tinyint
}

entity "sys_role" as SysRole {
  * role_id : bigint UNSIGNED [PK]
  --
  role_name : varchar(100)
  role_key : varchar(10)
  status : tinyint
  order_num : bigint
  remark : varchar(255)
  create_time : datetime
  update_time : datetime
  is_deleted : tinyint
}

entity "sys_role_permission" as SysRolePermission {
  * role_permission_id : bigint [PK]
  --
  role_id : bigint
  permission_id : bigint
}

entity "sys_user" as SysUser {
  * user_id : bigint UNSIGNED [PK]
  --
  nickname : varchar(50)
  username : varchar(50)
  gender : tinyint(1)
  password : varchar(100)
  avatar : varchar(255)
  intro : varchar(100)
  email : varchar(50)
  register_ip : varchar(100)
  register_type : tinyint
  register_address : varchar(50)
  login_ip : varchar(100)
  login_address : varchar(50)
  login_type : tinyint
  login_time : datetime
  is_disable : tinyint(1)
  create_time : datetime
  update_time : datetime
  is_deleted : tinyint(1)
}

entity "sys_user_role" as SysUserRole {
  * user_role_id : int [PK]
  --
  user_id : int
  role_id : varchar(20)
}

entity "sys_website_info" as SysWebsiteInfo {
  * id : bigint unsigned [PK]
  --
  webmaster_avatar : varchar(250)
  webmaster_name : varchar(50)
  webmaster_copy : varchar(100)
  webmaster_profile_background : varchar(255)
  gitee_link : varchar(100)
  github_link : varchar(100)
  website_name : varchar(50)
  header_notification : varchar(150)
  sidebar_announcement : varchar(255)
  record_info : varchar(255)
  start_time : datetime
  create_time : datetime
  update_time : datetime
  is_deleted : tinyint(1)
}

' 业务表
entity "t_article" as TArticle {
  * article_id : bigint UNSIGNED [PK]
  --
  user_id : bigint
  category_id : bigint
  article_title : varchar(50)
  article_content : longtext
  article_type : tinyint
  status : tinyint
  visited_count : bigint unsigned
  is_top : tinyint
  create_time : datetime
  update_time : datetime
  article_cover : varchar(200)
  is_deleted : tinyint(1)
}

entity "t_article_tag" as TArticleTag {
  * article_tag_id : bigint unsigned [PK]
  --
  article_id : bigint unsigned
  tag_id : bigint unsigned
  create_time : datetime
  is_deleted : tinyint
}

entity "t_banner" as TBanner {
  * banner_id : bigint unsigned [PK]
  --
  path : varchar(255)
  size : bigint
  type : varchar(55)
  user_id : bigint
  order : int
  create_time : datetime
}

entity "t_category" as TCategory {
  * category_id : bigint unsigned [PK]
  --
  category_name : varchar(20)
  create_time : datetime
  update_time : datetime
  is_deleted : tinyint(1)
}

entity "t_comment" as TComment {
  * comment_id : bigint [PK]
  --
  type : tinyint(1)
  type_id : bigint
  parent_id : bigint
  reply_id : bigint
  comment_content : text
  comment_user_id : bigint
  reply_user_id : bigint
  is_check : tinyint(1)
  create_time : datetime
  update_time : datetime
  is_deleted : tinyint(1)
}

entity "t_leave_word" as TLeaveWord {
  * leave_word_id : bigint [PK]
  --
  user_id : bigint
  content : longtext
  is_check : tinyint(1)
  create_time : datetime
  update_time : datetime
  is_deleted : tinyint(1)
}

entity "t_like" as TLike {
  * like_id : bigint [PK]
  --
  user_id : bigint
  type : tinyint
  type_id : bigint
  create_time : datetime
  update_time : datetime
}

entity "t_favorite" as TFavorite {
  * favorite_id : bigint [PK]
  --
  user_id : bigint
  type : int
  type_id : bigint
  is_check : int
  create_time : datetime
}

entity "t_tag" as TTag {
  * tag_id : bigint UNSIGNED [PK]
  --
  tag_name : varchar(20)
  create_time : datetime
  update_time : datetime
  is_deleted : tinyint
}

entity "t_tree_hole" as TTreeHole {
  * tree_hole_id : bigint [PK]
  --
  user_id : bigint
  content : varchar(100)
  is_check : tinyint(1)
  create_time : datetime
  update_time : datetime
  is_deleted : tinyint(1)
}

' 关系定义
' 用户与角色：多对多
SysUser ||--o{ SysUserRole : has
SysRole ||--o{ SysUserRole : has

' 角色与权限：多对多
SysRole ||--o{ SysRolePermission : has
SysPermission ||--o{ SysRolePermission : has

' 用户与文章：一对多
SysUser ||--o{ TArticle : writes

' 文章与分类：一对多
TCategory ||--o{ TArticle : contains

' 文章与标签：多对多
TArticle ||--o{ TArticleTag : has
TTag ||--o{ TArticleTag : has

' 用户与评论：一对多
SysUser ||--o{ TComment : writes

' 文章与评论：一对多（当type=1时）
TArticle ||--o{ TComment : receives

' 用户与点赞：一对多
SysUser ||--o{ TLike : gives

' 用户与收藏：一对多
SysUser ||--o{ TFavorite : collects

' 用户与留言：一对多
SysUser ||--o{ TLeaveWord : writes

' 用户与树洞：一对多
SysUser ||--o{ TTreeHole : writes

' 用户与轮播图：一对多
SysUser ||--o{ TBanner : uploads

' 登录日志与用户：多对一
SysLoginLog }o--|| SysUser : belongs_to
```

## 如何查看ER图

1. 安装PlantUML插件：在VS Code中安装PlantUML插件，或使用在线PlantUML编辑器
2. 打开`database_er_diagram.puml`文件
3. 查看生成的ER图

或者使用在线PlantUML编辑器：
1. 访问 https://www.plantuml.com/plantuml/uml
2. 复制上面的PlantUML代码
3. 粘贴到编辑器中
4. 点击"Submit"按钮查看ER图

## 数据库设计说明

- **系统表**：主要用于系统管理，包括用户权限、登录日志等
- **业务表**：主要用于博客核心功能，包括文章、评论、标签等
- **关系设计**：采用了合理的关系设计，如多对多关系通过中间表实现，一对多关系通过外键实现
- **字段设计**：每个表都有合理的字段设计，包括主键、外键、业务字段等
- **时间戳**：大部分表都包含create_time和update_time字段，用于记录数据的创建和更新时间
- **软删除**：大部分表都包含is_deleted字段，用于实现软删除功能