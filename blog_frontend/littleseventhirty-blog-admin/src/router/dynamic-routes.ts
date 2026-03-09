import type { RouteRecordRaw } from 'vue-router'
import { basicRouteMap } from './router-modules'
import { AccessEnum } from '~@/utils/constant'

export const ROOT_ROUTE_REDIRECT_PATH = '/welcome'
const Layout = () => import('~/layouts/index.vue')

export const rootRoute: RouteRecordRaw = {
  path: '/',
  name: 'rootPath',
  redirect: ROOT_ROUTE_REDIRECT_PATH,
  component: Layout,
  children: [],
}

export default [
  {
    path: '/welcome',
    name: 'Welcome',
    component: () => import('~/pages/welcome/index.vue'),
    meta: {
      title: '欢迎页',
      icon: 'HomeOutlined',
    },
  },
  {
    path: '/account',
    redirect: '/account/center',
    name: 'Account',
    meta: {
      title: '个人页',
      icon: 'UserOutlined',
      locale: 'menu.account',
    },
    component: basicRouteMap.RouteView,
    children: [
      {
        path: '/account/center',
        name: 'AccountCenter',
        component: () => import('~/pages/account/center.vue'),
        meta: {
          title: '个人主页',
          locale: 'menu.account.center',
        },
      },
      {
        path: '/account/settings',
        name: 'AccountSettings',
        component: () => import('~/pages/account/settings.vue'),
        meta: {
          title: '个人设置',
          locale: 'menu.account.settings',
        },
      },
      {
        path: '/account/settings/:id',
        name: 'AccountSettings1',
        component: () => import('~/pages/account/settings.vue'),
        meta: {
          title: '个人设置1',
          locale: 'menu.account.settings',
          hideInMenu: true,
          parentKeys: ['/account/settings'],
        },
      },
    ],
  },
  {
    path: '/blog',
    redirect: '/blog/essay/list',
    name: 'Blog',
    meta: {
      title: '博客管理',
      icon: 'EditOutlined',
    },
    component: basicRouteMap.RouteView,
    children: [
      {
        path: '/blog/essay/list',
        name: 'EssayList',
        component: () => import('~/pages/blog/essay/list/index.vue'),
        meta: {
          title: '文章列表',
        },
      },
      {
        path: '/blog/essay/publish',
        name: 'EssayPublish',
        component: () => import('~/pages/blog/essay/publish/index.vue'),
        meta: {
          title: '发布文章',
        },
      },
      {
        path: '/blog/category',
        name: 'Category',
        component: () => import('~/pages/blog/category/index.vue'),
        meta: {
          title: '分类管理',
        },
      },
      {
        path: '/blog/tag',
        name: 'Tag',
        component: () => import('~/pages/blog/tag/index.vue'),
        meta: {
          title: '标签管理',
        },
      },
      {
        path: '/blog/comment',
        name: 'Comment',
        component: () => import('~/pages/blog/comment/index.vue'),
        meta: {
          title: '评论管理',
        },
      },
      {
        path: '/blog/info',
        name: 'Info',
        component: () => import('~/pages/blog/info/index.vue'),
        meta: {
          title: '站点信息',
        },
      },
      {
        path: '/blog/info/web-info',
        name: 'WebInfo',
        component: () => import('~/pages/blog/info/web-info/index.vue'),
        meta: {
          title: '网站信息',
          hideInMenu: true,
        },
      },
      {
        path: '/blog/info/web-info/banners',
        name: 'Banners',
        component: () => import('~/pages/blog/info/web-info/banners/index.vue'),
        meta: {
          title: '轮播图管理',
          hideInMenu: true,
        },
      },
      {
        path: '/blog/info/stationmaster-info',
        name: 'StationmasterInfo',
        component: () => import('~/pages/blog/info/stationmaster-info/index.vue'),
        meta: {
          title: '站长信息',
          hideInMenu: true,
        },
      },
      {
        path: '/blog/link',
        name: 'Link',
        component: () => import('~/pages/blog/link/index.vue'),
        meta: {
          title: '友情链接',
        },
      },
      {
        path: '/blog/collect',
        name: 'Collect',
        component: () => import('~/pages/blog/collect/index.vue'),
        meta: {
          title: '收藏管理',
        },
      },
      {
        path: '/blog/message',
        name: 'Message',
        component: () => import('~/pages/blog/message/index.vue'),
        meta: {
          title: '留言管理',
        },
      },
      {
        path: '/blog/photo',
        name: 'Photo',
        component: () => import('~/pages/blog/photo/index.vue'),
        meta: {
          title: '相册管理',
        },
      },
      {
        path: '/blog/tree-hole',
        name: 'TreeHole',
        component: () => import('~/pages/blog/tree-hole/index.vue'),
        meta: {
          title: '树洞管理',
        },
      },
      {
        path: '/blog/black-list',
        name: 'BlackList',
        component: () => import('~/pages/blog/black-list/index.vue'),
        meta: {
          title: '黑名单管理',
        },
      },
    ],
  },
  {
    path: '/system',
    redirect: '/system/user',
    name: 'System',
    meta: {
      title: '系统管理',
      icon: 'SettingOutlined',
    },
    component: basicRouteMap.RouteView,
    children: [
      {
        path: '/system/user',
        name: 'User',
        component: () => import('~/pages/system/user/index.vue'),
        meta: {
          title: '用户管理',
        },
      },
      {
        path: '/system/role',
        name: 'Role',
        component: () => import('~/pages/system/role/index.vue'),
        meta: {
          title: '角色管理',
        },
      },
      {
        path: '/system/permission',
        name: 'Permission',
        component: () => import('~/pages/system/permission/index.vue'),
        meta: {
          title: '权限管理',
        },
      },
      {
        path: '/system/menu',
        name: 'Menu',
        component: () => import('~/pages/system/menu/index.vue'),
        meta: {
          title: '菜单管理',
        },
      },
      {
        path: '/system/log/login',
        name: 'LoginLog',
        component: () => import('~/pages/system/log/login/index.vue'),
        meta: {
          title: '登录日志',
        },
      },
      {
        path: '/system/log/operate',
        name: 'OperateLog',
        component: () => import('~/pages/system/log/operate/index.vue'),
        meta: {
          title: '操作日志',
        },
      },
      {
        path: '/system/server-monitoring',
        name: 'ServerMonitoring',
        component: () => import('~/pages/system/server-monitoring/index.vue'),
        meta: {
          title: '服务器监控',
        },
      },
    ],
  },
  {
    path: '/data-screen',
    name: 'DataScreen',
    component: () => import('~/pages/data-screen/index.vue'),
    meta: {
      title: '数据大屏',
      icon: 'BarChartOutlined',
    },
  },
  {
    path: '/exception',
    redirect: '/exception/403',
    name: 'Exception',
    meta: {
      title: '异常页',
      icon: 'WarningOutlined',
      locale: 'menu.exception',
    },
    children: [
      {
        path: '/exception/403',
        name: 'Exception403',
        component: () => import('~/pages/exception/403.vue'),
        meta: {
          title: '403',
          locale: 'menu.exception.not-permission',
        },
      },
      {
        path: '/exception/404',
        name: 'Exception404',
        component: () => import('~/pages/exception/404.vue'),
        meta: {
          title: '404',
          locale: 'menu.exception.not-find',
        },
      },
      {
        path: '/exception/500',
        name: 'Exception500',
        component: () => import('~/pages/exception/500.vue'),
        meta: {
          title: '500',
          locale: 'menu.exception.server-error',
        },
      },
    ],
  },
] as RouteRecordRaw[]
