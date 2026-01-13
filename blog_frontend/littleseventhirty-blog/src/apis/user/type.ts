export interface UserInfo{
    // 昵称
    nickname:string;
    // 用户名
    username:string;
    // 头像
    avater:string;
    // 介绍
    introduce:string;
    // 注册类型（0邮箱，1gitee，2github。。。）
    registerType:number;
    // 邮件地址
    email:string
    // 用户角色列表（管理员，普通，。。。）
    roles:string[];
    // 性别（男0，女1，其它2）
    gender:number;
    // 用户权限列表（字符串数组）
    permissions:string[];
    // 最后登录时间（字符串）
    loginTime:string;
    // 账号创建时间（字符串）
    createTime:string;
}