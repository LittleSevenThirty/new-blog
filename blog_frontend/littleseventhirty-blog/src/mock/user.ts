import Mock from "mockjs";

// 模拟用户信息接口
Mock.mock("/api/user/auth/info","get",{
    // 昵称
    nickname:"littleseventhirty",
    // 用户名
    username:"奇奇",
    // 头像
    avater:"https://cn.bing.com/images/search?q=%e6%9d%a5%e4%b8%aa%e5%a4%b4%e5%83%8f&id=A56A7681D4D11F79E72CD37864ACDB73D2572C5B&FORM=IACFIR",
    // 介绍
    introduce:"实验数据",
    // 注册类型（0手机号，1邮箱，2其它。。。）
    registerType:2,
    // 邮件地址
    email:"example@example.com",
    // 用户角色列表（管理员，普通，。。。）
    roles:[
      "管理员","普通人"
    ],
    // 性别（男0，女1，其它2）
    gender:0,
    // 用户权限列表（字符串数组）
    permissions:[],
    // 最后登录时间（字符串）
    loginTime:"2026-1-12 12:00",
    // 账号创建时间（字符串）
    createTime:"2026-1-12 12:00",
});

// 用户登出接口
Mock.mock("/api/user/logout","post",{
  "code":200,
  "msg":"操作成功",
  "data":null
});