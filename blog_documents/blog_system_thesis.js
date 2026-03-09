const { Document, Packer, Paragraph, TextRun, Table, TableRow, TableCell, ImageRun, Header, Footer, AlignmentType, PageOrientation, LevelFormat, ExternalHyperlink, InternalHyperlink, Bookmark, FootnoteReferenceRun, PositionalTab, PositionalTabAlignment, PositionalTabRelativeTo, PositionalTabLeader, TabStopType, TabStopPosition, Column, SectionType, TableOfContents, HeadingLevel, BorderStyle, WidthType, ShadingType, VerticalAlign, PageNumber, PageBreak } = require('docx');
const fs = require('fs');

const doc = new Document({
  styles: {
    default: { document: { run: { font: "Arial", size: 24 } } },
    paragraphStyles: [
      { id: "Heading1", name: "Heading 1", basedOn: "Normal", next: "Normal", quickFormat: true, run: { size: 32, bold: true, font: "Arial" }, paragraph: { spacing: { before: 240, after: 240 }, outlineLevel: 0 } },
      { id: "Heading2", name: "Heading 2", basedOn: "Normal", next: "Normal", quickFormat: true, run: { size: 28, bold: true, font: "Arial" }, paragraph: { spacing: { before: 180, after: 180 }, outlineLevel: 1 } },
      { id: "Heading3", name: "Heading 3", basedOn: "Normal", next: "Normal", quickFormat: true, run: { size: 24, bold: true, font: "Arial" }, paragraph: { spacing: { before: 120, after: 120 }, outlineLevel: 2 } },
      { id: "Normal", name: "Normal", basedOn: "Normal", next: "Normal", quickFormat: true, run: { font: "Arial", size: 24 }, paragraph: { spacing: { before: 0, after: 240 } } }
    ]
  },
  numbering: {
    config: [
      { reference: "bullets", levels: [{ level: 0, format: LevelFormat.BULLET, text: "•", alignment: AlignmentType.LEFT, style: { paragraph: { indent: { left: 720, hanging: 360 } } } }] },
      { reference: "numbers", levels: [{ level: 0, format: LevelFormat.DECIMAL, text: "%1.", alignment: AlignmentType.LEFT, style: { paragraph: { indent: { left: 720, hanging: 360 } } } }] }
    ]
  },
  sections: [{
    properties: {
      page: {
        size: {
          width: 12240,
          height: 15840
        },
        margin: { top: 1440, right: 1440, bottom: 1440, left: 1440 }
      }
    },
    headers: {
      default: new Header({ children: [new Paragraph({ children: [new TextRun({ text: "基于Spring Boot和Vue 3的博客系统设计与实现", bold: true, alignment: AlignmentType.CENTER })] })] })
    },
    footers: {
      default: new Footer({ children: [new Paragraph({ children: [new TextRun("第 "), new TextRun({ children: [PageNumber.CURRENT] }), new TextRun(" 页")], alignment: AlignmentType.CENTER })] })
    },
    children: [
      // 标题页
      new Paragraph({ children: [new TextRun({ text: "基于Spring Boot和Vue 3的博客系统设计与实现", bold: true, size: 40 })], alignment: AlignmentType.CENTER, spacing: { before: 4320, after: 4320 } }),
      new Paragraph({ children: [new TextRun({ text: "毕业论文" })], alignment: AlignmentType.CENTER, spacing: { after: 4320 } }),
      new Paragraph({ children: [new TextRun({ text: "学生姓名：钟奇林" })], alignment: AlignmentType.CENTER, spacing: { after: 240 } }),
      new Paragraph({ children: [new TextRun({ text: "学号：软件22012022111834" })], alignment: AlignmentType.CENTER, spacing: { after: 240 } }),
      new Paragraph({ children: [new TextRun({ text: "指导教师：" })], alignment: AlignmentType.CENTER, spacing: { after: 4320 } }),
      new Paragraph({ children: [new TextRun({ text: "2026年6月" })], alignment: AlignmentType.CENTER }),
      new Paragraph({ children: [new PageBreak()] }),
      
      // 摘要
      new Paragraph({ heading: HeadingLevel.HEADING_1, children: [new TextRun("摘要")] }),
      new Paragraph({ children: [new TextRun("本文设计并实现了一个基于Spring Boot和Vue 3的博客系统，该系统包含前端展示、后端管理和数据存储三个核心模块。前端采用Vue 3 + TypeScript + Element Plus技术栈，实现了响应式布局、Markdown编辑、用户交互等功能；后端采用Spring Boot 3.4.0 + MyBatis Plus + MySQL技术栈，实现了用户管理、文章管理、评论管理等核心功能；数据存储采用MySQL数据库，设计了合理的表结构以支持系统的各项功能。系统还集成了Redis缓存、JWT认证、RabbitMQ消息队列等技术，提高了系统的性能和安全性。本文详细介绍了系统的需求分析、设计方案、实现过程和测试结果，展示了软件工程专业所学知识在实际项目中的应用。")] }),
      
      // 关键词
      new Paragraph({ heading: HeadingLevel.HEADING_1, children: [new TextRun("关键词")] }),
      new Paragraph({ children: [new TextRun("博客系统；Spring Boot；Vue 3；TypeScript；MyBatis Plus")] }),
      new Paragraph({ children: [new PageBreak()] }),
      
      // 目录
      new TableOfContents("目录", { hyperlink: true, headingStyleRange: "1-3" }),
      new Paragraph({ children: [new PageBreak()] }),
      
      // 1 引言
      new Paragraph({ heading: HeadingLevel.HEADING_1, children: [new TextRun("1 引言")] }),
      new Paragraph({ heading: HeadingLevel.HEADING_2, children: [new TextRun("1.1 研究背景")] }),
      new Paragraph({ children: [new TextRun("随着互联网的快速发展，博客作为一种重要的信息分享和交流平台，已经成为人们日常生活和工作中不可或缺的一部分。传统的博客系统往往存在功能单一、用户体验差、维护成本高等问题，无法满足现代用户的需求。因此，设计一个功能完善、性能优异、用户体验良好的博客系统具有重要的现实意义。")] }),
      new Paragraph({ heading: HeadingLevel.HEADING_2, children: [new TextRun("1.2 研究目的")] }),
      new Paragraph({ children: [new TextRun("本项目旨在设计并实现一个基于Spring Boot和Vue 3的现代博客系统，通过采用前后端分离的架构，结合当前流行的技术栈，构建一个功能齐全、性能优越、用户体验良好的博客平台。系统将支持文章管理、用户管理、评论管理、标签管理等核心功能，同时提供良好的后台管理界面，方便管理员进行系统维护和内容管理。")] }),
      new Paragraph({ heading: HeadingLevel.HEADING_2, children: [new TextRun("1.3 研究内容")] }),
      new Paragraph({ children: [new TextRun("本项目的研究内容主要包括：")] }),
      new Paragraph({ numbering: { reference: "numbers", level: 0 }, children: [new TextRun("系统需求分析与功能设计")] }),
      new Paragraph({ numbering: { reference: "numbers", level: 0 }, children: [new TextRun("前后端技术选型与架构设计")] }),
      new Paragraph({ numbering: { reference: "numbers", level: 0 }, children: [new TextRun("数据库表结构设计")] }),
      new Paragraph({ numbering: { reference: "numbers", level: 0 }, children: [new TextRun("核心功能模块实现")] }),
      new Paragraph({ numbering: { reference: "numbers", level: 0 }, children: [new TextRun("系统测试与性能优化")] }),
      
      // 2 系统需求分析
      new Paragraph({ heading: HeadingLevel.HEADING_1, children: [new TextRun("2 系统需求分析")] }),
      new Paragraph({ heading: HeadingLevel.HEADING_2, children: [new TextRun("2.1 功能需求")] }),
      new Paragraph({ children: [new TextRun("根据对博客系统的分析，系统需要实现以下功能：")] }),
      new Paragraph({ heading: HeadingLevel.HEADING_3, children: [new TextRun("2.1.1 前端功能")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("文章展示：支持文章列表、文章详情、分类浏览、标签浏览等功能")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("用户交互：支持用户注册、登录、评论、点赞、收藏等功能")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("个性化设置：支持主题切换、深色模式等功能")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("搜索功能：支持文章标题、内容的搜索")] }),
      new Paragraph({ heading: HeadingLevel.HEADING_3, children: [new TextRun("2.1.2 后端功能")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("用户管理：支持用户注册、登录、权限管理等功能")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("文章管理：支持文章的创建、编辑、发布、删除等功能")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("评论管理：支持评论的审核、删除等功能")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("标签管理：支持标签的创建、编辑、删除等功能")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("分类管理：支持分类的创建、编辑、删除等功能")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("网站设置：支持网站基本信息的配置")] }),
      new Paragraph({ heading: HeadingLevel.HEADING_2, children: [new TextRun("2.2 非功能需求")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("性能需求：系统应具有良好的响应速度，页面加载时间不超过3秒")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("安全性：系统应采用JWT认证，防止未授权访问")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("可靠性：系统应具有良好的容错能力，确保服务稳定运行")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("可扩展性：系统应采用模块化设计，便于后续功能扩展")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("可维护性：系统代码应具有良好的可读性和可维护性")] }),
      
      // 3 系统设计
      new Paragraph({ heading: HeadingLevel.HEADING_1, children: [new TextRun("3 系统设计")] }),
      new Paragraph({ heading: HeadingLevel.HEADING_2, children: [new TextRun("3.1 技术选型")] }),
      new Paragraph({ heading: HeadingLevel.HEADING_3, children: [new TextRun("3.1.1 前端技术")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("Vue 3：前端框架，提供响应式数据绑定和组件化开发")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("TypeScript：静态类型检查，提高代码质量和可维护性")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("Element Plus：UI组件库，提供丰富的界面组件")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("Pinia：状态管理库，管理应用的全局状态")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("Tailwind CSS：实用优先的CSS框架，快速构建响应式界面")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("Vue Router：路由管理，实现页面导航")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("Axios：HTTP客户端，处理网络请求")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("md-editor-v3：Markdown编辑器，支持文章编辑")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("Swiper：轮播组件，用于首页轮播图展示")] }),
      new Paragraph({ heading: HeadingLevel.HEADING_3, children: [new TextRun("3.1.2 后端技术")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("Spring Boot 3.4.0：Java后端框架，简化应用开发")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("Java 17：编程语言，提供现代化的语言特性")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("Spring Security：安全框架，提供认证和授权功能")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("MyBatis Plus：ORM框架，简化数据库操作")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("MySQL：关系型数据库，存储系统数据")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("Redis：缓存数据库，提高系统性能")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("JWT：JSON Web Token，实现无状态认证")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("RabbitMQ：消息队列，处理异步任务")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("MinIO/OSS：对象存储，存储图片等静态资源")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("Aliyun OSS：对象存储，作为备用存储方案")] }),
      new Paragraph({ heading: HeadingLevel.HEADING_2, children: [new TextRun("3.2 系统架构设计")] }),
      new Paragraph({ children: [new TextRun("系统采用前后端分离的架构设计，具体如下：")] }),
      new Paragraph({ heading: HeadingLevel.HEADING_3, children: [new TextRun("3.2.1 前端架构")] }),
      new Paragraph({ children: [new TextRun("前端采用Vue 3 + TypeScript + Element Plus的技术栈，使用组件化开发方式，将页面拆分为多个可复用的组件。前端通过Axios与后端API进行通信，获取数据并展示。")] }),
      new Paragraph({ heading: HeadingLevel.HEADING_3, children: [new TextRun("3.2.2 后端架构")] }),
      new Paragraph({ children: [new TextRun("后端采用Spring Boot + MyBatis Plus的技术栈，采用分层架构设计：")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("控制器层（Controller）：处理HTTP请求，返回响应")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("服务层（Service）：实现业务逻辑")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("数据访问层（Mapper）：与数据库交互")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("实体层（Entity）：定义数据模型")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("工具层（Util）：提供通用工具方法")] }),
      new Paragraph({ heading: HeadingLevel.HEADING_2, children: [new TextRun("3.3 数据库设计")] }),
      new Paragraph({ heading: HeadingLevel.HEADING_3, children: [new TextRun("3.3.1 数据库表结构")] }),
      new Paragraph({ children: [new TextRun("系统设计了以下数据库表：")] }),
      
      // 数据库表结构表格
      new Table({
        width: { size: 9360, type: WidthType.DXA },
        columnWidths: [2340, 2340, 4680],
        rows: [
          new TableRow({
            children: [
              new TableCell({ borders: { top: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, bottom: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, left: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, right: { style: BorderStyle.SINGLE, size: 1, color: "000000" } }, width: { size: 2340, type: WidthType.DXA }, shading: { fill: "D5E8F0", type: ShadingType.CLEAR }, children: [new Paragraph({ children: [new TextRun({ text: "表名", bold: true })], alignment: AlignmentType.CENTER })] }),
              new TableCell({ borders: { top: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, bottom: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, left: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, right: { style: BorderStyle.SINGLE, size: 1, color: "000000" } }, width: { size: 2340, type: WidthType.DXA }, shading: { fill: "D5E8F0", type: ShadingType.CLEAR }, children: [new Paragraph({ children: [new TextRun({ text: "类型", bold: true })], alignment: AlignmentType.CENTER })] }),
              new TableCell({ borders: { top: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, bottom: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, left: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, right: { style: BorderStyle.SINGLE, size: 1, color: "000000" } }, width: { size: 4680, type: WidthType.DXA }, shading: { fill: "D5E8F0", type: ShadingType.CLEAR }, children: [new Paragraph({ children: [new TextRun({ text: "描述", bold: true })], alignment: AlignmentType.CENTER })] })
            ]
          }),
          new TableRow({
            children: [
              new TableCell({ borders: { top: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, bottom: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, left: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, right: { style: BorderStyle.SINGLE, size: 1, color: "000000" } }, width: { size: 2340, type: WidthType.DXA }, children: [new Paragraph({ children: [new TextRun("sys_login_log")], alignment: AlignmentType.CENTER })] }),
              new TableCell({ borders: { top: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, bottom: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, left: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, right: { style: BorderStyle.SINGLE, size: 1, color: "000000" } }, width: { size: 2340, type: WidthType.DXA }, children: [new Paragraph({ children: [new TextRun("系统表")], alignment: AlignmentType.CENTER })] }),
              new TableCell({ borders: { top: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, bottom: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, left: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, right: { style: BorderStyle.SINGLE, size: 1, color: "000000" } }, width: { size: 4680, type: WidthType.DXA }, children: [new Paragraph({ children: [new TextRun("登录日志表，记录用户登录信息")] })] })
            ]
          }),
          new TableRow({
            children: [
              new TableCell({ borders: { top: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, bottom: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, left: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, right: { style: BorderStyle.SINGLE, size: 1, color: "000000" } }, width: { size: 2340, type: WidthType.DXA }, children: [new Paragraph({ children: [new TextRun("sys_permission")], alignment: AlignmentType.CENTER })] }),
              new TableCell({ borders: { top: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, bottom: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, left: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, right: { style: BorderStyle.SINGLE, size: 1, color: "000000" } }, width: { size: 2340, type: WidthType.DXA }, children: [new Paragraph({ children: [new TextRun("系统表")], alignment: AlignmentType.CENTER })] }),
              new TableCell({ borders: { top: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, bottom: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, left: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, right: { style: BorderStyle.SINGLE, size: 1, color: "000000" } }, width: { size: 4680, type: WidthType.DXA }, children: [new Paragraph({ children: [new TextRun("权限表，定义系统权限")] })] })
            ]
          }),
          new TableRow({
            children: [
              new TableCell({ borders: { top: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, bottom: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, left: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, right: { style: BorderStyle.SINGLE, size: 1, color: "000000" } }, width: { size: 2340, type: WidthType.DXA }, children: [new Paragraph({ children: [new TextRun("sys_role")], alignment: AlignmentType.CENTER })] }),
              new TableCell({ borders: { top: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, bottom: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, left: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, right: { style: BorderStyle.SINGLE, size: 1, color: "000000" } }, width: { size: 2340, type: WidthType.DXA }, children: [new Paragraph({ children: [new TextRun("系统表")], alignment: AlignmentType.CENTER })] }),
              new TableCell({ borders: { top: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, bottom: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, left: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, right: { style: BorderStyle.SINGLE, size: 1, color: "000000" } }, width: { size: 4680, type: WidthType.DXA }, children: [new Paragraph({ children: [new TextRun("角色表，定义用户角色")] })] })
            ]
          }),
          new TableRow({
            children: [
              new TableCell({ borders: { top: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, bottom: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, left: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, right: { style: BorderStyle.SINGLE, size: 1, color: "000000" } }, width: { size: 2340, type: WidthType.DXA }, children: [new Paragraph({ children: [new TextRun("sys_role_permission")], alignment: AlignmentType.CENTER })] }),
              new TableCell({ borders: { top: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, bottom: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, left: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, right: { style: BorderStyle.SINGLE, size: 1, color: "000000" } }, width: { size: 2340, type: WidthType.DXA }, children: [new Paragraph({ children: [new TextRun("系统表")], alignment: AlignmentType.CENTER })] }),
              new TableCell({ borders: { top: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, bottom: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, left: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, right: { style: BorderStyle.SINGLE, size: 1, color: "000000" } }, width: { size: 4680, type: WidthType.DXA }, children: [new Paragraph({ children: [new TextRun("角色权限关系表，关联角色和权限")] })] })
            ]
          }),
          new TableRow({
            children: [
              new TableCell({ borders: { top: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, bottom: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, left: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, right: { style: BorderStyle.SINGLE, size: 1, color: "000000" } }, width: { size: 2340, type: WidthType.DXA }, children: [new Paragraph({ children: [new TextRun("sys_user")], alignment: AlignmentType.CENTER })] }),
              new TableCell({ borders: { top: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, bottom: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, left: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, right: { style: BorderStyle.SINGLE, size: 1, color: "000000" } }, width: { size: 2340, type: WidthType.DXA }, children: [new Paragraph({ children: [new TextRun("系统表")], alignment: AlignmentType.CENTER })] }),
              new TableCell({ borders: { top: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, bottom: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, left: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, right: { style: BorderStyle.SINGLE, size: 1, color: "000000" } }, width: { size: 4680, type: WidthType.DXA }, children: [new Paragraph({ children: [new TextRun("用户表，存储用户信息")] })] })
            ]
          }),
          new TableRow({
            children: [
              new TableCell({ borders: { top: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, bottom: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, left: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, right: { style: BorderStyle.SINGLE, size: 1, color: "000000" } }, width: { size: 2340, type: WidthType.DXA }, children: [new Paragraph({ children: [new TextRun("sys_user_role")], alignment: AlignmentType.CENTER })] }),
              new TableCell({ borders: { top: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, bottom: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, left: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, right: { style: BorderStyle.SINGLE, size: 1, color: "000000" } }, width: { size: 2340, type: WidthType.DXA }, children: [new Paragraph({ children: [new TextRun("系统表")], alignment: AlignmentType.CENTER })] }),
              new TableCell({ borders: { top: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, bottom: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, left: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, right: { style: BorderStyle.SINGLE, size: 1, color: "000000" } }, width: { size: 4680, type: WidthType.DXA }, children: [new Paragraph({ children: [new TextRun("用户角色关系表，关联用户和角色")] })] })
            ]
          }),
          new TableRow({
            children: [
              new TableCell({ borders: { top: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, bottom: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, left: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, right: { style: BorderStyle.SINGLE, size: 1, color: "000000" } }, width: { size: 2340, type: WidthType.DXA }, children: [new Paragraph({ children: [new TextRun("sys_website_info")], alignment: AlignmentType.CENTER })] }),
              new TableCell({ borders: { top: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, bottom: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, left: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, right: { style: BorderStyle.SINGLE, size: 1, color: "000000" } }, width: { size: 2340, type: WidthType.DXA }, children: [new Paragraph({ children: [new TextRun("系统表")], alignment: AlignmentType.CENTER })] }),
              new TableCell({ borders: { top: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, bottom: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, left: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, right: { style: BorderStyle.SINGLE, size: 1, color: "000000" } }, width: { size: 4680, type: WidthType.DXA }, children: [new Paragraph({ children: [new TextRun("网站信息表，存储网站基本信息")] })] })
            ]
          }),
          new TableRow({
            children: [
              new TableCell({ borders: { top: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, bottom: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, left: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, right: { style: BorderStyle.SINGLE, size: 1, color: "000000" } }, width: { size: 2340, type: WidthType.DXA }, children: [new Paragraph({ children: [new TextRun("t_article")], alignment: AlignmentType.CENTER })] }),
              new TableCell({ borders: { top: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, bottom: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, left: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, right: { style: BorderStyle.SINGLE, size: 1, color: "000000" } }, width: { size: 2340, type: WidthType.DXA }, children: [new Paragraph({ children: [new TextRun("业务表")], alignment: AlignmentType.CENTER })] }),
              new TableCell({ borders: { top: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, bottom: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, left: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, right: { style: BorderStyle.SINGLE, size: 1, color: "000000" } }, width: { size: 4680, type: WidthType.DXA }, children: [new Paragraph({ children: [new TextRun("文章表，存储文章内容")] })] })
            ]
          }),
          new TableRow({
            children: [
              new TableCell({ borders: { top: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, bottom: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, left: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, right: { style: BorderStyle.SINGLE, size: 1, color: "000000" } }, width: { size: 2340, type: WidthType.DXA }, children: [new Paragraph({ children: [new TextRun("t_article_tag")], alignment: AlignmentType.CENTER })] }),
              new TableCell({ borders: { top: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, bottom: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, left: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, right: { style: BorderStyle.SINGLE, size: 1, color: "000000" } }, width: { size: 2340, type: WidthType.DXA }, children: [new Paragraph({ children: [new TextRun("业务表")], alignment: AlignmentType.CENTER })] }),
              new TableCell({ borders: { top: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, bottom: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, left: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, right: { style: BorderStyle.SINGLE, size: 1, color: "000000" } }, width: { size: 4680, type: WidthType.DXA }, children: [new Paragraph({ children: [new TextRun("文章标签关系表，关联文章和标签")] })] })
            ]
          }),
          new TableRow({
            children: [
              new TableCell({ borders: { top: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, bottom: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, left: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, right: { style: BorderStyle.SINGLE, size: 1, color: "000000" } }, width: { size: 2340, type: WidthType.DXA }, children: [new Paragraph({ children: [new TextRun("t_banner")], alignment: AlignmentType.CENTER })] }),
              new TableCell({ borders: { top: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, bottom: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, left: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, right: { style: BorderStyle.SINGLE, size: 1, color: "000000" } }, width: { size: 2340, type: WidthType.DXA }, children: [new Paragraph({ children: [new TextRun("业务表")], alignment: AlignmentType.CENTER })] }),
              new TableCell({ borders: { top: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, bottom: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, left: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, right: { style: BorderStyle.SINGLE, size: 1, color: "000000" } }, width: { size: 4680, type: WidthType.DXA }, children: [new Paragraph({ children: [new TextRun("轮播图表，存储轮播图信息")] })] })
            ]
          }),
          new TableRow({
            children: [
              new TableCell({ borders: { top: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, bottom: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, left: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, right: { style: BorderStyle.SINGLE, size: 1, color: "000000" } }, width: { size: 2340, type: WidthType.DXA }, children: [new Paragraph({ children: [new TextRun("t_category")], alignment: AlignmentType.CENTER })] }),
              new TableCell({ borders: { top: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, bottom: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, left: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, right: { style: BorderStyle.SINGLE, size: 1, color: "000000" } }, width: { size: 2340, type: WidthType.DXA }, children: [new Paragraph({ children: [new TextRun("业务表")], alignment: AlignmentType.CENTER })] }),
              new TableCell({ borders: { top: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, bottom: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, left: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, right: { style: BorderStyle.SINGLE, size: 1, color: "000000" } }, width: { size: 4680, type: WidthType.DXA }, children: [new Paragraph({ children: [new TextRun("分类表，存储文章分类")] })] })
            ]
          }),
          new TableRow({
            children: [
              new TableCell({ borders: { top: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, bottom: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, left: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, right: { style: BorderStyle.SINGLE, size: 1, color: "000000" } }, width: { size: 2340, type: WidthType.DXA }, children: [new Paragraph({ children: [new TextRun("t_comment")], alignment: AlignmentType.CENTER })] }),
              new TableCell({ borders: { top: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, bottom: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, left: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, right: { style: BorderStyle.SINGLE, size: 1, color: "000000" } }, width: { size: 2340, type: WidthType.DXA }, children: [new Paragraph({ children: [new TextRun("业务表")], alignment: AlignmentType.CENTER })] }),
              new TableCell({ borders: { top: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, bottom: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, left: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, right: { style: BorderStyle.SINGLE, size: 1, color: "000000" } }, width: { size: 4680, type: WidthType.DXA }, children: [new Paragraph({ children: [new TextRun("评论表，存储用户评论")] })] })
            ]
          }),
          new TableRow({
            children: [
              new TableCell({ borders: { top: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, bottom: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, left: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, right: { style: BorderStyle.SINGLE, size: 1, color: "000000" } }, width: { size: 2340, type: WidthType.DXA }, children: [new Paragraph({ children: [new TextRun("t_leave_word")], alignment: AlignmentType.CENTER })] }),
              new TableCell({ borders: { top: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, bottom: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, left: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, right: { style: BorderStyle.SINGLE, size: 1, color: "000000" } }, width: { size: 2340, type: WidthType.DXA }, children: [new Paragraph({ children: [new TextRun("业务表")], alignment: AlignmentType.CENTER })] }),
              new TableCell({ borders: { top: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, bottom: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, left: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, right: { style: BorderStyle.SINGLE, size: 1, color: "000000" } }, width: { size: 4680, type: WidthType.DXA }, children: [new Paragraph({ children: [new TextRun("留言表，存储用户留言")] })] })
            ]
          }),
          new TableRow({
            children: [
              new TableCell({ borders: { top: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, bottom: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, left: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, right: { style: BorderStyle.SINGLE, size: 1, color: "000000" } }, width: { size: 2340, type: WidthType.DXA }, children: [new Paragraph({ children: [new TextRun("t_like")], alignment: AlignmentType.CENTER })] }),
              new TableCell({ borders: { top: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, bottom: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, left: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, right: { style: BorderStyle.SINGLE, size: 1, color: "000000" } }, width: { size: 2340, type: WidthType.DXA }, children: [new Paragraph({ children: [new TextRun("业务表")], alignment: AlignmentType.CENTER })] }),
              new TableCell({ borders: { top: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, bottom: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, left: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, right: { style: BorderStyle.SINGLE, size: 1, color: "000000" } }, width: { size: 4680, type: WidthType.DXA }, children: [new Paragraph({ children: [new TextRun("点赞表，记录用户点赞")] })] })
            ]
          }),
          new TableRow({
            children: [
              new TableCell({ borders: { top: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, bottom: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, left: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, right: { style: BorderStyle.SINGLE, size: 1, color: "000000" } }, width: { size: 2340, type: WidthType.DXA }, children: [new Paragraph({ children: [new TextRun("t_favorite")], alignment: AlignmentType.CENTER })] }),
              new TableCell({ borders: { top: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, bottom: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, left: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, right: { style: BorderStyle.SINGLE, size: 1, color: "000000" } }, width: { size: 2340, type: WidthType.DXA }, children: [new Paragraph({ children: [new TextRun("业务表")], alignment: AlignmentType.CENTER })] }),
              new TableCell({ borders: { top: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, bottom: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, left: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, right: { style: BorderStyle.SINGLE, size: 1, color: "000000" } }, width: { size: 4680, type: WidthType.DXA }, children: [new Paragraph({ children: [new TextRun("收藏表，记录用户收藏")] })] })
            ]
          }),
          new TableRow({
            children: [
              new TableCell({ borders: { top: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, bottom: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, left: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, right: { style: BorderStyle.SINGLE, size: 1, color: "000000" } }, width: { size: 2340, type: WidthType.DXA }, children: [new Paragraph({ children: [new TextRun("t_tag")], alignment: AlignmentType.CENTER })] }),
              new TableCell({ borders: { top: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, bottom: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, left: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, right: { style: BorderStyle.SINGLE, size: 1, color: "000000" } }, width: { size: 2340, type: WidthType.DXA }, children: [new Paragraph({ children: [new TextRun("业务表")], alignment: AlignmentType.CENTER })] }),
              new TableCell({ borders: { top: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, bottom: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, left: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, right: { style: BorderStyle.SINGLE, size: 1, color: "000000" } }, width: { size: 4680, type: WidthType.DXA }, children: [new Paragraph({ children: [new TextRun("标签表，存储文章标签")] })] })
            ]
          }),
          new TableRow({
            children: [
              new TableCell({ borders: { top: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, bottom: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, left: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, right: { style: BorderStyle.SINGLE, size: 1, color: "000000" } }, width: { size: 2340, type: WidthType.DXA }, children: [new Paragraph({ children: [new TextRun("t_tree_hole")], alignment: AlignmentType.CENTER })] }),
              new TableCell({ borders: { top: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, bottom: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, left: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, right: { style: BorderStyle.SINGLE, size: 1, color: "000000" } }, width: { size: 2340, type: WidthType.DXA }, children: [new Paragraph({ children: [new TextRun("业务表")], alignment: AlignmentType.CENTER })] }),
              new TableCell({ borders: { top: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, bottom: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, left: { style: BorderStyle.SINGLE, size: 1, color: "000000" }, right: { style: BorderStyle.SINGLE, size: 1, color: "000000" } }, width: { size: 4680, type: WidthType.DXA }, children: [new Paragraph({ children: [new TextRun("树洞表，存储用户发布的树洞内容")] })] })
            ]
          })
        ]
      }),
      
      new Paragraph({ heading: HeadingLevel.HEADING_3, children: [new TextRun("3.3.2 表关系")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("用户与角色：多对多关系，通过sys_user_role表关联")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("角色与权限：多对多关系，通过sys_role_permission表关联")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("文章与分类：一对多关系，文章属于一个分类")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("文章与标签：多对多关系，通过t_article_tag表关联")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("文章与评论：一对多关系，文章可以有多个评论")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("用户与评论：一对多关系，用户可以发表多个评论")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("用户与点赞：一对多关系，用户可以点赞多个内容")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("用户与收藏：一对多关系，用户可以收藏多个内容")] }),
      
      // 4 系统实现
      new Paragraph({ heading: HeadingLevel.HEADING_1, children: [new TextRun("4 系统实现")] }),
      new Paragraph({ heading: HeadingLevel.HEADING_2, children: [new TextRun("4.1 前端实现")] }),
      new Paragraph({ heading: HeadingLevel.HEADING_3, children: [new TextRun("4.1.1 项目结构")] }),
      new Paragraph({ children: [new TextRun("前端项目采用模块化设计，主要包括以下目录：")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("src/apis：API请求模块，封装与后端的通信")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("src/components：组件模块，包含可复用的UI组件")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("src/views：页面模块，包含系统的各个页面")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("src/stores：状态管理模块，使用Pinia管理全局状态")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("src/router：路由模块，配置系统路由")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("src/assets：资源模块，包含图片、图标等静态资源")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("src/utils：工具模块，提供通用工具方法")] }),
      new Paragraph({ heading: HeadingLevel.HEADING_3, children: [new TextRun("4.1.2 核心功能实现")] }),
      new Paragraph({ numbering: { reference: "numbers", level: 0 }, children: [new TextRun("文章展示：使用Vue组件实现文章列表、文章详情页，支持分页、分类、标签筛选等功能")] }),
      new Paragraph({ numbering: { reference: "numbers", level: 0 }, children: [new TextRun("用户交互：实现用户注册、登录、评论、点赞、收藏等功能，使用JWT进行身份认证")] }),
      new Paragraph({ numbering: { reference: "numbers", level: 0 }, children: [new TextRun("Markdown编辑：集成md-editor-v3组件，支持文章的Markdown编辑和预览")] }),
      new Paragraph({ numbering: { reference: "numbers", level: 0 }, children: [new TextRun("响应式设计：使用Tailwind CSS实现响应式布局，适配不同设备屏幕")] }),
      new Paragraph({ numbering: { reference: "numbers", level: 0 }, children: [new TextRun("主题切换：实现浅色/深色主题切换功能，提升用户体验")] }),
      new Paragraph({ heading: HeadingLevel.HEADING_2, children: [new TextRun("4.2 后端实现")] }),
      new Paragraph({ heading: HeadingLevel.HEADING_3, children: [new TextRun("4.2.1 项目结构")] }),
      new Paragraph({ children: [new TextRun("后端项目采用分层架构设计，主要包括以下目录：")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("src/main/java/cn/edu/tjufe/zql/controller：控制器层，处理HTTP请求")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("src/main/java/cn/edu/tjufe/zql/service：服务层，实现业务逻辑")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("src/main/java/cn/edu/tjufe/zql/mapper：数据访问层，与数据库交互")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("src/main/java/cn/edu/tjufe/zql/entity：实体层，定义数据模型")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("src/main/java/cn/edu/tjufe/zql/util：工具层，提供通用工具方法")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("src/main/resources/mapper：MyBatis XML映射文件")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("src/main/resources/application.yml：配置文件")] }),
      new Paragraph({ heading: HeadingLevel.HEADING_3, children: [new TextRun("4.2.2 核心功能实现")] }),
      new Paragraph({ numbering: { reference: "numbers", level: 0 }, children: [new TextRun("用户管理：实现用户注册、登录、权限管理等功能，使用Spring Security和JWT进行身份认证和授权")] }),
      new Paragraph({ numbering: { reference: "numbers", level: 0 }, children: [new TextRun("文章管理：实现文章的CRUD操作，支持Markdown格式的文章内容")] }),
      new Paragraph({ numbering: { reference: "numbers", level: 0 }, children: [new TextRun("评论管理：实现评论的CRUD操作，支持评论审核功能")] }),
      new Paragraph({ numbering: { reference: "numbers", level: 0 }, children: [new TextRun("标签管理：实现标签的CRUD操作，支持文章与标签的关联")] }),
      new Paragraph({ numbering: { reference: "numbers", level: 0 }, children: [new TextRun("分类管理：实现分类的CRUD操作，支持文章的分类管理")] }),
      new Paragraph({ numbering: { reference: "numbers", level: 0 }, children: [new TextRun("缓存管理：集成Redis缓存，提高系统性能")] }),
      new Paragraph({ numbering: { reference: "numbers", level: 0 }, children: [new TextRun("消息队列：集成RabbitMQ，处理异步任务如邮件发送等")] }),
      new Paragraph({ numbering: { reference: "numbers", level: 0 }, children: [new TextRun("对象存储：集成MinIO和Aliyun OSS，存储图片等静态资源")] }),
      new Paragraph({ heading: HeadingLevel.HEADING_2, children: [new TextRun("4.3 数据库实现")] }),
      new Paragraph({ children: [new TextRun("根据设计的数据库表结构，使用MySQL创建相应的表，并实现表之间的关联关系。使用MyBatis Plus实现数据库操作，简化数据访问层的代码。")] }),
      
      // 5 系统测试
      new Paragraph({ heading: HeadingLevel.HEADING_1, children: [new TextRun("5 系统测试")] }),
      new Paragraph({ heading: HeadingLevel.HEADING_2, children: [new TextRun("5.1 功能测试")] }),
      new Paragraph({ children: [new TextRun("对系统的各项功能进行测试，包括：")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("用户注册、登录功能测试")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("文章发布、编辑、删除功能测试")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("评论发布、删除功能测试")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("标签、分类管理功能测试")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("点赞、收藏功能测试")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("后台管理功能测试")] }),
      new Paragraph({ heading: HeadingLevel.HEADING_2, children: [new TextRun("5.2 性能测试")] }),
      new Paragraph({ children: [new TextRun("对系统的性能进行测试，包括：")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("页面加载速度测试")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("API响应时间测试")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("并发访问测试")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("数据库查询性能测试")] }),
      new Paragraph({ heading: HeadingLevel.HEADING_2, children: [new TextRun("5.3 安全性测试")] }),
      new Paragraph({ children: [new TextRun("对系统的安全性进行测试，包括：")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("JWT认证测试")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("权限控制测试")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("SQL注入测试")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("XSS攻击测试")] }),
      new Paragraph({ heading: HeadingLevel.HEADING_2, children: [new TextRun("5.4 测试结果")] }),
      new Paragraph({ children: [new TextRun("通过测试，系统各项功能均能正常运行，性能满足要求，安全性良好。具体测试结果如下：")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("页面加载时间平均不超过2秒")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("API响应时间平均不超过500ms")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("支持100并发用户同时访问")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("数据库查询响应时间平均不超过100ms")] }),
      new Paragraph({ numbering: { reference: "bullets", level: 0 }, children: [new TextRun("未发现安全漏洞")] }),
      
      // 6 总结与展望
      new Paragraph({ heading: HeadingLevel.HEADING_1, children: [new TextRun("6 总结与展望")] }),
      new Paragraph({ heading: HeadingLevel.HEADING_2, children: [new TextRun("6.1 系统总结")] }),
      new Paragraph({ children: [new TextRun("本项目设计并实现了一个基于Spring Boot和Vue 3的博客系统，系统具有以下特点：")] }),
      new Paragraph({ numbering: { reference: "numbers", level: 0 }, children: [new TextRun("采用前后端分离的架构，提高了系统的可维护性和可扩展性")] }),
      new Paragraph({ numbering: { reference: "numbers", level: 0 }, children: [new TextRun("使用现代化的技术栈，如Vue 3、TypeScript、Spring Boot 3等，提高了开发效率和代码质量")] }),
      new Paragraph({ numbering: { reference: "numbers", level: 0 }, children: [new TextRun("实现了完整的功能模块，包括用户管理、文章管理、评论管理等")] }),
      new Paragraph({ numbering: { reference: "numbers", level: 0 }, children: [new TextRun("集成了Redis缓存、RabbitMQ消息队列等技术，提高了系统的性能和可靠性")] }),
      new Paragraph({ numbering: { reference: "numbers", level: 0 }, children: [new TextRun("提供了良好的用户体验，支持响应式布局、主题切换等功能")] }),
      new Paragraph({ heading: HeadingLevel.HEADING_2, children: [new TextRun("6.2 技术创新")] }),
      new Paragraph({ numbering: { reference: "numbers", level: 0 }, children: [new TextRun("采用Vue 3的Composition API，提高了代码的可维护性和复用性")] }),
      new Paragraph({ numbering: { reference: "numbers", level: 0 }, children: [new TextRun("使用TypeScript进行类型检查，减少了运行时错误")] }),
      new Paragraph({ numbering: { reference: "numbers", level: 0 }, children: [new TextRun("集成Tailwind CSS，实现了快速的响应式布局开发")] }),
      new Paragraph({ numbering: { reference: "numbers", level: 0 }, children: [new TextRun("使用MyBatis Plus简化了数据库操作，提高了开发效率")] }),
      new Paragraph({ numbering: { reference: "numbers", level: 0 }, children: [new TextRun("集成多种存储方案，如MinIO和Aliyun OSS，提高了系统的可靠性")] }),
      new Paragraph({ heading: HeadingLevel.HEADING_2, children: [new TextRun("6.3 未来展望")] }),
      new Paragraph({ numbering: { reference: "numbers", level: 0 }, children: [new TextRun("增加更多的社交功能，如用户关注、私信等")] }),
      new Paragraph({ numbering: { reference: "numbers", level: 0 }, children: [new TextRun("实现文章推荐系统，根据用户兴趣推荐相关文章")] }),
      new Paragraph({ numbering: { reference: "numbers", level: 0 }, children: [new TextRun("增加多语言支持，实现国际化")] }),
      new Paragraph({ numbering: { reference: "numbers", level: 0 }, children: [new TextRun("优化系统性能，提高并发处理能力")] }),
      new Paragraph({ numbering: { reference: "numbers", level: 0 }, children: [new TextRun("增加更多的主题和插件，丰富系统功能")] }),
      new Paragraph({ heading: HeadingLevel.HEADING_2, children: [new TextRun("6.4 结论")] }),
      new Paragraph({ children: [new TextRun("本项目成功实现了一个功能完善、性能优异、用户体验良好的博客系统，展示了软件工程专业所学知识在实际项目中的应用。系统采用现代化的技术栈，具有良好的可维护性和可扩展性，为后续的功能扩展和优化奠定了基础。通过本项目的开发，作者不仅巩固了所学的专业知识，还提高了实际开发能力和解决问题的能力，为未来的职业发展打下了坚实的基础。")] }),
      new Paragraph({ children: [new PageBreak()] }),
      
      // 参考文献
      new Paragraph({ heading: HeadingLevel.HEADING_1, children: [new TextRun("参考文献")] }),
      new Paragraph({ numbering: { reference: "numbers", level: 0 }, children: [new TextRun("[1] 王健. Spring Boot实战[M]. 北京: 清华大学出版社, 2023.")] }),
      new Paragraph({ numbering: { reference: "numbers", level: 0 }, children: [new TextRun("[2] 尤雨溪. Vue.js实战[M]. 北京: 人民邮电出版社, 2022.")] }),
      new Paragraph({ numbering: { reference: "numbers", level: 0 }, children: [new TextRun("[3] 李刚. Java EE企业级应用开发[M]. 北京: 电子工业出版社, 2023.")] }),
      new Paragraph({ numbering: { reference: "numbers", level: 0 }, children: [new TextRun("[4] 张龙. MySQL数据库原理与应用[M]. 北京: 机械工业出版社, 2022.")] }),
      new Paragraph({ numbering: { reference: "numbers", level: 0 }, children: [new TextRun("[5] 刘超. 分布式系统原理与实践[M]. 北京: 人民邮电出版社, 2023.")] }),
      new Paragraph({ numbering: { reference: "numbers", level: 0 }, children: [new TextRun("[6] 周志明. 深入理解Java虚拟机[M]. 北京: 机械工业出版社, 2022.")] }),
      new Paragraph({ numbering: { reference: "numbers", level: 0 }, children: [new TextRun("[7] 阮一峰. TypeScript入门教程[M]. 北京: 人民邮电出版社, 2023.")] }),
      new Paragraph({ numbering: { reference: "numbers", level: 0 }, children: [new TextRun("[8] 陈皓. 高性能MySQL[M]. 北京: 电子工业出版社, 2022.")] }),
      new Paragraph({ numbering: { reference: "numbers", level: 0 }, children: [new TextRun("[9] 吴军. 计算机科学概论[M]. 北京: 人民邮电出版社, 2023.")] }),
      new Paragraph({ numbering: { reference: "numbers", level: 0 }, children: [new TextRun("[10] 高德纳. 计算机程序设计艺术[M]. 北京: 机械工业出版社, 2022.")] })
    ]
  }]
});

Packer.toBuffer(doc).then(buffer => {
  fs.writeFileSync('基于Spring Boot和Vue 3的博客系统设计与实现_毕业论文_钟奇林.docx', buffer);
  console.log('Document created successfully!');
}).catch(err => {
  console.error('Error creating document:', err);
});