<script setup lang="ts">
import { onMounted, onUnmounted, Ref } from 'vue';
import { reactive, ref } from 'vue';
const trailCanvas = ref < HTMLCanvasElement | null > (null);
// 定义拖尾颜色
let colors = ["#FFD700", "#e6c200", "#c0c0c0", "#f8f8ff", "#e8e8e8"];
let ctx: CanvasRenderingContext2D | null = null;
let timeoutList: any = []; //计时器列表，存放后续计时器
let arr: any = [];  //存放小星星信息

// 封装绘制一个五角星函数
// x是圆心横坐标，y是圆心纵坐标，其实就是鼠标位置（x ，y）
// r是里面小圆半径 ，l是大圆半径
// rot是初始旋转角度
function star(x: number, y: number, r: number, l: number, rot: number) {
  if (ctx == null) return;
  ctx.beginPath();
  // 循环5次，因为5个点
  for (let i = 0; i < 5; i++) {
    //先绘制小圆上一个点
    ctx.lineTo(
      Math.cos(((18 + i * 72 - rot) * Math.PI) / 180) * r + x,
      -Math.sin(((18 + i * 72 - rot) * Math.PI) / 180) * r + y
    );
    //连线到大圆上一个点
    ctx.lineTo(
      Math.cos(((54 + i * 72 - rot) * Math.PI) / 180) * l + x,
      -Math.sin(((54 + i * 72 - rot) * Math.PI) / 180) * l + y
    );
  }
  ctx.closePath();
}

// 绘制一堆星星
function draw() {
  if (ctx == null) return;
  //循环数组
  for (let i = 0; i < arr.length; i++) {
    let temp = arr[i];
    //调用绘制一个星星函数
    star(temp.x, temp.y, temp.r, temp.r * 3, temp.rot);
    //星星颜色
    ctx.fillStyle = temp.color;
    //星星边框颜色
    ctx.strokeStyle = temp.color;
    //线宽度
    ctx.lineWidth = 0.1;
    //角有弧度
    ctx.lineJoin = "round";
    // 填充
    ctx.fill();
    // 绘制路径
    ctx.stroke();
  }
}

//更新动画
function update() {
  //循环数组
  for (let i = 0; i < arr.length; i++) {
    // x坐标+dx移动距离
    arr[i].x += arr[i].dx;
    // y坐标+dy移动距离
    arr[i].y += arr[i].dy;
    // 加上旋转角度
    arr[i].rot += arr[i].td;
    // 半径慢慢减小
    arr[i].r -= 0.015;
    // 当半径小于0时
    if (arr[i].r < 0) {
      //删除该星星
      arr.splice(i, 1);
    }
  }
}

// 添加当前位置星星数据
function addStarts(e: MouseEvent) {
  // 每移动触发一次事件给arr数组添加一个星星
  arr.push({
    // x是初始横坐标
    x: e.clientX,
    //y是初始纵坐标
    y: e.clientY,
    //r是星星里面那个小圆半径，哪来的小圆等会说
    r: Math.random() * 0.5 + 1.5,
    //运动时旋转的角度
    td: Math.random() * 4 - 2,
    // X轴移动距离
    dx: Math.random() * 2 - 1,
    // y轴移动距离
    dy: Math.random() * 1 + 1,
    // 初始的旋转角度
    rot: Math.random() * 90 + 90,
    // 颜色
    color: colors[Math.floor(Math.random() * colors.length)],
  });
}

// ------------------- 主要逻辑 ---------------------------------
function resizeCanvas() {
  if (!trailCanvas.value) return;
  trailCanvas.value.width = window.innerWidth;
  trailCanvas.value.height = window.innerHeight;
}

function handleMouseMove(e: MouseEvent) {
  // 添加星星数据
  addStarts(e);

  //设置100毫秒内效果
  for (let index = 0; index < 100; index++) {
    if (index === 0 && timeoutList.length > 0) {
      for (const timeoutName of timeoutList) {
        clearTimeout(timeoutName) // 清除之前的定时器
      }
    }
    timeoutList[index] = setTimeout(() => {
      // 元素可能卸载再挂载，此时会出现trailCanvas为null情况
      if(!trailCanvas.value) return;
      //清屏
      //@ts-ignore
      ctx.clearRect(0, 0, trailCanvas.value.width, trailCanvas.value.height);
      //绘制
      draw();
      //更新
      update();
    }, index * 10); // 整体拖尾效果完成时间越短消失越快，遮挡越少
  }
};


// 初始化画布函数
function initCanvas() {
  if (!trailCanvas.value) return;
  resizeCanvas();
  ctx=trailCanvas.value.getContext("2d");
  // 监听屏幕变化事件
  window.addEventListener("resize", resizeCanvas);
  window.addEventListener("mousemove", handleMouseMove);
}

onMounted(() => {
  initCanvas();
})

onUnmounted(() => {
  window.removeEventListener("mousemove", handleMouseMove);
  window.removeEventListener("resize", resizeCanvas);
})
</script>

<template>
  <div class="mouse_trail_container">
    <canvas ref="trailCanvas" class="trail_canvas"></canvas>
  </div>
</template>

<style lang="scss" scoped>
.mouse_trail_container {
  position: fixed;
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  z-index: 9999;
  pointer-events: none; // 永远不作为鼠标事件的target

  .trail_canvas {
    position: absolute;
    top: 0;
    left: 0;
  }
}
</style>