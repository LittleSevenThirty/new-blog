// 实现效果，将上次更新的时间显示修改为 ...几天前修改过
export const returnTime = (time: string): string => {
    if (!time) return "";
    // 使用正则替换时间字符串中所有可能的-，以符合IOS的时间格式yyyy/mm/dd
    let regex = /\-/g;
    time = time.replace(regex, "/");
    let times = new Date().getTime() - new Date(time).getTime();    // 时间换算，毫秒单位
    let result;
    if (times < 6e4) {
        result = Math.trunc(times / 1000) + "秒钟";
    } else if (times >= 6e4 && times < 3.6e6) {
        result = Math.trunc(times / 6e4) + "分钟";
    } else if (times >= 3.6e6 && times < 8.64e7) {
        result = Math.trunc(times / 3.6e6) + "小时";
    } else {
        result = Math.trunc(times / 8.64e7) + "天";
    }
    return result;
}

// 获取安全字符串
export function escapeRegExp(str: string) {
    return str.replace(/[.*+?^${}()|[\]\\]/g, '\\$&');
}

/**
 * 节流函数-时间戳版
 * @param func 回调函数
 * @param wait 节流时间 
 */
export function throttle(func: Function, wait: number) {
    let previous = 0;
    return function () {
        let now = Date.now();
        //@ts-ignore
        let context = this;
        let args = [...arguments];
        if (now - previous >= wait) {
            func();
            previous=now;   // 闭包，记录本次时间戳
        }
    }
}