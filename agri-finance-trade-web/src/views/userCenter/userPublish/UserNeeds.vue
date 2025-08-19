<template>
  <div style="height:100%">
    <UserMessage :ctype="type" :cdesciibe="describe"></UserMessage>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useStore } from 'vuex'
import UserMessage from "./UserMessage.vue";

// 使用 store
const store = useStore();

// 响应式数据
const type = ref("needs");
const describe = ref("需求");

// 生命周期钩子替代 created
import { onMounted } from 'vue'

onMounted(() => {
  store.commit("updatePublishActiveIndex", "2-2");
});
</script>

<style>
</style>