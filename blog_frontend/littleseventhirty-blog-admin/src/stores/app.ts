import type { ThemeConfig } from 'ant-design-vue/es/config-provider/context'
import { theme as antdTheme } from 'ant-design-vue/es'
import type { ContentWidth, LayoutType, ThemeType } from '~@/layouts/basic-layout/typing'
import type { AnimationNameValueType } from '~@/config/default-setting'
import defaultSetting from '~@/config/default-setting'
import { ref, reactive, watch } from 'vue'
import { useDark, usePreferredLanguages } from '@vueuse/core'
import { useLocalStorage } from '@vueuse/core'

/**
 * 布局设置接口
 * 定义应用布局相关的配置项
 */
export interface LayoutSetting {
  /** 应用标题 */
  title?: string
  /** 应用 logo */
  logo?: string
  /** 主题类型 */
  theme: ThemeType
  /** 侧边栏是否折叠 */
  collapsed: boolean
  /** 抽屉是否可见 */
  drawerVisible: boolean
  /** 主题主色 */
  colorPrimary?: string
  /** 布局类型 */
  layout?: LayoutType
  /** 内容宽度类型 */
  contentWidth?: ContentWidth
  /** 是否固定头部 */
  fixedHeader?: boolean
  /** 是否固定侧边栏 */
  fixedSider?: boolean
  /** 是否分割菜单 */
  splitMenus?: boolean
  /** 是否显示水印 */
  watermark?: boolean
  /** 是否显示头部 */
  header?: boolean
  /** 是否显示底部 */
  footer?: boolean
  /** 是否显示菜单 */
  menu?: boolean
  /** 是否显示菜单头部 */
  menuHeader?: boolean
  /** 是否开启色弱模式 */
  colorWeak?: boolean
  /** 是否启用多标签页 */
  multiTab?: boolean
  /** 是否固定多标签页 */
  multiTabFixed?: boolean
  /** 头部高度 */
  headerHeight?: number
  /** 版权信息 */
  copyright?: string
  /** 是否启用路由缓存 */
  keepAlive?: boolean
  /** 是否启用菜单手风琴模式 */
  accordionMode?: boolean
  /** 左侧菜单是否折叠 */
  leftCollapsed?: boolean
  /** 动画名称 */
  animationName?: AnimationNameValueType
}

/**
 * 应用状态管理
 * 使用 Pinia 定义应用相关的状态和操作
 */
export const useAppStore = defineStore('app', () => {
  /** 布局设置，使用默认配置初始化 */
  const layoutSetting = reactive<LayoutSetting>(defaultSetting)

  /** 主题配置 */
  const themeConfig = reactive<ThemeConfig>({
    algorithm: antdTheme.defaultAlgorithm,
    token: {
      colorBgContainer: '#fff',
      colorPrimary: layoutSetting.colorPrimary,
    },
    components: {},
  })

  /** 暗色模式状态 */
  const isDark = useDark()

  /** 语言偏好 */
  const preferredLanguages = usePreferredLanguages()

  /** 语言设置 */
  const lsLocaleState = useLocalStorage('locale', 'zh-CN')
  const locale = ref<string>(lsLocaleState.value)

  /**
   * 切换语言
   * @param locale 语言代码
   */
  const toggleLocale = (locale: string) => {
    lsLocaleState.value = locale
  }

  /**
   * 切换主题
   * @param theme 主题类型
   */
  const toggleTheme = (theme: ThemeType) => {
    // 如果主题没有变化，直接返回
    if (layoutSetting.theme === theme)
      return

    // 更新主题设置
    layoutSetting.theme = theme

    // 处理浅色主题
    if (theme === 'light' || theme === 'inverted') {
      if (themeConfig.token)
        themeConfig.token.colorBgContainer = '#fff'
      if (themeConfig.components?.Menu)
        delete themeConfig.components.Menu

      themeConfig.algorithm = antdTheme.defaultAlgorithm
      toggleDark(false)
    }
    // 处理深色主题
    else if (theme === 'dark') {
      toggleDark(true)
      if (themeConfig.token)
        themeConfig.token.colorBgContainer = 'rgb(36, 37, 37)'
      if (themeConfig.components) {
        themeConfig.components = {
          ...themeConfig.components,
          Menu: {
            colorItemBg: 'rgb(36, 37, 37)',
            colorSubItemBg: 'rgb(36, 37, 37)',
            menuSubMenuBg: 'rgb(36, 37, 37)',
          } as any,
        }
      }
      themeConfig.algorithm = antdTheme.darkAlgorithm
    }
  }

  /**
   * 切换抽屉可见性
   * @param visible 是否可见
   */
  const toggleDrawerVisible = (visible: boolean) => {
    layoutSetting.drawerVisible = visible
  }

  /**
   * 切换主题主色
   * @param color 颜色值
   */
  const toggleColorPrimary = (color: string) => {
    layoutSetting.colorPrimary = color
    if (themeConfig.token)
      themeConfig.token.colorPrimary = color
  }

  // 如果加载进来是暗色模式，就切换到暗色模式
  if (isDark.value)
    toggleTheme('dark')

  // 监听isDark的变化，自动切换主题
  watch(isDark, () => {
    if (isDark.value)
      toggleTheme('dark')
    else toggleTheme('light')
  })

  // 监听语言偏好的变化，自动切换语言
  watch(preferredLanguages, () => {
    toggleLocale(preferredLanguages.value[0])
  })

  /**
   * 切换侧边栏折叠状态
   * @param collapsed 是否折叠
   */
  const toggleCollapsed = (collapsed: boolean) => {
    layoutSetting.collapsed = collapsed
  }

  /**
   * 切换布局类型
   * @param layout 布局类型
   */
  const toggleLayout = (layout: LayoutType) => {
    // 处理特殊情况：如果当前是反转主题且要切换到混合布局，则切换到浅色主题
    if (layoutSetting.theme === 'inverted' && layout === 'mix')
      layoutSetting.theme = 'light'

    // 处理菜单分割
    if (layout !== 'mix')
      layoutSetting.splitMenus = false
    else layoutSetting.leftCollapsed = true

    // 处理内容宽度
    if (layout === 'top')
      layoutSetting.contentWidth = 'Fixed'
    else layoutSetting.contentWidth = 'Fluid'

    // 更新布局设置
    layoutSetting.layout = layout
  }

  /**
   * 修改布局设置
   * @param key 设置项键名
   * @param value 设置项值
   */
  const changeSettingLayout = (key: keyof LayoutSetting, value: any) => {
    // 特殊处理主题、主色和布局的变更
    if (key === 'theme')
      toggleTheme(value as ThemeType)
    else if (key === 'colorPrimary')
      toggleColorPrimary(value)
    else if (key === 'layout')
      toggleLayout(value as LayoutType)
    // 其他设置项直接更新
    else if (key in layoutSetting)
      (layoutSetting as any)[key] = value
  }

  // 导出状态和方法
  return {
    layoutSetting,
    theme: themeConfig,
    locale,
    toggleLocale,
    toggleTheme,
    toggleCollapsed,
    toggleDrawerVisible,
    changeSettingLayout,
    toggleColorPrimary,
  }
})
