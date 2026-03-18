<script lang="ts" setup>
import { onMounted, onUnmounted, ref } from 'vue';
import Card from './Card.vue';

// 日期
const time=ref();
const date=ref();
const week=["星期天","星期一","星期二","星期三","星期四","星期五","星期六"];
let timerID:NodeJS.Timeout;

function updateTime(){
  const currentDate=new Date();
  time.value=formatting(currentDate.getHours(),2)+":"+formatting(currentDate.getMinutes(),2)+":"+formatting(currentDate.getSeconds(),2);
  date.value=formatting(currentDate.getFullYear(),4)+"-"+formatting(currentDate.getMonth()+1,2)+"-"+formatting(currentDate.getDate(),2)+" "+week[currentDate.getDay()];
}

function formatting(time:number,digit:number):String{
  let zero='';
  for(let i=0;i<digit;i++){
    zero+='0';
  }
  return (zero+time).slice(-digit); // 返回格式后的最后几个字符
}

onMounted(()=>{
  updateTime();
  timerID=setInterval(updateTime,1000);
});

onUnmounted(()=>{
  if(timerID){
    clearInterval(timerID);
  }
})

</script>

<template>
  <Card title="电子时钟" prefix-icon="time" isRotate isScale>
    <template v-slot:content>
      <div id="clock">
        <p class="date">{{ date }}</p>
        <p class="time">{{ time }}</p>
      </div>
    </template>
  </Card>
</template>

<style>
#clock {
  font-family:'Share Tech Mono',monospace;
  color: black;
  text-align: center;
  box-shadow: 0 0 20px rgba(0,0,0,0.2),0 0 20px rgba(0,0,0,0);
  border-radius: 0.6em 0 0.6em 0;

  .date {
    /* 文本间距 */
    letter-spacing:0.1em;
    margin-bottom: 5px;
    font-size:15px;
  }

  .time{
    letter-spacing: 0.05em;
    font-size:40px;
    padding: 5px 0;
  }
}
</style>