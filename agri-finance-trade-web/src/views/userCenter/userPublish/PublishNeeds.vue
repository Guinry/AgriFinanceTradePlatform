<template>
  <div class="publish-needs-container">
    <keep-alive>
      <publish-message :ctype="type"></publish-message>
    </keep-alive>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useStore } from 'vuex'
import PublishMessage from "../../../components/PublishMessage.vue";

// 使用 store
const store = useStore()

// 响应式数据
const type = ref("needs")

// 生命周期钩子替代 created
import { onMounted } from 'vue'

onMounted(() => {
  store.commit("updatePublishActiveIndex", "2")
})
</script>

<style lang="less" scoped>
.publish-needs-container{
  min-height: 100%;
  width: 1100px;
  margin: 0 auto;
  background: #fff;
}
</style>