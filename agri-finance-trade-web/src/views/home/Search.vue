<template>
  <div class="search-container">
    <div class="left">
      <i style="color:#FCFCFC;font-style:normal;">
        &nbsp;热门关键词：
        <a class="tag-item" @click="handleTopicDetail('苹果')">苹果</a>|
        <a class="tag-item" @click="handleTopicDetail('新疆哈密瓜')">新疆哈密瓜</a>|
        <a class="tag-item" @click="handleTopicDetail('樱桃')">樱桃</a>|
        <a class="tag-item" @click="handleTopicDetail('西红柿')">西红柿</a>|
        <a class="tag-item" @click="handleTopicDetail('水稻')">水稻</a>|
        <a class="tag-item" @click="handleTopicDetail('玉米')">玉米</a>|
        <a class="tag-item" @click="handleTopicDetail('赣南脐橙')">赣南脐橙</a>
      </i>
    </div>
    <div class="right">
      <el-input placeholder="请输入关键词" v-model="input">
        <template #prefix>
          <i class="el-input__icon el-icon-search"></i>
        </template>
      </el-input>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const input = ref('')
const router = useRouter()

const handleTopicDetail = (val) => {
  router.push("/home/goods/")  // 你可以根据需要更改路由逻辑
}
</script>

<style lang="less" scoped>
.search-container {
  background-color: #4ab344;
  width: 100%;
  height: 80px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
  box-sizing: border-box;
}

.left {
  color: #ffffff;
  font-size: 16px;
  display: flex;
  flex-wrap: wrap; /* 让标签项换行 */
  align-items: center;
  justify-content: flex-start;
}

.tag-item {
  margin-right: 10px;
  cursor: pointer;
  color: #FCFCFC;
  font-size: 16px;
}

.right {
  width: 250px; /* 调整宽度，避免过宽 */
}

.el-input {
  width: 100%;
}
</style>
