import { onUnmounted } from "vue";

// 自定义指令 v-view-request 观察器创建
const vViewRequest = {
  /**
   * @param el 使用命令的元素
   * @param binding 元素上该指令绑定的一些参数
   */
  mounted(el: any, binding: any) {
    // 被观察元素配置
    const options = {
      root: binding.value?.root || null,  // 可选根元素
      rootMargin: binding.value?.rootMargin || '0px', // 可选根元素边距
      threshold: binding.value?.threshold || 0,  // 可选相交比例阈值
    }

    // 创建对应交叉观察器
    const observer=new IntersectionObserver((entries)=>{
      entries.forEach((entry)=>{
        // 元素进入屏幕
        if(entry.isIntersecting){
          binding.value?.callback?.(el);  // 调用对应回调
          observer.unobserve(el); // 停止观察
        }
      })
    },options);
    // 开始观察
    observer.observe(el);

    // 组件卸载时观察器停止观察 错误使用钩子onUnmounted必须在setup环境下
    // onUnmounted(()=>{
    //   observer.unobserve(el);
    // })
    // 保存observer，用于卸载时清理
    el._intersectionObserver=observer;
  },
  beforeUnmount(el:any){
    const observer=el._intersectionObserver;
    if(observer){
      observer.unobserve(el);
      delete el._intersectionObserver;
    }
  }
}

export default vViewRequest;