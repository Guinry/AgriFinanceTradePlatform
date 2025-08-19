<template>
  <NavigationBar />
  <div class="guide-detail-content">
    <div class="detail-item">
      <div class="item-text">问题：</div>
      <div class="detail-content">{{ detailObj.question }}</div>
    </div>
    <div class="detail-item">
      <div class="item-text">回答：</div>
      <div class="detail-content">{{ detailObj.answer }}</div>
    </div>
  </div>
  <Footer />
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { useStore } from 'vuex'
import { questionDetail } from "../../api/order";
import NavigationBar from "../../components/NavigationBar.vue";
import Footer from "../../components/Footer.vue";

const route = useRoute()
const store = useStore()

const detailObj = ref({})

const getData = () => {
  questionDetail({id: route.params.id}).then(res => {
    detailObj.value = res.data
  }).catch((error) => {
    console.error('API 调用失败:', error);
  });
}

onMounted(() => {
  getData()
})
</script>

<style lang="less" scoped>
.guide-detail-content {
  max-width: 1200px;
  margin: 30px auto;
  padding: 30px;
  background-color: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.detail-item {
  display: flex;
  align-items: flex-start;
  margin-bottom: 20px;
  padding: 15px 20px;
  background-color: #fff;
  border-left: 5px solid #3bafda; /* 使用醒目的颜色 */
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
  transition: box-shadow 0.3s ease;
}

.detail-item:hover {
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* Hover效果增加阴影 */
}

.item-text {
  font-size: 18px;
  font-weight: 600;
  color: #3bafda;
  width: 100px;
  flex-shrink: 0;
}

.detail-content {
  font-size: 16px;
  color: #333;
  line-height: 1.6;
  word-break: break-word;
  flex-grow: 1;
  white-space: pre-line; /* 保证换行 */
}

@media screen and (max-width: 1200px) {
  .guide-detail-content {
    padding: 20px;
  }

  .detail-item {
    flex-direction: column;
  }
}

</style>
