// 元素懒加载自定义指令
import { loadingImg } from "../utils/base64_img/loading_img";

const lazyBinding = (el: any, binding: any) => {
  if (el.dataset.lazyLoaded) return;  // dataset读取元素上自定义属性data-*,lazyLoaded对应data-lazyLoaded
  const placehold = loadingImg;
  const { nolazy } = binding.value || {};
  // 如果设置了不懒加载，则直接加载图片，并标记
  if (nolazy) {
    el.src = el.dataset.src || loadingImg;
    el.dataset.lazyLoaded = "true";
    return;
  }

  // 设置占位图
  el.src = loadingImg;

  // 设置观察期
  const observer = new IntersectionObserver((entries) => {
    // entry.intersectionRatio说明被观察元素已经出现在观察区内
    if (entries.find(entry => entry.intersectionRatio > 0)) {
      el.src = el.dataset.src || placehold;
      observer.unobserve(el);
      // 标记
      el.dataset.lazyLoaded = "true";
    }
  });

  observer.observe(el);
}

export default {
  beforeMounted(el:HTMLElement,binding:any){
    lazyBinding(el,binding);
  },
  updated(el:HTMLElement,binding:any){
    lazyBinding(el,binding);
  },
};