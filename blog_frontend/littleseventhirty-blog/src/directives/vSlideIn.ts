const DISTANCE = 100;
const DURATION = 500; // 500ms
const map = new WeakMap();

// 判断元素是否在视口的下方
function isBelowViewport(el: HTMLElement) {
  // 获取元素的位置属性
  const rect = el.getBoundingClientRect();
  // 判断元素上边缘是否在视口下方
  return rect.top - DISTANCE > window.innerHeight;
}

// 使用该自定义命令的元素会由下至上的渐入
const vSlideIn = {
  mounted(el: any) {
    if (isBelowViewport(el)) {
      return
    }
    // 定义动画
    const animation = el.animate(
      [{
        transform: `translateY(${DISTANCE}px)`,
        opacity: 0,
        offset: 0
      },
      {
        transform: `translateY(0px)`,
        opacity: 1,
        offset: 1
      }], {
      duration: DURATION,
      easing: 'ease-in-out',
      fill: 'forwards'
    });
    // 定义观察器
    const observer = new IntersectionObserver((entries) => {
      entries.forEach(entry => {
        if (entry.isIntersecting) {
          const animate = map.get(el);
          animate.play();
          observer.unobserve(el);
        }
      })
    })
    animation.pause();
    observer.observe(el);
    map.set(el, animation);
    el._intersectionObserver = observer;
  },
  unmounted(el: any) {
    const observer = el._intersectionObserver;
    if (observer) {
      observer.unobserve(el);
      delete el._intersectionObserver;
    }
  }
}

export default vSlideIn;