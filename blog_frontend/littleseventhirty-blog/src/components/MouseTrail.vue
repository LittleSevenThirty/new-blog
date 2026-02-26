<script setup lang="ts">
import { onMounted, onUnmounted, ref } from 'vue';

const trailCanvas = ref<HTMLCanvasElement | null>(null);
const colors = ["#FFD700", "#e6c200", "#c0c0c0", "#f8f8ff", "#e8e8e8"];
let ctx: CanvasRenderingContext2D | null = null;
let animationId: number | null = null;

interface Star {
  x: number;
  y: number;
  r: number; // inner radius
  l: number; // outer radius
  rot: number; // rotation
  dx: number; // x velocity
  dy: number; // y velocity
  dr: number; // rotation velocity
  color: string;
  opacity: number;
}

const stars: Star[] = [];

function drawStar(star: Star) {
  if (!ctx) return;
  ctx.beginPath();
  for (let i = 0; i < 5; i++) {
    ctx.lineTo(
      Math.cos(((18 + i * 72 - star.rot) * Math.PI) / 180) * star.r + star.x,
      -Math.sin(((18 + i * 72 - star.rot) * Math.PI) / 180) * star.r + star.y
    );
    ctx.lineTo(
      Math.cos(((54 + i * 72 - star.rot) * Math.PI) / 180) * star.l + star.x,
      -Math.sin(((54 + i * 72 - star.rot) * Math.PI) / 180) * star.l + star.y
    );
  }
  ctx.closePath();
  ctx.fillStyle = star.color;
  ctx.fill();
}

function updateStars() {
  if (!ctx || !trailCanvas.value) return;
  
  ctx.clearRect(0, 0, trailCanvas.value.width, trailCanvas.value.height);
  
  for (let i = stars.length - 1; i >= 0; i--) {
    const s = stars[i];
    s.x += s.dx;
    s.y += s.dy;
    s.rot += s.dr;
    s.r -= 0.02; // shrink inner radius
    s.l -= 0.06; // shrink outer radius
    
    if (s.r <= 0 || s.l <= 0) {
      stars.splice(i, 1);
    } else {
      drawStar(s);
    }
  }
  
  animationId = requestAnimationFrame(updateStars);
}

function addStar(e: MouseEvent) {
  const r = Math.random() * 2 + 3; // inner radius 3-5
  stars.push({
    x: e.clientX,
    y: e.clientY,
    r: r,
    l: r * 2, // outer radius
    rot: Math.random() * 360,
    dx: Math.random() * 2 - 1,
    dy: Math.random() * 2 - 1,
    dr: Math.random() * 4 - 2,
    color: colors[Math.floor(Math.random() * colors.length)],
    opacity: 1
  });
}

function handleMouseMove(e: MouseEvent) {
  addStar(e);
}

function resizeCanvas() {
  if (!trailCanvas.value) return;
  trailCanvas.value.width = window.innerWidth;
  trailCanvas.value.height = window.innerHeight;
}

onMounted(() => {
  if (trailCanvas.value) {
    ctx = trailCanvas.value.getContext('2d');
    resizeCanvas();
    window.addEventListener('resize', resizeCanvas);
    window.addEventListener('mousemove', handleMouseMove);
    updateStars();
  }
});

onUnmounted(() => {
  window.removeEventListener('resize', resizeCanvas);
  window.removeEventListener('mousemove', handleMouseMove);
  if (animationId) {
    cancelAnimationFrame(animationId);
  }
});
</script>

<template>
  <div class="mouse_trail_container">
    <canvas ref="trailCanvas" class="trail_canvas"></canvas>
  </div>
</template>

<style lang="scss" scoped>
.mouse_trail_container {
  position: fixed;
  width: 100vw;
  height: 100vh;
  top: 0;
  left: 0;
  z-index: 9999;
  pointer-events: none;

  .trail_canvas {
    display: block;
  }
}
</style>
