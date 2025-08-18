<template>
  <div class="questions-container">
    <Subtitle subtitle="常见问题" description="大家都在问的问题哦~"  style="margin-top:10px;"/>
    <div class="questions">
      <el-row :gutter="30">
        <el-col :span="8"  >
          <el-card shadow="always" class="card" @click="handleTopicDetail('苹果果树种植方法')"> 苹果果树种植方法?</el-card>
        </el-col>
        <el-col :span="8">
          <el-card shadow="always" class="card" @click="handleTopicDetail('苹果果树种植方法')"> 新疆哈密瓜甜度控制? </el-card>
        </el-col>
        <el-col :span="8">
          <el-card shadow="always" class="card" @click="handleTopicDetail('苹果果树种植方法')"> 玉米苗灌溉? </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useStore } from 'vuex'
import Subtitle from "../../components/Subtitle.vue";

// 定义组件名称
defineOptions({
  name: "Questions"
})

// 使用Composition API替代this
const router = useRouter()
const store = useStore()
const searchValue = ref('')

// 将methods转换为普通函数
const detailsClick = (item) => {
  store.commit("updateOrderId", item);
  router.push(`/home/details?id=${item.id}`).catch((err) => err);
}

const pageClick = (val) => {
  emit('pageClick',val)
}

const handleSearch = () => {
  emit('handleSearch',searchValue.value)
}

const handleDetail = (item) => {
  router.push(`/home/guide/${item.id}`)
}

const handleTopicDetail = (val) => {
  searchValue.value = val
  handleSearch()
}

</script>

<style scoped>
.questions-container{
  text-align: center;
  padding: 20px;
}

.questions{
  width: 100%;
  display: flex;
  justify-content: center;
  margin-top: 30px;
}

.card{
  border-radius: 15px;
  width: 100%;
  height: 200px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: bold;
  font-size: 20px;
  font-family: 'PingFang SC', 'Microsoft YaHei', sans-serif;
  cursor: pointer;
  transition: all 0.3s ease;
  color: #333;
  position: relative;
  overflow: hidden;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  background: linear-gradient(135deg, #f5f7fa 0%, #e4edf5 100%);
}

.card:before {
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(3, 93, 28, 0.1);
  opacity: 0;
  transition: opacity 0.3s ease;
}

.card:hover:before {
  opacity: 1;
}

.card:hover {
  transform: translateY(-8px);
  box-shadow: 0 12px 20px rgba(3, 93, 28, 0.2);
  color: #035D1C;
}

:deep(.el-card){
  border-radius: 15px;
  background-color: #fff;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
  border: 1px solid #eaeaea;
  height: 100%;
  transition: all 0.3s ease;
}

:deep(.el-card__body) {
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 20px;
}

:deep(.el-card:hover){
  border-radius: 15px;
  box-shadow: 0 12px 24px rgba(3, 93, 28, 0.15);
  transform: translateY(-5px);
}

@media (max-width: 768px) {
  .questions {
    padding: 0 15px;
  }
  
  :deep(.el-col) {
    margin-bottom: 20px;
  }
}
</style>