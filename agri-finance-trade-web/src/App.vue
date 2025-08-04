<template>
  <div id="app">
    <router-view v-if="isRouterAlive" />
  </div>
</template>

<script setup>
import { ref, nextTick, provide } from 'vue'

const isRouterAlive = ref(true)

const reload = () => {
  isRouterAlive.value = false
  nextTick(() => {
    isRouterAlive.value = true
  })
}

provide('reload', reload)
</script>

<style lang="less">
#app{
  height: 100%;
  background: #f9f9f9;
}
</style>