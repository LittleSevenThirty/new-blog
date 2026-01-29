// mockArticleData.js
export default {
  code: "200",
  msg: "success",
  data: {
    page: [
      {
        articleId: "1",
        categoryName: "技术",
        tags: "前端,Vue3,CompositionAPI",
        articleCover: "https://picsum.photos/seed/tech1/300/200",
        articleTitle: "Vue3 Composition API 深度解析与实战",
        articleContent: "本文系统讲解 Vue3 Composition API 的设计哲学与核心用法，对比 Options API 优势，结合状态管理、自定义 Hooks 等实战场景，附完整代码示例。适合从中级迈向高阶的前端开发者。",
        articleType: "原创",
        visitedCount: 2850,
        commmentCount: 42, // 注意：接口字段为 commmentCount（双m）
        likeCount: 189,
        favoriteCount: 97
      },
      {
        articleId: "2",
        categoryName: "生活",
        tags: "旅行,周末游,小众景点",
        articleCover: "https://picsum.photos/seed/life2/300/200",
        articleTitle: "逃离城市计划：江浙沪5个治愈系小众秘境",
        articleContent: "厌倦人挤人？这份精心整理的周末短途攻略，带你探访藏在山水间的静谧村落、茶园古道与临湖民宿。含交通贴士、最佳拍摄点、本地人推荐美食，轻松实现说走就走的治愈之旅。",
        articleType: "原创",
        visitedCount: 1920,
        commmentCount: 38,
        likeCount: 156,
        favoriteCount: 112
      },
      {
        articleId: "3",
        categoryName: "娱乐",
        tags: "电影,影评,年度盘点",
        articleCover: "https://picsum.photos/seed/ent3/300/200",
        articleTitle: "2023华语电影高光时刻：这5部作品值得反复品味",
        articleContent: "从《奥本海默》的震撼到《芭比》的思辨，本文聚焦2023年兼具艺术性与社会价值的华语佳作。深度解析镜头语言、角色塑造与时代隐喻，附观影指南与导演访谈精华。",
        articleType: "转载",
        visitedCount: 3410,
        commmentCount: 67,
        likeCount: 245,
        favoriteCount: 138
      },
      {
        articleId: "4",
        categoryName: "技术",
        tags: "Python,数据分析,可视化",
        articleCover: "https://picsum.photos/seed/tech4/300/200",
        articleTitle: "零基础用Python做数据分析：从Excel到Pandas实战",
        articleContent: "手把手教学！用真实电商数据集演示数据清洗、聚合分析、Matplotlib/Seaborn可视化全流程。附Jupyter Notebook代码与避坑指南，小白也能快速产出专业级分析报告。",
        articleType: "原创",
        visitedCount: 2105,
        commmentCount: 29,
        likeCount: 132,
        favoriteCount: 88
      },
      {
        articleId: "5",
        categoryName: "财经",
        tags: "理财,基金,新手指南",
        articleCover: "https://picsum.photos/seed/finance5/300/200",
        articleTitle: "月薪5000如何科学理财？三步建立你的财富安全垫",
        articleContent: "拒绝焦虑！本文用通俗语言拆解「应急资金-稳健增值-长期投资」三层理财模型，结合国内主流平台工具（余额宝、指数基金等），提供可落地的月度配置方案与风险提示。",
        articleType: "原创",
        visitedCount: 4280,
        commmentCount: 85,
        likeCount: 310,
        favoriteCount: 205
      },
      {
        articleId: "6",
        categoryName: "健康",
        tags: "办公室养生,肩颈缓解,微运动",
        articleCover: "https://picsum.photos/seed/health6/300/200",
        articleTitle: "久坐族自救指南：5个碎片化动作告别腰酸背痛",
        articleContent: "无需器械！利用接水、等电梯的30秒，完成肩颈放松、脊柱激活等微运动。附GIF动图详解+每日打卡表，坚持21天，体态与精力显著提升。附三甲医院康复科医生建议。",
        articleType: "原创",
        visitedCount: 1760,
        commmentCount: 33,
        likeCount: 142,
        favoriteCount: 95
      },
      {
        articleId: "7",
        categoryName: "教育",
        tags: "学习方法,效率工具,知识管理",
        articleCover: "https://picsum.photos/seed/edu7/300/200",
        articleTitle: "费曼学习法实战：用一张纸吃透任何复杂知识",
        articleContent: "为什么你学了就忘？本文揭秘诺贝尔奖得主费曼的高效学习心法，结合Notion/Obsidian工具模板，演示如何将输入→输出→迭代闭环。附「知识卡片」制作模板与案例库。",
        articleType: "转载",
        visitedCount: 2540,
        commmentCount: 41,
        likeCount: 178,
        favoriteCount: 126
      },
      {
        articleId: "8",
        categoryName: "旅游",
        tags: "京都,文化体验,深度游",
        articleCover: "https://picsum.photos/seed/travel8/300/200",
        articleTitle: "京都慢旅行：避开人潮的7个隐秘角落与四季攻略",
        articleContent: "不止清水寺！探访哲学之道旁的百年茶屋、岚山竹林深处的手作工坊、鸭川畔的深夜食堂。含季节限定活动（樱花/红叶/雪景）、和服体验避坑指南、本地人私藏美食地图。",
        articleType: "原创",
        visitedCount: 3120,
        commmentCount: 58,
        likeCount: 224,
        favoriteCount: 163
      },
      {
        articleId: "9",
        categoryName: "美食",
        tags: "家常菜,快手菜,减脂餐",
        articleCover: "https://picsum.photos/seed/food9/300/200",
        articleTitle: "15分钟搞定！打工人必备的5道高颜值减脂晚餐",
        articleContent: "拒绝外卖焦虑！鸡胸肉新吃法、低卡版麻婆豆腐、彩虹蔬菜卷... 每道菜精确到克的热量标注+备餐技巧。附「周末预处理」时间表，工作日轻松实现健康饮食自由。",
        articleType: "原创",
        visitedCount: 1890,
        commmentCount: 47,
        likeCount: 165,
        favoriteCount: 108
      },
      {
        articleId: "10",
        categoryName: "科技",
        tags: "AI,大模型,行业应用",
        articleCover: "https://picsum.photos/seed/tech10/300/200",
        articleTitle: "AIGC落地进行时：企业如何安全高效应用大模型？",
        articleContent: "超越 hype！深度剖析金融、医疗、教育领域AIGC落地案例，详解数据安全、成本控制、人机协作关键点。附国内合规平台选型对比与 prompt 工程实战技巧，助力企业理性拥抱变革。",
        articleType: "转载",
        visitedCount: 5230,
        commmentCount: 93,
        likeCount: 387,
        favoriteCount: 241
      }
    ],
    total: 10
  }
};