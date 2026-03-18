INSERT INTO blog.t_article (article_id, user_id, category_id, article_title, article_content, article_type, status, visited_count, is_top, create_time, update_time, article_cover, is_deleted) VALUES (1, 1, 1, '内置指令v-slot', '# **内置指令v-slot**
- **缩写**：`#`
---

# 📝 Vue 3 插槽（Slots）与 `v-slot` 指令详解笔记

> ✅ **适用场景**：Vue 3 + `<script setup>`（组合式 API）  
> 📚 官方文档参考：
> - [v-slot 指令](https://cn.vuejs.org/api/built-in-directives.html#v-slot)
> - [插槽指南](https://cn.vuejs.org/guide/components/slots)

---

## 一、为什么需要插槽？—— 先理解“问题”


想象你在用一个 **按钮组件**：

```html
<!-- 父组件 -->
<PrimaryButton>点击登录</PrimaryButton>
```

你希望这个按钮：
- 外观统一（比如蓝色背景、圆角）
- 但**文字内容由使用者决定**

如果没有插槽，你只能这样传内容：

```html
<PrimaryButton text="点击登录" />
```

但如果你还想在按钮里放 **图标 + 文字** 呢？

```html
<PrimaryButton>
  <Icon name="user" /> 登录
</PrimaryButton>
```

👉 **这就需要“插槽”**：让父组件把任意模板片段“塞进”子组件的指定位置！

---

## 二、什么是插槽（Slot）？

- **插槽 = 子组件预留的“占位符”**
- **父组件 = 往这个占位符里“填内容”**
- 类比：乐高底板（子组件）上有凹槽（`<slot>`），你可以插任意积木（父组件的内容）

---

## 三、基本插槽（默认插槽）

### 1. 子组件：定义插槽出口（`<slot>`）

```html
<!-- FancyButton.vue -->
<template>
  <button class="fancy-btn">
    <slot></slot> <!-- 这里就是插槽出口 -->
  </button>
</template>

<style scoped>
.fancy-btn {
  background: #42b883;
  color: white;
  padding: 8px 16px;
  border: none;
  border-radius: 4px;
}
</style>
```

### 2. 父组件：提供插槽内容

```html
<!-- Parent.vue -->
<template>
  <FancyButton>立即购买</FancyButton>
  
  <!-- 也可以放复杂内容 -->
  <FancyButton>
    <span style="color: yellow">🔥 限时优惠</span>
    <IconStar />
  </FancyButton>
</template>
```

✅ 最终渲染效果：
```html
<button class="fancy-btn">立即购买</button>
<button class="fancy-btn"><span>🔥 限时优惠</span><i class="icon-star"></i></button>
```

> 💡 插槽内容可以是：文本、HTML、其他组件、响应式数据等任意合法模板！

---

## 四、具名插槽（Named Slots）—— 多个插槽

当子组件有 **多个插入点**（如 header / main / footer），就需要给插槽起名字。

### 1. 子组件：定义多个具名插槽

```html
<!-- BaseLayout.vue -->
<template>
  <div class="layout">
    <header>
      <slot name="header"></slot> <!-- 头部插槽 -->
    </header>
    <main>
      <slot></slot> <!-- 默认插槽（无 name） -->
    </main>
    <footer>
      <slot name="footer"></slot> <!-- 底部插槽 -->
    </footer>
  </div>
</template>
```

> ⚠️ 没有 `name` 的 `<slot>` 自动叫 **default（默认插槽）**

### 2. 父组件：用 `v-slot` 指定内容去哪

```html
<template>
  <BaseLayout>
    <!-- 具名插槽：用 template + v-slot:name -->
    <template v-slot:header>
      <h1>我的网站标题</h1>
    </template>

    <!-- 默认插槽：可以直接写在外面 -->
    <p>这是主要内容。</p>
    <p>可以有多段。</p>

    <!-- 也可以显式写 default -->
    <template v-slot:default>
      <p>这也会进入 main 区域</p>
    </template>

    <template v-slot:footer>
      <p>© 2025 我的公司</p>
    </template>
  </BaseLayout>
</template>
```

### 🔥 缩写语法（强烈推荐！）

`v-slot:xxx` 可简写为 `#xxx`：

```html
<template>
  <BaseLayout>
    <template #header>
      <h1>标题</h1>
    </template>
    
    <template #default>
      <p>主要内容</p>
    </template>
    
    <template #footer>
      <p>页脚</p>
    </template>
  </BaseLayout>
</template>
```

> ✅ 规则：`#` 必须用在 `<template>` 上（除非是默认插槽且内容简单，见下文）

---

## 五、默认插槽的简化写法

如果只有 **默认插槽**，且没有其他具名插槽，你可以**不写 `<template>`**：

```html
<FancyButton>点我</FancyButton> <!-- 直接写内容 -->
```

但如果同时有具名插槽，**默认插槽必须用 `<template #default>` 包裹**，否则会出错！

---

## 六、作用域插槽（Scoped Slots）—— 子组件向插槽“传数据”

有时，插槽内容不仅需要父组件的数据，还需要**子组件的数据**！

### 场景举例：
- 子组件是一个列表（`<UserList>`），它从 API 获取用户数据
- 父组件想自定义每个用户的显示样式（头像+名字+操作按钮）

### 1. 子组件：在 `<slot>` 上绑定数据（像传 prop）

```html
<!-- UserList.vue -->
<template>
  <ul>
    <li v-for="user in users" :key="user.id">
      <!-- 把 user 对象传给插槽 -->
      <slot :user="user" :index="index"></slot>
    </li>
  </ul>
</template>

<script setup>
const users = [
  { id: 1, name: \'张三\', email: \'zhang@example.com\' },
  { id: 2, name: \'李四\', email: \'li@example.com\' }
]
</script>
```

### 2. 父组件：用 `v-slot` 接收数据（解构语法超好用！）

```html
<template>
  <UserList v-slot="{ user, index }">
    <div>
      {{ index + 1 }}. {{ user.name }} ({{ user.email }})
      <button @click="edit(user)">编辑</button>
    </div>
  </UserList>
</template>

<script setup>
function edit(user) {
  console.log(\'编辑用户:\', user)
}
</script>
```

> 💡 `v-slot="{ user, index }"` 是 ES6 解构语法，等价于：
> ```html
> <UserList v-slot="slotProps">
>   {{ slotProps.user.name }}
> </UserList>
> ```

### 🔥 缩写 + 解构 = 最佳实践！

```html
<UserList #default="{ user }">
  <strong>{{ user.name }}</strong>
</UserList>
```

---

## 七、动态插槽名

插槽名也可以是变量：

```html
<template>
  <BaseLayout>
    <template #[dynamicSlotName]>
      动态内容
    </template>
  </BaseLayout>
</template>

<script setup>
const dynamicSlotName = \'header\' // 也可以是 \'footer\'
</script>
```

---

## 八、检查插槽是否存在（高级）

有时你想“只有父组件提供了插槽内容，我才渲染某部分”，可用 `$slots`：

```html
<!-- Card.vue -->
<template>
  <div class="card">
    <!-- 只有提供了 header 插槽才显示 header 区域 -->
    <div v-if="$slots.header" class="card-header">
      <slot name="header" />
    </div>
    
    <div class="card-body">
      <slot /> <!-- 默认插槽 -->
    </div>
  </div>
</template>
```

> ✅ `$slots` 是组件实例的一个属性，包含所有插槽函数（存在即 truthy）

---

## 九、常见误区 & 注意事项

| 问题 | 正确做法 |
|------|--------|
| ❌ 在组件标签上直接写 `v-slot` 而不是 `<template>` | ❌ `<MyComp v-slot="{ data }">` → ✅ `<MyComp><template #default="{ data }">` |
| ❌ 混淆插槽和 props | ✅ **props 传数据，插槽传模板**；插槽更灵活 |
| ❌ 忘记默认插槽的隐式规则 | ✅ 只有默认插槽时可省略 `<template>`；有具名插槽时必须显式写 `#default` |
| ❌ 在作用域插槽中试图访问子组件未传递的数据 | ✅ 子组件必须在 `<slot :xxx="value">` 中显式暴露 |

---

## 十、速查表（Cheat Sheet）

```html
<!-- 默认插槽 -->
<Child>内容</Child>

<!-- 具名插槽 -->
<Child>
  <template #header>头部</template>
  <template #default>主体</template>
  <template #footer>底部</template>
</Child>

<!-- 作用域插槽 -->
<List v-slot="{ item }">
  <div>{{ item.name }}</div>
</List>

<!-- 缩写 -->
<Child #header>...</Child>
```

---

## 十一、一句话总结

> **插槽（Slots）让组件像“模具”一样：子组件定义结构框架，父组件注入具体内容，甚至还能让子组件把内部数据“递出来”给父组件使用。**

---

## **参考**
* [Vue官方文档_深入组件_插槽](https://cn.vuejs.org/guide/components/slots.html)', 1, 1, 90, 1, '2026-03-05 10:34:08', '2026-03-05 11:55:43', 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Vue%203%20watchers%20tutorial%20cover&image_size=landscape_16_9', 0);
INSERT INTO blog.t_article (article_id, user_id, category_id, article_title, article_content, article_type, status, visited_count, is_top, create_time, update_time, article_cover, is_deleted) VALUES (2, 1, 1, 'Vue 3 侦听器（Watchers）新手完全指南', '# Vue 3 侦听器（Watchers）新手完全指南

> 本文基于 Vue 3 官方文档整理，旨在帮助初学者系统理解侦听器的使用方法。侦听器是 Vue 响应式系统的重要组成部分，让我们能够在数据变化时执行特定操作。

## 一、什么是侦听器？

在 Vue 中，**侦听器**（Watchers）是一种让我们能够在数据变化时**执行副作用操作**的机制。当你需要在数据变化时执行异步操作、复杂的计算或直接操作 DOM 时，侦听器就派上用场了。

> **小贴士**：计算属性（Computed）适合处理简单的派生状态，而侦听器则适合处理更复杂的逻辑，尤其是需要执行异步操作或产生副作用的场景。', 1, 1, 100, 1, '2026-03-04 21:31:35', '2026-03-05 11:55:43', 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Vue%203%20watchers%20tutorial%20cover&image_size=landscape_16_9', 0);
INSERT INTO blog.t_article (article_id, user_id, category_id, article_title, article_content, article_type, status, visited_count, is_top, create_time, update_time, article_cover, is_deleted) VALUES (3, 2, 1, 'Vue 3 组件基础与动态组件', '# Vue 3 组件基础与动态组件

> 本文介绍 Vue 3 中的组件基础概念和动态组件的使用方法，帮助开发者更好地理解和应用组件化开发。

## 一、组件基础

组件是 Vue 应用的基本构建块，允许我们将 UI 拆分为独立、可复用的模块。

### 1. 定义组件

在 Vue 3 中，我们可以使用 `defineComponent` 函数或 `<script setup>` 语法来定义组件。

### 2. 组件通信

- **Props**：父组件向子组件传递数据
- **Events**：子组件向父组件发送消息
- **Slots**：父组件向子组件注入内容', 1, 1, 80, 1, '2026-03-04 21:31:35', '2026-03-05 11:55:43', 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=Vue%203%20components%20tutorial%20cover&image_size=landscape_16_9', 0);
INSERT INTO blog.t_article (article_id, user_id, category_id, article_title, article_content, article_type, status, visited_count, is_top, create_time, update_time, article_cover, is_deleted) VALUES (4, 3, 2, '前端开发最佳实践', '# 前端开发最佳实践

> 本文总结了前端开发中的一些最佳实践，帮助开发者编写更高效、更可维护的代码。

## 一、代码组织

- **模块化**：将代码拆分为小而专注的模块
- **命名规范**：使用一致的命名约定
- **注释**：添加清晰的注释说明复杂逻辑

## 二、性能优化

- **减少 HTTP 请求**：合并文件、使用缓存
- **优化资源加载**：使用懒加载、预加载
- **减少 DOM 操作**：使用虚拟 DOM、批量更新', 1, 1, 60, 1, '2026-03-04 21:31:35', '2026-03-05 11:55:43', 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=frontend%20development%20best%20practices%20cover&image_size=landscape_16_9', 0);
