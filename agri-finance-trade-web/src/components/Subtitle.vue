<template>
  <div class="subtitle-div">
    <img src="../assets/img/icon.png">
    <div class="subtitle-font">
      ——— {{ subtitle }} ———
    </div>
    <div style="color: #A9A9A9">
      {{ description }}
    </div>
  </div>
</template>

<script setup>
// 定义 props
const props = defineProps({
  subtitle: {
    type: String,
    required: true
  },
  description: {
    type: String,
    required: true
  }
})
</script>

<style scoped>

.subtitle-font{
  font-family: 微软雅黑;
  font-weight: bold;
  font-size: 28px;
  color: black;
}

.subtitle-div{
  text-align: center;
  margin: 40px 0px;
  animation: fadeInAnimation ease 4s;
  animation-iteration-count: 1; /*设置动画播放次数*/
  animation-fill-mode: forwards;
}

@keyframes fadeInAnimation {
  0% {
    opacity: 0; /*设置不透明度*/
  }
  100% {
    opacity: 1;
  }
}
</style>
